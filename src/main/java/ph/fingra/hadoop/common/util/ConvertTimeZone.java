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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ConvertTimeZone {
    
    /*
     * Convert UTC to LOCAL (yyyyMMddHHmmss format)
     */
    public String convertUtcToLocal(String utcTime) throws Exception {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.UK);
        Date dateLocalTime = new Date();
        String localTime = "";
        
        try {
            // parse utctime string
            Date dateUtcTime = dateFormat.parse(utcTime);
            
            // utc date -> long
            long longUtcTime = dateUtcTime.getTime();
            
            // calculate time difference using TimeZone
            // (use "getOffset" instead of "getRawOffset" because of daylight saving time)
            TimeZone zone = TimeZone.getDefault();
            int offset = zone.getOffset(longUtcTime);
            long longLocalTime = longUtcTime + offset;
            
            dateLocalTime.setTime(longLocalTime);
            
            // get localtime string 
            localTime = dateFormat.format(dateLocalTime);
            
        } catch (ParseException e) {
            throw new Exception("Invalid date format", e);
        }
        
        return localTime;
    }
    
    public String applyStandardFormat(String logtime) throws Exception {
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.UK);
        String formatTime = "";
        
        try {
            Date date = dateFormat.parse(logtime);
            
            formatTime = dateFormat.format(date);
        } catch (ParseException e) {
            throw new Exception("Invalid date format", e);
        }
        
        return formatTime;
    }
    
    public static void main(String[] args) throws Exception {
        
        //String utcTime = "20120713184755";
        String utcTime = "20120713064755";
        
        ConvertTimeZone timeZone = new ConvertTimeZone();
        String localTime = timeZone.convertUtcToLocal(utcTime);
        
        System.out.println("UTC: " + utcTime + ", LOCAL: " + localTime);
        
        System.out.println("KR - u: 20120714150810, l: 20120715000810 - convert:"
                + timeZone.convertUtcToLocal("20120714150810"));
        System.out.println("TH - u: 20120714150909, l: 20120714220909 - convert:"
                + timeZone.convertUtcToLocal("20120714150909"));
        System.out.println("AU - u: 20120714151305, l: 20120715011305 - convert:"
                + timeZone.convertUtcToLocal("20120714151305"));
    }
    
}
