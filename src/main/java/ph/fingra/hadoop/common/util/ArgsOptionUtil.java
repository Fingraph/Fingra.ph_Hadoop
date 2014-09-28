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

package ph.fingra.hadoop.common.util;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.domain.TargetDate;

public class ArgsOptionUtil {
    
    public static String getOption(String[] args, String name, String defaultvalue) {
        
        String optval = defaultvalue;
        String prefix = "-D";
        String suffix = "=";
        String option = prefix + name + suffix;
        
        if (args!=null && args.length>0) {
            
            for (String arg : args) {
                arg = arg.trim();
                if (arg.startsWith(option)) {
                    optval = arg.substring(option.length());
                }
            }
        }
        
        return optval;
    }
    
    public static boolean checkRunmode(String mode) {
        
        if (mode!=null && (mode.equals(ConstantVars.RUNMODE_HOUR)
                || mode.equals(ConstantVars.RUNMODE_DAY)
                || mode.equals(ConstantVars.RUNMODE_WEEK)
                || mode.equals(ConstantVars.RUNMODE_MONTH))) {
            return true;
        }
        else
            return false;
    }
    
    public static boolean checkTargetDateByMode(String mode, String targetdate) {
        
        boolean isvalid = false;
        
        if (mode.equals(ConstantVars.RUNMODE_HOUR)) {
            isvalid = DateTimeUtil.isValidDate(targetdate, ConstantVars.OPTIONDATE_FORMAT_HOUR);
        }
        else if (mode.equals(ConstantVars.RUNMODE_DAY)) {
            isvalid = DateTimeUtil.isValidDate(targetdate, ConstantVars.OPTIONDATE_FORMAT_DAY);
        }
        else if (mode.equals(ConstantVars.RUNMODE_WEEK)) {
            isvalid = DateTimeUtil.isValidDate(targetdate, ConstantVars.OPTIONDATE_FORMAT_WEEK);
        }
        else {
            isvalid = DateTimeUtil.isValidDate(targetdate, ConstantVars.OPTIONDATE_FORMAT_MONTH);
        }
        
        return isvalid;
    }
    
    public static String getDefaultTargetDateByMode(String mode) {
        
        String targetdate = "";
        String year = "", month = "", day = "";
        String hour = "";
        int week = 0;
        
        if (mode.equals(ConstantVars.RUNMODE_HOUR)) {
            String lasthour = DateTimeUtil.getOnehouragoFormatString("yyyyMMddHH");
            year = lasthour.substring(0, 4);
            month = lasthour.substring(4, 6);
            day = lasthour.substring(6, 8);
            hour = lasthour.substring(8);
            
            targetdate = year + "-" + month + "-" + day + "-" + hour;
        }
        else {
            String yesterday = DateTimeUtil.getYesterdayFormatString("yyyyMMdd");
            year = yesterday.substring(0, 4);
            month = yesterday.substring(4, 6);
            day = yesterday.substring(6);
            week = DateTimeUtil.getWeekOfYearByDay(year, month, day);
            
            if (mode.equals(ConstantVars.RUNMODE_DAY)) {
                targetdate = year + "-" + month + "-" + day;
            }
            else if (mode.equals(ConstantVars.RUNMODE_WEEK)) {
                targetdate = year + "-" + (week < 10 ? "0"+week : String.valueOf(week));
            }
            else {
                targetdate = year + "-" + month;
            }
        }
        
        return targetdate;
    }
    
    public static TargetDate getTargetDate(String mode, String targetdate) {
        
        TargetDate obj = new TargetDate();
        String year = "", month = "", day = "";
        String hour = "";
        int week = 0;
        
        if (mode.equals(ConstantVars.RUNMODE_HOUR)) {
            year = targetdate.substring(0, 4);
            month = targetdate.substring(5, 7);
            day = targetdate.substring(8, 10);
            hour = targetdate.substring(11);
            week = DateTimeUtil.getWeekOfYearByDay(year, month, day);
            
            obj.setRunmode(mode);
            obj.setYear(year);
            obj.setMonth(month);
            obj.setDay(day);
            obj.setHour(hour);
            obj.setWeek(week);
        }
        else if (mode.equals(ConstantVars.RUNMODE_DAY)) {
            year = targetdate.substring(0, 4);
            month = targetdate.substring(5, 7);
            day = targetdate.substring(8);
            week = DateTimeUtil.getWeekOfYearByDay(year, month, day);
            
            obj.setRunmode(mode);
            obj.setYear(year);
            obj.setMonth(month);
            obj.setDay(day);
            obj.setHour("");
            obj.setWeek(week);
        }
        else if (mode.equals(ConstantVars.RUNMODE_WEEK)) {
            year = targetdate.substring(0, 4);
            week = Integer.parseInt(targetdate.substring(5));
            // first day of week
            String startday = DateTimeUtil.startDayOfWeek(year, week, "yyyyMMdd");
            month = startday.substring(4, 6);
            day = startday.substring(6);
            
            obj.setRunmode(mode);
            obj.setYear(year);
            obj.setMonth(month);
            obj.setDay(day);
            obj.setHour("");
            obj.setWeek(week);
        }
        else {
            year = targetdate.substring(0, 4);
            month = targetdate.substring(5);
            // first day of month
            String startday = DateTimeUtil.startDayOfMonth(year, month, "yyyyMMdd");
            day = startday.substring(6);
            week = DateTimeUtil.getWeekOfYearByDay(year, month, day);
            
            obj.setRunmode(mode);
            obj.setYear(year);
            obj.setMonth(month);
            obj.setDay(day);
            obj.setHour("");
            obj.setWeek(week);
        }
        
        return obj;
    }
    
}
