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

package ph.fingra.hadoop.mapred.parts.performance;

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
import ph.fingra.hadoop.common.ConstantVars.LogParserType;
import ph.fingra.hadoop.common.ConstantVars.LogValidation;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.HfsPathInfo;
import ph.fingra.hadoop.common.LfsPathInfo;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.ConvertTimeZone;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.mapred.common.CopyToLocalFile;
import ph.fingra.hadoop.mapred.common.HdfsFileUtil;
import ph.fingra.hadoop.mapred.parse.AppNewuserDbParser;
import ph.fingra.hadoop.mapred.parse.AppNewuserHourDbParser;
import ph.fingra.hadoop.mapred.parse.CommonLogParser;
import ph.fingra.hadoop.mapred.parse.ComponentLogParser;
import ph.fingra.hadoop.mapred.parse.domain.AppNewuserHourDb;
import ph.fingra.hadoop.mapred.parts.performance.domain.AppNewuserHourKey;

public class NewuserStatistic extends Configured implements Tool {
    
    @Override
    public int run(String[] args) throws Exception {
        
        String opt_mode = "";
        String opt_target = "";
        int opt_numreduce = 0;
        
        FingraphConfig fingraphConfig = new FingraphConfig();
        TargetDate targetDate = null;
        
        Configuration conf = getConf();
        Path[] inputPaths = null;
        Path outputPath_intermediate = null;
        Path outputPath_final = null;
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
        
        WorkLogger.log(NewuserStatistic.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate()
                + " , [reducer count] " + opt_numreduce);
        
        // get this job's input path
        // hourly > origin log file + app newuser db file
        // daily/weekly/monthly > app newuser db file
        inputPaths = HdfsFileUtil.getAppNewuserInputPaths(fingraphConfig, opt_mode,
                targetDate.getYear(), targetDate.getMonth(), targetDate.getDay());
        if (inputPaths==null) {
            WorkLogger.log("There is no input data.");
            return 0;
        }
        
        // get this job's final output path
        HfsPathInfo hfsPath = new HfsPathInfo(fingraphConfig, opt_mode);
        outputPath_final = new Path(hfsPath.getNewuser());
        if (HdfsFileUtil.isExistFile(hfsPath.getApp_newuser_db()) == true) {
            dbPath = new Path(hfsPath.getApp_newuser_db());
        }
        dbfilename = hfsPath.getApp_newuser_db();
        
        int status = 0;
        if (opt_mode.equals(ConstantVars.RUNMODE_HOUR)) {
            
            // get this job's intermediate output path
            outputPath_intermediate = new Path(hfsPath.getAppnewuserhourlymerge());
            
            // delete previous output path if is exist
            FileSystem fs = FileSystem.get(conf);
            List<Path> deletePaths = new ArrayList<Path>();
            deletePaths.add(outputPath_intermediate);
            deletePaths.add(outputPath_final);
            for (Path deletePath : deletePaths) {
                fs.delete(deletePath, true);
            }
            
            Job jobIntermediate = createHourJobIntermediate(conf, inputPaths,
                    outputPath_intermediate, opt_numreduce, fingraphConfig,
                    dbPath, dbfilename, targetDate);
            
            status = jobIntermediate.waitForCompletion(true) ? 0 : 1;
            
            Job jobFinal = createHourJobFinal(conf, outputPath_intermediate,
                    outputPath_final, opt_numreduce, fingraphConfig, targetDate);
            
            status = jobFinal.waitForCompletion(true) ? 0 : 1;
        }
        else {
            
            // delete previous output path if is exist
            FileSystem fs = FileSystem.get(conf);
            List<Path> deletePaths = new ArrayList<Path>();
            deletePaths.add(outputPath_final);
            for (Path deletePath : deletePaths) {
                fs.delete(deletePath, true);
            }
            
            Job job = createJob(conf, inputPaths, outputPath_final, opt_numreduce,
                    fingraphConfig, targetDate);
            
            status = job.waitForCompletion(true) ? 0 : 1;
        }
        
        // copy to local final result paths
        LfsPathInfo lfsPath = new LfsPathInfo(fingraphConfig, targetDate);
        CopyToLocalFile copier = new CopyToLocalFile();
        copier.dirToFile(outputPath_final.toString(), lfsPath.getNewuser());
        
        return status;
    }
    
    public Job createJob(Configuration conf, Path[] inputpaths, Path outputpath,
            int numreduce, FingraphConfig finconfig, TargetDate targetdate)
            throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        conf.set("runmode", targetdate.getRunmode());
        conf.set("year", targetdate.getYear());
        conf.set("month", targetdate.getMonth());
        conf.set("day", targetdate.getDay());
        conf.set("week", targetdate.getWeek_str());
        
        Job job = new Job(conf);
        String jobName = "perform/newuser job";
        job.setJobName(jobName);
        
        job.setJarByClass(NewuserStatistic.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(NewuserMapper.class);
        job.setCombinerClass(NewuserReducer.class);
        job.setReducerClass(NewuserReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setPartitionerClass(NewuserPartitioner.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class NewuserMapper
        extends Mapper<LongWritable, Text, Text, LongWritable> {
        
        private boolean verbose = false;
        private boolean counter = false;
        private String target_runmode = "";
        private String target_year = "";
        private String target_month = "";
        private String target_day = "";
        private String target_week = "";
        
        private AppNewuserDbParser dbparser = new AppNewuserDbParser();
        
        private Text out_key = new Text();
        private LongWritable out_val = new LongWritable(1);
        private DataUsable usable = DataUsable.USE;
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            verbose = context.getConfiguration().getBoolean("verbose", false);
            counter = context.getConfiguration().getBoolean("counter", false);
            target_runmode = context.getConfiguration().get("runmode");
            target_year = context.getConfiguration().get("year");
            target_month = context.getConfiguration().get("month");
            target_day = context.getConfiguration().get("day");
            target_week = context.getConfiguration().get("week");
        }
        
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            
            dbparser.parse(value);
            if (dbparser.hasError() == false) {
                
                if (target_runmode.equals(ConstantVars.RUNMODE_DAY)) {
                    
                    if (dbparser.getYear().equals(target_year)
                            && dbparser.getMonth().equals(target_month)
                            && dbparser.getDay().equals(target_day)) {
                        
                        out_key.set(dbparser.getAppkey());
                        
                        context.write(out_key, out_val);
                        
                        usable = DataUsable.USE;
                    }
                    else
                        usable = DataUsable.USELESS;
                    
                    if (counter)
                        context.getCounter(usable).increment(1);
                }
                else if (target_runmode.equals(ConstantVars.RUNMODE_WEEK)) {
                    
                    if (dbparser.getYear().equals(target_year)
                            && dbparser.getWeek().equals(target_week)) {
                        
                        out_key.set(dbparser.getAppkey());
                        
                        context.write(out_key, out_val);
                        
                        usable = DataUsable.USE;
                    }
                    else
                        usable = DataUsable.USELESS;
                    
                    if (counter)
                        context.getCounter(usable).increment(1);
                }
                else if (target_runmode.equals(ConstantVars.RUNMODE_MONTH)) {
                    
                    if (dbparser.getYear().equals(target_year)
                            && dbparser.getMonth().equals(target_month)) {
                        
                        out_key.set(dbparser.getAppkey());
                        
                        context.write(out_key, out_val);
                        
                        usable = DataUsable.USE;
                    }
                    else
                        usable = DataUsable.USELESS;
                    
                    if (counter)
                        context.getCounter(usable).increment(1);
                }
            }
            else {
                if (verbose)
                    System.err.println("Ignoring corrupt input: " + value);
            }
            
            if (counter)
                context.getCounter(dbparser.getErrorLevel()).increment(1);
        }
    }
    
    static class NewuserReducer
        extends Reducer<Text, LongWritable, Text, LongWritable> {
        
        private Text out_key = new Text();
        private LongWritable out_val = new LongWritable(0);
        
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values,
                Context context) throws IOException, InterruptedException {
            
            long sum = 0;
            for (LongWritable cur_val : values) {
                sum += cur_val.get();
            }
            
            out_key.set(key);
            out_val.set(sum);
            
            context.write(out_key, out_val);
        }
    }
    
    private static class NewuserPartitioner
        extends Partitioner<Text, LongWritable> {
        @Override
        public int getPartition(Text key, LongWritable value,
                int numPartitions) {
            return Math.abs(key.hashCode() * 127) % numPartitions;
        }
    }
    
    public Job createHourJobIntermediate(Configuration conf, Path[] inputpaths,
            Path outputpath, int numreduce, FingraphConfig finconfig, Path dbpath,
            String dbfilename, TargetDate targetdate) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        conf.set("dbfilename", dbfilename);
        conf.set("year", targetdate.getYear());
        conf.set("month", targetdate.getMonth());
        conf.set("day", targetdate.getDay());
        
        Job job = new Job(conf);
        String jobName = "merge/appnewuserhourlymerge hour job";
        job.setJobName(jobName);
        
        job.setJarByClass(NewuserStatistic.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(AppNewuserHourMapper.class);
        job.setCombinerClass(AppNewuserHourCombiner.class);
        job.setReducerClass(AppNewuserHourReducer.class);
        
        job.setMapOutputKeyClass(AppNewuserHourKey.class);
        job.setMapOutputValueClass(AppNewuserHourDb.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        job.setPartitionerClass(AppNewuserHourPartitioner.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    public Job createHourJobFinal(Configuration conf, Path inputpath,
            Path outputpath, int numreduce, FingraphConfig finconfig,
            TargetDate targetdate) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        conf.set("year", targetdate.getYear());
        conf.set("month", targetdate.getMonth());
        conf.set("day", targetdate.getDay());
        conf.set("hour", targetdate.getHour());
        
        Job job = new Job(conf);
        String jobName = "perform/newuser hour job";
        job.setJobName(jobName);
        
        job.setJarByClass(NewuserStatistic.class);
        
        FileInputFormat.addInputPath(job, inputpath);
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(NewuserHourMapper.class);
        job.setCombinerClass(NewuserHourReducer.class);
        job.setReducerClass(NewuserHourReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setPartitionerClass(NewuserHourPartitioner.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class AppNewuserHourMapper
        extends Mapper<LongWritable, Text, AppNewuserHourKey, AppNewuserHourDb> {
        
        private boolean verbose = false;
        private boolean counter = false;
        private String dbfilename = "";
        private String in_file_name = "";
        private String in_file_year = "";
        private String in_file_month = "";
        private String in_file_day = "";
        
        private int LTIME_LENGTH = ConstantVars.LOG_DATE_FORMAT.length();
        private int HOUR_INDEX = 8;
        private int HOUR_LENGTH = 2;
        
        private AppNewuserDbParser dbparser = new AppNewuserDbParser();
        private CommonLogParser commonparser = new CommonLogParser();
        private ComponentLogParser compoparser = new ComponentLogParser();
        
        private ConvertTimeZone timeZone = new ConvertTimeZone();
        
        private AppNewuserHourKey out_key = new AppNewuserHourKey();
        private AppNewuserHourDb out_val = new AppNewuserHourDb();
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            verbose = context.getConfiguration().getBoolean("verbose", false);
            counter = context.getConfiguration().getBoolean("counter", false);
            dbfilename = context.getConfiguration().get("dbfilename");
            if (dbfilename.lastIndexOf("/") > -1) {
                dbfilename = dbfilename.substring(dbfilename.lastIndexOf("/")+1);
            }
            
            in_file_name = ((FileSplit)context.getInputSplit()).getPath().getName();
            String file_date = FormatUtil.getDateFromLogfile(in_file_name);
            if (file_date.isEmpty() == false) {
                in_file_year = file_date.substring(0, 4);
                in_file_month = file_date.substring(5, 7);
                in_file_day = file_date.substring(8);
            }
        }
        
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            
            if (in_file_name.equals(dbfilename)) {
                // db file
                
                dbparser.parse(value);
                if (dbparser.hasError() == false) {
                    
                    // hour : hour from converted utctime to server operation time
                    boolean rise_error = false;
                    String utime2ltime = "";
                    String utime2ltime_hour = "";
                    try {
                        utime2ltime = timeZone.convertUtcToLocal(dbparser.getUtctime());
                    }
                    catch (Exception e) {
                        rise_error = true;
                    }
                    if (utime2ltime.length()==LTIME_LENGTH) {
                        utime2ltime_hour = utime2ltime.substring(HOUR_INDEX, HOUR_INDEX+HOUR_LENGTH);
                    }
                    else {
                        rise_error = true;
                    }
                    
                    if (rise_error==false) {
                        out_key.set(dbparser.getAppkey(), dbparser.getToken());
                        
                        out_val.set(dbparser.getAppkey(), dbparser.getToken(),
                                dbparser.getYear(), dbparser.getMonth(), dbparser.getDay(),
                                utime2ltime_hour);
                        
                        context.write(out_key, out_val);
                    }
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
                        
                        // hour : hour from converted utctime to server operation time
                        boolean rise_error = false;
                        String utime2ltime = "";
                        String utime2ltime_hour = "";
                        try {
                            utime2ltime = timeZone.convertUtcToLocal(commonparser.getUtctime());
                        }
                        catch (Exception e) {
                            rise_error = true;
                        }
                        if (utime2ltime.length()==LTIME_LENGTH) {
                            utime2ltime_hour = utime2ltime.substring(HOUR_INDEX, HOUR_INDEX+HOUR_LENGTH);
                        }
                        else {
                            rise_error = true;
                        }
                        
                        if (rise_error==false) {
                            out_key.set(commonparser.getAppkey(), commonparser.getToken());
                            
                            out_val.set(commonparser.getAppkey(), commonparser.getToken(),
                                    in_file_year, in_file_month, in_file_day,
                                    utime2ltime_hour);
                            
                            context.write(out_key, out_val);
                        }
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
                        
                        // hour : hour from converted utctime to server operation time
                        boolean rise_error = false;
                        String utime2ltime = "";
                        String utime2ltime_hour = "";
                        try {
                            utime2ltime = timeZone.convertUtcToLocal(compoparser.getUtctime());
                        }
                        catch (Exception e) {
                            rise_error = true;
                        }
                        if (utime2ltime.length()==LTIME_LENGTH) {
                            utime2ltime_hour = utime2ltime.substring(HOUR_INDEX, HOUR_INDEX+HOUR_LENGTH);
                        }
                        else {
                            rise_error = true;
                        }
                        
                        if (rise_error==false) {
                            out_key.set(compoparser.getAppkey(), compoparser.getToken());
                            
                            out_val.set(compoparser.getAppkey(), compoparser.getToken(),
                                    in_file_year, in_file_month, in_file_day,
                                    utime2ltime_hour);
                            
                            context.write(out_key, out_val);
                        }
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
    
    static class AppNewuserHourCombiner
        extends Reducer<AppNewuserHourKey, AppNewuserHourDb, AppNewuserHourKey, AppNewuserHourDb> {
        
        @Override
        protected void reduce(AppNewuserHourKey key, Iterable<AppNewuserHourDb> values,
                Context context) throws IOException, InterruptedException {
            
            AppNewuserHourDb earliest_val = new AppNewuserHourDb();
            String earliest_datetime = "";
            String cur_datetime = "";
            
            boolean isfirst = true;
            for (AppNewuserHourDb cur_val : values) {
                
                if (isfirst) {
                    earliest_val.copy(cur_val);
                    earliest_datetime = cur_val.year + cur_val.month + cur_val.day + cur_val.hour;
                    isfirst = false;
                }
                else {
                    cur_datetime = cur_val.year + cur_val.month + cur_val.day + cur_val.hour;
                    if (earliest_datetime.compareTo(cur_datetime) > 0) {
                        earliest_val.copy(cur_val);
                        earliest_datetime = cur_datetime;
                    }
                }
            }
            
            context.write(key, earliest_val);
        }
    }
    
    static class AppNewuserHourReducer
        extends Reducer<AppNewuserHourKey, AppNewuserHourDb, Text, Text> {
        
        private String target_year = "";
        private String target_month = "";
        private String target_day = "";
        private String anal_date;
        
        private Text out_key = new Text();
        private Text out_val = new Text();
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            target_year = context.getConfiguration().get("year");
            target_month = context.getConfiguration().get("month");
            target_day = context.getConfiguration().get("day");
            anal_date = target_year + target_month + target_day;
        }
        
        @Override
        protected void reduce(AppNewuserHourKey key, Iterable<AppNewuserHourDb> values,
                Context context) throws IOException, InterruptedException {
            
            AppNewuserHourDb earliest_val = new AppNewuserHourDb();
            String earliest_datetime = "";
            String cur_datetime = "";
            
            boolean isfirst = true;
            for (AppNewuserHourDb cur_val : values) {
                
                if (isfirst) {
                    earliest_val.copy(cur_val);
                    earliest_datetime = cur_val.year + cur_val.month + cur_val.day + cur_val.hour;
                    isfirst = false;
                }
                else {
                    cur_datetime = cur_val.year + cur_val.month + cur_val.day + cur_val.hour;
                    if (earliest_datetime.compareTo(cur_datetime) > 0) {
                        earliest_val.copy(cur_val);
                        earliest_datetime = cur_datetime;
                    }
                }
            }
            
            earliest_datetime = earliest_val.year + earliest_val.month + earliest_val.day;
            
            if (anal_date.equals(earliest_datetime)) {
                
                out_key.set(key.appkey + ConstantVars.RESULT_FIELD_SEPERATER
                        + key.token);
                
                out_val.set(earliest_val.year + ConstantVars.RESULT_FIELD_SEPERATER
                        + earliest_val.month + ConstantVars.RESULT_FIELD_SEPERATER
                        + earliest_val.day + ConstantVars.RESULT_FIELD_SEPERATER
                        + earliest_val.hour);
                
                context.write(out_key, out_val);
            }
        }
    }
    
    private static class AppNewuserHourPartitioner
        extends Partitioner<AppNewuserHourKey, AppNewuserHourDb> {
        @Override
        public int getPartition(AppNewuserHourKey key, AppNewuserHourDb value,
                int numPartitions) {
            return Math.abs(key.hashCode() * 127) % numPartitions;
        }
    }
    
    static class NewuserHourMapper
        extends Mapper<LongWritable, Text, Text, LongWritable> {
        
        private boolean verbose = false;
        private boolean counter = false;
        private String target_year = "";
        private String target_month = "";
        private String target_day = "";
        private String target_hour = "";
        
        private AppNewuserHourDbParser dbparser = new AppNewuserHourDbParser();
        
        private Text out_key = new Text();
        private LongWritable out_val = new LongWritable(1);
        private DataUsable usable = DataUsable.USE;
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            verbose = context.getConfiguration().getBoolean("verbose", false);
            counter = context.getConfiguration().getBoolean("counter", false);
            target_year = context.getConfiguration().get("year");
            target_month = context.getConfiguration().get("month");
            target_day = context.getConfiguration().get("day");
            target_hour = context.getConfiguration().get("hour");
        }
        
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            
            dbparser.parse(value);
            if (dbparser.hasError() == false) {
                
                if (dbparser.getYear().equals(target_year)
                        && dbparser.getMonth().equals(target_month)
                        && dbparser.getDay().equals(target_day)
                        && dbparser.getHour().equals(target_hour)) {
                    
                    out_key.set(dbparser.getAppkey());
                    
                    context.write(out_key, out_val);
                    
                    usable = DataUsable.USE;
                }
                else
                    usable = DataUsable.USELESS;
                
                if (counter)
                    context.getCounter(usable).increment(1);
            }
            else {
                if (verbose)
                    System.err.println("Ignoring corrupt input: " + value);
            }
            
            if (counter)
                context.getCounter(dbparser.getErrorLevel()).increment(1);
        }
    }
    
    static class NewuserHourReducer
        extends Reducer<Text, LongWritable, Text, LongWritable> {
        
        private Text out_key = new Text();
        private LongWritable out_val = new LongWritable(0);
        
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values,
                Context context) throws IOException, InterruptedException {
            
            long sum = 0;
            for (LongWritable cur_val : values) {
                sum += cur_val.get();
            }
            
            out_key.set(key);
            out_val.set(sum);
            
            context.write(out_key, out_val);
        }
    }
    
    private static class NewuserHourPartitioner
        extends Partitioner<Text, LongWritable> {
        @Override
        public int getPartition(Text key, LongWritable value,
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
        
        WorkLogger.log(NewuserStatistic.class.getSimpleName()
                + " : Start mapreduce job");
        
        try {
            exitCode = ToolRunner.run(new NewuserStatistic(), args);
            
            WorkLogger.log(NewuserStatistic.class.getSimpleName()
                    + " : End mapreduce job");
        }
        catch (Exception e) {
            ErrorLogger.log(NewuserStatistic.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(NewuserStatistic.class.getSimpleName()
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
