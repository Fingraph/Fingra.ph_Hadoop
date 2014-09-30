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

public class SessionlengthSectionAll extends BaseObject implements Serializable {
    
    private static final long serialVersionUID = 1308681733204005559L;
    
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
    private long under_three_sec;
    private long three_ten_sec;
    private long ten_thirty_sec;
    private long thirty_sixty_sec;
    private long one_three_min;
    private long three_ten_min;
    private long ten_thirty_min;
    private long over_thirty_min;
    
    public void copy(SessionlengthSectionAll src) {
        
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
        this.under_three_sec = src.under_three_sec;
        this.three_ten_sec = src.three_ten_sec;
        this.ten_thirty_sec = src.ten_thirty_sec;
        this.thirty_sixty_sec = src.thirty_sixty_sec;
        this.one_three_min = src.one_three_min;
        this.three_ten_min = src.three_ten_min;
        this.ten_thirty_min = src.ten_thirty_min;
        this.over_thirty_min = src.over_thirty_min;
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
    public long getUnder_three_sec() {
        return under_three_sec;
    }
    public void setUnder_three_sec(long under_three_sec) {
        this.under_three_sec = under_three_sec;
    }
    public long getThree_ten_sec() {
        return three_ten_sec;
    }
    public void setThree_ten_sec(long three_ten_sec) {
        this.three_ten_sec = three_ten_sec;
    }
    public long getTen_thirty_sec() {
        return ten_thirty_sec;
    }
    public void setTen_thirty_sec(long ten_thirty_sec) {
        this.ten_thirty_sec = ten_thirty_sec;
    }
    public long getThirty_sixty_sec() {
        return thirty_sixty_sec;
    }
    public void setThirty_sixty_sec(long thirty_sixty_sec) {
        this.thirty_sixty_sec = thirty_sixty_sec;
    }
    public long getOne_three_min() {
        return one_three_min;
    }
    public void setOne_three_min(long one_three_min) {
        this.one_three_min = one_three_min;
    }
    public long getThree_ten_min() {
        return three_ten_min;
    }
    public void setThree_ten_min(long three_ten_min) {
        this.three_ten_min = three_ten_min;
    }
    public long getTen_thirty_min() {
        return ten_thirty_min;
    }
    public void setTen_thirty_min(long ten_thirty_min) {
        this.ten_thirty_min = ten_thirty_min;
    }
    public long getOver_thirty_min() {
        return over_thirty_min;
    }
    public void setOver_thirty_min(long over_thirty_min) {
        this.over_thirty_min = over_thirty_min;
    }
}
