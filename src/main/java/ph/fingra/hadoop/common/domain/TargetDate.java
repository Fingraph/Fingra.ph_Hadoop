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

package ph.fingra.hadoop.common.domain;

import ph.fingra.hadoop.common.BaseObject;
import ph.fingra.hadoop.common.ConstantVars;

public class TargetDate extends BaseObject {
    
    private String runmode;
    private String year;
    private String month;
    private String day;
    private String hour;
    private int week;
    
    public String getFulldate() {
        if (runmode.equals(ConstantVars.RUNMODE_HOUR)) {
            return year + "-" + month + "-" + day + "-" + hour;
        }
        else if (runmode.equals(ConstantVars.RUNMODE_DAY)) {
            return year + "-" + month + "-" + day;
        }
        else if (runmode.equals(ConstantVars.RUNMODE_WEEK)) {
            return year + "-" + getWeek_str();
        }
        else {
            return year + "-" + month;
        }
    }
    public String getWeek_str() {
        return (week < 10 ? "0"+week : String.valueOf(week));
    }
    
    public String getRunmode() {
        return runmode;
    }
    public void setRunmode(String runmode) {
        this.runmode = runmode;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    public int getWeek() {
        return week;
    }
    public void setWeek(int week) {
        this.week = week;
    }
    
}
