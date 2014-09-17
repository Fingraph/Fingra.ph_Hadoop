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

package ph.fingra.hadoop.mapred.parse.domain;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ph.fingra.hadoop.mapred.common.BaseWritable;

public class AppNewuserDb extends BaseWritable<AppNewuserDb> {
    
    public String appkey = "";
    public String token = "";
    public String year = "";
    public String month = "";
    public String day = "";
    public String week = "";
    public String utctime = "";
    public String localtime = "";
    public String country = "";
    public String language = "";
    public String device = "";
    public String osversion = "";
    public String resolution = "";
    public String appversion = "";
    
    public void set(String appkey, String token,
            String year, String month, String day, String week,
            String utctime, String localtime,
            String country, String language, String device, String osversion,
            String resolution, String appversion) {
        
        this.appkey = appkey;
        this.token = token;
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
        this.utctime = utctime;
        this.localtime = localtime;
        this.country = country;
        this.language = language;
        this.device = device;
        this.osversion = osversion;
        this.resolution = resolution;
        this.appversion = appversion;
    }
    
    @Override
    public void readFields(DataInput in) throws IOException {
        
        this.appkey = in.readUTF();
        this.token = in.readUTF();
        this.year = in.readUTF();
        this.month = in.readUTF();
        this.day = in.readUTF();
        this.week = in.readUTF();
        this.utctime = in.readUTF();
        this.localtime = in.readUTF();
        this.country = in.readUTF();
        this.language = in.readUTF();
        this.device = in.readUTF();
        this.osversion = in.readUTF();
        this.resolution = in.readUTF();
        this.appversion = in.readUTF();
    }
    
    @Override
    public void write(DataOutput out) throws IOException {
        
        out.writeUTF(this.appkey);
        out.writeUTF(this.token);
        out.writeUTF(this.year);
        out.writeUTF(this.month);
        out.writeUTF(this.day);
        out.writeUTF(this.week);
        out.writeUTF(this.utctime);
        out.writeUTF(this.localtime);
        out.writeUTF(this.country);
        out.writeUTF(this.language);
        out.writeUTF(this.device);
        out.writeUTF(this.osversion);
        out.writeUTF(this.resolution);
        out.writeUTF(this.appversion);
    }
    
    /**
     * staic method for deserialize
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static AppNewuserDb read(DataInput in) throws IOException {
        
        AppNewuserDb l = new AppNewuserDb();
        l.readFields(in);
        return l;
    }
    
    public void copy(AppNewuserDb source) {
        
        this.appkey = source.appkey;
        this.token = source.token;
        this.year = source.year;
        this.month = source.month;
        this.day = source.day;
        this.week = source.week;
        this.utctime = source.utctime;
        this.localtime = source.localtime;
        this.country = source.country;
        this.language = source.language;
        this.device = source.device;
        this.osversion = source.osversion;
        this.resolution = source.resolution;
        this.appversion = source.appversion;
    }
    
    public void clear() {
        
        this.appkey = "";
        this.token = "";
        this.year = "";
        this.month = "";
        this.day = "";
        this.week = "";
        this.utctime = "";
        this.localtime = "";
        this.country = "";
        this.language = "";
        this.device = "";
        this.osversion = "";
        this.resolution = "";
        this.appversion = "";
    }
}
