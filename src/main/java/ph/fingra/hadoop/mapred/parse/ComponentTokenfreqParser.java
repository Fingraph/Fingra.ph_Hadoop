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

public class ComponentTokenfreqParser {
    
    private static final int COL_APPKEY = 0;
    private static final int COL_COMPONENTKEY = 1;
    private static final int COL_TOKEN = 2;
    private static final int COL_SESSIONCOUNT = 3;
    
    private static final int COL_COUNT = 4;
    
    // componenttokenfreq format
    private static final String COMPONENTTOKENFREQ_PATTERN_REGEX
        = "^[a-zA-Z0-9]*\\t"            // appkey
                + "[a-zA-Z0-9]*\\t"     // componentkey
                + "[a-zA-Z0-9_-]*\\t"   // token
                + "[\\d]*$";            // session count
    
    private static Pattern COMPONENTTOKENFREQ_PATTERN;
    static {
        COMPONENTTOKENFREQ_PATTERN = Pattern.compile(COMPONENTTOKENFREQ_PATTERN_REGEX);
    }
    
    private String appkey;
    private String componentkey;
    private String token;
    private long sessioncount;
    
    private boolean raised_error;
    private int parse_error;
    private LogValidation error_level;
    
    public ComponentTokenfreqParser() {
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
        this.componentkey = "";
        this.token = "";
        this.sessioncount = 0;
        
        this.raised_error = false;
        this.parse_error = ParseError.NONE;
        this.error_level = LogValidation.CLEAN;
        
        if (record == null || record.trim().isEmpty()) {
            this.raised_error = true;
            this.parse_error = ParseError.EMPTYLINE;
            this.error_level = LogValidation.WASTE;
            return;
        }
        
        if (COMPONENTTOKENFREQ_PATTERN.matcher(record).matches() == true) {
            
            String[] fields = record.split(ConstantVars.DB_FIELD_SEPERATER_REGX, COL_COUNT);
            
            if (fields == null || fields.length < COL_COUNT) {
                this.raised_error = true;
                this.parse_error = ParseError.ERRORFIELDCOUNT;
                this.error_level = LogValidation.WASTE;
                return;
            }
            
            if (fields.length == COL_COUNT) {
                
                this.appkey = fields[COL_APPKEY].trim();
                this.componentkey = fields[COL_COMPONENTKEY].trim();
                this.token = fields[COL_TOKEN].trim();
                this.sessioncount = Long.parseLong(fields[COL_SESSIONCOUNT].trim());
                
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
    public String getComponentkey() {
        return this.componentkey;
    }
    public String getToken() {
        return this.token;
    }
    public long getSessioncount() {
        return this.sessioncount;
    }
    
    public void printDebug() {
        System.out.println("[haserror] " + this.raised_error);
        System.out.println("[appkey] " + this.appkey);
        System.out.println("[componentkey] " + this.componentkey);
        System.out.println("[token] " + this.token);
        System.out.println("[sessioncount] " + this.sessioncount);
    }
    
    public static void main(String[] args) throws Exception {
        
        ComponentTokenfreqParser parser = new ComponentTokenfreqParser();
        
        String test = "";
        
        System.out.println("full");
        test = "fin01263	evt196318	00000000-101c-4612-ffff-ffff9a3181c8	200";
        parser.parse(test);
        parser.printDebug();
        
        System.out.println("miss");
        test = "fin01263	evt196318	00000000-101c-4612-ffff-ffff9a3181c8";
        parser.parse(test);
        parser.printDebug();
    }
}
