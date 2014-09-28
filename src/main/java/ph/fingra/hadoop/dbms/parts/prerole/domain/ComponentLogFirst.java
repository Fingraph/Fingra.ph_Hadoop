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

package ph.fingra.hadoop.dbms.parts.prerole.domain;

import java.io.Serializable;

import ph.fingra.hadoop.common.BaseObject;

public class ComponentLogFirst extends BaseObject implements Serializable {
    
    private static final long serialVersionUID = -2891889787840065108L;
    
    private String appkey;
    private String componentkey;
    private String year;
    private String month;
    private String day;
    private String date;
    
    public String getAppkey() {
        return appkey;
    }
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
    public String getComponentkey() {
        return componentkey;
    }
    public void setComponentkey(String componentkey) {
        this.componentkey = componentkey;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
