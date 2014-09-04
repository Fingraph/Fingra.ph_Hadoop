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

package ph.fingra.hadoop.common.logger;

import org.apache.log4j.Logger;

public class ErrorLogger {
    
    private static ErrorLogger instance = new ErrorLogger();
    
    public static final Logger logger = Logger.getLogger(ErrorLogger.class);
    
    private ErrorLogger(){
        System.out.println("ErrorLogger initial");
    }
    
    public static ErrorLogger getInstance(){
        return instance;
    }
    
    public static void log(String message){
        logger.error(message);
    }
    
}
