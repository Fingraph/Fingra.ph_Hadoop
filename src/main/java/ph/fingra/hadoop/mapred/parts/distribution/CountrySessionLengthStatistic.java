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
import java.util.Iterator;
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
import ph.fingra.hadoop.common.util.DateTimeUtil;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.mapred.common.CopyToLocalFile;
import ph.fingra.hadoop.mapred.common.HdfsFileUtil;
import ph.fingra.hadoop.mapred.parse.CommonLogParser;
import ph.fingra.hadoop.mapred.parse.ComponentLogParser;
import ph.fingra.hadoop.mapred.parse.CountrySesstimeParser;
import ph.fingra.hadoop.mapred.parts.distribution.domain.CountrySesstimeEntity;
import ph.fingra.hadoop.mapred.parts.distribution.domain.CountrySesstimeKey;

public class CountrySessionLengthStatistic extends Configured implements Tool {
    
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
        
        WorkLogger.log(CountrySessionLengthStatistic.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate()
                + " , [reducer count] " + opt_numreduce);
        
        // get this job's input path - transform log file
        inputPaths = HdfsFileUtil.getTransformInputPaths(fingraphConfig, opt_mode,
                targetDate.getYear(), targetDate.getMonth(), targetDate.getDay(),
                targetDate.getHour(), targetDate.getWeek());
        
        // get this job's output path
        HfsPathInfo hfsPath = new HfsPathInfo(fingraphConfig, opt_mode);
        outputPath_intermediate = new Path(hfsPath.getSesstime());
        outputPath_final = new Path(hfsPath.getCountrysessionlength());
        
        // delete previous output path if is exist
        FileSystem fs = FileSystem.get(conf);
        List<Path> deletePaths = new ArrayList<Path>();
        deletePaths.add(outputPath_intermediate);
        deletePaths.add(outputPath_final);
        for (Path deletePath : deletePaths) {
            fs.delete(deletePath, true);
        }
        
        Job jobIntermediate = createJobIntermediate(conf, inputPaths, outputPath_intermediate,
                opt_numreduce, fingraphConfig);
        
        int status = jobIntermediate.waitForCompletion(true) ? 0 : 1;
        
        Job jobFinal = createJobFinal(conf, outputPath_intermediate, outputPath_final,
                opt_numreduce, fingraphConfig);
        
        status = jobFinal.waitForCompletion(true) ? 0 : 1;
        
        
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
        copier.dirToFile(outputPath_final.toString(), lfsPath.getCountrysessionlength());
        
        return status;
    }
    
    public Job createJobIntermediate(Configuration conf, Path[] inputpaths, Path outputpath,
            int numreduce, FingraphConfig finconfig) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        
        Job job = new Job(conf);
        String jobName = "distribution/countrysesstime job";
        job.setJobName(jobName);
        
        job.setJarByClass(CountrySessionLengthStatistic.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(CountrySesstimeMapper.class);
        job.setReducerClass(CountrySesstimeReducer.class);
        
        job.setMapOutputKeyClass(CountrySesstimeKey.class);
        job.setMapOutputValueClass(CountrySesstimeEntity.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setPartitionerClass(CountrySesstimePartitioner.class);
        job.setSortComparatorClass(CountrySesstimeSortComparator.class);
        job.setGroupingComparatorClass(CountrySesstimeGroupComparator.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    public Job createJobFinal(Configuration conf, Path inputpath, Path outputpath,
            int numreduce, FingraphConfig finconfig) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        
        Job job = new Job(conf);
        String jobName = "distribution/countrysessionlength job";
        job.setJobName(jobName);
        
        job.setJarByClass(CountrySessionLengthStatistic.class);
        
        FileInputFormat.addInputPath(job, inputpath);
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(CountrySecondsessMapper.class);
        job.setCombinerClass(CountrySecondsessReducer.class);
        job.setReducerClass(CountrySecondsessReducer.class);
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        
        job.setPartitionerClass(CountrySecondsessPartitioner.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class CountrySesstimeMapper
        extends Mapper<LongWritable, Text, CountrySesstimeKey, CountrySesstimeEntity> {
        
        private boolean verbose = false;
        private boolean counter = false;
        
        private CommonLogParser commonparser = new CommonLogParser();
        private ComponentLogParser compoparser = new ComponentLogParser();
        
        private CountrySesstimeKey out_key = new CountrySesstimeKey();
        private CountrySesstimeEntity out_val = new CountrySesstimeEntity();
        
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
                    
                    out_key.set(commonparser.getAppkey(), commonparser.getCountry(),
                            commonparser.getSession(), commonparser.getUtctime());
                    out_val.set(commonparser.getSession(), commonparser.getUtctime(),
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
                    
                    out_key.set(compoparser.getAppkey(), compoparser.getCountry(),
                            compoparser.getSession(), compoparser.getUtctime());
                    out_val.set(compoparser.getSession(), compoparser.getUtctime(),
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
    
    static class CountrySesstimeReducer
    extends Reducer<CountrySesstimeKey, CountrySesstimeEntity, Text, LongWritable> {
        
        private Text out_key = new Text();
        private LongWritable out_val = new LongWritable(0);
        
        @Override
        protected void reduce(CountrySesstimeKey key, Iterable<CountrySesstimeEntity> values,
                Context context) throws IOException, InterruptedException {
            
            Iterator<CountrySesstimeEntity> iter = values.iterator();
            String first_utctime = "";
            String last_utctime = "";
            while (iter.hasNext()) {
                
                // values :
                // - grouped by appkey/country/session
                // - and order by appkey/session/utctime
                
                CountrySesstimeEntity cur_val = iter.next();
                
                if (first_utctime.isEmpty()) {
                    first_utctime = cur_val.utctime;
                }
                if (iter.hasNext()==false) {
                    last_utctime = cur_val.utctime;
                }
            }
            
            long session_length = 0;
            if (first_utctime.isEmpty()==false
                    && last_utctime.isEmpty()==false) {
                try {
                    session_length = DateTimeUtil.secondsBetween(first_utctime,
                            last_utctime, ConstantVars.LOG_DATE_FORMAT);
                }
                catch (IOException ignore) {}
            }
            
            if (session_length > 0) {
                
                out_key.set(key.appkey + ConstantVars.RESULT_FIELD_SEPERATER
                        + key.country + ConstantVars.RESULT_FIELD_SEPERATER
                        + key.session);
                out_val.set(session_length);
                
                context.write(out_key, out_val);
            }
        }
    }
    
    private static class CountrySesstimePartitioner
        extends Partitioner<CountrySesstimeKey, CountrySesstimeEntity> {
        @Override
        public int getPartition(CountrySesstimeKey key, CountrySesstimeEntity value,
                int numPartitions) {
            return Math.abs((key.appkey+key.country+key.session).hashCode() * 127) % numPartitions;
        }
    }
    
    private static class CountrySesstimeSortComparator
        extends WritableComparator {
        protected CountrySesstimeSortComparator() {
            super(CountrySesstimeKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            CountrySesstimeKey k1 = (CountrySesstimeKey) w1;
            CountrySesstimeKey k2 = (CountrySesstimeKey) w2;
            
            // ordered by SesstimeKey compareTo
            int ret = k1.compareTo(k2);
            
            return ret;
        }
    }
    
    private static class CountrySesstimeGroupComparator
        extends WritableComparator {
        protected CountrySesstimeGroupComparator() {
            super(CountrySesstimeKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            CountrySesstimeKey k1 = (CountrySesstimeKey) w1;
            CountrySesstimeKey k2 = (CountrySesstimeKey) w2;
            
            // grouped by appkey/country/session
            int ret = k1.appkey.compareTo(k2.appkey); if (ret != 0) return ret;
            ret = k1.country.compareTo(k2.country); if (ret != 0) return ret;
            ret = k1.session.compareTo(k2.session);
            
            return ret;
        }
    }
    
    static class CountrySecondsessMapper
        extends Mapper<LongWritable, Text, Text, LongWritable> {
        
        private boolean verbose = false;
        private boolean counter = false;
        
        CountrySesstimeParser resultparser = new CountrySesstimeParser();
        
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
                        + resultparser.getCountry() + ConstantVars.RESULT_FIELD_SEPERATER
                        + resultparser.getSessionlength());
                
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
    
    static class CountrySecondsessReducer
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
    
    private static class CountrySecondsessPartitioner
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
        
        WorkLogger.log(CountrySessionLengthStatistic.class.getSimpleName()
                + " : Start mapreduce job");
        
        try {
            exitCode = ToolRunner.run(new CountrySessionLengthStatistic(), args);
            
            WorkLogger.log(CountrySessionLengthStatistic.class.getSimpleName()
                    + " : End mapreduce job");
        }
        catch (Exception e) {
            ErrorLogger.log(CountrySessionLengthStatistic.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(CountrySessionLengthStatistic.class.getSimpleName()
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
