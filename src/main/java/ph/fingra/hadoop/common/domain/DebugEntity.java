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

package ph.fingra.hadoop.common.domain;

import ph.fingra.hadoop.common.BaseObject;

public class DebugEntity extends BaseObject {
    
    private boolean debug_show_verbose;
    private boolean debug_show_counter;
    private boolean debug_show_spenttime;
    
    public boolean isDebug_show_verbose() {
        return debug_show_verbose;
    }
    public void setDebug_show_verbose(boolean debug_show_verbose) {
        this.debug_show_verbose = debug_show_verbose;
    }
    public boolean isDebug_show_counter() {
        return debug_show_counter;
    }
    public void setDebug_show_counter(boolean debug_show_counter) {
        this.debug_show_counter = debug_show_counter;
    }
    public boolean isDebug_show_spenttime() {
        return debug_show_spenttime;
    }
    public void setDebug_show_spenttime(boolean debug_show_spenttime) {
        this.debug_show_spenttime = debug_show_spenttime;
    }
}
