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

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    
    /**
     * *** Fingra.ph DateTimeUtil Information ***
     * 
     * Locale - UK
     * FirstDayOfWeek - MONDAY (Not SUNDAY!!!)
     * MinimalDaysInFirstWeek - 4
     * 
     */
    
    public static Date check(String src, String format) throws ParseException {
        
        if ( src == null )
            throw new ParseException("date string to check is null", 0);
        if ( format == null )
            throw new ParseException("format string to check date is null", 0);
        
        SimpleDateFormat formatter = new SimpleDateFormat (format, Locale.UK);
        
        Date date = null;
        try {
            date = formatter.parse(src);
        }
        catch (ParseException e) {
            throw new ParseException(" wrong date:\"" + src + "\" with format \"" + format + "\"", 0);
        }
        
        return date;
    }
    
    public static boolean isValidDate(String src, String format) {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        @SuppressWarnings("unused")
        Date date = null;
        
        try {
            date = formatter.parse(src);
        }
        catch (ParseException e) {
            return false;
        }
        
        return true;
    }
    
    public static String getTodayFormatString(String pattern) {
        
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.UK);
        return formatter.format(new Date());
    }
    
    public static String getYesterdayFormatString(String format) {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        
        Date date = new Date();
        date.setTime(date.getTime() - (1 * 1000 * 60 * 60 * 24));
        
        return formatter.format(date);
    }
    
    public static String getOnehouragoFormatString(String format) {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        
        Date date = new Date();
        date.setTime(date.getTime() - (1 * 1000 * 60 * 60));
        
        return formatter.format(date);
    }
    
    public static int getWeekOfYearByDay(String year, String month, String day) {
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        
        long milltimes = calendar.getTimeInMillis();
        
        calendar.setTimeInMillis(milltimes);
        
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        
        return week;
    }
    
    public static int getDayofWeekByDay(String year, String month, String day) {
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        
        long milltimes = calendar.getTimeInMillis();
        
        calendar.setTimeInMillis(milltimes);
        
        int ndayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        
        return ndayofweek;
    }
    
    public static String startDayOfWeek(String year, int week, String format) {
        
        SimpleDateFormat dayFormat = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        
        Date date = calendar.getTime();
        
        String start_day = dayFormat.format(date);
        
        return start_day;
    }
    
    public static String lastDayOfWeek(String year, int week, String format) {
        
        SimpleDateFormat dayFormat = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        
        Date date = calendar.getTime();
        
        String end_day = dayFormat.format(date);
        
        return end_day;
    }
    
    public static String startDayOfMonth(String year, String month, String format) {
        
        SimpleDateFormat dayFormat = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, (Integer.parseInt(month) - 1));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        Date date = calendar.getTime();
        
        String start_day = dayFormat.format(date);
        
        return start_day;
    }
    
    public static String lastDayOfMonth(String year, String month, String format) {
        
        SimpleDateFormat dayFormat = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, (Integer.parseInt(month) - 1));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        
        Date date = calendar.getTime();
        
        String end_day = dayFormat.format(date);
        
        return end_day;
    }
    
    public static String addDays(String src, int day, String format) throws IOException {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        Date date = null;
        try {
            date = formatter.parse(src);
        }
        catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
        
        date.setTime(date.getTime() + ((long)day * 1000 * 60 * 60 * 24));
        
        return formatter.format(date);
    }
    
    public static String addMonths(String src, int addMonth, String format) throws IOException {
        
        SimpleDateFormat formatter = new SimpleDateFormat (format, Locale.UK);
        SimpleDateFormat informatter = new SimpleDateFormat ("yyyyMMdd", Locale.UK);
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.UK);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.UK);
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.UK);
        DecimalFormat fourDf = new DecimalFormat("0000");
        DecimalFormat twoDf = new DecimalFormat("00");
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        Date date = null;
        try {
            date = formatter.parse(src);
        }
        catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
        
        int year = Integer.parseInt(yearFormat.format(date));
        int month = Integer.parseInt(monthFormat.format(date));
        int day = Integer.parseInt(dayFormat.format(date));
        
        month += addMonth;
        if (addMonth > 0) {
            while (month > 12) {
                month -= 12;
                year += 1;
            }
        } else {
            while (month <= 0) {
                month += 12;
                year -= 1;
            }
        }
        
        String tempdate = String.valueOf(fourDf.format(year))
                + String.valueOf(twoDf.format(month))
                + String.valueOf(twoDf.format(day));
        
        Date targetdate = null;
        try {
            targetdate = informatter.parse(tempdate);
        }
        catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
        
        return formatter.format(targetdate);
    }
    
    public static String addYears(String src, int year, String format) throws IOException {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        Date date = null;
        try {
            date = formatter.parse(src);
        }
        catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
        
        date.setTime(date.getTime() + ((long)year * 1000 * 60 * 60 * 24 * (365 + 1)));
        
        return formatter.format(date);
    }
    
    public static int secondsBetween(String from, String to, String format)
            throws IOException {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(from);
            date2 = formatter.parse(to);
        }
        catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
        
        long duration = date2.getTime() - date1.getTime();
        
        return (int)( duration/1000 );
    }
    
    public static int minutesBetween(String from, String to, String format)
            throws IOException {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(from);
            date2 = formatter.parse(to);
        }
        catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
        
        long duration = date2.getTime() - date1.getTime();
        
        return (int)( duration/(1000 * 60) );
    }
    
    public static int hoursBetween(String from, String to, String format)
            throws IOException {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(from);
            date2 = formatter.parse(to);
        }
        catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
        
        long duration = date2.getTime() - date1.getTime();
        
        return (int)( duration/(1000 * 60 * 60) );
    }
    
    public static int daysBetween(String from, String to, String format)
            throws IOException {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = formatter.parse(from);
            date2 = formatter.parse(to);
        }
        catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
        
        long duration = date2.getTime() - date1.getTime();
        
        return (int)( duration/(1000 * 60 * 60 * 24) );
    }
    
    public static int monthsBetween(String from, String to, String format)
            throws IOException {
        
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.UK);
        
        Calendar calendar = Calendar.getInstance(Locale.UK);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = formatter.parse(from);
            toDate = formatter.parse(to);
        }
        catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
        
        if (fromDate.compareTo(toDate) == 0) return 0;
        
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.UK);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.UK);
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.UK);
        
        int fromYear = Integer.parseInt(yearFormat.format(fromDate));
        int toYear = Integer.parseInt(yearFormat.format(toDate));
        int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
        int toMonth = Integer.parseInt(monthFormat.format(toDate));
        int fromDay = Integer.parseInt(dayFormat.format(fromDate));
        int toDay = Integer.parseInt(dayFormat.format(toDate));
        
        int result = 0;
        result += ((toYear - fromYear) * 12);
        result += (toMonth - fromMonth);
        
        // ceil & floor
        if (((toDay - fromDay) > 0) ) result += toDate.compareTo(fromDate);
        
        return result;
    }
    
}
