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

public class SdkFieldInfo {
    
    // STARTSESS / PAGEVIEW / ENDSESS command log
    public static final int CommonFieldCount = 12;
    public class CommonFieldIndex {
        public static final int CMD = 0;
        public static final int APPKEY = 1;
        public static final int SESSION = 2;
        public static final int UTCTIME = 3;
        public static final int LOCALTIME = 4;
        public static final int TOKEN = 5;
        public static final int COUNTRY = 6;
        public static final int LANGUAGE = 7;
        public static final int DEVICE = 8;
        public static final int OSVERSION = 9;
        public static final int RESOLUTION = 10;
        public static final int APPVERSION = 11;
    }
    
    // COMPONENT command log
    public static final int ComponentFieldCount = 13;
    public class ComponentFieldIndex {
        public static final int CMD = 0;
        public static final int APPKEY = 1;
        public static final int COMPONENTKEY = 2;
        public static final int SESSION = 3;
        public static final int UTCTIME = 4;
        public static final int LOCALTIME = 5;
        public static final int TOKEN = 6;
        public static final int COUNTRY = 7;
        public static final int LANGUAGE = 8;
        public static final int DEVICE = 9;
        public static final int OSVERSION = 10;
        public static final int RESOLUTION = 11;
        public static final int APPVERSION = 12;
    }
    
}
