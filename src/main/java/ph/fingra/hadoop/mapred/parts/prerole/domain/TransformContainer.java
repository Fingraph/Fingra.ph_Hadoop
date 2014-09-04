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

package ph.fingra.hadoop.mapred.parts.prerole.domain;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import ph.fingra.hadoop.mapred.common.BaseWritable;

public class TransformContainer extends BaseWritable<TransformContainer> {
    
    public String cmd = "";
    public String logline = "";
    
    public void set(String cmd, String log) {
        
        this.cmd = cmd;
        this.logline = log;
    }
    
    @Override
    public void readFields(DataInput in) throws IOException {
        
        this.cmd = in.readUTF();
        this.logline = in.readUTF();
    }
    
    @Override
    public void write(DataOutput out) throws IOException {
        
        out.writeUTF(this.cmd);
        out.writeUTF(this.logline);
    }
    
    /**
     * staic method for deserialize
     * 
     * @param in
     * @return
     * @throws IOException
     */
    public static TransformContainer read(DataInput in) throws IOException {
        
        TransformContainer l = new TransformContainer();
        l.readFields(in);
        return l;
    }
}
