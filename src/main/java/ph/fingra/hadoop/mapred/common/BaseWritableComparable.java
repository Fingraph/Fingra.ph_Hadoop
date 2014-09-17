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

package ph.fingra.hadoop.mapred.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

import ph.fingra.hadoop.common.BaseObject;

public abstract class BaseWritableComparable<T> extends BaseObject implements WritableComparable<T> {
    
    public abstract void readFields(DataInput in) throws IOException;
    
    public abstract void write(DataOutput out) throws IOException;
    
    public abstract int compareTo(T o);
    
    public abstract int hashCode();
    
}
