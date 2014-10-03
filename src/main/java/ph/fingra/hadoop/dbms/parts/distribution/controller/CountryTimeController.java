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
import ph.fingra.hadoop.dbms.parse.distribution.CountryhoursessionReader;
import ph.fingra.hadoop.dbms.parse.distribution.domain.Countryhoursession;
import ph.fingra.hadoop.dbms.parts.distribution.domain.CountryTimeAll;
import ph.fingra.hadoop.dbms.parts.distribution.service.CountryTimeService;
import ph.fingra.hadoop.dbms.parts.distribution.service.CountryTimeServiceImpl;

public class CountryTimeController {
    
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
        
        WorkLogger.log(CountryTimeController.class.getSimpleName()
                + " : [run mode] " + opt_mode
                + " , [target date] " + targetDate.getFulldate());
        
        // country time
        int ret = exeCountryTime(fingraphConfig, targetDate);
        
        return ret;
    }
    
    public int exeCountryTime(FingraphConfig config, TargetDate target)
            throws Exception {
        
        CountryTimeService serviceIF = CountryTimeServiceImpl.getInstance();
        CountryhoursessionReader reader = null;
        List<String> appkey_list = null;
        
        // get distribute/countryhoursession result
        try {
            reader = new CountryhoursessionReader(config, target);
            appkey_list = reader.getAppkeyResults();
        }
        catch (IOException ioe) {
            throw new Exception(ioe.getMessage());
        }
        if (appkey_list == null || appkey_list.size() <= 0) {
            WorkLogger.log("distribute/countryhoursession: empty data");
            return 1;
        }
        
        // delete previous data
        try {
            int cnt = 0;
            if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                cnt = serviceIF.selectCountryTimeDayCountByKey(target.getYear(),
                        target.getMonth(), target.getDay(), "", "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                cnt = serviceIF.selectCountryTimeWeekCountByKey(target.getYear(),
                        target.getWeek_str(), "", "");
            }
            else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                cnt = serviceIF.selectCountryTimeMonthCountByKey(target.getYear(),
                        target.getMonth(), "", "");
            }
            
            if (cnt > 0) {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    serviceIF.deleteCountryTimeDayByDate(target.getYear(),
                            target.getMonth(), target.getDay());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    serviceIF.deleteCountryTimeWeekByDate(target.getYear(),
                            target.getWeek_str());
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    serviceIF.deleteCountryTimeMonthByDate(target.getYear(),
                            target.getMonth());
                }
            }
        }
        catch (Exception e) {
            throw e;
        }
        
        for (String appkey : appkey_list) {
            
            List<String> countrycode_list = null;
            List<CountryTimeAll> indst_list = new ArrayList<CountryTimeAll>();
            Map<String, CountryTimeAll> dst_map = new HashMap<String, CountryTimeAll>();
            
            // get distribute/countryhoursession result
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
                
                List<Countryhoursession> src_list = null;
                
                // get distribute/countryhoursession result
                try {
                    src_list = reader.getCountryhoursessionResults(appkey, countrycode);
                }
                catch (IOException ioe) {
                    throw new Exception(ioe.getMessage());
                }
                if (src_list == null || src_list.size() <= 0) {
                    continue;
                }
                
                for (Countryhoursession src : src_list) {
                    
                    if (dst_map.containsKey(countrycode)) {
                        
                        CountryTimeAll prev_row = dst_map.get(countrycode);
                        
                        CountryTimeAll new_row = new CountryTimeAll();
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
                        if (src.getHoursection() >= 0 && src.getHoursection() <= 23) {
                            if (src.getHoursection() == 0)
                                new_row.setZero_session(prev_row.getZero_session() + src.getSessioncount());
                            else if (src.getHoursection() == 1)
                                new_row.setOne_session(prev_row.getOne_session() + src.getSessioncount());
                            else if (src.getHoursection() == 2)
                                new_row.setTwo_session(prev_row.getTwo_session() + src.getSessioncount());
                            else if (src.getHoursection() == 3)
                                new_row.setThree_session(prev_row.getThree_session() + src.getSessioncount());
                            else if (src.getHoursection() == 4)
                                new_row.setFour_session(prev_row.getFour_session() + src.getSessioncount());
                            else if (src.getHoursection() == 5)
                                new_row.setFive_session(prev_row.getFive_session() + src.getSessioncount());
                            else if (src.getHoursection() == 6)
                                new_row.setSix_session(prev_row.getSix_session() + src.getSessioncount());
                            else if (src.getHoursection() == 7)
                                new_row.setSeven_session(prev_row.getSeven_session() + src.getSessioncount());
                            else if (src.getHoursection() == 8)
                                new_row.setEight_session(prev_row.getEight_session() + src.getSessioncount());
                            else if (src.getHoursection() == 9)
                                new_row.setNine_session(prev_row.getNine_session() + src.getSessioncount());
                            else if (src.getHoursection() == 10)
                                new_row.setTen_session(prev_row.getTen_session() + src.getSessioncount());
                            else if (src.getHoursection() == 11)
                                new_row.setEleven_session(prev_row.getEleven_session() + src.getSessioncount());
                            else if (src.getHoursection() == 12)
                                new_row.setTwelve_session(prev_row.getTwelve_session() + src.getSessioncount());
                            else if (src.getHoursection() == 13)
                                new_row.setThirteen_session(prev_row.getThirteen_session() + src.getSessioncount());
                            else if (src.getHoursection() == 14)
                                new_row.setFourteen_session(prev_row.getFourteen_session() + src.getSessioncount());
                            else if (src.getHoursection() == 15)
                                new_row.setFifteen_session(prev_row.getFifteen_session() + src.getSessioncount());
                            else if (src.getHoursection() == 16)
                                new_row.setSixteen_session(prev_row.getSixteen_session() + src.getSessioncount());
                            else if (src.getHoursection() == 17)
                                new_row.setSeventeen_session(prev_row.getSeventeen_session() + src.getSessioncount());
                            else if (src.getHoursection() == 18)
                                new_row.setEighteen_session(prev_row.getEighteen_session() + src.getSessioncount());
                            else if (src.getHoursection() == 19)
                                new_row.setNineteen_session(prev_row.getNineteen_session() + src.getSessioncount());
                            else if (src.getHoursection() == 20)
                                new_row.setTwenty_session(prev_row.getTwenty_session() + src.getSessioncount());
                            else if (src.getHoursection() == 21)
                                new_row.setTwentyone_session(prev_row.getTwentyone_session() + src.getSessioncount());
                            else if (src.getHoursection() == 22)
                                new_row.setTwentytwo_session(prev_row.getTwentytwo_session() + src.getSessioncount());
                            else if (src.getHoursection() == 23)
                                new_row.setTwentythree_session(prev_row.getTwentythree_session() + src.getSessioncount());
                        }
                        
                        dst_map.remove(countrycode);
                        dst_map.put(countrycode, new_row);
                    }
                    else {
                        
                        CountryTimeAll new_row = new CountryTimeAll();
                        
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
                        if (src.getHoursection() >= 0 && src.getHoursection() <= 23) {
                            if (src.getHoursection() == 0)
                                new_row.setZero_session(src.getSessioncount());
                            else if (src.getHoursection() == 1)
                                new_row.setOne_session(src.getSessioncount());
                            else if (src.getHoursection() == 2)
                                new_row.setTwo_session(src.getSessioncount());
                            else if (src.getHoursection() == 3)
                                new_row.setThree_session(src.getSessioncount());
                            else if (src.getHoursection() == 4)
                                new_row.setFour_session(src.getSessioncount());
                            else if (src.getHoursection() == 5)
                                new_row.setFive_session(src.getSessioncount());
                            else if (src.getHoursection() == 6)
                                new_row.setSix_session(src.getSessioncount());
                            else if (src.getHoursection() == 7)
                                new_row.setSeven_session(src.getSessioncount());
                            else if (src.getHoursection() == 8)
                                new_row.setEight_session(src.getSessioncount());
                            else if (src.getHoursection() == 9)
                                new_row.setNine_session(src.getSessioncount());
                            else if (src.getHoursection() == 10)
                                new_row.setTen_session(src.getSessioncount());
                            else if (src.getHoursection() == 11)
                                new_row.setEleven_session(src.getSessioncount());
                            else if (src.getHoursection() == 12)
                                new_row.setTwelve_session(src.getSessioncount());
                            else if (src.getHoursection() == 13)
                                new_row.setThirteen_session(src.getSessioncount());
                            else if (src.getHoursection() == 14)
                                new_row.setFourteen_session(src.getSessioncount());
                            else if (src.getHoursection() == 15)
                                new_row.setFifteen_session(src.getSessioncount());
                            else if (src.getHoursection() == 16)
                                new_row.setSixteen_session(src.getSessioncount());
                            else if (src.getHoursection() == 17)
                                new_row.setSeventeen_session(src.getSessioncount());
                            else if (src.getHoursection() == 18)
                                new_row.setEighteen_session(src.getSessioncount());
                            else if (src.getHoursection() == 19)
                                new_row.setNineteen_session(src.getSessioncount());
                            else if (src.getHoursection() == 20)
                                new_row.setTwenty_session(src.getSessioncount());
                            else if (src.getHoursection() == 21)
                                new_row.setTwentyone_session(src.getSessioncount());
                            else if (src.getHoursection() == 22)
                                new_row.setTwentytwo_session(src.getSessioncount());
                            else if (src.getHoursection() == 23)
                                new_row.setTwentythree_session(src.getSessioncount());
                        }
                        
                        dst_map.put(countrycode, new_row);
                    }
                }
            }
            
            // map to list
            Iterator<CountryTimeAll> it = dst_map.values().iterator();
            while (it.hasNext()) {
                CountryTimeAll dst = it.next();
                indst_list.add(dst);
            }
            
            if (indst_list.size() <= 0) {
                continue;
            }
            
            @SuppressWarnings("unused")
            int ins_ret = 0;
            try {
                if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
                    ins_ret = serviceIF.insertBatchCountryTimeDay(indst_list);
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
                    ins_ret = serviceIF.insertBatchCountryTimeWeek(indst_list);
                }
                else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
                    ins_ret = serviceIF.insertBatchCountryTimeMonth(indst_list);
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
        
        WorkLogger.log(CountryTimeController.class.getSimpleName()
                + " : Start dbms controller");
        
        try {
            CountryTimeController controller = new CountryTimeController();
            
            exitCode = controller.run(args);
            
            WorkLogger.log(CountryTimeController.class.getSimpleName()
                    + " : End dbms controller");
        }
        catch (Exception e) {
            ErrorLogger.log(CountryTimeController.class.getSimpleName()
                    + " : Error : " + e.getMessage());
            WorkLogger.log(CountryTimeController.class.getSimpleName()
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
