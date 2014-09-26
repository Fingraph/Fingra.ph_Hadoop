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

package ph.fingra.hadoop.mapred.parts.distribution.domain;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ph.fingra.hadoop.mapred.common.BaseWritable;

public class ResolutionEntity extends BaseWritable<ResolutionEntity> {
    
    public String token = "";
    public String session = "";
    public String cmd = "";
    
    public void set(String token, String session, String cmd) {
        
        this.token = token;
        this.session = session;
        this.cmd = cmd;
    }
    
    @Override
    public void readFields(DataInput in) throws IOException {
        
        this.token = in.readUTF();
        this.session = in.readUTF();
        this.cmd = in.readUTF();
    }
    
    @Override
    public void write(DataOutput out) throws IOException {
        
        out.writeUTF(this.token);
        out.writeUTF(this.session);
        out.writeUTF(this.cmd);
    }
    
    /**
     * staic method for deserialize
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static ResolutionEntity read(DataInput in) throws IOException {
        
        ResolutionEntity l = new ResolutionEntity();
        l.readFields(in);
        return l;
    }
}
