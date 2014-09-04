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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.ConstantVars.LogParserType;

public class FormatUtil {
    
    private static final String NUMBER_REGEX = "^\\d*$";
    private static Pattern NUMBER_PATTERN;
    private static final String CURRENCY_REGEX = "^[\\d,.]*$";
    private static Pattern CURRENCY_PATTERN;
    
    static {
        NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
        CURRENCY_PATTERN = Pattern.compile(CURRENCY_REGEX);
    }
    
    public static boolean isValidNumber(String src) {
        return NUMBER_PATTERN.matcher(src).matches();
    }
    public static boolean isValidCurrency(String src) {
        return CURRENCY_PATTERN.matcher(src).matches();
    }
    
    public static int getInteger(String src) {
        int val = 0;
        
        try {
            val = Integer.parseInt(src);
        }
        catch (NumberFormatException e) {
            return 0;
        }
        
        return val;
    }
    
    public static long getLong(String src) {
        long val = 0;
        
        try {
            val = Long.parseLong(src);
        }
        catch (NumberFormatException e) {
            return 0;
        }
        
        return val;
    }
    
    public static double getDouble(String src) {
        double val = 0;
        
        try {
            if (src.indexOf(",") >= 0)
                src = src.replaceAll(",", "");
            val = Double.parseDouble(src);
        }
        catch (NumberFormatException e) {
            return 0;
        }
        
        return val;
    }
    
    public static String getDateFromLogfile(String src) {
        String val = "";
        
        if (src == null || src.isEmpty())
            return "";
        
        Pattern p = Pattern.compile("([0-9]{4})\\-([0-9]{2})\\-([0-9]{2})");
        
        Matcher m = p.matcher(src);
        
        if (m.find()) {
            val = src.substring(m.start(), m.end());
        }
        
        return val;
    }
    
    public static String getDurationFromMillitimes(long millis) {
        
        int seconds = (int)(millis / 1000) % 60 ;
        int minutes = (int)((millis / (1000*60)) % 60);
        int hours = (int)((millis / (1000*60*60)) % 24);
        
        ArrayList<String> timeArray = new ArrayList<String>();
        
        if(hours>0)   
            timeArray.add(String.valueOf(hours) + " h");
        if(minutes>0) 
            timeArray.add(String.valueOf(minutes) + " min");
        if(seconds>0) 
            timeArray.add(String.valueOf(seconds) + " sec");
        
        String duration = "";
        for (int i = 0; i < timeArray.size(); i++) 
        {
            duration = duration + timeArray.get(i);
            if (i != timeArray.size() - 1)
                duration = duration + ", ";
        }
        
        if (duration == "")
            duration = "0 sec";
        
        return duration;
    }
    
    public static LogParserType getLogParserType(String src) {
        
        LogParserType type = LogParserType.CommonLog;
        
        if (src == null || src.isEmpty())
            return type;
        
        if (src.startsWith(ConstantVars.CMD_STARTSESS)
                || src.startsWith(ConstantVars.CMD_PAGEVIEW)
                || src.startsWith(ConstantVars.CMD_ENDSESS)) {
            type = LogParserType.CommonLog;
        }
        else if (src.startsWith(ConstantVars.CMD_COMPONENT)) {
            type = LogParserType.ComponentLog;
        }
        else {
            type = LogParserType.InvalidLog;
        }
        
        return type;
    }
    
    public static String getStartLogString(StringBuilder buf,
            String appkey, String session, String utctime, String localtime,
            String token, String country, String language, String device,
            String osversion, String resolution, String appversion) {
        
        // Start Format
        // CMD APPKEY SESSION UTCTIME LOCALTIME TOKEN COUNTRY LANGUAGE DEVICE OSVERSION  RESOLUTION  APPVERSION
        
        buf.delete(0, buf.length());
        buf.append(ConstantVars.CMD_STARTSESS);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(appkey);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(session);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(utctime);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(localtime);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(token);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(country);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(language);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(device);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(osversion);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(resolution);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(appversion);
        
        return buf.toString();
    }
    public static String getPageviewLogString(StringBuilder buf,
            String appkey, String session, String utctime, String localtime,
            String token, String country, String language, String device,
            String osversion, String resolution, String appversion) {
        
        // Pageview Format
        // CMD APPKEY SESSION UTCTIME LOCALTIME TOKEN COUNTRY LANGUAGE DEVICE OSVERSION  RESOLUTION  APPVERSION
        
        buf.delete(0, buf.length());
        buf.append(ConstantVars.CMD_PAGEVIEW);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(appkey);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(session);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(utctime);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(localtime);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(token);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(country);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(language);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(device);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(osversion);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(resolution);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(appversion);
        
        return buf.toString();
    }
    public static String getEndLogString(StringBuilder buf,
            String appkey, String session, String utctime, String localtime,
            String token, String country, String language, String device,
            String osversion, String resolution, String appversion) {
        
        // End Format
        // CMD APPKEY SESSION UTCTIME LOCALTIME TOKEN COUNTRY LANGUAGE DEVICE OSVERSION  RESOLUTION  APPVERSION
        
        buf.delete(0, buf.length());
        buf.append(ConstantVars.CMD_ENDSESS);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(appkey);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(session);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(utctime);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(localtime);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(token);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(country);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(language);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(device);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(osversion);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(resolution);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(appversion);
        
        return buf.toString();
    }
    public static String getComponentLogString(StringBuilder buf,
            String appkey, String componentkey, String session, String utctime,
            String localtime, String token, String country, String language,
            String device, String osversion, String resolution, String appversion) {
        
        // Component Format
        // CMD APPKEY COMPONENTKEY SESSION UTCTIME LOCALTIME TOKEN COUNTRY LANGUAGE DEVICE OSVERSION  RESOLUTION  APPVERSION
        
        buf.delete(0, buf.length());
        buf.append(ConstantVars.CMD_COMPONENT);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(appkey);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(componentkey);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(session);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(utctime);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(localtime);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(token);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(country);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(language);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(device);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(osversion);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(resolution);
        buf.append(ConstantVars.LOG_FIELD_SEPERATER_STR);
        buf.append(appversion);
        
        return buf.toString();
    }
    
    public static void main(String[] args) throws Exception {
        
        System.out.println(FormatUtil.getDateFromLogfile("WAS1.fingraphsdk_android_log.2013-01-24.txt"));
        System.out.println(FormatUtil.getDateFromLogfile("WAS2.fingraphsdk_iphone_log.2013-01-24.txt"));
        System.out.println(FormatUtil.getDateFromLogfile("fingraphsdk_android_log.2013-01-24.txt"));
        System.out.println(FormatUtil.getDateFromLogfile("2013-01-24.txt"));
        System.out.println(FormatUtil.getDateFromLogfile("app_newuser_db"));
        
        System.out.println("01".compareTo("02"));
        
        System.out.println("20140310231701".compareTo("20140310231601"));
        System.out.println("2014081916".compareTo("2014082016"));
        
        System.out.println(getDouble("10"));
        System.out.println(getDouble("1.99"));
        System.out.println(getDouble("10,000.25"));
        
        System.out.println("20140310231701".substring(8, 8+2)); //=> 23시
        
        System.out.println(FormatUtil.isValidCurrency("100"));
        System.out.println(FormatUtil.isValidCurrency("100.99"));
        System.out.println(FormatUtil.isValidCurrency("1,000.99"));
        
        return;
    }
}
