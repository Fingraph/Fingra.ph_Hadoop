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
import ph.fingra.hadoop.common.SdkFieldInfo;
import ph.fingra.hadoop.common.SdkFieldInfo.CommonFieldIndex;
import ph.fingra.hadoop.common.util.DateTimeUtil;
import ph.fingra.hadoop.common.util.FormatUtil;

public class CommonLogParser {
    
    private static Pattern APPKEY_PATTERN;
    static {
        APPKEY_PATTERN = Pattern.compile(ConstantVars.APPKEY_PATTERN_REGEX);
    }
    
    private String cmd;
    private String appkey;
    private String session;
    private String utctime;
    private String localtime;
    private String token;
    private String country;
    private String language;
    private String device;
    private String osversion;
    private String resolution;
    private String appversion;
    
    private boolean raised_error;
    private int parse_error;
    private LogValidation error_level;
    
    public CommonLogParser() {
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
        this.cmd = "";
        this.appkey = "";
        this.session = "";
        this.utctime = "";
        this.localtime = "";
        this.token = "";
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
        
        // log split
        String[] fields = record.split(ConstantVars.LOG_FIELD_SEPERATER_REGX, SdkFieldInfo.CommonFieldCount);
        
        if (fields == null
                || fields.length < SdkFieldInfo.CommonFieldCount) {
            this.raised_error = true;
            this.parse_error = ParseError.ERRORFIELDCOUNT;
            this.error_level = LogValidation.WASTE;
            return;
        }
        
        this.cmd = fields[CommonFieldIndex.CMD];
        if (this.cmd!=null && this.cmd.isEmpty()==false
                && (this.cmd.equals(ConstantVars.CMD_STARTSESS)
                        || this.cmd.equals(ConstantVars.CMD_PAGEVIEW)
                        || this.cmd.equals(ConstantVars.CMD_ENDSESS))) {
            
            this.appkey = fields[CommonFieldIndex.APPKEY];
            this.session = fields[CommonFieldIndex.SESSION];
            this.utctime = fields[CommonFieldIndex.UTCTIME];
            this.localtime = fields[CommonFieldIndex.LOCALTIME];
            this.token = fields[CommonFieldIndex.TOKEN];
            this.country = fields[CommonFieldIndex.COUNTRY];
            this.language = fields[CommonFieldIndex.LANGUAGE];
            this.device = fields[CommonFieldIndex.DEVICE];
            this.osversion = fields[CommonFieldIndex.OSVERSION];
            this.resolution = fields[CommonFieldIndex.RESOLUTION];
            this.appversion = fields[CommonFieldIndex.APPVERSION];
            
            // "NULL" string => "" string, "|" -> "/"
            this.appkey = this.appkey.equals(ConstantVars.LOG_NULL) ? "" : this.appkey;
            this.session = this.session.equals(ConstantVars.LOG_NULL) ? "" : this.session;
            this.utctime = this.utctime.equals(ConstantVars.LOG_NULL) ? "" : this.utctime;
            this.localtime = this.localtime.equals(ConstantVars.LOG_NULL) ? "" : this.localtime;
            this.token = this.token.equals(ConstantVars.LOG_NULL) ? "" : this.token;
            this.country = this.country.equals(ConstantVars.LOG_NULL) ? "" : this.country.replaceAll("\\|", "/");
            this.language = this.language.equals(ConstantVars.LOG_NULL) ? "" : this.language.replaceAll("\\|", "/");
            this.device = this.device.equals(ConstantVars.LOG_NULL) ? "" : this.device.replaceAll("\\|", "/");
            this.osversion = this.osversion.equals(ConstantVars.LOG_NULL) ? "" : this.osversion.replaceAll("\\|", "/");
            this.resolution = this.resolution.equals(ConstantVars.LOG_NULL) ? "" : this.resolution.replaceAll("\\|", "/");
            this.appversion = this.appversion.equals(ConstantVars.LOG_NULL) ? "" : this.appversion.replaceAll("\\|", "/");
            
            // cut string over max-length
            if (this.country.length() > ConstantVars.MAX_LENGTH_COUNTRY) this.country = this.country.substring(0, ConstantVars.MAX_LENGTH_COUNTRY);
            if (this.language.length() > ConstantVars.MAX_LENGTH_LANGUAGE) this.language = this.language.substring(0, ConstantVars.MAX_LENGTH_LANGUAGE);
            if (this.device.length() > ConstantVars.MAX_LENGTH_DEVICE) this.device = this.device.substring(0, ConstantVars.MAX_LENGTH_DEVICE);
            if (this.osversion.length() > ConstantVars.MAX_LENGTH_OSVERSION) this.osversion = this.osversion.substring(0, ConstantVars.MAX_LENGTH_OSVERSION);
            if (this.resolution.length() > ConstantVars.MAX_LENGTH_RESOLUTION) this.resolution = this.resolution.substring(0, ConstantVars.MAX_LENGTH_RESOLUTION);
            if (this.appversion.length() > ConstantVars.MAX_LENGTH_APPVERSION) this.appversion = this.appversion.substring(0, ConstantVars.MAX_LENGTH_APPVERSION);
            
            // appkey error, error-level : MALFORMED
            if (this.appkey.isEmpty() || !isValidAppkey(this.appkey)) {
                this.raised_error = true;
                this.parse_error = ParseError.ERRORAPPKEY;
                this.error_level = LogValidation.MALFORMED;
                return;
            }
            
            // session/utctime/localtime/token error, error-level : MALFORMED
            if (this.session.isEmpty()) {
                this.raised_error = true;
                this.parse_error = ParseError.ERRORSESSION;
                this.error_level = LogValidation.MALFORMED;
                return;
            }
            if (!isValidTime(this.utctime) || !isValidTime(this.localtime)) {
                this.raised_error = true;
                this.parse_error = ParseError.ERRORTIME;
                this.error_level = LogValidation.MALFORMED;
                return;
            }
            if (this.token.isEmpty()) {
                this.raised_error = true;
                this.parse_error = ParseError.ERRORTOKEN;
                this.error_level = LogValidation.MALFORMED;
                return;
            }
            
            // country/language/device/osversion/resolution/appversion error, error-level : WELLFORMED
            if (this.country.isEmpty()
                    || this.language.isEmpty()
                    || this.device.isEmpty()
                    || this.osversion.isEmpty()
                    || this.resolution.isEmpty()
                    || this.appversion.isEmpty()) {
                this.raised_error = false;
                this.error_level = LogValidation.WELLFORMED;
            }
            else {
                this.raised_error = false;
                this.error_level = LogValidation.CLEAN;
            }
        }
        else {
            // error-level : MALFORMED
            this.raised_error = true;
            this.parse_error = ParseError.ERRORCMD;
            this.error_level = LogValidation.MALFORMED;
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
    
    public boolean isValidAppkey(String src) {
        return APPKEY_PATTERN.matcher(src).matches();
    }
    public boolean isValidTime(String src) {
        return DateTimeUtil.isValidDate(src, ConstantVars.LOG_DATE_FORMAT);
    }
    public boolean isValidNumber(String src) {
        return FormatUtil.isValidNumber(src);
    }
    
    public String getCmd() {
        return this.cmd;
    }
    public String getAppkey() {
        return this.appkey;
    }
    public String getSession() {
        return this.session;
    }
    public String getUtctime() {
        return this.utctime;
    }
    public String getLocaltime() {
        return this.localtime;
    }
    public String getToken() {
        return this.token;
    }
    public String getCountry() {
        if (this.country.isEmpty())
            return "UNKNOWN";
        this.country = this.country.toUpperCase();
        return this.country;
    }
    public String getLanguage() {
        if (this.language.isEmpty())
            return "unknown";
        this.language = this.language.toLowerCase();
        return this.language;
    }
    public String getDevice() {
        if (this.device.isEmpty())
            return "UNKNOWN";
        this.device = this.device.toUpperCase();
        return this.device;
    }
    public String getOsversion() {
        if (this.osversion.isEmpty())
            return "unknown";
        this.osversion = this.osversion.toLowerCase();
        return this.osversion;
    }
    public String getResolution() {
        if (this.resolution.isEmpty())
            return "UNKNOWN";
        this.resolution = this.resolution.toUpperCase();
        return this.resolution;
    }
    public String getAppversion() {
        if (this.appversion.isEmpty())
            return "unknown";
        this.appversion = this.appversion.toLowerCase();
        return this.appversion;
    }
    
    public void printDebug() {
        System.out.println("cmd : " + this.cmd);
        System.out.println("appkey : " + this.appkey);
        System.out.println("session : " + this.session);
        System.out.println("utctime : " + this.utctime);
        System.out.println("localtime : " + this.localtime);
        System.out.println("token : " + this.token);
        System.out.println("country : " + this.country);
        System.out.println("language : " + this.language);
        System.out.println("device : " + this.device);
        System.out.println("osversion : " + this.osversion);
        System.out.println("resolution : " + this.resolution);
        System.out.println("appversion : " + this.appversion);
    }
    
    public static void main(String[] args) throws Exception {
        
        CommonLogParser parser = new CommonLogParser();
        
        String log = "STARTSESS||fin278318||a03ab4b9-93d6-47d8-8159-3ddf7c3a878a||20140417061757||20140417151757||00000000-101c-4612-ffff-ffff9a3181c8||KR||ko||IM-A870L||4.1.2||720X1184||1.0";
        //String log = "PAGEVIEW||fin278318||a03ab4b9-93d6-47d8-8159-3ddf7c3a878a||20140417061757||20140417151757||00000000-101c-4612-ffff-ffff9a3181c8||KR||ko||IM-A870L||4.1.2||720X1184||1.0";
        //String log = "ENDSESS||fin278318||a03ab4b9-93d6-47d8-8159-3ddf7c3a878a||20140417061817||20140417151817||00000000-101c-4612-ffff-ffff9a3181c8||KR||ko||IM-A870L||4.1.2||720X1184||1.0";
        
        parser.parse(log);
        System.out.println(parser.getParseError());
        System.out.println(parser.getErrorLevel().toString());
        parser.printDebug();
        
        return;
    }
}
