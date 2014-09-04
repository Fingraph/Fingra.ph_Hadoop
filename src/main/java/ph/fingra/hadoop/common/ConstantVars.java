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

public class ConstantVars {
    
    public static final int MAX_LENGTH_COUNTRY          = 10;
    public static final int MAX_LENGTH_LANGUAGE         = 10;
    public static final int MAX_LENGTH_DEVICE           = 50;
    public static final int MAX_LENGTH_OSVERSION        = 50;
    public static final int MAX_LENGTH_RESOLUTION       = 20;
    public static final int MAX_LENGTH_APPVERSION       = 50;
    
    public static final String COUNTER_NAME             = "FingraphJobCounter";
    
    // clean > wellformed > malformed > waste
    public enum LogValidation {
        CLEAN,
        WELLFORMED,
        MALFORMED,
        WASTE
    }
    
    public class ParseError {
        public static final int NONE = 0;               //no error
        public static final int MISSING = 1;            //minor problem
        public static final int EMPTYLINE = 2;
        public static final int ERRORFIELDCOUNT = 3;
        public static final int ERRORCMD = 4;
        public static final int ERRORAPPKEY = 5;
        public static final int ERRORCOMPONENTKEY = 6;
        public static final int ERRORSESSION = 7;
        public static final int ERRORTIME = 8;
        public static final int ERRORTOKEN = 9;
        public static final int MISSMATCH = 10;
    }
    
    public enum CommandType {
        STARTSESS,      // ordinal value: 0
        PAGEVIEW,       // ordinal value: 1
        COMPONENT,      // ordinal value: 2
        ENDSESS;        // ordinal value: 3
        public static int getOrdinalByCmd(String cmd) {
            if (cmd.equals(CMD_STARTSESS) || cmd.equals(CMD_PAGEVIEW)
                    || cmd.equals(CMD_ENDSESS)) {
                for (CommandType val : CommandType.values()) {
                    if (val.name().equals(cmd)) {
                        return val.ordinal();
                    }
                }
                throw new IllegalArgumentException("Illegal name: " + cmd);
            }
            else if (cmd.equals(CMD_COMPONENT)) {
                return CommandType.COMPONENT.ordinal();
            }
            else {
                throw new IllegalArgumentException("Illegal name: " + cmd);
            }
        }
    }
    
    public enum DataUsable {
        USE, USELESS, EXPIRED
    }
    
    public enum LogParserType {
        CommonLog,      // STARTSESS, PAGEVIEW, ENDSESS
        ComponentLog,   // COMPONENT
        InvalidLog      // Invalid Parser Type
    }
    
    public static final String DOPTION_RUNMODE          = "runmode";
    public static final String DOPTION_TARGETDATE       = "targetdate";
    public static final String DOPTION_NUMREDUCE        = "numreduce";
    
    public static final String RUNMODE_HOUR             = "hour";
    public static final String RUNMODE_DAY              = "day";
    public static final String RUNMODE_WEEK             = "week";
    public static final String RUNMODE_MONTH            = "month";
    
    public static final String OPTIONDATE_FORMAT_HOUR   = "yyyy-MM-dd-HH";
    public static final String OPTIONDATE_FORMAT_DAY    = "yyyy-MM-dd";
    public static final String OPTIONDATE_FORMAT_WEEK   = "yyyy-ww";
    public static final String OPTIONDATE_FORMAT_MONTH  = "yyyy-MM";
    
    public static final String CMD_STARTSESS            = "STARTSESS";
    public static final String CMD_PAGEVIEW             = "PAGEVIEW";
    public static final String CMD_COMPONENT            = "EVENT";
    public static final String CMD_ENDSESS              = "ENDSESS";
    
    public static final String RESULT_FILE_PREFIX       = "part-";
    
    public static final String LOG_FIELD_SEPERATER_REGX = "\\|\\|";
    public static final String LOG_FIELD_SEPERATER_STR  = "||";
    public static final String DB_FIELD_SEPERATER_REGX  = "\\t";
    
    public static final String RESULT_FIELD_SEPERATER   = "\t";
    
    // database
    public static final String APP_NEWUSER_DB_FNAME     = "app_newuser_db";
    public static final String COMPONENT_NEWUSER_DB_FNAME   = "component_newuser_db";
    
    public static final String TARGETDATE_PATTERN       = "([0-9]{4})\\-";
    public static final String NUMOFREDUCER_PREFIX      = "-reducecount";
    public static final String NUMOFREDUCER_PATTERN     = "^-reducecount\\d*$";
    
    public static final int RUNTIME_1_MIN = 1*60;
    public static final int RUNTIME_3_MIN = 3*60;
    public static final int RUNTIME_10_MIN = 10*60;
    public static final int RUNTIME_30_MIN = 30*60;
    
    public static final String LOG_NULL                 = "NULL";
    
    public static final String LOG_DATE_FORMAT          = "yyyyMMddHHmmss";
    public static final String APPKEY_PATTERN_REGEX     = "^[a-zA-Z0-9]*$";
    public static final String COMPONENTKEY_PATTERN_REGEX   = "^[a-zA-Z0-9]*$";
    
}
