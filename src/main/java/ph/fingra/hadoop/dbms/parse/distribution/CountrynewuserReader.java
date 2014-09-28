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

package ph.fingra.hadoop.dbms.parse.distribution;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import ph.fingra.hadoop.common.ConstantVars;
import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.LfsPathInfo;
import ph.fingra.hadoop.common.domain.TargetDate;
import ph.fingra.hadoop.common.util.ArgsOptionUtil;
import ph.fingra.hadoop.common.util.DateTimeUtil;
import ph.fingra.hadoop.dbms.parse.distribution.domain.Countrynewuser;

public class CountrynewuserReader {
    
    @SuppressWarnings("unused")
    private String mode;
    private String year;
    private String month;
    private String day;
    private String hour;
    private int week;
    private String date;
    private String datehour;
    private int dayofweek;
    private String fromdate;
    private String todate;
    
    private LfsPathInfo lfsPath = null;
    private String resultUri = null;
    
    private String getWeek() {
        if (this.week == 0) return null;
        return (this.week<10) ? "0"+this.week : String.valueOf(this.week);
    }
    
    public CountrynewuserReader(FingraphConfig config, TargetDate target) throws IOException {
        
        this.mode = target.getRunmode();
        
        if (target.getRunmode().equals(ConstantVars.RUNMODE_HOUR)) {
            this.year = target.getYear();
            this.month = target.getMonth();
            this.day = target.getDay();
            this.hour = target.getHour();
            this.week = target.getWeek();
            this.date = this.year + "-" + this.month + "-" + this.day;
            this.datehour = this.year + "-" + this.month + "-" + this.day + " " + this.hour + ":00:00";
            this.dayofweek = DateTimeUtil.getDayofWeekByDay(this.year, this.month, this.day);
        }
        else if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY)) {
            this.year = target.getYear();
            this.month = target.getMonth();
            this.day = target.getDay();
            this.week = target.getWeek();
            this.date = this.year + "-" + this.month + "-" + this.day;
            this.dayofweek = DateTimeUtil.getDayofWeekByDay(this.year, this.month, this.day);
        }
        else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK)) {
            this.year = target.getYear();
            this.week = target.getWeek();
            this.fromdate = DateTimeUtil.startDayOfWeek(this.year, this.week, "yyyy-MM-dd");
            this.todate = DateTimeUtil.lastDayOfWeek(this.year, this.week, "yyyy-MM-dd");
        }
        else if (target.getRunmode().equals(ConstantVars.RUNMODE_MONTH)) {
            this.year = target.getYear();
            this.month = target.getMonth();
        }
        else {
            throw new IOException("Invalid runmode for creating reader");
        }
        
        this.lfsPath = new LfsPathInfo(config, target);
        
        this.resultUri = this.lfsPath.getCountrynewuser();
    }
    
    private static class CountrynewuserResultParser {
        
        private static final String COUNTRYNEWUSER_PATTERN_REGEX
            = "^[a-zA-Z0-9]*\\t"        // appkey
                    + "[\\w\\W]*\\t"    // country
                    + "\\d*$";          // usercount
        
        private static Pattern COUNTRYNEWUSER_PATTERN;
        static {
            COUNTRYNEWUSER_PATTERN = Pattern.compile(COUNTRYNEWUSER_PATTERN_REGEX);
        }
        
        public static Countrynewuser parse(String src) throws ParseException {
            
            Countrynewuser vo = null;
            
            if (src == null || src.isEmpty()) {
                throw new ParseException("unable to parse empty string", 0);
            }
            
            if (COUNTRYNEWUSER_PATTERN.matcher(src).matches() == true) {
                vo = new Countrynewuser();
                String[] values = src.split("\\t");
                vo.setAppkey(values[0].trim());
                vo.setCountry(values[1].trim());
                try {
                    vo.setUsercount(Long.parseLong(values[2].trim()));
                }
                catch (NumberFormatException e) {
                    throw new ParseException("invalid number value", 0);
                }
            }
            else {
                throw new ParseException("invalid pattern string", 0);
            }
            
            return vo;
        }
    }
    
    public List<Countrynewuser> getCountrynewuserResults(String appkey,
            String country) throws IOException {
        
        String uri = this.resultUri;
        
        List<Countrynewuser> list = new ArrayList<Countrynewuser>();
        
        FileInputStream fstream = null;
        DataInputStream in = null;
        BufferedReader br = null;
        try {
            
            fstream = new FileInputStream(uri);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                
                try {
                    Countrynewuser vo = CountrynewuserResultParser.parse(line);
                    if (vo != null && vo.getAppkey().equals(appkey)
                            && vo.getCountry().equals(country)) {
                        
                        vo.setYear(this.year);
                        vo.setMonth(this.month);
                        vo.setDay(this.day);
                        vo.setHour(this.hour);
                        vo.setWeek(this.getWeek());
                        vo.setDate(this.date);
                        vo.setDatehour(this.datehour);
                        vo.setDayofweek(this.dayofweek);
                        vo.setFromdate(this.fromdate);
                        vo.setTodate(this.todate);
                        
                        list.add(vo);
                    }
                }
                catch (ParseException ignore) {
                    continue;
                }
            }
        }
        catch (FileNotFoundException ignore) {
            ;
        }
        catch (IOException ioe) {
            throw ioe;
        }
        finally {
            if (br != null) br.close();
            if (in != null) in.close();
            if (fstream != null) fstream.close();
        }
        
        return list;
    }
    
    public List<String> getAppkeyResults() throws IOException {
        
        String uri = this.resultUri;
        
        List<String> list = new ArrayList<String>();
        
        FileInputStream fstream = null;
        DataInputStream in = null;
        BufferedReader br = null;
        try {
            
            fstream = new FileInputStream(uri);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            
            Set<String> appKeys = new HashSet<String>();
            
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                
                try {
                    Countrynewuser src = CountrynewuserResultParser.parse(line);
                    if (src != null && appKeys.contains(src.getAppkey()) == false) {
                        
                        list.add(src.getAppkey());
                        
                        appKeys.add(src.getAppkey());
                    }
                }
                catch (ParseException ignore) {
                    continue;
                }
            }
        }
        catch (FileNotFoundException ignore) {
            ;
        }
        catch (IOException ioe) {
            throw ioe;
        }
        finally {
            if (br != null) br.close();
            if (in != null) in.close();
            if (fstream != null) fstream.close();
        }
        
        return list;
    }
    
    public List<String> getConturycodeResults(String appkey) throws IOException {
        
        String uri = this.resultUri;
        
        List<String> list = new ArrayList<String>();
        
        FileInputStream fstream = null;
        DataInputStream in = null;
        BufferedReader br = null;
        try {
            
            fstream = new FileInputStream(uri);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            
            Set<String> countryCodes = new HashSet<String>();
            
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                
                try {
                    Countrynewuser src = CountrynewuserResultParser.parse(line);
                    if (src != null && src.getAppkey().equals(appkey)
                            && countryCodes.contains(src.getCountry()) == false) {
                        
                        list.add(src.getCountry());
                        
                        countryCodes.add(src.getCountry());
                    }
                }
                catch (ParseException ignore) {
                    continue;
                }
            }
        }
        catch (FileNotFoundException ignore) {
            ;
        }
        catch (IOException ioe) {
            throw ioe;
        }
        finally {
            if (br != null) br.close();
            if (in != null) in.close();
            if (fstream != null) fstream.close();
        }
        
        return list;
    }
    
    public static void main(String[] args) throws IOException {
        
        FingraphConfig config = new FingraphConfig();
        TargetDate target = ArgsOptionUtil.getTargetDate("day", "2014-08-20");
        
        CountrynewuserReader reader = new CountrynewuserReader(config, target);
        List<Countrynewuser> list = reader.getCountrynewuserResults("fin278318", "KR");
        
        for (Countrynewuser vo : list) {
            System.out.println(vo.toString());
        }
        
        System.out.println("--------------------");
        
        target = ArgsOptionUtil.getTargetDate("week", "2014-34");
        reader = new CountrynewuserReader(config, target);
        list = reader.getCountrynewuserResults("fin278318", "KR");
        for (Countrynewuser vo : list) {
            System.out.println(vo.toString());
        }
        
        System.out.println("--------------------");
        
        target = ArgsOptionUtil.getTargetDate("month", "2014-08");
        reader = new CountrynewuserReader(config, target);
        list = reader.getCountrynewuserResults("fin278318", "KR");
        for (Countrynewuser vo : list) {
            System.out.println(vo.toString());
        }
        
        System.exit(0);
    }
}
