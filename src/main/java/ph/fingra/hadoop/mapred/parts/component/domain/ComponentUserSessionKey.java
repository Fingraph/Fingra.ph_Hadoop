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

package ph.fingra.hadoop.mapred.parts.component.domain;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.commons.lang.builder.HashCodeBuilder;

import ph.fingra.hadoop.mapred.common.BaseWritableComparable;

public class ComponentUserSessionKey
    extends BaseWritableComparable<ComponentUserSessionKey> {
    
    public String appkey = "";
    public String componentkey = "";
    public String token = "";
    public String session = "";
    
    public void set(String appkey, String componentkey, String token,
            String session) {
        
        this.appkey = appkey;
        this.componentkey = componentkey;
        this.token = token;
        this.session = session;
    }
    
    @Override
    public void readFields(DataInput in) throws IOException {
        
        this.appkey = in.readUTF();
        this.componentkey = in.readUTF();
        this.token = in.readUTF();
        this.session = in.readUTF();
    }
    
    @Override
    public void write(DataOutput out) throws IOException {
        
        out.writeUTF(this.appkey);
        out.writeUTF(this.componentkey);
        out.writeUTF(this.token);
        out.writeUTF(this.session);
    }
    
    @Override
    public int compareTo(ComponentUserSessionKey o) {
        
        int ret = 0;
        
        ret = this.appkey.compareTo(o.appkey); if (ret != 0) return ret;
        ret = this.componentkey.compareTo(o.componentkey); if (ret != 0) return ret;
        ret = this.token.compareTo(o.token); if (ret != 0) return ret;
        ret = this.session.compareTo(o.session);
        
        return ret;
    }
    
    @Override
    public int hashCode() {
        
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
