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

package ph.fingra.hadoop.dbms.parts.performance.domain;

import java.io.Serializable;

import ph.fingra.hadoop.common.BaseObject;

public class FrequencyAll extends BaseObject implements Serializable {
    
    private static final long serialVersionUID = -3633718498202823507L;
    
    private String year;
    private String month;
    private String week;
    private String day;
    private String hour;
    private String appkey;
    private String date;
    private String datehour;
    private int dayofweek;
    private String fromdate;
    private String todate;
    private long freq_user_1;
    private long freq_user_2;
    private long freq_user_3_4;
    private long freq_user_5_6;
    private long freq_user_7_9;
    private long freq_user_10_14;
    private long freq_user_15_19;
    private long freq_user_20_49;
    private long freq_user_50_99;
    private long freq_user_100_499;
    private long freq_user_over_500;
    
    public void copy(FrequencyAll src) {
        
        this.year = src.year;
        this.month = src.month;
        this.week = src.week;
        this.day = src.day;
        this.hour = src.hour;
        this.appkey = src.appkey;
        this.date = src.date;
        this.datehour = src.datehour;
        this.dayofweek = src.dayofweek;
        this.fromdate = src.fromdate;
        this.todate = src.todate;
        this.freq_user_1 = src.freq_user_1;
        this.freq_user_2 = src.freq_user_2;
        this.freq_user_3_4 = src.freq_user_3_4;
        this.freq_user_5_6 = src.freq_user_5_6;
        this.freq_user_7_9 = src.freq_user_7_9;
        this.freq_user_10_14 = src.freq_user_10_14;
        this.freq_user_15_19 = src.freq_user_15_19;
        this.freq_user_20_49 = src.freq_user_20_49;
        this.freq_user_50_99 = src.freq_user_50_99;
        this.freq_user_100_499 = src.freq_user_100_499;
        this.freq_user_over_500 = src.freq_user_over_500;
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
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
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
    public String getAppkey() {
        return appkey;
    }
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDatehour() {
        return datehour;
    }
    public void setDatehour(String datehour) {
        this.datehour = datehour;
    }
    public int getDayofweek() {
        return dayofweek;
    }
    public void setDayofweek(int dayofweek) {
        this.dayofweek = dayofweek;
    }
    public String getFromdate() {
        return fromdate;
    }
    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }
    public String getTodate() {
        return todate;
    }
    public void setTodate(String todate) {
        this.todate = todate;
    }
    public long getFreq_user_1() {
        return freq_user_1;
    }
    public void setFreq_user_1(long freq_user_1) {
        this.freq_user_1 = freq_user_1;
    }
    public long getFreq_user_2() {
        return freq_user_2;
    }
    public void setFreq_user_2(long freq_user_2) {
        this.freq_user_2 = freq_user_2;
    }
    public long getFreq_user_3_4() {
        return freq_user_3_4;
    }
    public void setFreq_user_3_4(long freq_user_3_4) {
        this.freq_user_3_4 = freq_user_3_4;
    }
    public long getFreq_user_5_6() {
        return freq_user_5_6;
    }
    public void setFreq_user_5_6(long freq_user_5_6) {
        this.freq_user_5_6 = freq_user_5_6;
    }
    public long getFreq_user_7_9() {
        return freq_user_7_9;
    }
    public void setFreq_user_7_9(long freq_user_7_9) {
        this.freq_user_7_9 = freq_user_7_9;
    }
    public long getFreq_user_10_14() {
        return freq_user_10_14;
    }
    public void setFreq_user_10_14(long freq_user_10_14) {
        this.freq_user_10_14 = freq_user_10_14;
    }
    public long getFreq_user_15_19() {
        return freq_user_15_19;
    }
    public void setFreq_user_15_19(long freq_user_15_19) {
        this.freq_user_15_19 = freq_user_15_19;
    }
    public long getFreq_user_20_49() {
        return freq_user_20_49;
    }
    public void setFreq_user_20_49(long freq_user_20_49) {
        this.freq_user_20_49 = freq_user_20_49;
    }
    public long getFreq_user_50_99() {
        return freq_user_50_99;
    }
    public void setFreq_user_50_99(long freq_user_50_99) {
        this.freq_user_50_99 = freq_user_50_99;
    }
    public long getFreq_user_100_499() {
        return freq_user_100_499;
    }
    public void setFreq_user_100_499(long freq_user_100_499) {
        this.freq_user_100_499 = freq_user_100_499;
    }
    public long getFreq_user_over_500() {
        return freq_user_over_500;
    }
    public void setFreq_user_over_500(long freq_user_over_500) {
        this.freq_user_over_500 = freq_user_over_500;
    }
}
