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

package ph.fingra.hadoop.mapred;

import org.apache.hadoop.util.ProgramDriver;

import ph.fingra.hadoop.common.logger.ErrorLogger;
import ph.fingra.hadoop.mapred.parts.prerole.AppNewuserMerge;
import ph.fingra.hadoop.mapred.parts.prerole.BasekeysMaker;
import ph.fingra.hadoop.mapred.parts.prerole.ComponentNewuserMerge;
import ph.fingra.hadoop.mapred.parts.prerole.LogCountStatistic;
import ph.fingra.hadoop.mapred.parts.prerole.PreTransform;

public class PreroleDriver {
    
    public static void main(String argv[]) {
        
        int exitcode = -1;
        
        ProgramDriver pgd = new ProgramDriver();
        try {
            
            pgd.addClass("logcount", LogCountStatistic.class,
                    "Fingraph OSS map/reduce program for prerole/logcount");
            pgd.addClass("pretransform", PreTransform.class,
                    "Fingraph OSS map/reduce program for prerole/pretransform");
            pgd.addClass("appnewusermerge", AppNewuserMerge.class,
                    "Fingraph OSS map/reduce program for merge/appnewusermerge");
            pgd.addClass("componentnewusermerge", ComponentNewuserMerge.class,
                    "Fingraph OSS map/reduce program for merge/componentnewusermerge");
            pgd.addClass("basekeys", BasekeysMaker.class,
                    "Fingraph OSS map/reduce program for prerole/basekeys");
            
            pgd.driver(argv);
            
            // seccess
            exitcode = 0;
        }
        catch(Throwable e) {
            ErrorLogger.log(e.toString());
        }
        
        System.exit(exitcode);
    }
}
