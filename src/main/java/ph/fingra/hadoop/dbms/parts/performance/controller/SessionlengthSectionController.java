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
import ph.fingra.hadoop.dbms.parse.performance.SessionlengthReader;
import ph.fingra.hadoop.dbms.parse.performance.domain.Sessionlength;
import ph.fingra.hadoop.dbms.parts.performance.domain.SessionlengthSectionAll;
import ph.fingra.hadoop.dbms.parts.performance.service.SessionlengthSectionService;
import ph.fingra.hadoop.dbms.parts.performance.service.SessionlengthSectionServiceImpl;

public class SessionlengthSectionController {
    
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
        
        WorkLogger.log(SessionlengthSectionController.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate());
        
        // sessionlength section
        int ret = exeSessionlengthSection(fingraphConfig, targetDate);
        
        return ret;
    }
    
    public int exeSessionlengthSection(FingraphConfig config, TargetDate target)
            throws Exception {
        
        SessionlengthSectionService serviceIF = SessionlengthSectionServiceImpl.getInstance();
        List<String> appkey_list = null;
        List<SessionlengthSectionAll> indst_list = new ArrayList<SessionlengthSectionAll>();
        Map<String, SessionlengthSectionAll> dst_map = new HashMap<String, SessionlengthSectionAll>();
        
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
                cnt = serviceIF.selectSessionlengthSectionDayCountByKey(target.getYear(),
                        target.getMonth(), target.getDay(), "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                cnt = serviceIF.selectSessionlengthSectionWeekCountByKey(target.getYear(),
                        target.getWeek_str(), "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                cnt = serviceIF.selectSessionlengthSectionMonthCountByKey(target.getYear(),
                        target.getMonth(), "");
            }
            
            if (cnt > 0) {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    serviceIF.deleteSessionlengthSectionDayByDate(target.getYear(),
                            target.getMonth(), target.getDay());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    serviceIF.deleteSessionlengthSectionWeekByDate(target.getYear(),
                            target.getWeek_str());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    serviceIF.deleteSessionlengthSectionMonthByDate(target.getYear(),
                            target.getMonth());
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        for (String appkey : appkey_list) {
            
            List<Sessionlength> src_list = null;
            
            // get perform/sessionlength result
            try {
                SessionlengthReader reader = new SessionlengthReader(config, target);
                src_list = reader.getSessionlengthResults(appkey);
            }
            catch (IOException ioe) {
                throw new Exception(ioe.getMessage());
            }
            if (src_list == null || src_list.size() <= 0) {
                continue;
            }
            
            for (Sessionlength src : src_list) {
                
                if (dst_map.containsKey(appkey)) {
                    
                    SessionlengthSectionAll prev_row = dst_map.get(appkey);
                    
                    SessionlengthSectionAll new_row = new SessionlengthSectionAll();
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
                    if (src.getSecondsection() >= 0) {
                        if (src.getSecondsection() < 3)
                            new_row.setUnder_three_sec(prev_row.getUnder_three_sec() + src.getSessioncount());
                        else if (src.getSecondsection() >= 3 && src.getSecondsection() < 10)
                            new_row.setThree_ten_sec(prev_row.getThree_ten_sec() + src.getSessioncount());
                        else if (src.getSecondsection() >= 10 && src.getSecondsection() < 30)
                            new_row.setTen_thirty_sec(prev_row.getTen_thirty_sec() + src.getSessioncount());
                        else if (src.getSecondsection() >= 30 && src.getSecondsection() < 60)
                            new_row.setThirty_sixty_sec(prev_row.getThirty_sixty_sec() + src.getSessioncount());
                        else if (src.getSecondsection() >= ConstantVars.RUNTIME_1_MIN/*1*60*/ && src.getSecondsection() < ConstantVars.RUNTIME_3_MIN/*3*60*/)
                            new_row.setOne_three_min(prev_row.getOne_three_min() + src.getSessioncount());
                        else if (src.getSecondsection() >= ConstantVars.RUNTIME_3_MIN/*3*60*/ && src.getSecondsection() < ConstantVars.RUNTIME_10_MIN/*10*60*/)
                            new_row.setThree_ten_min(prev_row.getThree_ten_min() + src.getSessioncount());
                        else if (src.getSecondsection() >= ConstantVars.RUNTIME_10_MIN/*10*60*/ && src.getSecondsection() < ConstantVars.RUNTIME_30_MIN/*30*60*/)
                            new_row.setTen_thirty_min(prev_row.getTen_thirty_min() + src.getSessioncount());
                        else if (src.getSecondsection() >= ConstantVars.RUNTIME_30_MIN/*30*60*/)
                            new_row.setOver_thirty_min(prev_row.getOver_thirty_min() + src.getSessioncount());
                    }
                    
                    dst_map.remove(appkey);
                    dst_map.put(appkey, new_row);
                }
                else {
                    
                    SessionlengthSectionAll new_row = new SessionlengthSectionAll();
                    
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
                    if (src.getSecondsection() >= 0) {
                        if (src.getSecondsection() < 3)
                            new_row.setUnder_three_sec(src.getSessioncount());
                        else if (src.getSecondsection() >= 3 && src.getSecondsection() < 10)
                            new_row.setThree_ten_sec(src.getSessioncount());
                        else if (src.getSecondsection() >= 10 && src.getSecondsection() < 30)
                            new_row.setTen_thirty_sec(src.getSessioncount());
                        else if (src.getSecondsection() >= 30 && src.getSecondsection() < 60)
                            new_row.setThirty_sixty_sec(src.getSessioncount());
                        else if (src.getSecondsection() >= ConstantVars.RUNTIME_1_MIN/*1*60*/ && src.getSecondsection() < ConstantVars.RUNTIME_3_MIN/*3*60*/)
                            new_row.setOne_three_min(src.getSessioncount());
                        else if (src.getSecondsection() >= ConstantVars.RUNTIME_3_MIN/*3*60*/ && src.getSecondsection() < ConstantVars.RUNTIME_10_MIN/*10*60*/)
                            new_row.setThree_ten_min(src.getSessioncount());
                        else if (src.getSecondsection() >= ConstantVars.RUNTIME_10_MIN/*10*60*/ && src.getSecondsection() < ConstantVars.RUNTIME_30_MIN/*30*60*/)
                            new_row.setTen_thirty_min(src.getSessioncount());
                        else if (src.getSecondsection() >= ConstantVars.RUNTIME_30_MIN/*30*60*/)
                            new_row.setOver_thirty_min(src.getSessioncount());
                    }
                    
                    dst_map.put(appkey, new_row);
                }
            }
            
        }
        
        // map to list
        Iterator<SessionlengthSectionAll> it = dst_map.values().iterator();
        while (it.hasNext()) {
            SessionlengthSectionAll dst = it.next();
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
                ins_ret = serviceIF.insertBatchSessionlengthSectionDay(indst_list);
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                ins_ret = serviceIF.insertBatchSessionlengthSectionWeek(indst_list);
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                ins_ret = serviceIF.insertBatchSessionlengthSectionMonth(indst_list);
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
        
        WorkLogger.log(SessionlengthSectionController.class.getSimpleName()
                + " : Start dbms controller");
        
        try {
            SessionlengthSectionController controller = new SessionlengthSectionController();
            
            exitCode = controller.run(args);
            
            WorkLogger.log(SessionlengthSectionController.class.getSimpleName()
                    + " : End dbms controller");
        }
        catch (Exception e) {
            ErrorLogger.log(SessionlengthSectionController.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(SessionlengthSectionController.class.getSimpleName()
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
