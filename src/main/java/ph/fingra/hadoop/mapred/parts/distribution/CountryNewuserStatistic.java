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

package ph.fingra.hadoop.mapred.parts.distribution;

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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.HfsPathInfo;
import ph.fingra.hadoop.common.LfsPathInfo;
import ph.fingra.hadoop.common.ConstantVars.DataUsable;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.mapred.common.CopyToLocalFile;
import ph.fingra.hadoop.mapred.common.HdfsFileUtil;
import ph.fingra.hadoop.mapred.parse.AppNewuserDbParser;

public class CountryNewuserStatistic extends Configured implements Tool {
    
    @Override
    public int run(String[] args) throws Exception {
        
        String opt_mode = "";
        String opt_target = "";
        int opt_numreduce = 0;
        
        FingraphConfig fingraphConfig = new FingraphConfig();
        TargetDate targetDate = null;
        
        Configuration conf = getConf();
        Path[] inputPaths = null;
        Path outputPath = null;
        
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
        
        WorkLogger.log(CountryNewuserStatistic.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate()
                + " , [reducer count] " + opt_numreduce);
        
        // get this job's input path - origin log file, AppNewuser newuser db file
        inputPaths = HdfsFileUtil.getAppNewuserInputPaths(fingraphConfig, opt_mode,
                targetDate.getYear(), targetDate.getMonth(), targetDate.getDay());
        if (inputPaths==null) {
            WorkLogger.log("There is no input data.");
            return 0;
        }
        
        // get this job's output path
        HfsPathInfo hfsPath = new HfsPathInfo(fingraphConfig, opt_mode);
        outputPath = new Path(hfsPath.getCountrynewuser());
        
        // delete previous output path if is exist
        FileSystem fs = FileSystem.get(conf);
        List<Path> deletePaths = new ArrayList<Path>();
        deletePaths.add(outputPath);
        for (Path deletePath : deletePaths) {
            fs.delete(deletePath, true);
        }
        
        Job job = createJob(conf, inputPaths, outputPath, opt_numreduce,
                fingraphConfig, targetDate);
        
        int status = job.waitForCompletion(true) ? 0 : 1;
        
        // copy to local result paths
        LfsPathInfo lfsPath = new LfsPathInfo(fingraphConfig, targetDate);
        CopyToLocalFile copier = new CopyToLocalFile();
        copier.dirToFile(outputPath.toString(), lfsPath.getCountrynewuser());
        
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
        String jobName = "distribute/countrynewuser job";
        job.setJobName(jobName);
        
        job.setJarByClass(CountryNewuserStatistic.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(CountryNewuserMapper.class);
        job.setCombinerClass(CountryNewuserReducer.class);
        job.setReducerClass(CountryNewuserReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setPartitionerClass(CountryNewuserPartitioner.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class CountryNewuserMapper
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
                        
                        out_key.set(dbparser.getAppkey() + ConstantVars.RESULT_FIELD_SEPERATER
                                + dbparser.getCountry());
                        
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
                        
                        out_key.set(dbparser.getAppkey() + ConstantVars.RESULT_FIELD_SEPERATER
                                + dbparser.getCountry());
                        
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
                        
                        out_key.set(dbparser.getAppkey() + ConstantVars.RESULT_FIELD_SEPERATER
                                + dbparser.getCountry());
                        
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
    
    static class CountryNewuserReducer
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
    
    private static class CountryNewuserPartitioner
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
        
        WorkLogger.log(CountryNewuserStatistic.class.getSimpleName()
                + " : Start mapreduce job");
        
        try {
            exitCode = ToolRunner.run(new CountryNewuserStatistic(), args);
            
            WorkLogger.log(CountryNewuserStatistic.class.getSimpleName()
                    + " : End mapreduce job");
        }
        catch (Exception e) {
            ErrorLogger.log(CountryNewuserStatistic.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(CountryNewuserStatistic.class.getSimpleName()
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
