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

package ph.fingra.hadoop.dbms.parse.component;

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
import ph.fingra.hadoop.dbms.parse.component.domain.Componentresolution;

public class ComponentresolutionReader {
    
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
    
    public ComponentresolutionReader(FingraphConfig config, TargetDate target) throws IOException {
        
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
        
        this.resultUri = this.lfsPath.getComponentresolution();
    }
    
    private static class ComponentresolutionResultParser {
        
        private static final String COMPONENTRESOLUTION_PATTERN_REGEX
            = "^[a-zA-Z0-9]*\\t"        // appkey
                    + "[a-zA-Z0-9]*\\t" // componentkey
                    + "[a-zA-Z0-9]*\\t" // resolution
                    + "\\d*\\t"         // usercount
                    + "\\d*$";          // sessioncount
        
        private static Pattern COMPONENTRESOLUTION_PATTERN;
        static {
            COMPONENTRESOLUTION_PATTERN = Pattern.compile(COMPONENTRESOLUTION_PATTERN_REGEX);
        }
        
        public static Componentresolution parse(String src) throws ParseException {
            
            Componentresolution vo = null;
            
            if (src == null || src.isEmpty()) {
                throw new ParseException("unable to parse empty string", 0);
            }
            
            if (COMPONENTRESOLUTION_PATTERN.matcher(src).matches() == true) {
                vo = new Componentresolution();
                String[] values = src.split("\\t");
                vo.setAppkey(values[0].trim());
                vo.setComponentkey(values[1].trim());
                vo.setResolution(values[2].trim());
                try {
                    vo.setUsercount(Long.parseLong(values[3].trim()));
                    vo.setSessioncount(Long.parseLong(values[4].trim()));
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
    
    public List<Componentresolution> getComponentresolutionResults(String appkey,
            String componentkey) throws IOException {
        
        String uri = this.resultUri;
        
        List<Componentresolution> list = new ArrayList<Componentresolution>();
        
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
                    Componentresolution vo = ComponentresolutionResultParser.parse(line);
                    if (vo != null && vo.getAppkey().equals(appkey)
                            && vo.getComponentkey().equals(componentkey)) {
                        
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
                    Componentresolution src = ComponentresolutionResultParser.parse(line);
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
    
    public List<String> getComponentkeyResults(String appkey) throws IOException {
        
        String uri = this.resultUri;
        
        List<String> list = new ArrayList<String>();
        
        FileInputStream fstream = null;
        DataInputStream in = null;
        BufferedReader br = null;
        try {
            
            fstream = new FileInputStream(uri);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
            
            Set<String> componentKeys = new HashSet<String>();
            
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;
                
                try {
                    Componentresolution src = ComponentresolutionResultParser.parse(line);
                    if (src != null && src.getAppkey().equals(appkey)
                            && componentKeys.contains(src.getComponentkey()) == false) {
                        
                        list.add(src.getComponentkey());
                        
                        componentKeys.add(src.getComponentkey());
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
        
        ComponentresolutionReader reader = new ComponentresolutionReader(config, target);
        List<Componentresolution> list = reader.getComponentresolutionResults("fin278318", "evt196318");
        
        for (Componentresolution vo : list) {
            System.out.println(vo.toString());
        }
        
        System.out.println("--------------------");
        
        target = ArgsOptionUtil.getTargetDate("week", "2014-34");
        reader = new ComponentresolutionReader(config, target);
        list = reader.getComponentresolutionResults("fin278318", "evt196318");
        for (Componentresolution vo : list) {
            System.out.println(vo.toString());
        }
        
        System.out.println("--------------------");
        
        target = ArgsOptionUtil.getTargetDate("month", "2014-08");
        reader = new ComponentresolutionReader(config, target);
        list = reader.getComponentresolutionResults("fin278318", "evt196318");
        for (Componentresolution vo : list) {
            System.out.println(vo.toString());
        }
        
        System.exit(0);
    }
}
