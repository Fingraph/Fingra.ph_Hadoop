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

package ph.fingra.hadoop.dbms.parts.distribution.domain;

import java.io.Serializable;

import ph.fingra.hadoop.common.BaseObject;

public class CountryTimeAll extends BaseObject implements Serializable {
    
    private static final long serialVersionUID = -1719239092926477722L;
    
    private String year;
    private String month;
    private String week;
    private String day;
    private String hour;
    private String appkey;
    private String country;
    private String date;
    private String datehour;
    private int dayofweek;
    private String fromdate;
    private String todate;
    private long zero_session;
    private long one_session;
    private long two_session;
    private long three_session;
    private long four_session;
    private long five_session;
    private long six_session;
    private long seven_session;
    private long eight_session;
    private long nine_session;
    private long ten_session;
    private long eleven_session;
    private long twelve_session;
    private long thirteen_session;
    private long fourteen_session;
    private long fifteen_session;
    private long sixteen_session;
    private long seventeen_session;
    private long eighteen_session;
    private long nineteen_session;
    private long twenty_session;
    private long twentyone_session;
    private long twentytwo_session;
    private long twentythree_session;
    
    public void copy(CountryTimeAll src) {
        
        this.year = src.year;
        this.month = src.month;
        this.week = src.week;
        this.day = src.day;
        this.hour = src.hour;
        this.appkey = src.appkey;
        this.country = src.country;
        this.date = src.date;
        this.datehour = src.datehour;
        this.dayofweek = src.dayofweek;
        this.fromdate = src.fromdate;
        this.todate = src.todate;
        this.zero_session = src.zero_session;
        this.one_session = src.one_session;
        this.two_session = src.two_session;
        this.three_session = src.three_session;
        this.four_session = src.four_session;
        this.five_session = src.five_session;
        this.six_session = src.six_session;
        this.seven_session = src.seven_session;
        this.eight_session = src.eight_session;
        this.nine_session = src.nine_session;
        this.ten_session = src.ten_session;
        this.eleven_session = src.eleven_session;
        this.twelve_session = src.twelve_session;
        this.thirteen_session = src.thirteen_session;
        this.fourteen_session = src.fourteen_session;
        this.fifteen_session = src.fifteen_session;
        this.sixteen_session = src.sixteen_session;
        this.seventeen_session = src.seventeen_session;
        this.eighteen_session = src.eighteen_session;
        this.nineteen_session = src.nineteen_session;
        this.twenty_session = src.twenty_session;
        this.twentyone_session = src.twentyone_session;
        this.twentytwo_session = src.twentytwo_session;
        this.twentythree_session = src.twentythree_session;
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
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
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
    public long getZero_session() {
        return zero_session;
    }
    public void setZero_session(long zero_session) {
        this.zero_session = zero_session;
    }
    public long getOne_session() {
        return one_session;
    }
    public void setOne_session(long one_session) {
        this.one_session = one_session;
    }
    public long getTwo_session() {
        return two_session;
    }
    public void setTwo_session(long two_session) {
        this.two_session = two_session;
    }
    public long getThree_session() {
        return three_session;
    }
    public void setThree_session(long three_session) {
        this.three_session = three_session;
    }
    public long getFour_session() {
        return four_session;
    }
    public void setFour_session(long four_session) {
        this.four_session = four_session;
    }
    public long getFive_session() {
        return five_session;
    }
    public void setFive_session(long five_session) {
        this.five_session = five_session;
    }
    public long getSix_session() {
        return six_session;
    }
    public void setSix_session(long six_session) {
        this.six_session = six_session;
    }
    public long getSeven_session() {
        return seven_session;
    }
    public void setSeven_session(long seven_session) {
        this.seven_session = seven_session;
    }
    public long getEight_session() {
        return eight_session;
    }
    public void setEight_session(long eight_session) {
        this.eight_session = eight_session;
    }
    public long getNine_session() {
        return nine_session;
    }
    public void setNine_session(long nine_session) {
        this.nine_session = nine_session;
    }
    public long getTen_session() {
        return ten_session;
    }
    public void setTen_session(long ten_session) {
        this.ten_session = ten_session;
    }
    public long getEleven_session() {
        return eleven_session;
    }
    public void setEleven_session(long eleven_session) {
        this.eleven_session = eleven_session;
    }
    public long getTwelve_session() {
        return twelve_session;
    }
    public void setTwelve_session(long twelve_session) {
        this.twelve_session = twelve_session;
    }
    public long getThirteen_session() {
        return thirteen_session;
    }
    public void setThirteen_session(long thirteen_session) {
        this.thirteen_session = thirteen_session;
    }
    public long getFourteen_session() {
        return fourteen_session;
    }
    public void setFourteen_session(long fourteen_session) {
        this.fourteen_session = fourteen_session;
    }
    public long getFifteen_session() {
        return fifteen_session;
    }
    public void setFifteen_session(long fifteen_session) {
        this.fifteen_session = fifteen_session;
    }
    public long getSixteen_session() {
        return sixteen_session;
    }
    public void setSixteen_session(long sixteen_session) {
        this.sixteen_session = sixteen_session;
    }
    public long getSeventeen_session() {
        return seventeen_session;
    }
    public void setSeventeen_session(long seventeen_session) {
        this.seventeen_session = seventeen_session;
    }
    public long getEighteen_session() {
        return eighteen_session;
    }
    public void setEighteen_session(long eighteen_session) {
        this.eighteen_session = eighteen_session;
    }
    public long getNineteen_session() {
        return nineteen_session;
    }
    public void setNineteen_session(long nineteen_session) {
        this.nineteen_session = nineteen_session;
    }
    public long getTwenty_session() {
        return twenty_session;
    }
    public void setTwenty_session(long twenty_session) {
        this.twenty_session = twenty_session;
    }
    public long getTwentyone_session() {
        return twentyone_session;
    }
    public void setTwentyone_session(long twentyone_session) {
        this.twentyone_session = twentyone_session;
    }
    public long getTwentytwo_session() {
        return twentytwo_session;
    }
    public void setTwentytwo_session(long twentytwo_session) {
        this.twentytwo_session = twentytwo_session;
    }
    public long getTwentythree_session() {
        return twentythree_session;
    }
    public void setTwentythree_session(long twentythree_session) {
        this.twentythree_session = twentythree_session;
    }
}
