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

public class AppNewuserHourDb extends BaseWritable<AppNewuserHourDb> {
    
    public String appkey = "";
    public String token = "";
    public String year = "";
    public String month = "";
    public String day = "";
    public String hour = "";
    
    public void set(String appkey, String token,
            String year, String month, String day, String hour) {
        
        this.appkey = appkey;
        this.token = token;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }
    
    @Override
    public void readFields(DataInput in) throws IOException {
        
        this.appkey = in.readUTF();
        this.token = in.readUTF();
        this.year = in.readUTF();
        this.month = in.readUTF();
        this.day = in.readUTF();
        this.hour = in.readUTF();
    }
    
    @Override
    public void write(DataOutput out) throws IOException {
        
        out.writeUTF(this.appkey);
        out.writeUTF(this.token);
        out.writeUTF(this.year);
        out.writeUTF(this.month);
        out.writeUTF(this.day);
        out.writeUTF(this.hour);
    }
    
    /**
     * staic method for deserialize
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static AppNewuserHourDb read(DataInput in) throws IOException {
        
        AppNewuserHourDb l = new AppNewuserHourDb();
        l.readFields(in);
        return l;
    }
    
    public void copy(AppNewuserHourDb source) {
        
        this.appkey = source.appkey;
        this.token = source.token;
        this.year = source.year;
        this.month = source.month;
        this.day = source.day;
        this.hour = source.hour;
    }
    
    public void clear() {
        
        this.appkey = "";
        this.token = "";
        this.year = "";
        this.month = "";
        this.day = "";
        this.hour = "";
    }
}
