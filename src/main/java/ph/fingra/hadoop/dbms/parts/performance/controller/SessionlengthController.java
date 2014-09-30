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

package ph.fingra.hadoop.dbms.parts.performance.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.dbms.parse.performance.SessionlengthReader;
import ph.fingra.hadoop.dbms.parse.performance.domain.Sessionlength;
import ph.fingra.hadoop.dbms.parts.performance.domain.SessionlengthAll;
import ph.fingra.hadoop.dbms.parts.performance.service.SessionlengthService;
import ph.fingra.hadoop.dbms.parts.performance.service.SessionlengthServiceImpl;

public class SessionlengthController {
    
    // sessionlength(second) - sessioncount pair class
    private class SecondSessionPair {
        public int second;
        public long session;
        public SecondSessionPair() {
            this.second = 0;
            this.session = 0;
        }
        public void copy(SecondSessionPair src) {
            this.second = src.second;
            this.session = src.session;
        }
    }
    
    // collection sort class
    private class SecondComp implements Comparator<SecondSessionPair> {
        @Override
        public int compare(SecondSessionPair o1, SecondSessionPair o2) {
            return (o1.second == o2.second) ? 0:((o1.second < o2.second) ? -1:1);
        }
    }
    
    // get sessionlength median value
    private double getMedian(SecondSessionPair[] pairlist, int totsession) {
        if (pairlist == null || pairlist.length <= 0)
            return 0;   // empty array -> 0
        
        double median = 0.0;
        int center = totsession / 2;
        int pos1 = 0, pos2 = 0;
        
        if (totsession % 2 == 1) {
            pos1 = pos2 = center;
        }
        else {
            pos1 = center - 1;
            pos2 = center;
        }
        
        long sum_count = 0;
        SecondSessionPair prev_pair = new SecondSessionPair();
        for (int i=0; i<pairlist.length; i++) {
            SecondSessionPair pair = pairlist[i];
            
            sum_count += pair.session;
            
            if (sum_count > pos2) {
                if ((pos1 >= (sum_count - pair.session)) && (pos2 <= sum_count)) {
                    median = pair.second;
                }
                else {
                    median = ((double)(prev_pair.second + pair.second)) / 2.0;
                }
                break;
            }
            
            prev_pair.copy(pair);
        }
        
        return median;
    }
    
    // get total use time
    public long getTotaltime(SecondSessionPair[] pairlist) {
        if (pairlist == null || pairlist.length <= 0)
            return 0;   // empty array -> 0
        
        long totaltime = 0;
        for (int i=0; i<pairlist.length; i++) {
            SecondSessionPair pair = pairlist[i];
            totaltime += (pair.second * pair.session);
        }
        
        return totaltime;
    }
    
    public int run(String[] args) throws Exception {
        
        String opt_mode = "";
        String opt_target = "";
        
        FingraphConfig fingraphConfig = new FingraphConfig();
        TargetDate targetDate = null;
        
        // get -D optional value
        opt_mode = ArgsOptionUtil.getOption(args, ConstantVars.DOPTION_RUNMODE, "");
        opt_target = ArgsOptionUtil.getOption(args, ConstantVars.DOPTION_TARGETDATE, "");
        
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
        
        WorkLogger.log(SessionlengthController.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate());
        
        // sessionlength & totaltime
        int ret = exeSessionlength(fingraphConfig, targetDate);
        
        return ret;
    }
    
    public int exeSessionlength(FingraphConfig config, TargetDate target)
            throws Exception {
        
        SessionlengthService serviceIF = SessionlengthServiceImpl.getInstance();
        List<String> appkey_list = null;
        List<SessionlengthAll> indst_list = new ArrayList<SessionlengthAll>();
        
        // get perform/sessionlength result
        try {
            SessionlengthReader reader = new SessionlengthReader(config, target);
            appkey_list = reader.getAppkeyResults();
        }
        catch (IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        if (appkey_list == null || appkey_list.size() <= 0) {
            WorkLogger.log("perform/sessionlength: empty data");
            return 1;
        }
        
        // delete previous data
        try {
            int cnt = 0;
            if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                cnt = serviceIF.selectSessionlengthDayCountByKey(target.getYear(),
                        target.getMonth(), target.getDay(), "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                cnt = serviceIF.selectSessionlengthWeekCountByKey(target.getYear(),
                        target.getWeek_str(), "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                cnt = serviceIF.selectSessionlengthMonthCountByKey(target.getYear(),
                        target.getMonth(), "");
            }
            
            if (cnt > 0) {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    serviceIF.deleteSessionlengthDayByDate(target.getYear(),
                            target.getMonth(), target.getDay());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    serviceIF.deleteSessionlengthWeekByDate(target.getYear(),
                            target.getWeek_str());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    serviceIF.deleteSessionlengthMonthByDate(target.getYear(),
                            target.getMonth());
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        for (String appkey : appkey_list) {
            
            List<Sessionlength> src_list = null;
            List<SecondSessionPair> secondsessionpair_list = new ArrayList<SecondSessionPair>();
            int totsession = 0;
            double median = 0.0;
            long totaltime = 0;
            
            // get perform/sessionlength result
            try {
                SessionlengthReader reader = new SessionlengthReader(config, target);
                src_list = reader.getSessionlengthResults(appkey);
            }
            catch (IOException ioe) {
                throw new Exception(ioe.getMessage());
            }
            if (src_list == null || src_list.size() <= 0) {
                WorkLogger.log("perform/sessionlength: empty data");
                return 1;
            }
            
            for (Sessionlength second : src_list) {
                SecondSessionPair pair = new SecondSessionPair();
                pair.second = second.getSecondsection();
                pair.session = second.getSessioncount();
                secondsessionpair_list.add(pair);
                totsession += second.getSessioncount();
            }
            if (secondsessionpair_list.size() <= 0) {
                median = 0.0;
                totaltime = 0;
            }
            else {
                Collections.sort(secondsessionpair_list, new SecondComp());
                
                SecondSessionPair[] pair_array =
                        secondsessionpair_list.toArray(new SecondSessionPair[secondsessionpair_list.size()]);
                
                median = getMedian(pair_array, totsession);
                totaltime = getTotaltime(pair_array);
            }
            
            Sessionlength src = src_list.get(0);
            
            SessionlengthAll new_row = new SessionlengthAll();
            
            new_row.setYear(src.getYear());
            new_row.setMonth(src.getMonth());
            new_row.setWeek(src.getWeek());
            new_row.setDay(src.getDay());
            new_row.setHour(src.getHour());
            new_row.setAppkey(src.getAppkey());
            new_row.setDate(src.getDate());
            new_row.setDatehour(src.getDatehour());
            new_row.setDayofweek(src.getDayofweek());
            new_row.setFromdate(src.getFromdate());
            new_row.setTodate(src.getTodate());
            new_row.setSessionlength(median);
            new_row.setTotaltime(totaltime);
            
            indst_list.add(new_row);
        }
        
        @SuppressWarnings("unused")
        int ins_ret = 0;
        try {
            if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                ins_ret = serviceIF.insertBatchSessionlengthDay(indst_list);
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                ins_ret = serviceIF.insertBatchSessionlengthWeek(indst_list);
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                ins_ret = serviceIF.insertBatchSessionlengthMonth(indst_list);
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        return 1;
    }
    
    public static void main(String[] args) {
        
        long start_time=0, end_time=0;
        int exitCode = 0;
        
        start_time = System.currentTimeMillis();
        
        WorkLogger.log(SessionlengthController.class.getSimpleName()
                + " : Start dbms controller");
        
        try {
            SessionlengthController controller = new SessionlengthController();
            
            exitCode = controller.run(args);
            
            WorkLogger.log(SessionlengthController.class.getSimpleName()
                    + " : End dbms controller");
        }
        catch (Exception e) {
            ErrorLogger.log(SessionlengthController.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(SessionlengthController.class.getSimpleName()
                    + " : Failed dbms controller");
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
