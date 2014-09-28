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
import java.util.List;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.common.logger.WorkLogger;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.FormatUtil;
import ph.fingra.hadoop.dbms.parse.prerole.AppkeyReader;
import ph.fingra.hadoop.dbms.parse.prerole.ComponentkeyReader;
import ph.fingra.hadoop.dbms.parse.prerole.domain.Appkey;
import ph.fingra.hadoop.dbms.parse.prerole.domain.Componentkey;
import ph.fingra.hadoop.dbms.parts.prerole.domain.AppLogFirst;
import ph.fingra.hadoop.dbms.parts.prerole.domain.ComponentLogFirst;
import ph.fingra.hadoop.dbms.parts.prerole.service.KeyLogService;
import ph.fingra.hadoop.dbms.parts.prerole.service.KeyLogServiceImpl;

public class KeyLogController {
    
    public int run(String[] args) throws Exception {
        
        String opt_mode = "";
        String opt_target = "";
        
        FingraphConfig fingraphConfig = new FingraphConfig();
        TargetDate targetDate = null;
        
        // get -D optional value
        opt_mode = ArgsOptionUtil.getOption(args, ConstantVars.DOPTION_RUNMODE, "day");
        opt_target = ArgsOptionUtil.getOption(args, ConstantVars.DOPTION_TARGETDATE, "2014-08-20");
        
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
        
        WorkLogger.log(KeyLogController.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate());
        
        // LogCountStatistic's run mode restriction
        if (opt_mode.equals(ConstantVars.RUNMODE_DAY)==false) {
            WorkLogger.warn(KeyLogController.class.getSimpleName()
                    + " : this class can operate only day mode");
            return 0;
        }
        
        // appkey first log
        int status = exeAppLogFirst(fingraphConfig, targetDate);
        
        // componentkey first log
        status = exeComponentLogFirst(fingraphConfig, targetDate);
        
        return status;
    }
    
    public int exeAppLogFirst(FingraphConfig config, TargetDate target)
            throws Exception {
        
        KeyLogService serviceIF = null;
        List<Appkey> src_list = null;
        
        // get prerole/appkey result
        try {
            AppkeyReader reader = new AppkeyReader(config, target);
            src_list = reader.getAppkeyResults();
        }
        catch (IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        if (src_list == null || src_list.size() <= 0) {
            WorkLogger.log("prerole/appkey: empty data");
            return 0;
        }
        
        serviceIF = KeyLogServiceImpl.getInstance();
        
        for (Appkey src : src_list) {
            
            AppLogFirst new_log = new AppLogFirst();
            
            new_log.setAppkey(src.getAppkey());
            new_log.setYear(src.getYear());
            new_log.setMonth(src.getMonth());
            new_log.setDay(src.getDay());
            new_log.setDate(src.getDate());
            
            try {
                
                AppLogFirst old_log = serviceIF.selectAppLogFirst(src.getAppkey());
                
                serviceIF.renewalAppLogFirst(new_log, old_log);
            }
            catch (Exception e) {
                throw e;
            }
        }
        
        return 0;
    }
    
    public int exeComponentLogFirst(FingraphConfig config, TargetDate target)
            throws Exception {
        
        KeyLogService serviceIF = null;
        List<Appkey> key_list = null;
        List<Componentkey> src_list = null;
        
        // get prerole/componentkey result
        try {
            ComponentkeyReader reader = new ComponentkeyReader(config, target);
            key_list = reader.getAppkeyResults();
        }
        catch (IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        if (key_list == null || key_list.size() <= 0) {
            WorkLogger.log("prerole/componentkey: empty data");
            return 0;
        }
        
        serviceIF = KeyLogServiceImpl.getInstance();
        
        for (Appkey key : key_list) {
            
            // get prerole/componentkey result
            try {
                ComponentkeyReader reader = new ComponentkeyReader(config, target);
                src_list = reader.getComponentkeyResults(key.getAppkey());
            }
            catch (IOException ioe) {
                throw new Exception(ioe.getMessage());
            }
            if (src_list == null || src_list.size() <= 0) {
                continue;
            }
            
            for (Componentkey src : src_list) {
                
                ComponentLogFirst new_log = new ComponentLogFirst();
                
                new_log.setAppkey(src.getAppkey());
                new_log.setComponentkey(src.getComponentkey());
                new_log.setYear(src.getYear());
                new_log.setMonth(src.getMonth());
                new_log.setDay(src.getDay());
                new_log.setDate(src.getDate());
                
                try {
                    
                    ComponentLogFirst old_log = serviceIF.selectComponentLogFirst(
                            src.getAppkey(), src.getComponentkey());
                    
                    serviceIF.renewalComponentLogFirst(new_log, old_log);
                }
                catch (Exception e) {
                    throw e;
                }
            }
        }
        
        return 0;
    }
    
    public static void main(String[] args) {
        
        long start_time=0, end_time=0;
        int exitCode = 0;
        
        start_time = System.currentTimeMillis();
        
        WorkLogger.log(KeyLogController.class.getSimpleName()
                + " : Start dbms controller");
        
        try {
            KeyLogController controller = new KeyLogController();
            
            exitCode = controller.run(args);
            
            WorkLogger.log(KeyLogController.class.getSimpleName()
                    + " : End dbms controller");
        }
        catch (Exception e) {
            ErrorLogger.log(KeyLogController.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(KeyLogController.class.getSimpleName()
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
