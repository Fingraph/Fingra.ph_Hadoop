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

package ph.fingra.hadoop.common;

import java.io.IOException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import ph.fingra.hadoop.common.FingraphConfig;
import ph.fingra.hadoop.common.domain.TargetDate;

public class LfsPathInfo {
    
    private String PROJECT_ROOT;
    private String RESULT_DIR;
    private String DATE_DIR;
    
    private String RESULT;
    
    private String RESULT_prerole;
    private String RESULT_prerole_logcount;
    private String RESULT_prerole_appkey;
    private String RESULT_prerole_componentkey;
    
    private String RESULT_perform;
    private String RESULT_perform_newuser;
    private String RESULT_perform_usersession;
    private String RESULT_perform_frequency;
    private String RESULT_perform_hoursession;
    private String RESULT_perform_sessionlength;
    private String RESULT_perform_pageview;
    
    private String RESULT_distribute;
    private String RESULT_distribute_device;
    private String RESULT_distribute_country;
    private String RESULT_distribute_language;
    private String RESULT_distribute_appversion;
    private String RESULT_distribute_osversion;
    private String RESULT_distribute_resolution;
    private String RESULT_distribute_countrynewuser;
    private String RESULT_distribute_countryhoursession;
    private String RESULT_distribute_countrysessionlength;
    private String RESULT_distribute_countrypageview;
    
    private String RESULT_component;
    private String RESULT_component_componentnewuser;
    private String RESULT_component_componentusersession;
    private String RESULT_component_componenthoursession;
    private String RESULT_component_componentfrequency;
    private String RESULT_component_componentdevice;
    private String RESULT_component_componentcountry;
    private String RESULT_component_componentlanguage;
    private String RESULT_component_componentappversion;
    private String RESULT_component_componentosversion;
    private String RESULT_component_componentresolution;
    
	public String getLogcount() {
        return RESULT_prerole_logcount;
    }
    public String getAppkey() {
        return RESULT_prerole_appkey;
    }
    public String getComponentkey() {
        return RESULT_prerole_componentkey;
    }
    public String getNewuser() {
        return RESULT_perform_newuser;
    }
    public String getUsersession() {
        return RESULT_perform_usersession;
    }
    public String getFrequency() {
        return RESULT_perform_frequency;
    }
    public String getHoursession() {
        return RESULT_perform_hoursession;
    }
    public String getSessionlength() {
        return RESULT_perform_sessionlength;
    }
    public String getPageview() {
        return RESULT_perform_pageview;
    }
    public String getDevice() {
        return RESULT_distribute_device;
    }
    public String getCountry() {
        return RESULT_distribute_country;
    }
    public String getLanguage() {
        return RESULT_distribute_language;
    }
    public String getAppversion() {
        return RESULT_distribute_appversion;
    }
    public String getOsversion() {
        return RESULT_distribute_osversion;
    }
    public String getResolution() {
        return RESULT_distribute_resolution;
    }
    public String getCountrynewuser() {
        return RESULT_distribute_countrynewuser;
    }
    public String getCountryhoursession() {
        return RESULT_distribute_countryhoursession;
    }
    public String getCountrysessionlength() {
        return RESULT_distribute_countrysessionlength;
    }
    public String getCountrypageview() {
        return RESULT_distribute_countrypageview;
    }
    public String getComponentnewuser() {
        return RESULT_component_componentnewuser;
    }
    public String getComponentusersession() {
        return RESULT_component_componentusersession;
    }
    public String getComponenthoursession() {
        return RESULT_component_componenthoursession;
    }
    public String getComponentfrequency() {
        return RESULT_component_componentfrequency;
    }
    public String getComponentdevice() {
        return RESULT_component_componentdevice;
    }
    public String getComponentcountry() {
        return RESULT_component_componentcountry;
    }
    public String getComponentlanguage() {
        return RESULT_component_componentlanguage;
    }
    public String getComponentappversion() {
        return RESULT_component_componentappversion;
    }
    public String getComponentosversion() {
        return RESULT_component_componentosversion;
    }
    public String getComponentresolution() {
        return RESULT_component_componentresolution;
    }
    
    public LfsPathInfo(FingraphConfig config, TargetDate target) {
        
        PROJECT_ROOT = config.getProject_path();
        RESULT_DIR = config.getSetting().getLfs_result_path();
        
        if (target.getRunmode().equals(ConstantVars.RUNMODE_HOUR))
            DATE_DIR = "hourly/" + target.getYear() + "/" + target.getMonth()
                + "/" + target.getDay() + "/" + target.getHour();
        else if (target.getRunmode().equals(ConstantVars.RUNMODE_DAY))
            DATE_DIR = "daily/" + target.getYear() + "/" + target.getMonth()
                + "/" + target.getDay();
        else if (target.getRunmode().equals(ConstantVars.RUNMODE_WEEK))
            DATE_DIR = "weekly/" + target.getYear() + "/" + target.getWeek_str();
        else
            DATE_DIR = "monthly/" + target.getYear() + "/" + target.getMonth();
        
        initPaths();
    }
    
    private void initPaths() {
        
        ///////////////////////////////////////////////////////////////////////
        // result
        ///////////////////////////////////////////////////////////////////////
        RESULT = PROJECT_ROOT
                + (PROJECT_ROOT.endsWith("/") ? "" : "/")
                + RESULT_DIR
                + (RESULT_DIR.endsWith("/") ? "" : "/")
                + DATE_DIR;
        
        
        /*
         * Total Result Directories
         */
        
        // result/prerole
        RESULT_prerole
                = RESULT + "/prerole";
        
        // result/prerole/logcount
        RESULT_prerole_logcount
                = RESULT_prerole + "/logcount";
        // result/prerole/appkey
        RESULT_prerole_appkey
                = RESULT_prerole + "/appkey";
        // result/prerole/componentkey
        RESULT_prerole_componentkey
                = RESULT_prerole + "/componentkey";
        
        // result/perform
        RESULT_perform
                = RESULT + "/perform";
        
        // result/perform/newuser
        RESULT_perform_newuser
                = RESULT_perform + "/newuser";
        // result/perform/usersession
        RESULT_perform_usersession
                = RESULT_perform + "/usersession";
        // result/perform/frequency
        RESULT_perform_frequency
                = RESULT_perform + "/frequency";
        // result/perform/hoursession
        RESULT_perform_hoursession
                = RESULT_perform + "/hoursession";
        // result/perform/sessionlength
        RESULT_perform_sessionlength
                = RESULT_perform + "/sessionlength";
        // result/perform/pageview
        RESULT_perform_pageview
                = RESULT_perform + "/pageview";
        
        // result/distribute
        RESULT_distribute
                = RESULT + "/distribute";
        
        // result/distribute/device
        RESULT_distribute_device
                = RESULT_distribute + "/device";
        // result/distribute/country
        RESULT_distribute_country
                = RESULT_distribute + "/country";
        // result/distribute/language
        RESULT_distribute_language
                = RESULT_distribute + "/language";
        // result/distribute/appversion
        RESULT_distribute_appversion
                = RESULT_distribute + "/appversion";
        // result/distribute/osversion
        RESULT_distribute_osversion
                = RESULT_distribute + "/osversion";
        // result/distribute/resolution
        RESULT_distribute_resolution
                = RESULT_distribute + "/resolution";
        // result/distribute/countrynewuser
        RESULT_distribute_countrynewuser
                = RESULT_distribute + "/countrynewuser";
        // result/distribute/countryhoursession
        RESULT_distribute_countryhoursession
                = RESULT_distribute + "/countryhoursession";
        // result/distribute/countrysessionlength
        RESULT_distribute_countrysessionlength
                = RESULT_distribute + "/countrysessionlength";
        // result/distribute/countrypageview
        RESULT_distribute_countrypageview
                = RESULT_distribute + "/countrypageview";
        
        // result/component
        RESULT_component
                = RESULT + "/component";
        
        // result/component/componentnewuser
        RESULT_component_componentnewuser
                = RESULT_component + "/componentnewuser";
        // result/component/componentusersession
        RESULT_component_componentusersession
                = RESULT_component + "/componentusersession";
        // result/component/componenthoursession
        RESULT_component_componenthoursession
                = RESULT_component + "/componenthoursession";
        // result/component/componentfrequency
        RESULT_component_componentfrequency
                = RESULT_component + "/componentfrequency";
        // result/component/componentdevice
        RESULT_component_componentdevice
                = RESULT_component + "/componentdevice";
        // result/component/componentcountry
        RESULT_component_componentcountry
                = RESULT_component + "/componentcountry";
        // result/component/componentlanguage
        RESULT_component_componentlanguage
                = RESULT_component + "/componentlanguage";
        // result/component/componentappversion
        RESULT_component_componentappversion
                = RESULT_component + "/componentappversion";
        // result/component/componentosversion
        RESULT_component_componentosversion
                = RESULT_component + "/componentosversion";
        // result/component/componentresolution
        RESULT_component_componentresolution
                = RESULT_component + "/componentresolution";
        
    }
    
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
    
    public static void main(String[] args) throws IOException {
        
        FingraphConfig config = new FingraphConfig();
        TargetDate target = null;
        
        target = new TargetDate();
        target.setRunmode("hour");
        target.setYear("2014");
        target.setMonth("08");
        target.setDay("27");
        target.setHour("16");
        target.setWeek(0);
        LfsPathInfo lfsPath = new LfsPathInfo(config, target);
        System.out.println(lfsPath.toString());
        
        target = new TargetDate();
        target.setRunmode("day");
        target.setYear("2014");
        target.setMonth("08");
        target.setDay("27");
        target.setHour("");
        target.setWeek(0);
        LfsPathInfo lfsPath1 = new LfsPathInfo(config, target);
        System.out.println(lfsPath1.toString());
        
        target = new TargetDate();
        target.setRunmode("week");
        target.setYear("2014");
        target.setMonth("");
        target.setDay("");
        target.setHour("");
        target.setWeek(35);
        LfsPathInfo lfsPath2 = new LfsPathInfo(config, target);
        System.out.println(lfsPath2.RESULT);
        
        target = new TargetDate();
        target.setRunmode("month");
        target.setYear("2014");
        target.setMonth("08");
        target.setDay("");
        target.setHour("");
        target.setWeek(0);
        LfsPathInfo lfsPath3 = new LfsPathInfo(config, target);
        System.out.println(lfsPath3.RESULT);
        
        System.exit(0);
    }
    
}
