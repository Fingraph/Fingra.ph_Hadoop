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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.dbms.parse.performance.FrequencyReader;
import ph.fingra.hadoop.dbms.parse.performance.domain.Frequency;
import ph.fingra.hadoop.dbms.parts.performance.domain.FrequencyAll;
import ph.fingra.hadoop.dbms.parts.performance.service.FrequencyService;
import ph.fingra.hadoop.dbms.parts.performance.service.FrequencyServiceImpl;

public class FrequencyController {
    
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
        
        WorkLogger.log(FrequencyController.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate());
        
        // frequency
        int ret = exeFrequency(fingraphConfig, targetDate);
        
        return ret;
    }
    
    public int exeFrequency(FingraphConfig config, TargetDate target)
            throws Exception {
        
        FrequencyService serviceIF = FrequencyServiceImpl.getInstance();
        List<String> appkey_list = null;
        List<FrequencyAll> indst_list = new ArrayList<FrequencyAll>();
        Map<String, FrequencyAll> dst_map = new HashMap<String, FrequencyAll>();
        
        // get perform/frequency result
        try {
            FrequencyReader reader = new FrequencyReader(config, target);
            appkey_list = reader.getAppkeyResults();
        }
        catch (IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        if (appkey_list == null || appkey_list.size() <= 0) {
            WorkLogger.log("perform/frequency: empty data");
            return 1;
        }
        
        // delete previous data
        try {
            int cnt = 0;
            if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                cnt = serviceIF.selectFrequencyDayCountByKey(target.getYear(),
                        target.getMonth(), target.getDay(), "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                cnt = serviceIF.selectFrequencyWeekCountByKey(target.getYear(),
                        target.getWeek_str(), "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                cnt = serviceIF.selectFrequencyMonthCountByKey(target.getYear(),
                        target.getMonth(), "");
            }
            
            if (cnt > 0) {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    serviceIF.deleteFrequencyDayByDate(target.getYear(),
                            target.getMonth(), target.getDay());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    serviceIF.deleteFrequencyWeekByDate(target.getYear(),
                            target.getWeek_str());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    serviceIF.deleteFrequencyMonthByDate(target.getYear(),
                            target.getMonth());
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        for (String appkey : appkey_list) {
            
            List<Frequency> src_list = null;
            
            // get perform/frequency result
            try {
                FrequencyReader reader = new FrequencyReader(config, target);
                src_list = reader.getFrequencyResults(appkey);
            }
            catch (IOException ioe) {
                throw new Exception(ioe.getMessage());
            }
            if (src_list == null || src_list.size() <= 0) {
                continue;
            }
            
            for (Frequency src : src_list) {
                
                if (dst_map.containsKey(appkey)) {
                    
                    FrequencyAll prev_row = dst_map.get(appkey);
                    
                    FrequencyAll new_row = new FrequencyAll();
                    new_row.copy(prev_row);
                    
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
                    if (src.getSessionfrequency() > 0) {
                        if (src.getSessionfrequency() == 1)
                            new_row.setFreq_user_1(prev_row.getFreq_user_1() + src.getUsercount());
                        else if (src.getSessionfrequency() == 2)
                            new_row.setFreq_user_2(prev_row.getFreq_user_2() + src.getUsercount());
                        else if (src.getSessionfrequency() >= 3 && src.getSessionfrequency() <= 4)
                            new_row.setFreq_user_3_4(prev_row.getFreq_user_3_4() + src.getUsercount());
                        else if (src.getSessionfrequency() >= 5 && src.getSessionfrequency() <= 6)
                            new_row.setFreq_user_5_6(prev_row.getFreq_user_5_6() + src.getUsercount());
                        else if (src.getSessionfrequency() >= 7 && src.getSessionfrequency() <= 9)
                            new_row.setFreq_user_7_9(prev_row.getFreq_user_7_9() + src.getUsercount());
                        else if (src.getSessionfrequency() >= 10 && src.getSessionfrequency() <= 14)
                            new_row.setFreq_user_10_14(prev_row.getFreq_user_10_14() + src.getUsercount());
                        else if (src.getSessionfrequency() >= 15 && src.getSessionfrequency() <= 19)
                            new_row.setFreq_user_15_19(prev_row.getFreq_user_15_19() + src.getUsercount());
                        else if (src.getSessionfrequency() >= 20 && src.getSessionfrequency() <= 49)
                            new_row.setFreq_user_20_49(prev_row.getFreq_user_20_49() + src.getUsercount());
                        else if (src.getSessionfrequency() >= 50 && src.getSessionfrequency() <= 99)
                            new_row.setFreq_user_50_99(prev_row.getFreq_user_50_99() + src.getUsercount());
                        else if (src.getSessionfrequency() >= 100 && src.getSessionfrequency() <= 499)
                            new_row.setFreq_user_100_499(prev_row.getFreq_user_100_499() + src.getUsercount());
                        else if (src.getSessionfrequency() >= 500)
                            new_row.setFreq_user_over_500(prev_row.getFreq_user_over_500() + src.getUsercount());
                    }
                    
                    dst_map.remove(appkey);
                    dst_map.put(appkey, new_row);
                }
                else {
                    
                    FrequencyAll new_row = new FrequencyAll();
                    
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
                    if (src.getSessionfrequency() > 0) {
                        if (src.getSessionfrequency() == 1)
                            new_row.setFreq_user_1(src.getUsercount());
                        else if (src.getSessionfrequency() == 2)
                            new_row.setFreq_user_2(src.getUsercount());
                        else if (src.getSessionfrequency() >= 3 && src.getSessionfrequency() <= 4)
                            new_row.setFreq_user_3_4(src.getUsercount());
                        else if (src.getSessionfrequency() >= 5 && src.getSessionfrequency() <= 6)
                            new_row.setFreq_user_5_6(src.getUsercount());
                        else if (src.getSessionfrequency() >= 7 && src.getSessionfrequency() <= 9)
                            new_row.setFreq_user_7_9(src.getUsercount());
                        else if (src.getSessionfrequency() >= 10 && src.getSessionfrequency() <= 14)
                            new_row.setFreq_user_10_14(src.getUsercount());
                        else if (src.getSessionfrequency() >= 15 && src.getSessionfrequency() <= 19)
                            new_row.setFreq_user_15_19(src.getUsercount());
                        else if (src.getSessionfrequency() >= 20 && src.getSessionfrequency() <= 49)
                            new_row.setFreq_user_20_49(src.getUsercount());
                        else if (src.getSessionfrequency() >= 50 && src.getSessionfrequency() <= 99)
                            new_row.setFreq_user_50_99(src.getUsercount());
                        else if (src.getSessionfrequency() >= 100 && src.getSessionfrequency() <= 499)
                            new_row.setFreq_user_100_499(src.getUsercount());
                        else if (src.getSessionfrequency() >= 500)
                            new_row.setFreq_user_over_500(src.getUsercount());
                    }
                    
                    dst_map.put(appkey, new_row);
                }
            }
            
        }
        
        // map to list
        Iterator<FrequencyAll> it = dst_map.values().iterator();
        while (it.hasNext()) {
            FrequencyAll dst = it.next();
            indst_list.add(dst);
        }
        
        if (indst_list.size() <= 0) {
            WorkLogger.log("batch list: empty data");
            return 1;
        }
        
        @SuppressWarnings("unused")
        int ins_ret = 0;
        try {
            if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                ins_ret = serviceIF.insertBatchFrequencyDay(indst_list);
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                ins_ret = serviceIF.insertBatchFrequencyWeek(indst_list);
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                ins_ret = serviceIF.insertBatchFrequencyMonth(indst_list);
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
        
        WorkLogger.log(FrequencyController.class.getSimpleName()
                + " : Start dbms controller");
        
        try {
            FrequencyController controller = new FrequencyController();
            
            exitCode = controller.run(args);
            
            WorkLogger.log(FrequencyController.class.getSimpleName()
                    + " : End dbms controller");
        }
        catch (Exception e) {
            ErrorLogger.log(FrequencyController.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(FrequencyController.class.getSimpleName()
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
