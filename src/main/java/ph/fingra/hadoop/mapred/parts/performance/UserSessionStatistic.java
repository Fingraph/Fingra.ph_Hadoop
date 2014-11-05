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
import ph.fingra.hadoop.mapred.parts.performance.domain.UserSessionEntity;
import ph.fingra.hadoop.mapred.parts.performance.domain.UserSessionHourEntity;
import ph.fingra.hadoop.mapred.parts.performance.domain.UserSessionHourKey;
import ph.fingra.hadoop.mapred.parts.performance.domain.UserSessionKey;

public class UserSessionStatistic extends Configured implements Tool {
    
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
        
        WorkLogger.log(UserSessionStatistic.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate()
                + " , [reducer count] " + opt_numreduce);
        
        // get this job's output path
        HfsPathInfo hfsPath = new HfsPathInfo(fingraphConfig, opt_mode);
        outputPath = new Path(hfsPath.getUsersession());
        
        // delete previous output path if is exist
        FileSystem fs = FileSystem.get(conf);
        List<Path> deletePaths = new ArrayList<Path>();
        deletePaths.add(outputPath);
        for (Path deletePath : deletePaths) {
            fs.delete(deletePath, true);
        }
        
        int status = 0;
        if (opt_mode.equals(ConstantVars.RUNMODE_HOUR)) {
            
            // get this job's input path - original log file
            inputPaths = HdfsFileUtil.getOriginInputPaths(fingraphConfig, opt_mode,
                    targetDate.getYear(), targetDate.getMonth(), targetDate.getDay(),
                    targetDate.getHour(), targetDate.getWeek());
            if (inputPaths==null) {
                WorkLogger.log("There is no input data.");
                return 0;
            }
            
            Job job = createHourJob(conf, inputPaths, outputPath, opt_numreduce,
                    fingraphConfig, targetDate);
            
            status = job.waitForCompletion(true) ? 0 : 1;
        }
        else {
            
            // get this job's input path - transform log file
            inputPaths = HdfsFileUtil.getTransformInputPaths(fingraphConfig, opt_mode,
                    targetDate.getYear(), targetDate.getMonth(), targetDate.getDay(),
                    targetDate.getHour(), targetDate.getWeek());
            if (inputPaths==null) {
                WorkLogger.log("There is no input data.");
                return 0;
            }
            
            Job job = createJob(conf, inputPaths, outputPath, opt_numreduce,
                    fingraphConfig);
            
            status = job.waitForCompletion(true) ? 0 : 1;
        }
        
        // copy to local result paths
        LfsPathInfo lfsPath = new LfsPathInfo(fingraphConfig, targetDate);
        CopyToLocalFile copier = new CopyToLocalFile();
        copier.dirToFile(outputPath.toString(), lfsPath.getUsersession());
        
        return status;
    }
    
    public Job createJob(Configuration conf, Path[] inputpaths, Path outputpath,
            int numreduce, FingraphConfig finconfig) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        
        Job job = new Job(conf);
        String jobName = "perform/usersession job";
        job.setJobName(jobName);
        
        job.setJarByClass(UserSessionStatistic.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(UserSessionMapper.class);
        job.setReducerClass(UserSessionReducer.class);
        
        job.setMapOutputKeyClass(UserSessionKey.class);
        job.setMapOutputValueClass(UserSessionEntity.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        job.setPartitionerClass(UserSessionPartitioner.class);
        job.setSortComparatorClass(UserSessionSortComparator.class);
        job.setGroupingComparatorClass(UserSessionGroupComparator.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class UserSessionMapper
        extends Mapper<LongWritable, Text, UserSessionKey, UserSessionEntity> {
        
        private boolean verbose = false;
        private boolean counter = false;
        
        private CommonLogParser commonparser = new CommonLogParser();
        private ComponentLogParser compoparser = new ComponentLogParser();
        
        private UserSessionKey out_key = new UserSessionKey();
        private UserSessionEntity out_val = new UserSessionEntity();
        
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
    
    static class UserSessionReducer
        extends Reducer<UserSessionKey, UserSessionEntity, Text, Text> {
        
        private Text out_key = new Text();
        private Text out_val = new Text();
        
        @Override
        protected void reduce(UserSessionKey key, Iterable<UserSessionEntity> values,
                Context context) throws IOException, InterruptedException {
            
            long user_count = 0;
            long session_count = 0;
            String prev_token = "";
            String prev_session = "";
            for (UserSessionEntity cur_val : values) {
                
                // values :
                // - grouped by appkey
                // - and order by appkey/token/session
                
                if (prev_token.equals(cur_val.token) == false) {
                    user_count += 1l;
                }
                if (prev_session.equals(cur_val.session) == false) {
                    session_count += 1l;
                }
                
                prev_token = cur_val.token;
                prev_session = cur_val.session;
            }
            
            out_key.set(key.appkey);
            out_val.set(String.valueOf(user_count) + ConstantVars.RESULT_FIELD_SEPERATER
                    + String.valueOf(session_count));
            
            context.write(out_key, out_val);
        }
    }
    
    private static class UserSessionPartitioner
        extends Partitioner<UserSessionKey, UserSessionEntity> {
        @Override
        public int getPartition(UserSessionKey key, UserSessionEntity value,
                int numPartitions) {
            return Math.abs((key.appkey).hashCode() * 127) % numPartitions;
        }
    }
    
    private static class UserSessionSortComparator
        extends WritableComparator {
        protected UserSessionSortComparator() {
            super(UserSessionKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            UserSessionKey k1 = (UserSessionKey) w1;
            UserSessionKey k2 = (UserSessionKey) w2;
            
            // ordered by UserSessionKey compareTo
            int ret = k1.compareTo(k2);
            
            return ret;
        }
    }
    
    private static class UserSessionGroupComparator
        extends WritableComparator {
        protected UserSessionGroupComparator() {
            super(UserSessionKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            UserSessionKey k1 = (UserSessionKey) w1;
            UserSessionKey k2 = (UserSessionKey) w2;
            
            // grouped by appkey
            int ret = k1.appkey.compareTo(k2.appkey);
            
            return ret;
        }
    }
    
    public Job createHourJob(Configuration conf, Path[] inputpaths,
            Path outputpath, int numreduce, FingraphConfig finconfig,
            TargetDate targetdate) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        conf.set("hour", targetdate.getHour());
        
        Job job = new Job(conf);
        String jobName = "perform/usersession hour job";
        job.setJobName(jobName);
        
        job.setJarByClass(UserSessionStatistic.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(UserSessionHourMapper.class);
        job.setReducerClass(UserSessionHourReducer.class);
        
        job.setMapOutputKeyClass(UserSessionHourKey.class);
        job.setMapOutputValueClass(UserSessionHourEntity.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        
        job.setPartitionerClass(UserSessionHourPartitioner.class);
        job.setSortComparatorClass(UserSessionHourSortComparator.class);
        job.setGroupingComparatorClass(UserSessionHourGroupComparator.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class UserSessionHourMapper
        extends Mapper<LongWritable, Text, UserSessionHourKey, UserSessionHourEntity> {
        
        private boolean verbose = false;
        private boolean counter = false;
        
        private int LTIME_LENGTH = ConstantVars.LOG_DATE_FORMAT.length();
        private int HOUR_INDEX = 8;
        private int HOUR_LENGTH = 2;
        
        private CommonLogParser commonparser = new CommonLogParser();
        private ComponentLogParser compoparser = new ComponentLogParser();
        
        private ConvertTimeZone timeZone = new ConvertTimeZone();
        
        private UserSessionHourKey out_key = new UserSessionHourKey();
        private UserSessionHourEntity out_val = new UserSessionHourEntity();
        
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
    
    static class UserSessionHourReducer
        extends Reducer<UserSessionHourKey, UserSessionHourEntity, Text, Text> {
        
        private String target_hour = "";
        
        private Text out_key = new Text();
        private Text out_val = new Text();
        
        protected void setup(Context context)
                throws IOException, InterruptedException {
            target_hour = context.getConfiguration().get("hour");
        }
        
        @Override
        protected void reduce(UserSessionHourKey key, Iterable<UserSessionHourEntity> values,
                Context context) throws IOException, InterruptedException {
            
            long user_count = 0;
            long session_count = 0;
            String prev_token = "";
            String prev_session = "";
            for (UserSessionHourEntity cur_val : values) {
                
                // values :
                // - grouped by appkey
                // - and order by appkey/token/utctime/session
                
                if (prev_token.equals(cur_val.token) == false
                        && cur_val.hour.equals(target_hour)) {
                    user_count += 1l;
                }
                if (prev_session.equals(cur_val.session) == false
                        && cur_val.hour.equals(target_hour)) {
                    session_count += 1l;
                }
                
                prev_token = cur_val.token;
                prev_session = cur_val.session;
            }
            
            if (user_count>0 || session_count>0) {
                out_key.set(key.appkey);
                out_val.set(String.valueOf(user_count) + ConstantVars.RESULT_FIELD_SEPERATER
                        + String.valueOf(session_count));
                
                context.write(out_key, out_val);
            }
        }
    }
    
    private static class UserSessionHourPartitioner
        extends Partitioner<UserSessionHourKey, UserSessionHourEntity> {
        @Override
        public int getPartition(UserSessionHourKey key, UserSessionHourEntity value,
                int numPartitions) {
            return Math.abs((key.appkey).hashCode() * 127) % numPartitions;
        }
    }
    
    private static class UserSessionHourSortComparator
        extends WritableComparator {
        protected UserSessionHourSortComparator() {
            super(UserSessionHourKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            UserSessionHourKey k1 = (UserSessionHourKey) w1;
            UserSessionHourKey k2 = (UserSessionHourKey) w2;
            
            // ordered by UserSessionHourKey compareTo
            int ret = k1.compareTo(k2);
            
            return ret;
        }
    }
    
    private static class UserSessionHourGroupComparator
        extends WritableComparator {
        protected UserSessionHourGroupComparator() {
            super(UserSessionHourKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            UserSessionHourKey k1 = (UserSessionHourKey) w1;
            UserSessionHourKey k2 = (UserSessionHourKey) w2;
            
            // grouped by appkey
            int ret = k1.appkey.compareTo(k2.appkey);
            
            return ret;
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
        
        WorkLogger.log(UserSessionStatistic.class.getSimpleName()
                + " : Start mapreduce job");
        
        try {
            exitCode = ToolRunner.run(new UserSessionStatistic(), args);
            
            WorkLogger.log(UserSessionStatistic.class.getSimpleName()
                    + " : End mapreduce job");
        }
        catch (Exception e) {
            ErrorLogger.log(UserSessionStatistic.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(UserSessionStatistic.class.getSimpleName()
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
