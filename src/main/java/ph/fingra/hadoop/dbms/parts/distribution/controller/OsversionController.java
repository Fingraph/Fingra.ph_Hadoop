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

package ph.fingra.hadoop.dbms.parts.distribution.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.dbms.parse.distribution.OsversionReader;
import ph.fingra.hadoop.dbms.parse.distribution.domain.Osversion;
import ph.fingra.hadoop.dbms.parts.distribution.domain.OsversionAll;
import ph.fingra.hadoop.dbms.parts.distribution.service.OsversionService;
import ph.fingra.hadoop.dbms.parts.distribution.service.OsversionServiceImpl;

public class OsversionController {
    
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
        
        WorkLogger.log(OsversionController.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate());
        
        // osversion user & session
        int ret = exeOsversion(fingraphConfig, targetDate);
        
        return ret;
    }
    
    public int exeOsversion(FingraphConfig config, TargetDate target)
            throws Exception {
        
        OsversionService serviceIF = OsversionServiceImpl.getInstance();
        List<String> appkey_list = null;
        
        // get distribute/osversion result
        try {
            OsversionReader reader = new OsversionReader(config, target);
            appkey_list = reader.getAppkeyResults();
        }
        catch (IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        if (appkey_list == null || appkey_list.size() <= 0) {
            WorkLogger.log("distribute/osversion: empty data");
            return 1;
        }
        
        // delete previous data
        try {
            int cnt = 0;
            if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                cnt = serviceIF.selectOsversionDayCountByKey(target.getYear(),
                        target.getMonth(), target.getDay(), "", "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                cnt = serviceIF.selectOsversionWeekCountByKey(target.getYear(),
                        target.getWeek_str(), "", "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                cnt = serviceIF.selectOsversionMonthCountByKey(target.getYear(),
                        target.getMonth(), "", "");
            }
            
            if (cnt > 0) {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    serviceIF.deleteOsversionDayByDate(target.getYear(),
                            target.getMonth(), target.getDay());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    serviceIF.deleteOsversionWeekByDate(target.getYear(),
                            target.getWeek_str());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    serviceIF.deleteOsversionMonthByDate(target.getYear(),
                            target.getMonth());
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        for (String appkey : appkey_list) {
            
            List<Osversion> src_list = null;
            List<OsversionAll> indst_list = new ArrayList<OsversionAll>();
            
            // get distribute/osversion result
            try {
                OsversionReader reader = new OsversionReader(config, target);
                src_list = reader.getOsversionResults(appkey);
            }
            catch (IOException ioe) {
                throw new Exception(ioe.getMessage());
            }
            if (src_list == null || src_list.size() <= 0) {
                continue;
            }
            
            for (Osversion src : src_list) {
                
                OsversionAll new_row = new OsversionAll();
                
                new_row.setYear(src.getYear());
                new_row.setMonth(src.getMonth());
                new_row.setWeek(src.getWeek());
                new_row.setDay(src.getDay());
                new_row.setHour(src.getHour());
                new_row.setAppkey(src.getAppkey());
                new_row.setOsversion(src.getOsversion());
                new_row.setDate(src.getDate());
                new_row.setDatehour(src.getDatehour());
                new_row.setDayofweek(src.getDayofweek());
                new_row.setFromdate(src.getFromdate());
                new_row.setTodate(src.getTodate());
                new_row.setUser(src.getUsercount());
                new_row.setSession(src.getSessioncount());
                
                indst_list.add(new_row);
            }
            
            if (indst_list.size() <= 0) {
                continue;
            }
            
            @SuppressWarnings("unused")
            int ins_ret = 0;
            try {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    ins_ret = serviceIF.insertBatchOsversionDay(indst_list);
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    ins_ret = serviceIF.insertBatchOsversionWeek(indst_list);
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    ins_ret = serviceIF.insertBatchOsversionMonth(indst_list);
                }
            }
            catch (Exception e) {
                throw e;
            }
        }
        
        return 1;
    }
    
    public static void main(String[] args) {
        
        long start_time=0, end_time=0;
        int exitCode = 0;
        
        start_time = System.currentTimeMillis();
        
        WorkLogger.log(OsversionController.class.getSimpleName()
                + " : Start dbms controller");
        
        try {
            OsversionController controller = new OsversionController();
            
            exitCode = controller.run(args);
            
            WorkLogger.log(OsversionController.class.getSimpleName()
                    + " : End dbms controller");
        }
        catch (Exception e) {
            ErrorLogger.log(OsversionController.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(OsversionController.class.getSimpleName()
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
