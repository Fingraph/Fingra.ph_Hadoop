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

package ph.fingra.hadoop.dbms.common;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class MapperUtils {
    
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj instanceof List) return obj == null || ((List)obj).isEmpty();
        else if (obj instanceof Map) return obj == null || ((Map)obj).isEmpty();
        else if (obj instanceof Object[]) return obj==null || Array.getLength(obj) == 0;
        else return obj == null;
    }
    
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
    
    public static boolean isEmptyString(String s) {
        return s == null || "".equals(s.trim());
    }
    
    public static boolean isNotEmptyString(String s) {
        return !isEmptyString(s);
    }
    
    public static boolean isDoubleNaN(Double d) {
        return Double.isNaN(d);
    }
    
    public static boolean isDoubleInfinite(Double d) {
        return Double.isInfinite(d);
    }
    
    public static boolean isValidDouble(Double d) {
        boolean valid = false;
        valid = isDoubleNaN(d);
        if (valid == true) return !valid;
        valid = isDoubleInfinite(d);
        return !valid;
    }
    
    public static boolean isNotNegative(Integer d) {
        boolean valid = false;
        if (d >= 0) valid = true;
        return valid;
    }
}
