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

public class CountrySesstimeParser {
    
    private static final int COL_APPKEY = 0;
    private static final int COL_COUNTRY = 1;
    private static final int COL_SESSION = 2;
    private static final int COL_SESSIONLENGTH = 3;
    
    private static final int COL_COUNT = 4;
    
    // sesstime format
    private static final String SESSTIME_PATTERN_REGEX
        = "^[a-zA-Z0-9]*\\t"            // appkey
                + "[\\w\\W]*\\t"        // country
                + "[a-zA-Z0-9_-]*\\t"   // session
                + "[\\d]*$";            // session length
    
    private static Pattern SESSTIME_PATTERN;
    static {
        SESSTIME_PATTERN = Pattern.compile(SESSTIME_PATTERN_REGEX);
    }
    
    private String appkey;
    private String country;
    private String session;
    private long sessionlength;
    
    private boolean raised_error;
    private int parse_error;
    private LogValidation error_level;
    
    public CountrySesstimeParser() {
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
        this.country = "";
        this.session = "";
        this.sessionlength = 0;
        
        this.raised_error = false;
        this.parse_error = ParseError.NONE;
        this.error_level = LogValidation.CLEAN;
        
        if (record == null || record.trim().isEmpty()) {
            this.raised_error = true;
            this.parse_error = ParseError.EMPTYLINE;
            this.error_level = LogValidation.WASTE;
            return;
        }
        
        if (SESSTIME_PATTERN.matcher(record).matches() == true) {
            
            String[] fields = record.split(ConstantVars.DB_FIELD_SEPERATER_REGX, COL_COUNT);
            
            if (fields == null || fields.length < COL_COUNT) {
                this.raised_error = true;
                this.parse_error = ParseError.ERRORFIELDCOUNT;
                this.error_level = LogValidation.WASTE;
                return;
            }
            
            if (fields.length == COL_COUNT) {
                
                this.appkey = fields[COL_APPKEY].trim();
                this.country = fields[COL_COUNTRY].trim();
                this.session = fields[COL_SESSION].trim();
                this.sessionlength = Long.parseLong(fields[COL_SESSIONLENGTH].trim());
                
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
    
    public String getAppkey() {
        return this.appkey;
    }
    public String getCountry() {
        return this.country;
    }
    public String getSession() {
        return this.session;
    }
    public long getSessionlength() {
        return this.sessionlength;
    }
    
    public void printDebug() {
        System.out.println("[haserror] " + this.raised_error);
        System.out.println("[appkey] " + this.appkey);
        System.out.println("[country] " + this.country);
        System.out.println("[session] " + this.session);
        System.out.println("[sessionlength] " + this.sessionlength);
    }
    
    public static void main(String[] args) throws Exception {
        
        CountrySesstimeParser parser = new CountrySesstimeParser();
        
        String test = "";
        
        System.out.println("full");
        test = "fin01263	KR	a03ab4b9-93d6-47d8-8159-3ddf7c3a878a	25";
        parser.parse(test);
        parser.printDebug();
        
        System.out.println("miss");
        test = "fin01263	KR	a03ab4b9-93d6-47d8-8159-3ddf7c3a878a";
        parser.parse(test);
        parser.printDebug();
    }
}
