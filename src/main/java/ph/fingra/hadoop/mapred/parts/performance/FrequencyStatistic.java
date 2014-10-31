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
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
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
import ph.fingra.hadoop.common.ConstantVars.LogParserType;
import ph.fingra.hadoop.common.ConstantVars.LogValidation;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.ConvertTimeZone;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.mapred.common.CopyToLocalFile;
import ph.fingra.hadoop.mapred.common.HdfsFileUtil;
import ph.fingra.hadoop.mapred.parse.CommonLogParser;
import ph.fingra.hadoop.mapred.parse.ComponentLogParser;
import ph.fingra.hadoop.mapred.parse.TokenfreqParser;
import ph.fingra.hadoop.mapred.parts.performance.domain.TokenfreqEntity;
import ph.fingra.hadoop.mapred.parts.performance.domain.TokenfreqHourEntity;
import ph.fingra.hadoop.mapred.parts.performance.domain.TokenfreqHourKey;
import ph.fingra.hadoop.mapred.parts.performance.domain.TokenfreqKey;

public class FrequencyStatistic extends Configured implements Tool {
    
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
        
        WorkLogger.log(FrequencyStatistic.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate()
                + " , [reducer count] " + opt_numreduce);
        
        // get this job's output path
        HfsPathInfo hfsPath = new HfsPathInfo(fingraphConfig, opt_mode);
        outputPath_intermediate = new Path(hfsPath.getTokenfreq());
        outputPath_final = new Path(hfsPath.getFrequency());
        
        // delete previous output path if is exist
        FileSystem fs = FileSystem.get(conf);
        List<Path> deletePaths = new ArrayList<Path>();
        deletePaths.add(outputPath_intermediate);
        deletePaths.add(outputPath_final);
        for (Path deletePath : deletePaths) {
            fs.delete(deletePath, true);
        }
        
        int status = 0;
        if (opt_mode.equals(ConstantVars.RUNMODE_HOUR)) {
            
            // get this job's input path - original log file
            inputPaths = HdfsFileUtil.getOriginInputPaths(fingraphConfig, opt_mode,
                    targetDate.getYear(), targetDate.getMonth(), targetDate.getDay(),
                    targetDate.getHour(), targetDate.getWeek());
            
            Job jobIntermediate = createHourJobIntermediate(conf, inputPaths,
                    outputPath_intermediate, opt_numreduce, fingraphConfig,
                    targetDate);
            
            status = jobIntermediate.waitForCompletion(true) ? 0 : 1;
            
            Job jobFinal = createHourJobFinal(conf, outputPath_intermediate,
                    outputPath_final, opt_numreduce, fingraphConfig);
            
            status = jobFinal.waitForCompletion(true) ? 0 : 1;
        }
        else {
            
            // get this job's input path - transform log file
            inputPaths = HdfsFileUtil.getTransformInputPaths(fingraphConfig, opt_mode,
                    targetDate.getYear(), targetDate.getMonth(), targetDate.getDay(),
                    targetDate.getHour(), targetDate.getWeek());
            
            Job jobIntermediate = createJobIntermediate(conf, inputPaths,
                    outputPath_intermediate, opt_numreduce, fingraphConfig);
            
            status = jobIntermediate.waitForCompletion(true) ? 0 : 1;
            
            Job jobFinal = createJobFinal(conf, outputPath_intermediate,
                    outputPath_final, opt_numreduce, fingraphConfig);
            
            status = jobFinal.waitForCompletion(true) ? 0 : 1;
        }
        
        
        /*
        If you want to chain several jobs,
        run map/reduce jobs using ControlledJob, JobControl, Thread ...
        
        (example code)
        ControlledJob jobIntermediateC = new ControlledJob(jobIntermediate, null);
        
        List<ControlledJob> jobDependencies = new ArrayList<ControlledJob>();
        jobDependencies.add(jobIntermediateC);
        ControlledJob jobFinalC = new ControlledJob(jobFinal, jobDependencies);
        
        JobControl control = new JobControl("JobControl-Name");
        control.addJob(jobIntermediateC);
        control.addJob(jobFinalC);
        
        Thread jobControlThread = new Thread(control, "JobTread-Name");
        
        jobControlThread.start();
        
        while (!control.allFinished()) {
            Thread.sleep(1000);
        }
        */
        
        
        // copy to local result paths
        LfsPathInfo lfsPath = new LfsPathInfo(fingraphConfig, targetDate);
        CopyToLocalFile copier = new CopyToLocalFile();
        copier.dirToFile(outputPath_final.toString(), lfsPath.getFrequency());
        
        return status;
    }
    
    public Job createJobIntermediate(Configuration conf, Path[] inputpaths, Path outputpath,
            int numreduce, FingraphConfig finconfig) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        
        Job job = new Job(conf);
        String jobName = "perform/tokenfreq job";
        job.setJobName(jobName);
        
        job.setJarByClass(FrequencyStatistic.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(TokenfreqMapper.class);
        job.setReducerClass(TokenfreqReducer.class);
        
        job.setMapOutputKeyClass(TokenfreqKey.class);
        job.setMapOutputValueClass(TokenfreqEntity.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setPartitionerClass(TokenfreqPartitioner.class);
        job.setSortComparatorClass(TokenfreqSortComparator.class);
        job.setGroupingComparatorClass(TokenfreqGroupComparator.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    public Job createJobFinal(Configuration conf, Path inputpath, Path outputpath,
            int numreduce, FingraphConfig finconfig) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        
        Job job = new Job(conf);
        String jobName = "perform/frequency job";
        job.setJobName(jobName);
        
        job.setJarByClass(FrequencyStatistic.class);
        
        FileInputFormat.addInputPath(job, inputpath);
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(FrequencyMapper.class);
        job.setCombinerClass(FrequencyReducer.class);
        job.setReducerClass(FrequencyReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setPartitionerClass(FrequencyPartitioner.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class TokenfreqMapper
        extends Mapper<LongWritable, Text, TokenfreqKey, TokenfreqEntity> {
        
        private boolean verbose = false;
        private boolean counter = false;
        
        private CommonLogParser commonparser = new CommonLogParser();
        private ComponentLogParser compoparser = new ComponentLogParser();
        
        private TokenfreqKey out_key = new TokenfreqKey();
        private TokenfreqEntity out_val = new TokenfreqEntity();
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            verbose = context.getConfiguration().getBoolean("verbose", false);
            counter = context.getConfiguration().getBoolean("counter", false);
        }
        
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            
            // logtype check
            LogParserType logtype = FormatUtil.getLogParserType(value.toString());
            
            if (logtype.equals(LogParserType.CommonLog)) {
                
                // CommonLog : STARTSESS/PAGEVIEW/ENDSESS
                commonparser.parse(value);
                if (commonparser.hasError() == false) {
                    
                    out_key.set(commonparser.getAppkey(), commonparser.getToken(),
                            commonparser.getSession());
                    out_val.set(commonparser.getToken(), commonparser.getSession(),
                            commonparser.getCmd());
                    
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
                    
                    out_key.set(compoparser.getAppkey(), compoparser.getToken(),
                            compoparser.getSession());
                    out_val.set(compoparser.getToken(), compoparser.getSession(),
                            compoparser.getCmd());
                    
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
    
    static class TokenfreqReducer
        extends Reducer<TokenfreqKey, TokenfreqEntity, Text, LongWritable> {
        
        private Text out_key = new Text();
        private LongWritable out_val = new LongWritable(0);
        
        @Override
        protected void reduce(TokenfreqKey key, Iterable<TokenfreqEntity> values,
                Context context) throws IOException, InterruptedException {
            
            long session_count = 0;
            String prev_session = "";
            for (TokenfreqEntity cur_val : values) {
                
                // values :
                // - grouped by appkey/token
                // - and order by appkey/token/session
                
                if (prev_session.equals(cur_val.session) == false) {
                    session_count += 1l;
                }
                
                prev_session = cur_val.session;
            }
            
            out_key.set(key.appkey + ConstantVars.RESULT_FIELD_SEPERATER
                    + key.token);
            out_val.set(session_count);
            
            context.write(out_key, out_val);
        }
    }
    
    private static class TokenfreqPartitioner
        extends Partitioner<TokenfreqKey, TokenfreqEntity> {
        @Override
        public int getPartition(TokenfreqKey key, TokenfreqEntity value,
                int numPartitions) {
            return Math.abs((key.appkey+key.token).hashCode() * 127) % numPartitions;
        }
    }
    
    private static class TokenfreqSortComparator
        extends WritableComparator {
        protected TokenfreqSortComparator() {
            super(TokenfreqKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            TokenfreqKey k1 = (TokenfreqKey) w1;
            TokenfreqKey k2 = (TokenfreqKey) w2;
            
            // ordered by TokenfreqKey compareTo
            int ret = k1.compareTo(k2);
            
            return ret;
        }
    }
    
    private static class TokenfreqGroupComparator
        extends WritableComparator {
        protected TokenfreqGroupComparator() {
            super(TokenfreqKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            TokenfreqKey k1 = (TokenfreqKey) w1;
            TokenfreqKey k2 = (TokenfreqKey) w2;
            
            // grouped by appkey/token
            int ret = k1.appkey.compareTo(k2.appkey); if (ret != 0) return ret;
            ret = k1.token.compareTo(k2.token);
            
            return ret;
        }
    }
    
    static class FrequencyMapper
        extends Mapper<LongWritable, Text, Text, LongWritable> {
        
        private boolean verbose = false;
        private boolean counter = false;
        
        TokenfreqParser resultparser = new TokenfreqParser();
        
        private Text out_key = new Text();
        private LongWritable out_val = new LongWritable(1);
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            verbose = context.getConfiguration().getBoolean("verbose", false);
            counter = context.getConfiguration().getBoolean("counter", false);
        }
        
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            
            resultparser.parse(value);
            if (resultparser.hasError() == false) {
                
                out_key.set(resultparser.getAppkey() + ConstantVars.RESULT_FIELD_SEPERATER
                        + String.valueOf(resultparser.getSessioncount()));
                
                context.write(out_key, out_val);
            }
            else {
                if (verbose)
                    System.err.println("Ignoring corrupt input: " + value);
            }
            
            if (counter)
                context.getCounter(resultparser.getErrorLevel()).increment(1);
        }
    }
    
    static class FrequencyReducer
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
    
    private static class FrequencyPartitioner
        extends Partitioner<Text, LongWritable> {
        @Override
        public int getPartition(Text key, LongWritable value,
                int numPartitions) {
            return Math.abs(key.hashCode() * 127) % numPartitions;
        }
    }
    
    public Job createHourJobIntermediate(Configuration conf, Path[] inputpaths,
            Path outputpath, int numreduce, FingraphConfig finconfig,
            TargetDate targetdate) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        conf.set("hour", targetdate.getHour());
        
        Job job = new Job(conf);
        String jobName = "perform/tokenfreq hour job";
        job.setJobName(jobName);
        
        job.setJarByClass(FrequencyStatistic.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(TokenfreqHourMapper.class);
        job.setReducerClass(TokenfreqHourReducer.class);
        
        job.setMapOutputKeyClass(TokenfreqHourKey.class);
        job.setMapOutputValueClass(TokenfreqHourEntity.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setPartitionerClass(TokenfreqHourPartitioner.class);
        job.setSortComparatorClass(TokenfreqHourSortComparator.class);
        job.setGroupingComparatorClass(TokenfreqHourGroupComparator.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    public Job createHourJobFinal(Configuration conf, Path inputpath,
            Path outputpath, int numreduce, FingraphConfig finconfig)
            throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        
        Job job = new Job(conf);
        String jobName = "perform/frequency hour job";
        job.setJobName(jobName);
        
        job.setJarByClass(FrequencyStatistic.class);
        
        FileInputFormat.addInputPath(job, inputpath);
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(FrequencyHourMapper.class);
        job.setCombinerClass(FrequencyHourReducer.class);
        job.setReducerClass(FrequencyHourReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setPartitionerClass(FrequencyHourPartitioner.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class TokenfreqHourMapper
        extends Mapper<LongWritable, Text, TokenfreqHourKey, TokenfreqHourEntity> {
        
        private boolean verbose = false;
        private boolean counter = false;
        
        private int LTIME_LENGTH = ConstantVars.LOG_DATE_FORMAT.length();
        private int HOUR_INDEX = 8;
        private int HOUR_LENGTH = 2;
        
        private CommonLogParser commonparser = new CommonLogParser();
        private ComponentLogParser compoparser = new ComponentLogParser();
        
        private ConvertTimeZone timeZone = new ConvertTimeZone();
        
        private TokenfreqHourKey out_key = new TokenfreqHourKey();
        private TokenfreqHourEntity out_val = new TokenfreqHourEntity();
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            verbose = context.getConfiguration().getBoolean("verbose", false);
            counter = context.getConfiguration().getBoolean("counter", false);
        }
        
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            
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
                        out_key.set(commonparser.getAppkey(), commonparser.getToken(),
                                commonparser.getUtctime(), commonparser.getSession());
                        out_val.set(commonparser.getToken(), commonparser.getSession(),
                                utime2ltime_hour, commonparser.getCmd());
                        
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
                        out_key.set(compoparser.getAppkey(), compoparser.getToken(),
                                compoparser.getUtctime(), compoparser.getSession());
                        out_val.set(compoparser.getToken(), compoparser.getSession(),
                                utime2ltime_hour, compoparser.getCmd());
                        
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
    
    static class TokenfreqHourReducer
        extends Reducer<TokenfreqHourKey, TokenfreqHourEntity, Text, LongWritable> {
        
        private String target_hour = "";
        
        private Text out_key = new Text();
        private LongWritable out_val = new LongWritable(0);
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            target_hour = context.getConfiguration().get("hour");
        }
        
        @Override
        protected void reduce(TokenfreqHourKey key, Iterable<TokenfreqHourEntity> values,
                Context context) throws IOException, InterruptedException {
            
            long session_count = 0;
            String prev_session = "";
            for (TokenfreqHourEntity cur_val : values) {
                
                // values :
                // - grouped by appkey/token
                // - and order by appkey/token/utctime/session
                
                if (prev_session.equals(cur_val.session) == false
                        && cur_val.hour.equals(target_hour)) {
                    session_count += 1l;
                }
                
                prev_session = cur_val.session;
            }
            
            if (session_count>0) {
                out_key.set(key.appkey + ConstantVars.RESULT_FIELD_SEPERATER
                        + key.token);
                out_val.set(session_count);
                
                context.write(out_key, out_val);
            }
        }
    }
    
    private static class TokenfreqHourPartitioner
        extends Partitioner<TokenfreqHourKey, TokenfreqHourEntity> {
        @Override
        public int getPartition(TokenfreqHourKey key, TokenfreqHourEntity value,
                int numPartitions) {
            return Math.abs((key.appkey+key.token).hashCode() * 127) % numPartitions;
        }
    }
    
    private static class TokenfreqHourSortComparator
        extends WritableComparator {
        protected TokenfreqHourSortComparator() {
            super(TokenfreqHourKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            TokenfreqHourKey k1 = (TokenfreqHourKey) w1;
            TokenfreqHourKey k2 = (TokenfreqHourKey) w2;
            
            // ordered by TokenfreqHourKey compareTo
            int ret = k1.compareTo(k2);
            
            return ret;
        }
    }
    
    private static class TokenfreqHourGroupComparator
        extends WritableComparator {
        protected TokenfreqHourGroupComparator() {
            super(TokenfreqHourKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            TokenfreqHourKey k1 = (TokenfreqHourKey) w1;
            TokenfreqHourKey k2 = (TokenfreqHourKey) w2;
            
            // grouped by appkey/token
            int ret = k1.appkey.compareTo(k2.appkey); if (ret != 0) return ret;
            ret = k1.token.compareTo(k2.token);
            
            return ret;
        }
    }
    
    static class FrequencyHourMapper
        extends Mapper<LongWritable, Text, Text, LongWritable> {
        
        private boolean verbose = false;
        private boolean counter = false;
        
        TokenfreqParser resultparser = new TokenfreqParser();
        
        private Text out_key = new Text();
        private LongWritable out_val = new LongWritable(1);
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            verbose = context.getConfiguration().getBoolean("verbose", false);
            counter = context.getConfiguration().getBoolean("counter", false);
        }
        
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            
            resultparser.parse(value);
            if (resultparser.hasError() == false) {
                
                out_key.set(resultparser.getAppkey() + ConstantVars.RESULT_FIELD_SEPERATER
                        + String.valueOf(resultparser.getSessioncount()));
                
                context.write(out_key, out_val);
            }
            else {
                if (verbose)
                    System.err.println("Ignoring corrupt input: " + value);
            }
            
            if (counter)
                context.getCounter(resultparser.getErrorLevel()).increment(1);
        }
    }
    
    static class FrequencyHourReducer
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
    
    private static class FrequencyHourPartitioner
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
        
        WorkLogger.log(FrequencyStatistic.class.getSimpleName()
                + " : Start mapreduce job");
        
        try {
            exitCode = ToolRunner.run(new FrequencyStatistic(), args);
            
            WorkLogger.log(FrequencyStatistic.class.getSimpleName()
                    + " : End mapreduce job");
        }
        catch (Exception e) {
            ErrorLogger.log(FrequencyStatistic.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(FrequencyStatistic.class.getSimpleName()
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
