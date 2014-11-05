/**
 * Copyright 2014 tgrape Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ph.fingra.hadoop.mapred.parts.prerole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.ConstantVars.DataUsable;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.HfsPathInfo;
import ph.fingra.hadoop.common.ConstantVars.LogParserType;
import ph.fingra.hadoop.common.ConstantVars.LogValidation;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.DateTimeUtil;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.mapred.common.CopyWithinHdfsFile;
import ph.fingra.hadoop.mapred.common.HdfsFileUtil;
import ph.fingra.hadoop.mapred.parse.AppNewuserDbParser;
import ph.fingra.hadoop.mapred.parse.CommonLogParser;
import ph.fingra.hadoop.mapred.parse.ComponentLogParser;
import ph.fingra.hadoop.mapred.parse.domain.AppNewuserDb;
import ph.fingra.hadoop.mapred.parts.prerole.domain.AppNewuserKey;

public class AppNewuserMerge extends Configured implements Tool {
    
    @Override
    public int run(String[] args) throws Exception {
        
        String opt_mode = "";
        String opt_target = "";
        int opt_numreduce = 0;
        
        FingraphConfig fingraphConfig = new FingraphConfig();
        TargetDate targetDate = null;
        TargetDate cutDate = null;
        
        Configuration conf = getConf();
        Path[] inputPaths = null;
        Path outputPath = null;
        Path dbPath = null;
        String dbfilename = null;
        
        // get -D optional value
        opt_mode = conf.get(ConstantVars.DOPTION_RUNMODE, "");
        opt_target = conf.get(ConstantVars.DOPTION_TARGETDATE, "");
        opt_numreduce = conf.getInt(ConstantVars.DOPTION_NUMREDUCE, 0);
        
        // runmode & targetdate check
        if (ArgsOptionUtil.checkRunmode(opt_mode)==false) {
            throw new Exception("option value of -Drunmode is not correct");
        }
        if (opt_target.isEmpty()==false) {
            if (ArgsOptionUtil.checkTargetDateByMode(opt_mode, opt_target)==false) {
                throw new Exception("option value of -Dtargetdate is not correct");
            }
        }
        else {
            opt_target = ArgsOptionUtil.getDefaultTargetDateByMode(opt_mode);
        }
        
        // get TargetDate info from opt_target
        targetDate = ArgsOptionUtil.getTargetDate(opt_mode, opt_target);
        
        // get the most out of date stored in app_newuser_db
        int period = 0 - fingraphConfig.getSetting().getHfs_database_appnewuser_keep_month();
        String cutdate = DateTimeUtil.addMonths(
                targetDate.getYear()+"-"+targetDate.getMonth()+"-"+targetDate.getDay(),
                period, "yyyy-MM-dd");
        cutDate = ArgsOptionUtil.getTargetDate(ConstantVars.RUNMODE_DAY, cutdate);
        
        WorkLogger.log(AppNewuserMerge.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate()
                + " , [reducer count] " + opt_numreduce);
        
        // get this job's input path - transform log file
        inputPaths = HdfsFileUtil.getTransformInputPaths(fingraphConfig, opt_mode,
                targetDate.getYear(), targetDate.getMonth(), targetDate.getDay(),
                targetDate.getHour(), targetDate.getWeek());
        if (inputPaths==null) {
            WorkLogger.log("There is no input data.");
            return 0;
        }
        
        // get this job's output path
        HfsPathInfo hfsPath = new HfsPathInfo(fingraphConfig, opt_mode);
        outputPath = new Path(hfsPath.getAppnewusermerge());
        if (HdfsFileUtil.isExistFile(hfsPath.getApp_newuser_db()) == true) {
            dbPath = new Path(hfsPath.getApp_newuser_db());
        }
        dbfilename = hfsPath.getApp_newuser_db();
        
        // delete previous output path if is exist
        FileSystem fs = FileSystem.get(conf);
        List<Path> deletePaths = new ArrayList<Path>();
        deletePaths.add(outputPath);
        for (Path deletePath : deletePaths) {
            fs.delete(deletePath, true);
        }
        
        Job job = createJob(conf, inputPaths, outputPath, opt_numreduce,
                fingraphConfig, dbPath, dbfilename, cutDate);
        
        int status = job.waitForCompletion(true) ? 0 : 1;
        
        // copy to hdfs database paths & backup
        HdfsFileUtil.deleteNBackupFile(hfsPath.getDATABASE_root(),
                hfsPath.getApp_newuser_db(),
                fingraphConfig.getSetting().getHfs_database_appnewuser_backup_count(),
                targetDate.getYear()+targetDate.getMonth()+targetDate.getDay(),
                ConstantVars.APP_NEWUSER_DB_FNAME);
        CopyWithinHdfsFile copier = new CopyWithinHdfsFile();
        copier.dirToFile(outputPath.toString(), hfsPath.getApp_newuser_db());
        
        return status;
    }
    
    public Job createJob(Configuration conf, Path[] inputpaths, Path outputpath,
            int numreduce, FingraphConfig finconfig, Path dbpath, String dbfilename,
            TargetDate cutdate)
            throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        conf.set("dbfilename", dbfilename);
        conf.set("cutyear", cutdate.getYear());
        conf.set("cutmonth", cutdate.getMonth());
        conf.set("cutday", cutdate.getDay());
        
        Job job = new Job(conf);
        String jobName = "merge/appnewusermerge job";
        job.setJobName(jobName);
        
        job.setJarByClass(AppNewuserMerge.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        if (dbpath != null) {
            FileInputFormat.addInputPath(job, dbpath);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(AppNewuserMapper.class);
        job.setCombinerClass(AppNewuserCombiner.class);
        job.setReducerClass(AppNewuserReducer.class);
        
        job.setMapOutputKeyClass(AppNewuserKey.class);
        job.setMapOutputValueClass(AppNewuserDb.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        job.setPartitionerClass(AppNewuserPartitioner.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class AppNewuserMapper
        extends Mapper<LongWritable, Text, AppNewuserKey, AppNewuserDb> {
        
        private boolean verbose = false;
        private boolean counter = false;
        private String dbfilename = "";
        private String opt_cut_date = "";
        private String in_file_name = "";
        private String in_file_year = "";
        private String in_file_month = "";
        private String in_file_day = "";
        private String in_file_week = "";
        
        private AppNewuserDbParser dbparser = new AppNewuserDbParser();
        private CommonLogParser commonparser = new CommonLogParser();
        private ComponentLogParser compoparser = new ComponentLogParser();
        
        private AppNewuserKey out_key = new AppNewuserKey();
        private AppNewuserDb out_val = new AppNewuserDb();
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            verbose = context.getConfiguration().getBoolean("verbose", false);
            counter = context.getConfiguration().getBoolean("counter", false);
            dbfilename = context.getConfiguration().get("dbfilename");
            if (dbfilename.lastIndexOf("/") > -1) {
                dbfilename = dbfilename.substring(dbfilename.lastIndexOf("/")+1);
            }
            String cutyear = context.getConfiguration().get("cutyear", "");
            String cutmonth = context.getConfiguration().get("cutmonth", "");
            String cutday = context.getConfiguration().get("cutday", "");
            if (cutyear.isEmpty()==false && cutmonth.isEmpty()==false
                    && cutday.isEmpty()==false) {
                opt_cut_date = cutyear+cutmonth+cutday;
            }
            
            in_file_name = ((FileSplit)context.getInputSplit()).getPath().getName();
            String file_date = FormatUtil.getDateFromLogfile(in_file_name);
            if (file_date.isEmpty() == false) {
                in_file_year = file_date.substring(0, 4);
                in_file_month = file_date.substring(5, 7);
                in_file_day = file_date.substring(8);
                int week = DateTimeUtil.getWeekOfYearByDay(in_file_year, in_file_month, in_file_day);
                in_file_week = (week < 10 ? "0"+week : String.valueOf(week));
            }
        }
        
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            
            if (in_file_name.equals(dbfilename)) {
                // db file
                
                dbparser.parse(value);
                if (dbparser.hasError() == false) {
                    
                    /*
                     * ignore newuser db data when it has exceeded the number of
                     * months of storage in fingraphoss-config.xml
                     */
                    if (opt_cut_date.isEmpty()==false) {
                        
                        String dbdate = dbparser.getYear()+dbparser.getMonth()+dbparser.getDay();
                        if (Integer.parseInt(opt_cut_date) > Integer.parseInt(dbdate)) {
                            if (verbose)
                                System.err.println("Ignoring expired input: " + value);
                            if (counter)
                                context.getCounter(DataUsable.EXPIRED).increment(1);
                            return;
                        }
                    }
                    
                    out_key.set(dbparser.getAppkey(), dbparser.getToken());
                    
                    out_val.set(dbparser.getAppkey(), dbparser.getToken(),
                            dbparser.getYear(), dbparser.getMonth(), dbparser.getDay(),
                            dbparser.getWeek(), dbparser.getUtctime(), dbparser.getLocaltime(),
                            dbparser.getCountry(), dbparser.getLanguage(), dbparser.getDevice(),
                            dbparser.getOsversion(), dbparser.getResolution(), dbparser.getAppversion());
                    
                    context.write(out_key, out_val);
                }
                else {
                    if (verbose)
                        System.err.println("Ignoring corrupt input: " + value);
                }
                
                if (counter)
                    context.getCounter(dbparser.getErrorLevel()).increment(1);
            }
            else {
                // log file
                
                // logtype check
                LogParserType logtype = FormatUtil.getLogParserType(value.toString());
                
                if (logtype.equals(LogParserType.CommonLog)) {
                    
                    // CommonLog : STARTSESS/PAGEVIEW/ENDSESS
                    commonparser.parse(value);
                    if (commonparser.hasError() == false) {
                        
                        out_key.set(commonparser.getAppkey(), commonparser.getToken());
                        
                        out_val.set(commonparser.getAppkey(), commonparser.getToken(),
                                in_file_year, in_file_month, in_file_day, in_file_week,
                                commonparser.getUtctime(), commonparser.getLocaltime(), commonparser.getCountry(),
                                commonparser.getLanguage(), commonparser.getDevice(), commonparser.getOsversion(),
                                commonparser.getResolution(), commonparser.getAppversion());
                        
                        context.write(out_key, out_val);
                    }
                    else {
                        if (verbose)
                            System.err.println("Ignoring corrupt input: " + value);
                    }
                    
                    if (counter)
                        context.getCounter(commonparser.getErrorLevel()).increment(1);
                }
                else if (logtype.equals(LogParserType.ComponentLog)) {
                    
                    // ComponentLog : COMPONENT
                    compoparser.parse(value);
                    if (compoparser.hasError() == false) {
                        
                        out_key.set(compoparser.getAppkey(), compoparser.getToken());
                        
                        out_val.set(compoparser.getAppkey(), compoparser.getToken(),
                                in_file_year, in_file_month, in_file_day, in_file_week,
                                compoparser.getUtctime(), compoparser.getLocaltime(), compoparser.getCountry(),
                                compoparser.getLanguage(), compoparser.getDevice(), compoparser.getOsversion(),
                                compoparser.getResolution(), compoparser.getAppversion());
                        
                        context.write(out_key, out_val);
                    }
                    else {
                        if (verbose)
                            System.err.println("Ignoring corrupt input: " + value);
                    }
                    
                    if (counter)
                        context.getCounter(compoparser.getErrorLevel()).increment(1);
                }
                else {
                    if (verbose)
                        System.err.println("Ignoring corrupt input: " + value);
                    if (counter)
                        context.getCounter(LogValidation.MALFORMED).increment(1);
                }
            }
        }
    }
    
    static class AppNewuserCombiner
        extends Reducer<AppNewuserKey, AppNewuserDb, AppNewuserKey, AppNewuserDb> {
        
        @Override
        protected void reduce(AppNewuserKey key, Iterable<AppNewuserDb> values,
                Context context) throws IOException, InterruptedException {
            
            AppNewuserDb earliest_val = new AppNewuserDb();
            String earliest_datetime = "";
            String cur_datetime = "";
            
            boolean isfirst = true;
            for (AppNewuserDb cur_val : values) {
                
                if (isfirst) {
                    earliest_val.copy(cur_val);
                    earliest_datetime = cur_val.utctime;
                    isfirst = false;
                }
                else {
                    cur_datetime = cur_val.utctime;
                    if (earliest_datetime.compareTo(cur_datetime) > 0) {
                        earliest_val.copy(cur_val);
                        earliest_datetime = cur_datetime;
                    }
                }
            }
            
            context.write(key, earliest_val);
        }
    }
    
    static class AppNewuserReducer
        extends Reducer<AppNewuserKey, AppNewuserDb, Text, Text> {
        
        private Text out_key = new Text();
        private Text out_val = new Text();
        
        @Override
        protected void reduce(AppNewuserKey key, Iterable<AppNewuserDb> values,
                Context context) throws IOException, InterruptedException {
            
            AppNewuserDb earliest_val = new AppNewuserDb();
            String earliest_datetime = "";
            String cur_datetime = "";
            
            boolean isfirst = true;
            for (AppNewuserDb cur_val : values) {
                
                if (isfirst) {
                    earliest_val.copy(cur_val);
                    earliest_datetime = cur_val.utctime;
                    isfirst = false;
                }
                else {
                    cur_datetime = cur_val.utctime;
                    if (earliest_datetime.compareTo(cur_datetime) > 0) {
                        earliest_val.copy(cur_val);
                        earliest_datetime = cur_datetime;
                    }
                }
            }
            
            out_key.set(key.appkey + ConstantVars.RESULT_FIELD_SEPERATER
                    + key.token);
            
            out_val.set(earliest_val.year + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.month + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.day + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.week + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.utctime + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.localtime + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.country + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.language + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.device + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.osversion + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.resolution + ConstantVars.RESULT_FIELD_SEPERATER
                    + earliest_val.appversion);
            
            context.write(out_key, out_val);
        }
    }
    
    private static class AppNewuserPartitioner
        extends Partitioner<AppNewuserKey, AppNewuserDb> {
        @Override
        public int getPartition(AppNewuserKey key, AppNewuserDb value,
                int numPartitions) {
            return Math.abs(key.hashCode() * 127) % numPartitions;
        }
    }
    
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        long start_time=0, end_time=0;
        int exitCode = 0;
        
        start_time = System.currentTimeMillis();
        
        WorkLogger.log(AppNewuserMerge.class.getSimpleName()
                + " : Start mapreduce job");
        
        try {
            exitCode = ToolRunner.run(new AppNewuserMerge(), args);
            
            WorkLogger.log(AppNewuserMerge.class.getSimpleName()
                    + " : End mapreduce job");
        }
        catch (Exception e) {
            ErrorLogger.log(AppNewuserMerge.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(AppNewuserMerge.class.getSimpleName()
                    + " : Failed mapreduce job");
        }
        
        end_time = System.currentTimeMillis();
        
        try {
            FingraphConfig config = new FingraphConfig();
            if (config.getDebug().isDebug_show_spenttime())
                WorkLogger.log("DEBUG - run times : "
                        + FormatUtil.getDurationFromMillitimes(end_time - start_time));
        }
        catch (IOException ignore) {}
        
        System.exit(exitCode);
    }
}
