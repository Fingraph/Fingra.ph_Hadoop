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

package ph.fingra.hadoop.dbms.parts.prerole.controller;

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
import ph.fingra.hadoop.dbms.parse.prerole.LogcountReader;
import ph.fingra.hadoop.dbms.parse.prerole.domain.Logcount;
import ph.fingra.hadoop.dbms.parts.prerole.domain.LogsAll;
import ph.fingra.hadoop.dbms.parts.prerole.service.LogsService;
import ph.fingra.hadoop.dbms.parts.prerole.service.LogsServiceImpl;

public class LogsController {
    
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
        
        WorkLogger.log(LogsController.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate());
        
        // LogCountStatistic's run mode restriction
        if (opt_mode.equals(ConstantVars.RUNMODE_DAY)==false) {
            WorkLogger.warn(LogsController.class.getSimpleName()
                    + " : this class can operate only day mode");
            return 0;
        }
        
        // logs count
        int ret = exeLogs(fingraphConfig, targetDate);
        
        return ret;
    }
    
    public int exeLogs(FingraphConfig config, TargetDate target)
            throws Exception {
        
        LogsService serviceIF = LogsServiceImpl.getInstance();
        List<Logcount> src_list = null;
        List<LogsAll> indst_list = new ArrayList<LogsAll>();
        
        // get prerole/logcount result
        try {
            LogcountReader reader = new LogcountReader(config, target);
            src_list = reader.getLogcountResults();
        }
        catch (IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        if (src_list == null || src_list.size() <= 0) {
            WorkLogger.log("prerole/logcount: empty data");
            return 1;
        }
        
        // delete previous data
        try {
            int cnt = serviceIF.selectLogsDayCountByKey(target.getYear(),
                    target.getMonth(), target.getDay(), "");
            if (cnt > 0) {
                serviceIF.deleteLogsDayByDate(target.getYear(), target.getMonth(),
                        target.getDay());
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        for (Logcount src : src_list) {
            
            LogsAll new_row = new LogsAll();
            
            new_row.setYear(src.getYear());
            new_row.setMonth(src.getMonth());
            new_row.setDay(src.getDay());
            new_row.setAppkey(src.getAppkey());
            new_row.setDate(src.getDate());
            new_row.setDayofweek(src.getDayofweek());
            new_row.setTotal(src.getTotal());
            
            indst_list.add(new_row);
        }
        
        @SuppressWarnings("unused")
        int ins_ret = 0;
        try {
            ins_ret = serviceIF.insertBatchLogsDay(indst_list);
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
        
        WorkLogger.log(LogsController.class.getSimpleName()
                + " : Start dbms controller");
        
        try {
            LogsController controller = new LogsController();
            
            exitCode = controller.run(args);
            
            WorkLogger.log(LogsController.class.getSimpleName()
                    + " : End dbms controller");
        }
        catch (Exception e) {
            ErrorLogger.log(LogsController.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(LogsController.class.getSimpleName()
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
