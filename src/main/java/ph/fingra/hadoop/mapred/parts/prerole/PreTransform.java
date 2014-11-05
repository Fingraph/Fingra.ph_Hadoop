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
import org.apache.hadoop.io.NullWritable;
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
import ph.fingra.hadoop.common.ConstantVars.LogParserType;
import ph.fingra.hadoop.common.ConstantVars.LogValidation;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.mapred.common.CopyWithinHdfsFile;
import ph.fingra.hadoop.mapred.common.HdfsFileUtil;
import ph.fingra.hadoop.mapred.parse.CommonLogParser;
import ph.fingra.hadoop.mapred.parse.ComponentLogParser;
import ph.fingra.hadoop.mapred.parts.prerole.domain.TransformContainer;
import ph.fingra.hadoop.mapred.parts.prerole.domain.TransformKey;

public class PreTransform extends Configured implements Tool {
    
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
        
        WorkLogger.log(PreTransform.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate()
                + " , [reducer count] " + opt_numreduce);
        
        // PreTransform's run mode restriction
        if (opt_mode.equals(ConstantVars.RUNMODE_DAY)==false) {
            WorkLogger.warn(PreTransform.class.getSimpleName()
                    + " : this class can operate only day mode");
            return 0;
        }
        
        // get this job's input path - original log file
        inputPaths = HdfsFileUtil.getOriginInputPaths(fingraphConfig, opt_mode,
                targetDate.getYear(), targetDate.getMonth(), targetDate.getDay(),
                targetDate.getHour(), targetDate.getWeek());
        if (inputPaths==null) {
            WorkLogger.log("There is no input data.");
            return 0;
        }
        
        // get this job's output path
        HfsPathInfo hfsPath = new HfsPathInfo(fingraphConfig, opt_mode);
        outputPath = new Path(hfsPath.getPretransform());
        
        // delete previous output path if is exist
        FileSystem fs = FileSystem.get(conf);
        List<Path> deletePaths = new ArrayList<Path>();
        deletePaths.add(outputPath);
        for (Path deletePath : deletePaths) {
            fs.delete(deletePath, true);
        }
        
        Job job = createJob(conf, inputPaths, outputPath, opt_numreduce,
                fingraphConfig);
        
        int status = job.waitForCompletion(true) ? 0 : 1;
        
        // delete origin log file if delete option is on
        if (fingraphConfig.getSetting().isDelete_origin_file()) {
            HdfsFileUtil.deleteOriginFiles(fingraphConfig,
                    targetDate.getYear(), targetDate.getMonth(), targetDate.getDay());
        }
        // copy to hdfs log paths
        CopyWithinHdfsFile copier = new CopyWithinHdfsFile();
        copier.dirToFile(outputPath.toString(), HdfsFileUtil.getSaveTransformFilePath(
                fingraphConfig, targetDate.getYear(), targetDate.getMonth(), targetDate.getDay()));
        
        return status;
    }
    
    public Job createJob(Configuration conf, Path[] inputpaths, Path outputpath,
            int numreduce, FingraphConfig finconfig) throws IOException {
        
        conf.setBoolean("verbose", finconfig.getDebug().isDebug_show_verbose());
        conf.setBoolean("counter", finconfig.getDebug().isDebug_show_counter());
        
        Job job = new Job(conf);
        String jobName = "prerole/pretransform job";
        job.setJobName(jobName);
        
        job.setJarByClass(PreTransform.class);
        
        for (int i=0; i<inputpaths.length; i++) {
            FileInputFormat.addInputPath(job, inputpaths[i]);
        }
        FileOutputFormat.setOutputPath(job, outputpath);
        
        job.setMapperClass(PreTransformMapper.class);
        job.setReducerClass(PreTransformReducer.class);
        
        job.setMapOutputKeyClass(TransformKey.class);
        job.setMapOutputValueClass(TransformContainer.class);
        
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        
        job.setPartitionerClass(PreTransformPartitioner.class);
        job.setSortComparatorClass(PreTransformSortComparator.class);
        job.setGroupingComparatorClass(PreTransformGroupComparator.class);
        
        job.setNumReduceTasks(numreduce);
        
        return job;
    }
    
    static class PreTransformMapper
        extends Mapper<LongWritable, Text, TransformKey, TransformContainer> {
        
        private boolean verbose = false;
        private boolean counter = false;
        
        private CommonLogParser commonparser = new CommonLogParser();
        private ComponentLogParser compoparser = new ComponentLogParser();
        
        private TransformKey out_key = new TransformKey();
        private TransformContainer out_val = new TransformContainer();
        private StringBuilder buf = new StringBuilder("");
        
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
                
                /*
                 * If you have to customize information that you want to change
                 * then process additionally in here
                 */
                
                // CommonLog : STARTSESS/PAGEVIEW/ENDSESS
                commonparser.parse(value);
                if (commonparser.hasError() == false) {
                    
                    String format_str = "";
                    if (commonparser.getCmd().equals(ConstantVars.CMD_STARTSESS)) {
                        format_str = FormatUtil.getStartLogString(buf,
                                commonparser.getAppkey(), commonparser.getSession(),
                                commonparser.getUtctime(), commonparser.getLocaltime(),
                                commonparser.getToken(), commonparser.getCountry(),
                                commonparser.getLanguage(), commonparser.getDevice(),
                                commonparser.getOsversion(), commonparser.getResolution(),
                                commonparser.getAppversion());
                    }
                    else if (commonparser.getCmd().equals(ConstantVars.CMD_PAGEVIEW)) {
                        format_str = FormatUtil.getPageviewLogString(buf,
                                commonparser.getAppkey(), commonparser.getSession(),
                                commonparser.getUtctime(), commonparser.getLocaltime(),
                                commonparser.getToken(), commonparser.getCountry(),
                                commonparser.getLanguage(), commonparser.getDevice(),
                                commonparser.getOsversion(), commonparser.getResolution(),
                                commonparser.getAppversion());
                    }
                    else if (commonparser.getCmd().equals(ConstantVars.CMD_ENDSESS)) {
                        format_str = FormatUtil.getEndLogString(buf,
                                commonparser.getAppkey(), commonparser.getSession(),
                                commonparser.getUtctime(), commonparser.getLocaltime(),
                                commonparser.getToken(), commonparser.getCountry(),
                                commonparser.getLanguage(), commonparser.getDevice(),
                                commonparser.getOsversion(), commonparser.getResolution(),
                                commonparser.getAppversion());
                    }
                    
                    if (format_str.isEmpty()==false) {
                        out_key.set(commonparser.getAppkey(), commonparser.getToken(),
                                commonparser.getSession(), commonparser.getCmd(),
                                commonparser.getUtctime());
                        out_val.set(commonparser.getCmd(), format_str);
                        
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
                    
                    String format_str = "";
                    if (compoparser.getCmd().equals(ConstantVars.CMD_COMPONENT)) {
                        format_str = FormatUtil.getComponentLogString(buf,
                                compoparser.getAppkey(), compoparser.getComponentkey(),
                                compoparser.getSession(), compoparser.getUtctime(),
                                compoparser.getLocaltime(), compoparser.getToken(),
                                compoparser.getCountry(), compoparser.getLanguage(),
                                compoparser.getDevice(), compoparser.getOsversion(),
                                compoparser.getResolution(), compoparser.getAppversion());
                    }
                    
                    if (format_str.isEmpty()==false) {
                        out_key.set(compoparser.getAppkey(), compoparser.getToken(),
                                compoparser.getSession(), compoparser.getCmd(),
                                compoparser.getUtctime());
                        out_val.set(compoparser.getCmd(), format_str);
                        
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
    
    static class PreTransformReducer
        extends Reducer<TransformKey, TransformContainer, NullWritable, Text> {
        
        private Text out_val = new Text();
        
        @Override
        protected void reduce(TransformKey key, Iterable<TransformContainer> values,
                Context context) throws IOException, InterruptedException {
            
            // values :
            // - grouped by appkey/token/session
            // - and order by appkey/token/session/cmd/utctime
            
            boolean has_end = false;
            String last_end = "";
            for (TransformContainer val : values) {
                
                if (val.cmd.equals(ConstantVars.CMD_STARTSESS)
                        || val.cmd.equals(ConstantVars.CMD_PAGEVIEW)
                        || val.cmd.equals(ConstantVars.CMD_COMPONENT)) {
                    out_val.set(val.logline);
                    context.write(NullWritable.get(), out_val);
                }
                else if (val.cmd.equals(ConstantVars.CMD_ENDSESS)) {
                    has_end = true;
                    last_end = val.logline;
                }
            }
            
            if (has_end) {
                out_val.set(last_end);
                context.write(NullWritable.get(), out_val);
            }
        }
    }
    
    private static class PreTransformPartitioner
        extends Partitioner<TransformKey, TransformContainer> {
        @Override
        public int getPartition(TransformKey key, TransformContainer value,
                int numPartitions) {
            return Math.abs((key.appkey+key.token+key.session).hashCode() * 127) % numPartitions;
        }
    }
    
    private static class PreTransformSortComparator
        extends WritableComparator {
        protected PreTransformSortComparator() {
            super(TransformKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            TransformKey k1 = (TransformKey) w1;
            TransformKey k2 = (TransformKey) w2;
            
            // ordered by TransformKey compareTo
            int ret = k1.compareTo(k2);
            
            return ret;
        }
    }
    
    private static class PreTransformGroupComparator
        extends WritableComparator {
        protected PreTransformGroupComparator() {
            super(TransformKey.class, true);
        }
        @SuppressWarnings("rawtypes")
        @Override
        public int compare(WritableComparable w1, WritableComparable w2) {
            TransformKey k1 = (TransformKey) w1;
            TransformKey k2 = (TransformKey) w2;
            
            // grouped by appkey/token/session
            int ret = k1.appkey.compareTo(k2.appkey);
            if (ret != 0) return ret;
            ret = k1.token.compareTo(k2.token);
            if (ret != 0) return ret;
            ret = k1.session.compareTo(k2.session);
            
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
        
        WorkLogger.log(PreTransform.class.getSimpleName()
                + " : Start mapreduce job");
        
        try {
            exitCode = ToolRunner.run(new PreTransform(), args);
            
            WorkLogger.log(PreTransform.class.getSimpleName()
                    + " : End mapreduce job");
        }
        catch (Exception e) {
            ErrorLogger.log(PreTransform.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.warn(PreTransform.class.getSimpleName()
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
