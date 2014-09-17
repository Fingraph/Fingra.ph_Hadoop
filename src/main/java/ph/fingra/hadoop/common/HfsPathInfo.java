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

public class HfsPathInfo {
    
    @SuppressWarnings("unused")
    private String PROJECT_ROOT;
    private String HADOOP_USER_ROOT;
    private String RESULT_DIR;
    private String DATABASE_DIR;
    private String DATE_DIR;
    
    private String OUTPUT;
    
    private String OUTPUT_prerole;
    private String OUTPUT_prerole_logcount;
    private String OUTPUT_prerole_pretransform;
    private String OUTPUT_prerole_appkey;
    private String OUTPUT_prerole_componentkey;
    
    private String OUTPUT_merge;
    private String OUTPUT_merge_appnewusermerge;
    private String OUTPUT_merge_componentnewusermerge;
    private String OUTPUT_merge_appnewuserhourlymerge;
    
    private String OUTPUT_perform;
    private String OUTPUT_perform_newuser;
    private String OUTPUT_perform_usersession;
    private String OUTPUT_perform_tokenfreq;
    private String OUTPUT_perform_frequency;
    private String OUTPUT_perform_hoursession;
    private String OUTPUT_perform_sesstime;
    private String OUTPUT_perform_sessionlength;
    private String OUTPUT_perform_pageview;
    
    private String OUTPUT_distribute;
    private String OUTPUT_distribute_device;
    private String OUTPUT_distribute_country;
    private String OUTPUT_distribute_language;
    private String OUTPUT_distribute_appversion;
    private String OUTPUT_distribute_osversion;
    private String OUTPUT_distribute_resolution;
    private String OUTPUT_distribute_countrynewuser;
    private String OUTPUT_distribute_countryhoursession;
    private String OUTPUT_distribute_countrysesstime;
    private String OUTPUT_distribute_countrysessionlength;
    private String OUTPUT_distribute_countrypageview;
    
    private String OUTPUT_component;
    private String OUTPUT_component_componentnewuser;
    private String OUTPUT_component_componentusersession;
    private String OUTPUT_component_componenthoursession;
    private String OUTPUT_component_componenttokenfreq;
    private String OUTPUT_component_componentfrequency;
    private String OUTPUT_component_componentdevice;
    private String OUTPUT_component_componentcountry;
    private String OUTPUT_component_componentlanguage;
    private String OUTPUT_component_componentappversion;
    private String OUTPUT_component_componentosversion;
    private String OUTPUT_component_componentresolution;
    
    private String DATABASE;
    private String DATABASE_root;
    private String DATABASE_app_newuser_db;
    private String DATABASE_component_newuser_db;
    
	public String getLogcount() {
        return OUTPUT_prerole_logcount;
    }
    public String getPretransform() {
        return OUTPUT_prerole_pretransform;
    }
    public String getAppkey() {
        return OUTPUT_prerole_appkey;
    }
    public String getComponentkey() {
        return OUTPUT_prerole_componentkey;
    }
    public String getAppnewusermerge() {
        return OUTPUT_merge_appnewusermerge;
    }
    public String getComponentnewusermerge() {
        return OUTPUT_merge_componentnewusermerge;
    }
    public String getAppnewuserhourlymerge() {
        return OUTPUT_merge_appnewuserhourlymerge;
    }
    public String getNewuser() {
        return OUTPUT_perform_newuser;
    }
    public String getUsersession() {
        return OUTPUT_perform_usersession;
    }
    public String getTokenfreq() {
        return OUTPUT_perform_tokenfreq;
    }
    public String getFrequency() {
        return OUTPUT_perform_frequency;
    }
    public String getHoursession() {
        return OUTPUT_perform_hoursession;
    }
    public String getSesstime() {
        return OUTPUT_perform_sesstime;
    }
    public String getSessionlength() {
        return OUTPUT_perform_sessionlength;
    }
    public String getPageview() {
        return OUTPUT_perform_pageview;
    }
    public String getDevice() {
        return OUTPUT_distribute_device;
    }
    public String getCountry() {
        return OUTPUT_distribute_country;
    }
    public String getLanguage() {
        return OUTPUT_distribute_language;
    }
    public String getAppversion() {
        return OUTPUT_distribute_appversion;
    }
    public String getOsversion() {
        return OUTPUT_distribute_osversion;
    }
    public String getResolution() {
        return OUTPUT_distribute_resolution;
    }
    public String getCountrynewuser() {
        return OUTPUT_distribute_countrynewuser;
    }
    public String getCountryhoursession() {
        return OUTPUT_distribute_countryhoursession;
    }
    public String getCountrysesstime() {
        return OUTPUT_distribute_countrysesstime;
    }
    public String getCountrysessionlength() {
        return OUTPUT_distribute_countrysessionlength;
    }
    public String getCountrypageview() {
        return OUTPUT_distribute_countrypageview;
    }
    public String getComponentnewuser() {
        return OUTPUT_component_componentnewuser;
    }
    public String getComponentusersession() {
        return OUTPUT_component_componentusersession;
    }
    public String getComponenthoursession() {
        return OUTPUT_component_componenthoursession;
    }
    public String getComponenttokenfreq() {
        return OUTPUT_component_componenttokenfreq;
    }
    public String getComponentfrequency() {
        return OUTPUT_component_componentfrequency;
    }
    public String getComponentdevice() {
        return OUTPUT_component_componentdevice;
    }
    public String getComponentcountry() {
        return OUTPUT_component_componentcountry;
    }
    public String getComponentlanguage() {
        return OUTPUT_component_componentlanguage;
    }
    public String getComponentappversion() {
        return OUTPUT_component_componentappversion;
    }
    public String getComponentosversion() {
        return OUTPUT_component_componentosversion;
    }
    public String getComponentresolution() {
        return OUTPUT_component_componentresolution;
    }
    public String getDATABASE_root() {
        return this.DATABASE_root;
    }
    public String getApp_newuser_db() {
        return DATABASE_app_newuser_db;
    }
    public String getComponent_newuser_db() {
        return DATABASE_component_newuser_db;
    }
    
    public HfsPathInfo(FingraphConfig config, String runmode) {
        
        this.PROJECT_ROOT = config.getProject_path();
        this.HADOOP_USER_ROOT = config.getHadoop_user_path();
        this.RESULT_DIR = config.getSetting().getHfs_output_path();
        this.DATABASE_DIR = config.getSetting().getHfs_database_path();
        
        if (runmode.equals(ConstantVars.RUNMODE_HOUR))
            this.DATE_DIR = "hourly";
        else if (runmode.equals(ConstantVars.RUNMODE_DAY))
            this.DATE_DIR = "daily";
        else if (runmode.equals(ConstantVars.RUNMODE_WEEK))
            this.DATE_DIR = "weekly";
        else
            this.DATE_DIR = "monthly";
        
        initPaths();
    }
    
    private void initPaths() {
        
        ///////////////////////////////////////////////////////////////////////
        // database
        ///////////////////////////////////////////////////////////////////////
        this.DATABASE = this.HADOOP_USER_ROOT
                + (this.HADOOP_USER_ROOT.endsWith("/") ? "" : "/")
                + this.DATABASE_DIR;
        this.DATABASE_root = this.DATABASE;
        
        
        /*
         * Total Database Directories
         */
        
        // database/app_newuser_db
        this.DATABASE_app_newuser_db
                = this.DATABASE + (this.DATABASE.endsWith("/") ? "" : "/")
                    + ConstantVars.APP_NEWUSER_DB_FNAME;
        
        // database/component_newuser_db
        this.DATABASE_component_newuser_db
                = this.DATABASE + (this.DATABASE.endsWith("/") ? "" : "/")
                    + ConstantVars.COMPONENT_NEWUSER_DB_FNAME;
        
        ///////////////////////////////////////////////////////////////////////
        // output
        ///////////////////////////////////////////////////////////////////////
        this.OUTPUT = this.HADOOP_USER_ROOT
                + (this.HADOOP_USER_ROOT.endsWith("/") ? "" : "/")
                + this.RESULT_DIR
                + (this.RESULT_DIR.endsWith("/") ? "" : "/")
                + this.DATE_DIR;
        
        
        /*
         * Total Output Directories
         */
        
        // output/mode-dir/prerole
        this.OUTPUT_prerole
                = this.OUTPUT + "/prerole";
        
        // output/mode-dir/prerole/logcount
        this.OUTPUT_prerole_logcount
                = this.OUTPUT_prerole + "/logcount";
        // output/mode-dir/prerole/pretransform
        this.OUTPUT_prerole_pretransform
                = this.OUTPUT_prerole + "/pretransform";
        // output/mode-dir/prerole/appkey
        this.OUTPUT_prerole_appkey
                = this.OUTPUT_prerole + "/appkey";
        // output/mode-dir/prerole/componentkey
        this.OUTPUT_prerole_componentkey
                = this.OUTPUT_prerole + "/componentkey";
        
        // output/mode-dir/merge
        this.OUTPUT_merge
                = this.OUTPUT + "/merge";
        
        // output/mode-dir/merge/appnewusermerge
        this.OUTPUT_merge_appnewusermerge
                = this.OUTPUT_merge + "/appnewusermerge";
        // output/mode-dir/merge/componentnewusermerge
        this.OUTPUT_merge_componentnewusermerge
                = this.OUTPUT_merge + "/componentnewusermerge";
        // output/mode-dir/merge/appnewuserhourlymerge
        this.OUTPUT_merge_appnewuserhourlymerge
                = this.OUTPUT_merge + "/appnewuserhourlymerge";
        
        // output/mode-dir/perform
        this.OUTPUT_perform
                = this.OUTPUT + "/perform";
        
        // output/mode-dir/perform/newuser
        this.OUTPUT_perform_newuser
                = this.OUTPUT_perform + "/newuser";
        // output/mode-dir/perform/usersession
        this.OUTPUT_perform_usersession
                = this.OUTPUT_perform + "/usersession";
        // output/mode-dir/perform/tokenfreq
        this.OUTPUT_perform_tokenfreq
                = this.OUTPUT_perform + "/tokenfreq";
        // output/mode-dir/perform/frequency
        this.OUTPUT_perform_frequency
                = this.OUTPUT_perform + "/frequency";
        // output/mode-dir/perform/hoursession
        this.OUTPUT_perform_hoursession
                = this.OUTPUT_perform + "/hoursession";
        // output/mode-dir/perform/sesstime
        this.OUTPUT_perform_sesstime
                = this.OUTPUT_perform + "/sesstime";
        // output/mode-dir/perform/sessionlength
        this.OUTPUT_perform_sessionlength
                = this.OUTPUT_perform + "/sessionlength";
        // output/mode-dir/perform/pageview
        this.OUTPUT_perform_pageview
                = this.OUTPUT_perform + "/pageview";
        
        // output/mode-dir/distribute
        this.OUTPUT_distribute
                = this.OUTPUT + "/distribute";
        
        // output/mode-dir/distribute/device
        this.OUTPUT_distribute_device
                = this.OUTPUT_distribute + "/device";
        // output/mode-dir/distribute/country
        this.OUTPUT_distribute_country
                = this.OUTPUT_distribute + "/country";
        // output/mode-dir/distribute/language
        this.OUTPUT_distribute_language
                = this.OUTPUT_distribute + "/language";
        // output/mode-dir/distribute/appversion
        this.OUTPUT_distribute_appversion
                = this.OUTPUT_distribute + "/appversion";
        // output/mode-dir/distribute/osversion
        this.OUTPUT_distribute_osversion
                = this.OUTPUT_distribute + "/osversion";
        // output/mode-dir/distribute/resolution
        this.OUTPUT_distribute_resolution
                = this.OUTPUT_distribute + "/resolution";
        // output/mode-dir/distribute/countrynewuser
        this.OUTPUT_distribute_countrynewuser
                = this.OUTPUT_distribute + "/countrynewuser";
        // output/mode-dir/distribute/countryhoursession
        this.OUTPUT_distribute_countryhoursession
                = this.OUTPUT_distribute + "/countryhoursession";
        // output/mode-dir/distribute/countrysesstime
        this.OUTPUT_distribute_countrysesstime
                = this.OUTPUT_distribute + "/countrysesstime";
        // output/mode-dir/distribute/countrysessionlength
        this.OUTPUT_distribute_countrysessionlength
                = this.OUTPUT_distribute + "/countrysessionlength";
        // output/mode-dir/distribute/countrypageview
        this.OUTPUT_distribute_countrypageview
                = this.OUTPUT_distribute + "/countrypageview";
        
        // output/mode-dir/component
        this.OUTPUT_component
                = this.OUTPUT + "/component";
        
        // output/mode-dir/component/componentnewuser
        this.OUTPUT_component_componentnewuser
                = this.OUTPUT_component + "/componentnewuser";
        // output/mode-dir/component/componentusersession
        this.OUTPUT_component_componentusersession
                = this.OUTPUT_component + "/componentusersession";
        // output/mode-dir/component/componenthoursession
        this.OUTPUT_component_componenthoursession
                = this.OUTPUT_component + "/componenthoursession";
        // output/mode-dir/component/componenttokenfreq
        this.OUTPUT_component_componenttokenfreq
                = this.OUTPUT_component + "/componenttokenfreq";
        // output/mode-dir/component/componentfrequency
        this.OUTPUT_component_componentfrequency
                = this.OUTPUT_component + "/componentfrequency";
        // output/mode-dir/component/componentdevice
        this.OUTPUT_component_componentdevice
                = this.OUTPUT_component + "/componentdevice";
        // output/mode-dir/component/componentcountry
        this.OUTPUT_component_componentcountry
                = this.OUTPUT_component + "/componentcountry";
        // output/mode-dir/component/componentlanguage
        this.OUTPUT_component_componentlanguage
                = this.OUTPUT_component + "/componentlanguage";
        // output/mode-dir/component/componentappversion
        this.OUTPUT_component_componentappversion
                = this.OUTPUT_component + "/componentappversion";
        // output/mode-dir/component/componentosversion
        this.OUTPUT_component_componentosversion
                = this.OUTPUT_component + "/componentosversion";
        // output/mode-dir/component/componentresolution
        this.OUTPUT_component_componentresolution
                = this.OUTPUT_component + "/componentresolution";
        
    }
    
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
    
    public static void main(String[] args) throws IOException {
        
        FingraphConfig config = new FingraphConfig();
        
        HfsPathInfo hfsPath = new HfsPathInfo(config, "hour");
        System.out.println(hfsPath.toString());
        
        hfsPath = new HfsPathInfo(config, "day");
        System.out.println(hfsPath.toString());
        
        hfsPath = new HfsPathInfo(config, "week");
        System.out.println(hfsPath.OUTPUT);
        
        hfsPath = new HfsPathInfo(config, "month");
        System.out.println(hfsPath.OUTPUT);
    }
    
}
