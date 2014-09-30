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
import java.util.List;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.dbms.parse.performance.PageviewReader;
import ph.fingra.hadoop.dbms.parse.performance.domain.Pageview;
import ph.fingra.hadoop.dbms.parts.performance.domain.PageviewAll;
import ph.fingra.hadoop.dbms.parts.performance.service.PageviewService;
import ph.fingra.hadoop.dbms.parts.performance.service.PageviewServiceImpl;

public class PageviewController {
    
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
        
        WorkLogger.log(PageviewController.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate());
        
        // pageview count
        int ret = exePageview(fingraphConfig, targetDate);
        
        return ret;
    }
    
    public int exePageview(FingraphConfig config, TargetDate target)
            throws Exception {
        
        PageviewService serviceIF = PageviewServiceImpl.getInstance();
        List<Pageview> src_list = null;
        List<PageviewAll> indst_list = new ArrayList<PageviewAll>();
        
        // get perform/pageview result
        try {
            PageviewReader reader = new PageviewReader(config, target);
            src_list = reader.getPageviewResults();
        }
        catch (IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        if (src_list == null || src_list.size() <= 0) {
            WorkLogger.log("perform/pageview: empty data");
            return 1;
        }
        
        // delete previous data
        try {
            int cnt = 0;
            if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                cnt = serviceIF.selectPageviewDayCountByKey(target.getYear(),
                        target.getMonth(), target.getDay(), "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                cnt = serviceIF.selectPageviewWeekCountByKey(target.getYear(),
                        target.getWeek_str(), "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                cnt = serviceIF.selectPageviewMonthCountByKey(target.getYear(),
                        target.getMonth(), "");
            }
            
            if (cnt > 0) {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    serviceIF.deletePageviewDayByDate(target.getYear(),
                            target.getMonth(), target.getDay());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    serviceIF.deletePageviewWeekByDate(target.getYear(),
                            target.getWeek_str());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    serviceIF.deletePageviewMonthByDate(target.getYear(),
                            target.getMonth());
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        for (Pageview src : src_list) {
            
            PageviewAll new_row = new PageviewAll();
            
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
            new_row.setPageview(src.getPageviewcount());
            
            indst_list.add(new_row);
        }
        
        @SuppressWarnings("unused")
        int ins_ret = 0;
        try {
            if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                ins_ret = serviceIF.insertBatchPageviewDay(indst_list);
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                ins_ret = serviceIF.insertBatchPageviewWeek(indst_list);
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                ins_ret = serviceIF.insertBatchPageviewMonth(indst_list);
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
        
        WorkLogger.log(PageviewController.class.getSimpleName()
                + " : Start dbms controller");
        
        try {
            PageviewController controller = new PageviewController();
            
            exitCode = controller.run(args);
            
            WorkLogger.log(PageviewController.class.getSimpleName()
                    + " : End dbms controller");
        }
        catch (Exception e) {
            ErrorLogger.log(PageviewController.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(PageviewController.class.getSimpleName()
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
