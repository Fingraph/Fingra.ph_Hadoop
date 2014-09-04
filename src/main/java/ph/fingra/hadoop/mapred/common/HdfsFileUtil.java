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

package ph.fingra.hadoop.mapred.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.util.DateTimeUtil;

public class HdfsFileUtil {
    
    public static boolean isExistFile(String chkfile) throws IOException {
        
        return isExistFile(new Path(chkfile));
    }
    
    public static boolean isExistFile(Path chkfilepath) throws IOException {
        
        boolean isexist = false;
        
        Configuration conf = new Configuration();
        
        FileSystem hdfs = FileSystem.get(conf);
        
        if (hdfs.exists(chkfilepath)) {
            isexist = true;
        }
        
        return isexist;
    }
    
    public static int getDateMatchedFileCount(Path srcpath) throws IOException {
        
        int count = 0;
        Path parentPath = null;
        String date_ext = null;
        
        // directory path
        parentPath = srcpath.getParent();
        
        // date pattern
        Pattern p = Pattern.compile("([0-9]{4})\\-([0-9]{2})\\-([0-9]{2})");
        
        Matcher m = p.matcher(srcpath.getName());
        
        if (m.find()) {
            // suffix part like "yyyy-MM-dd.txt" in file name 
            date_ext = srcpath.getName().substring(m.start()/*, m.end()*/);
        }
        
        Configuration conf = new Configuration();
        
        FileSystem hdfs = FileSystem.get(conf);
        
        // get matched file list
        final String suffix = date_ext;
        PathFilter resultFileFilter = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return path.getName().endsWith(suffix);
            }
        };
        
        try {
            FileStatus[] status = hdfs.listStatus(parentPath, resultFileFilter);
            
            if (status != null) {
                Path[] listedPaths = FileUtil.stat2Paths(status);
                
                if (listedPaths != null) {
                    count = listedPaths.length;
                }
            }
        }
        catch (FileNotFoundException ignore) {}
        
        return count;
    }
    
    /*
     * 1) backup the srcfile as "srcfile_yyyymmdd", and copy srcdir to "srcfile"
     * 2) delete old backup file
     */
    public static boolean deleteNBackupFile(String srcdir, String srcfile,
            int maxcount, String runday, final String dbfnameprefix)
            throws IOException {
        
        Configuration conf = new Configuration();
        
        FileSystem hdfs = FileSystem.get(conf);
        
        Path targetPath = null;
        Path rootPath = new Path(srcdir);
        Path sourcePath = new Path(srcfile);
        String target_day = "";
        String target_file = "";
        boolean success = false;
        
        // if not exist srcfile, stop backup and return true
        if (hdfs.exists(sourcePath) == false) {
            return true;
        }
        
        // make backup file name as yesterday date
        target_day = DateTimeUtil.addDays(runday, -1, "yyyyMMdd");
        target_file = srcfile + "-" + target_day;
        //System.out.println("target_file - " + target_file);
        targetPath = new Path(target_file);
        
        // delete backup file if exist same name, then rename source file to backup file
        if (hdfs.exists(new Path(target_file))) {
            hdfs.delete(targetPath, true);
        }
        success = hdfs.rename(sourcePath, targetPath);
        
        // get bakup file list
        PathFilter resultFileFilter = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return path.getName().startsWith(dbfnameprefix + "-");
            }
        };
        
        try {
            FileStatus[] status = hdfs.listStatus(rootPath, resultFileFilter);
            
            Path[] listedPaths = FileUtil.stat2Paths(status);
            
            // delete more than maximum number of backup files
            if (listedPaths.length > maxcount ) {
                
                Comparator<Path> c = new Comparator<Path>() {
                    public int compare(Path o1, Path o2) {
                        int ret = 0;
                        ret = o1.getName().compareTo(o2.getName());
                        return -(ret);  // order by reverse of the period
                    }
                };
                
                Arrays.sort(listedPaths, c);
                
                for (int i=maxcount; i<listedPaths.length; i++) {
                    Path path = listedPaths[i];
                    hdfs.delete(path, true);
                }
            }
        }
        catch (FileNotFoundException ignore) {}
        
        return success;
    }
    
    public static Path[] getOriginInputPaths(FingraphConfig config, String mode,
            String year, String month, String day, String hour, int week)
            throws IOException {
        
        Path[] inputpaths = null;
        
        if (mode.equals(ConstantVars.RUNMODE_HOUR)) {
            
            inputpaths = new Path[1];
            
            String uri = config.getHadoop_user_path()
                    + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                    + config.getSetting().getHfs_input_path()
                    + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/")
                    + config.getSetting().getOrigin_input_file();
            uri = uri.replaceAll("\\{yyyy\\}", year);
            uri = uri.replaceAll("\\{MM\\}", month);
            uri = uri.replaceAll("\\{dd\\}", day);
            
            inputpaths[0] = new Path(uri);
        }
        else if (mode.equals(ConstantVars.RUNMODE_DAY)) {
            
            inputpaths = new Path[1];
            
            String uri = config.getHadoop_user_path()
                    + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                    + config.getSetting().getHfs_input_path()
                    + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/")
                    + config.getSetting().getOrigin_input_file();
            uri = uri.replaceAll("\\{yyyy\\}", year);
            uri = uri.replaceAll("\\{MM\\}", month);
            uri = uri.replaceAll("\\{dd\\}", day);
            
            inputpaths[0] = new Path(uri);
        }
        else if (mode.equals(ConstantVars.RUNMODE_WEEK)) {
            
            List<String> inputlist = new ArrayList<String>();
            
            String firstday, nextday;
            firstday = DateTimeUtil.startDayOfWeek(year, week, "yyyyMMdd");
            nextday = firstday;
            int today_intval = Integer.parseInt(DateTimeUtil.getTodayFormatString("yyyyMMdd"));
            int curday_intval = 0;
            
            for (int i=0; i < 7; i++) {
                if (i != 0) {
                    nextday = DateTimeUtil.addDays(firstday, i, "yyyyMMdd");
                }
                
                curday_intval = Integer.parseInt(nextday);
                if (curday_intval >= today_intval) {
                    // pass without putting into inputpaths if date is today or after
                    continue;
                }
                
                String tyear = nextday.substring(0, 4);
                String tmonth = nextday.substring(4, 6);
                String tday = nextday.substring(6);
                
                String uri = config.getHadoop_user_path()
                        + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                        + config.getSetting().getHfs_input_path()
                        + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/")
                        + config.getSetting().getTransform_input_file();
                uri = uri.replaceAll("\\{yyyy\\}", tyear);
                uri = uri.replaceAll("\\{MM\\}", tmonth);
                uri = uri.replaceAll("\\{dd\\}", tday);
                
                if (getDateMatchedFileCount(new Path(uri)) > 0) {
                    inputlist.add(uri);
                }
            }
            
            if (inputlist.size() <= 0) {
                throw new IOException("there is no matched log file in target week");
            }
            
            inputpaths = new Path[inputlist.size()];
            
            for (int i=0; i < inputlist.size(); i++) {
                String uri = inputlist.get(i);
                inputpaths[i] = new Path(uri);
            }
        }
        else {
            
            List<String> inputlist = new ArrayList<String>();
            
            String firstday, lastday, nextday;
            firstday = DateTimeUtil.startDayOfMonth(year, month, "yyyyMMdd");
            lastday = DateTimeUtil.lastDayOfMonth(year, month, "yyyyMMdd");
            int daycount_in_month = Integer.parseInt(lastday.substring(6));
            nextday = firstday;
            int today_intval = Integer.parseInt(DateTimeUtil.getTodayFormatString("yyyyMMdd"));
            int curday_intval = 0;
            
            for (int i=0; i < daycount_in_month; i++) {
                if (i != 0) {
                    nextday = DateTimeUtil.addDays(firstday, i, "yyyyMMdd");
                }
                
                curday_intval = Integer.parseInt(nextday);
                if (curday_intval >= today_intval) {
                    // pass without putting into inputpaths if date is today or after
                    continue;
                }
                
                String tyear = nextday.substring(0, 4);
                String tmonth = nextday.substring(4, 6);
                String tday = nextday.substring(6);
                
                String uri = config.getHadoop_user_path()
                        + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                        + config.getSetting().getHfs_input_path()
                        + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/")
                        + config.getSetting().getTransform_input_file();
                uri = uri.replaceAll("\\{yyyy\\}", tyear);
                uri = uri.replaceAll("\\{MM\\}", tmonth);
                uri = uri.replaceAll("\\{dd\\}", tday);
                
                if (getDateMatchedFileCount(new Path(uri)) > 0) {
                    inputlist.add(uri);
                }
            }
            
            if (inputlist.size() <= 0) {
                throw new IOException("there is no matched log file in target month");
            }
            
            inputpaths = new Path[inputlist.size()];
            
            for (int i=0; i < inputlist.size(); i++) {
                String uri = inputlist.get(i);
                inputpaths[i] = new Path(uri);
            }
        }
        
        return inputpaths;
    }
    
    public static Path[] getTransformInputPaths(FingraphConfig config, String mode,
            String year, String month, String day, String hour, int week)
            throws IOException {
        
        Path[] inputpaths = null;
        
        if (mode.equals(ConstantVars.RUNMODE_HOUR)) {
            
            inputpaths = new Path[1];
            
            String uri = config.getHadoop_user_path()
                    + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                    + config.getSetting().getHfs_input_path()
                    + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/")
                    + config.getSetting().getTransform_input_file();
            uri = uri.replaceAll("\\{yyyy\\}", year);
            uri = uri.replaceAll("\\{MM\\}", month);
            uri = uri.replaceAll("\\{dd\\}", day);
            
            inputpaths[0] = new Path(uri);
        }
        else if (mode.equals(ConstantVars.RUNMODE_DAY)) {
            
            inputpaths = new Path[1];
            
            String uri = config.getHadoop_user_path()
                    + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                    + config.getSetting().getHfs_input_path()
                    + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/")
                    + config.getSetting().getTransform_input_file();
            uri = uri.replaceAll("\\{yyyy\\}", year);
            uri = uri.replaceAll("\\{MM\\}", month);
            uri = uri.replaceAll("\\{dd\\}", day);
            
            inputpaths[0] = new Path(uri);
        }
        else if (mode.equals(ConstantVars.RUNMODE_WEEK)) {
            
            List<String> inputlist = new ArrayList<String>();
            
            String firstday, nextday;
            firstday = DateTimeUtil.startDayOfWeek(year, week, "yyyyMMdd");
            nextday = firstday;
            int today_intval = Integer.parseInt(DateTimeUtil.getTodayFormatString("yyyyMMdd"));
            int curday_intval = 0;
            
            for (int i=0; i < 7; i++) {
                if (i != 0) {
                    nextday = DateTimeUtil.addDays(firstday, i, "yyyyMMdd");
                }
                
                curday_intval = Integer.parseInt(nextday);
                if (curday_intval >= today_intval) {
                    // pass without putting into inputpaths if date is today or after
                    continue;
                }
                
                String tyear = nextday.substring(0, 4);
                String tmonth = nextday.substring(4, 6);
                String tday = nextday.substring(6);
                
                String uri = config.getHadoop_user_path()
                        + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                        + config.getSetting().getHfs_input_path()
                        + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/")
                        + config.getSetting().getTransform_input_file();
                uri = uri.replaceAll("\\{yyyy\\}", tyear);
                uri = uri.replaceAll("\\{MM\\}", tmonth);
                uri = uri.replaceAll("\\{dd\\}", tday);
                
                if (getDateMatchedFileCount(new Path(uri)) > 0) {
                    inputlist.add(uri);
                }
            }
            
            if (inputlist.size() <= 0) {
                throw new IOException("there is no matched log file in target week");
            }
            
            inputpaths = new Path[inputlist.size()];
            
            for (int i=0; i < inputlist.size(); i++) {
                String uri = inputlist.get(i);
                inputpaths[i] = new Path(uri);
            }
        }
        else {
            
            List<String> inputlist = new ArrayList<String>();
            
            String firstday, lastday, nextday;
            firstday = DateTimeUtil.startDayOfMonth(year, month, "yyyyMMdd");
            lastday = DateTimeUtil.lastDayOfMonth(year, month, "yyyyMMdd");
            int daycount_in_month = Integer.parseInt(lastday.substring(6));
            nextday = firstday;
            int today_intval = Integer.parseInt(DateTimeUtil.getTodayFormatString("yyyyMMdd"));
            int curday_intval = 0;
            
            for (int i=0; i < daycount_in_month; i++) {
                if (i != 0) {
                    nextday = DateTimeUtil.addDays(firstday, i, "yyyyMMdd");
                }
                
                curday_intval = Integer.parseInt(nextday);
                if (curday_intval >= today_intval) {
                    // pass without putting into inputpaths if date is today or after
                    continue;
                }
                
                String tyear = nextday.substring(0, 4);
                String tmonth = nextday.substring(4, 6);
                String tday = nextday.substring(6);
                
                String uri = config.getHadoop_user_path()
                        + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                        + config.getSetting().getHfs_input_path()
                        + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/")
                        + config.getSetting().getTransform_input_file();
                uri = uri.replaceAll("\\{yyyy\\}", tyear);
                uri = uri.replaceAll("\\{MM\\}", tmonth);
                uri = uri.replaceAll("\\{dd\\}", tday);
                
                if (getDateMatchedFileCount(new Path(uri)) > 0) {
                    inputlist.add(uri);
                }
            }
            
            if (inputlist.size() <= 0) {
                throw new IOException("there is no matched log file in target month");
            }
            
            inputpaths = new Path[inputlist.size()];
            
            for (int i=0; i < inputlist.size(); i++) {
                String uri = inputlist.get(i);
                inputpaths[i] = new Path(uri);
            }
        }
        
        return inputpaths;
    }
    
    public static String getSaveTransformFilePath(FingraphConfig config,
            String year, String month, String day) throws IOException {
        
        String savepath = null;
        
        String uri = config.getHadoop_user_path()
                + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                + config.getSetting().getHfs_input_path()
                + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/")
                + config.getSetting().getTransform_input_file();
        uri = uri.replaceAll("\\{yyyy\\}", year);
        uri = uri.replaceAll("\\{MM\\}", month);
        uri = uri.replaceAll("\\{dd\\}", day);
        
        savepath = new String(uri);
        
        return savepath;
    }
    
    public static boolean deleteOriginFiles(FingraphConfig config,
            String year, String month, String day) throws IOException {
        
        Configuration conf = new Configuration();
        
        FileSystem hdfs = FileSystem.get(conf);
        
        String root_uri = config.getHadoop_user_path()
                + (config.getHadoop_user_path().endsWith("/") ? "" : "/")
                + config.getSetting().getHfs_input_path()
                + (config.getSetting().getHfs_input_path().endsWith("/") ? "" : "/");
        root_uri = root_uri.replaceAll("\\{yyyy\\}", year);
        root_uri = root_uri.replaceAll("\\{MM\\}", month);
        root_uri = root_uri.replaceAll("\\{dd\\}", day);
        String file_uri = config.getSetting().getOrigin_input_file();
        file_uri = file_uri.replaceAll("\\{yyyy\\}", year);
        file_uri = file_uri.replaceAll("\\{MM\\}", month);
        file_uri = file_uri.replaceAll("\\{dd\\}", day);
        file_uri = file_uri.replace("*", "[\\w]*");
        final String patt = "^" + file_uri + "$";
        //System.out.println(patt);
        
        Path rootPath = new Path(root_uri);
        boolean success = false;
        
        // get matched file list
        PathFilter resultFileFilter = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return path.getName().matches(patt);
            }
        };
        
        try {
            FileStatus[] status = hdfs.listStatus(rootPath, resultFileFilter);
            
            if (status != null) {
                Path[] listedPaths = FileUtil.stat2Paths(status);
                
                if (listedPaths != null) {
                    for (Path path : listedPaths) {
                        success = hdfs.delete(path, true);
                    }
                }
            }
        }
        catch (FileNotFoundException ignore) {}
        
        return success;
    }
}
