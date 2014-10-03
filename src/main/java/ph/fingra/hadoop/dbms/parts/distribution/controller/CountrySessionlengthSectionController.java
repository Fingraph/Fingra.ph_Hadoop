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
import ph.fingra.hadoop.dbms.parse.distribution.CountrysessionlengthReader;
import ph.fingra.hadoop.dbms.parse.distribution.domain.Countrysessionlength;
import ph.fingra.hadoop.dbms.parts.distribution.domain.CountrySessionlengthSectionAll;
import ph.fingra.hadoop.dbms.parts.distribution.service.CountrySessionlengthSectionService;
import ph.fingra.hadoop.dbms.parts.distribution.service.CountrySessionlengthSectionServiceImpl;

public class CountrySessionlengthSectionController {
    
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
        
        WorkLogger.log(CountrySessionlengthSectionController.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate());
        
        // country sessionlength section
        int ret = exeCountrySessionlengthSection(fingraphConfig, targetDate);
        
        return ret;
    }
    
    public int exeCountrySessionlengthSection(FingraphConfig config, TargetDate target)
            throws Exception {
        
        CountrySessionlengthSectionService serviceIF = CountrySessionlengthSectionServiceImpl.getInstance();
        CountrysessionlengthReader reader = null;
        List<String> appkey_list = null;
        
        // get distribute/countrysessionlength result
        try {
            reader = new CountrysessionlengthReader(config, target);
            appkey_list = reader.getAppkeyResults();
        }
        catch (IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        if (appkey_list == null || appkey_list.size() <= 0) {
            WorkLogger.log("distribute/countrysessionlength: empty data");
            return 1;
        }
        
        // delete previous data
        try {
            int cnt = 0;
            if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                cnt = serviceIF.selectCountrySessionlengthSectionDayCountByKey(target.getYear(),
                        target.getMonth(), target.getDay(), "", "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                cnt = serviceIF.selectCountrySessionlengthSectionWeekCountByKey(target.getYear(),
                        target.getWeek_str(), "", "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                cnt = serviceIF.selectCountrySessionlengthSectionMonthCountByKey(target.getYear(),
                        target.getMonth(), "", "");
            }
            
            if (cnt > 0) {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    serviceIF.deleteCountrySessionlengthSectionDayByDate(target.getYear(),
                            target.getMonth(), target.getDay());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    serviceIF.deleteCountrySessionlengthSectionWeekByDate(target.getYear(),
                            target.getWeek_str());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    serviceIF.deleteCountrySessionlengthSectionMonthByDate(target.getYear(),
                            target.getMonth());
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        for (String appkey : appkey_list) {
            
            List<String> countrycode_list = null;
            List<CountrySessionlengthSectionAll> indst_list = new ArrayList<CountrySessionlengthSectionAll>();
            Map<String, CountrySessionlengthSectionAll> dst_map = new HashMap<String, CountrySessionlengthSectionAll>();
            
            // get distribute/countrysessionlength result
            try {
                countrycode_list = reader.getCountrycodeResults(appkey);
            }
            catch (IOException ioe) {
                throw new Exception(ioe.getMessage());
            }
            if (countrycode_list == null || countrycode_list.size() <= 0) {
                continue;
            }
            
            for (String countrycode : countrycode_list) {
                
                List<Countrysessionlength> src_list = null;
                
                // get distribute/countrysessionlength result
                try {
                    src_list = reader.getCountrysessionlengthResults(appkey, countrycode);
                }
                catch (IOException ioe) {
                    throw new Exception(ioe.getMessage());
                }
                if (src_list == null || src_list.size() <= 0) {
                    continue;
                }
                
                for (Countrysessionlength src : src_list) {
                    
                    if (dst_map.containsKey(countrycode)) {
                        
                        CountrySessionlengthSectionAll prev_row = dst_map.get(countrycode);
                        
                        CountrySessionlengthSectionAll new_row = new CountrySessionlengthSectionAll();
                        new_row.copy(prev_row);
                        
                        new_row.setYear(src.getYear());
                        new_row.setMonth(src.getMonth());
                        new_row.setWeek(src.getWeek());
                        new_row.setDay(src.getDay());
                        new_row.setHour(src.getHour());
                        new_row.setAppkey(src.getAppkey());
                        new_row.setCountry(src.getCountry());
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
                        
                        dst_map.remove(countrycode);
                        dst_map.put(countrycode, new_row);
                    }
                    else {
                        
                        CountrySessionlengthSectionAll new_row = new CountrySessionlengthSectionAll();
                        
                        new_row.setYear(src.getYear());
                        new_row.setMonth(src.getMonth());
                        new_row.setWeek(src.getWeek());
                        new_row.setDay(src.getDay());
                        new_row.setHour(src.getHour());
                        new_row.setAppkey(src.getAppkey());
                        new_row.setCountry(src.getCountry());
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
                        
                        dst_map.put(countrycode, new_row);
                    }
                }
                
            }
            
            // map to list
            Iterator<CountrySessionlengthSectionAll> it = dst_map.values().iterator();
            while (it.hasNext()) {
                CountrySessionlengthSectionAll dst = it.next();
                indst_list.add(dst);
            }
            
            if (indst_list.size() <= 0) {
                continue;
            }
            
            @SuppressWarnings("unused")
            int ins_ret = 0;
            try {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    ins_ret = serviceIF.insertBatchCountrySessionlengthSectionDay(indst_list);
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    ins_ret = serviceIF.insertBatchCountrySessionlengthSectionWeek(indst_list);
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    ins_ret = serviceIF.insertBatchCountrySessionlengthSectionMonth(indst_list);
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
        
        WorkLogger.log(CountrySessionlengthSectionController.class.getSimpleName()
                + " : Start dbms controller");
        
        try {
            CountrySessionlengthSectionController controller = new CountrySessionlengthSectionController();
            
            exitCode = controller.run(args);
            
            WorkLogger.log(CountrySessionlengthSectionController.class.getSimpleName()
                    + " : End dbms controller");
        }
        catch (Exception e) {
            ErrorLogger.log(CountrySessionlengthSectionController.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(CountrySessionlengthSectionController.class.getSimpleName()
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
