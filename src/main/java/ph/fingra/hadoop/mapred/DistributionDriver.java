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
import ph.fingra.hadoop.mapred.parts.distribution.AppversionStatistic;
import ph.fingra.hadoop.mapred.parts.distribution.CountryHourSessionStatistic;
import ph.fingra.hadoop.mapred.parts.distribution.CountryNewuserStatistic;
import ph.fingra.hadoop.mapred.parts.distribution.CountrySessionLengthStatistic;
import ph.fingra.hadoop.mapred.parts.distribution.CountryStatistic;
import ph.fingra.hadoop.mapred.parts.distribution.DeviceStatistic;
import ph.fingra.hadoop.mapred.parts.distribution.LanguageStatistic;
import ph.fingra.hadoop.mapred.parts.distribution.OsversionStatistic;
import ph.fingra.hadoop.mapred.parts.distribution.ResolutionStatistic;

public class DistributionDriver {

    public static void main(String argv[]) {
        
        int exitcode = -1;
        
        ProgramDriver pgd = new ProgramDriver();
        try {
            
            pgd.addClass("device", DeviceStatistic.class,
                    "Fingraph OSS map/reduce program for distribute/device");
            pgd.addClass("country", CountryStatistic.class,
                    "Fingraph OSS map/reduce program for distribute/country");
            pgd.addClass("language", LanguageStatistic.class,
                    "Fingraph OSS map/reduce program for distribute/language");
            pgd.addClass("appversion", AppversionStatistic.class,
                    "Fingraph OSS map/reduce program for distribute/appversion");
            pgd.addClass("osversion", OsversionStatistic.class,
                    "Fingraph OSS map/reduce program for distribute/osversion");
            pgd.addClass("resolution", ResolutionStatistic.class,
                    "Fingraph OSS map/reduce program for distribute/resolution");
            pgd.addClass("countrynewuser", CountryNewuserStatistic.class,
                    "Fingraph OSS map/reduce program for distribute/countrynewuser");
            pgd.addClass("countryhoursession", CountryHourSessionStatistic.class,
                    "Fingraph OSS map/reduce program for distribute/countryhoursession");
            pgd.addClass("countrysessionlength", CountrySessionLengthStatistic.class,
                    "Fingraph OSS map/reduce program for distribute/countrysessionlength");
            
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
