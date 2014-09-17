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

package ph.fingra.hadoop.mapred.parse;

import java.util.regex.Pattern;

import org.apache.hadoop.io.Text;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.ConstantVars.LogValidation;
import ph.fingra.hadoop.common.ConstantVars.ParseError;
import ph.fingra.hadoop.common.util.FormatUtil;

public class AppNewuserDbParser {
    
    private static final int COL_APPKEY = 0;
    private static final int COL_TOKEN = 1;
    private static final int COL_YEAR = 2;
    private static final int COL_MONTH = 3;
    private static final int COL_DAY = 4;
    private static final int COL_WEEK = 5;
    private static final int COL_UTCTIME = 6;
    private static final int COL_LOCALTIME = 7;
    private static final int COL_COUNTRY = 8;
    private static final int COL_LANGUAGE = 9;
    private static final int COL_DEVICE = 10;
    private static final int COL_OSVERSION = 11;
    private static final int COL_RESOLUTION = 12;
    private static final int COL_APPVERSION = 13;
    
    private static final int COL_COUNT = 14;
    
    // app_newuser_db format
    private static final String APPNEWUSERDB_PATTERN_REGEX
        = "^[a-zA-Z0-9]*\\t"            // appkey
                + "[a-zA-Z0-9_-]*\\t"   // token
                + "[0-9]{4}\\t[0-9]{2}\\t[0-9]{2}\\t[0-9]{2}\\t" // year, month, day, week
                + "[0-9]{14}\\t[0-9]{14}\\t"    // utctime, localtime
                + "[\\w\\W]*\\t"        // country
                + "[\\w\\W]*\\t"        // language
                + "[\\w\\W]*\\t"        // device
                + "[\\w\\W]*\\t"        // os version
                + "[a-zA-Z0-9]*\\t"     // resolution
                + "[\\w\\W]*$";         // app version
    
    private static Pattern APPNEWUSERDB_PATTERN;
    static {
        APPNEWUSERDB_PATTERN = Pattern.compile(APPNEWUSERDB_PATTERN_REGEX);
    }
    
    private String appkey;
    private String token;
    private String year;
    private String month;
    private String day;
    private String week;
    private String utctime;
    private String localtime;
    private String country;
    private String language;
    private String device;
    private String osversion;
    private String resolution;
    private String appversion;
    
    private boolean raised_error;
    private int parse_error;
    private LogValidation error_level;
    
    public AppNewuserDbParser() {
        this.raised_error = false;
        this.parse_error = ParseError.NONE;
        this.error_level = LogValidation.CLEAN;
    }
    
    public void parse(Text record) {
        if (record == null)
            parse("");
        else
            parse(record.toString());
    }
    
    private void parse(String record) {
        
        // initialize
        this.appkey = "";
        this.token = "";
        this.year = "";
        this.month = "";
        this.day = "";
        this.week = "";
        this.utctime ="";
        this.localtime = "";
        this.country = "";
        this.language = "";
        this.device = "";
        this.osversion = "";
        this.resolution = "";
        this.appversion = "";
        
        this.raised_error = false;
        this.parse_error = ParseError.NONE;
        this.error_level = LogValidation.CLEAN;
        
        if (record == null || record.trim().isEmpty()) {
            this.raised_error = true;
            this.parse_error = ParseError.EMPTYLINE;
            this.error_level = LogValidation.WASTE;
            return;
        }
        
        if (APPNEWUSERDB_PATTERN.matcher(record).matches() == true) {
            
            String[] fields = record.split(ConstantVars.DB_FIELD_SEPERATER_REGX, COL_COUNT);
            
            if (fields == null || fields.length < COL_COUNT) {
                this.raised_error = true;
                this.parse_error = ParseError.ERRORFIELDCOUNT;
                this.error_level = LogValidation.WASTE;
                return;
            }
            
            if (fields.length == COL_COUNT) {
                
                this.appkey = fields[COL_APPKEY].trim();
                this.token = fields[COL_TOKEN].trim();
                this.year = fields[COL_YEAR].trim();
                this.month = fields[COL_MONTH].trim();
                this.day = fields[COL_DAY].trim();
                this.week = fields[COL_WEEK].trim();
                this.utctime = fields[COL_UTCTIME].trim();
                this.localtime = fields[COL_LOCALTIME].trim();
                this.country = fields[COL_COUNTRY].trim();
                this.language = fields[COL_LANGUAGE].trim();
                this.device = fields[COL_DEVICE].trim();
                this.osversion = fields[COL_OSVERSION].trim();
                this.resolution = fields[COL_RESOLUTION].trim();
                this.appversion = fields[COL_APPVERSION].trim();
                
                if ( !isValidNumber(this.year) || !isValidNumber(this.month)
                        || !isValidNumber(this.day) || !isValidNumber(this.week)
                        || !isValidNumber(this.utctime) || !isValidNumber(this.localtime) ) {
                    this.raised_error = true;
                    this.parse_error = ParseError.ERRORTIME;
                    this.error_level = LogValidation.MALFORMED;
                    return;
                }
                
                this.raised_error = false;
                this.error_level = LogValidation.CLEAN;
            }
            else {
                this.raised_error = true;
                this.parse_error = ParseError.ERRORFIELDCOUNT;
                this.error_level = LogValidation.WASTE;
                return;
            }
        }
        else {
            this.raised_error = true;
            this.parse_error = ParseError.MISSMATCH;
            this.error_level = LogValidation.WASTE;
            return;
        }
        
        return;
    }
    
    public boolean hasError() {
        return this.raised_error;
    }
    public int getParseError() {
        return this.parse_error;
    }
    public LogValidation getErrorLevel() {
        return this.error_level;
    }
    
    public double getDouble(String src) {
        if (src == null || src.isEmpty())
            return 0;
        return Double.parseDouble(src);
    }
    public int getInteger(String src) {
        if (src == null || src.isEmpty())
            return 0;
        return Integer.parseInt(src);
    }
    
    public boolean isValidNumber(String src) {
        return FormatUtil.isValidNumber(src);
    }
    
    public String getAppkey() {
        return this.appkey;
    }
    public String getToken() {
        return this.token;
    }
    public String getYear() {
        return this.year;
    }
    public String getMonth() {
        return this.month;
    }
    public String getDay() {
        return this.day;
    }
    public String getWeek() {
        return this.week;
    }
    public String getUtctime() {
        return this.utctime;
    }
    public String getLocaltime() {
        return this.localtime;
    }
    public String getCountry() {
        return this.country;
    }
    public String getLanguage() {
        return this.language;
    }
    public String getDevice() {
        return this.device;
    }
    public String getOsversion() {
        return this.osversion;
    }
    public String getResolution() {
        return this.resolution;
    }
    public String getAppversion() {
        return this.appversion;
    }
    
    public void printDebug() {
        System.out.println("[haserror] " + this.raised_error);
        System.out.println("[appkey] " + this.appkey);
        System.out.println("[token] " + this.token);
        System.out.println("[year] " + this.year);
        System.out.println("[month] " + this.month);
        System.out.println("[day] " + this.day);
        System.out.println("[week] " + this.week);
        System.out.println("[utctime] " + this.utctime);
        System.out.println("[localtime] " + this.localtime);
        System.out.println("[country] " + this.country);
        System.out.println("[language] " + this.language);
        System.out.println("[device] " + this.device);
        System.out.println("[osversion] " + this.osversion);
        System.out.println("[resolution] " + this.resolution);
        System.out.println("[appversion] " + this.appversion);
    }
    
    public static void main(String[] args) throws Exception {
        
        AppNewuserDbParser parser = new AppNewuserDbParser();
        
        String test = "";
        
        System.out.println("full");
        test = "fin01263	00000000-101c-4612-ffff-ffff9a3181c8	2014	04	17	16	20140417061808	20140417151808	KR	ko	NEXUS 5	4.4.4	720X1184	1.0";
        parser.parse(test);
        parser.printDebug();
        
        System.out.println("miss");
        test = "fin01263	00000000-101c-4612-ffff-ffff9a3181c8	2014	04	17	16	20140417061808	20140417151808	UNKNOWN	unknown	UNKNOWN	unknown	UNKNOWN	unknown";
        parser.parse(test);
        parser.printDebug();
    }
}
