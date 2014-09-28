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

package ph.fingra.hadoop.dbms.parse.component.domain;

import java.io.Serializable;

import ph.fingra.hadoop.common.BaseObject;

public class Componentlanguage extends BaseObject implements Serializable {
    
    private static final long serialVersionUID = 5210120836298258442L;
    
    private String year;
    private String month;
    private String day;
    private String hour;
    private String week;
    private String date;
    private String datehour;
    private int dayofweek;
    private String fromdate;
    private String todate;
    
    private String appkey;
    private String componentkey;
    private String language;
    private long usercount;
    private long sessioncount;
    
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
    public String getWeek() {
        return week;
    }
    public void setWeek(String week) {
        this.week = week;
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
    public String getAppkey() {
        return appkey;
    }
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
    public String getComponentkey() {
        return componentkey;
    }
    public void setComponentkey(String componentkey) {
        this.componentkey = componentkey;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public long getUsercount() {
        return usercount;
    }
    public void setUsercount(long usercount) {
        this.usercount = usercount;
    }
    public long getSessioncount() {
        return sessioncount;
    }
    public void setSessioncount(long sessioncount) {
        this.sessioncount = sessioncount;
    }
}
