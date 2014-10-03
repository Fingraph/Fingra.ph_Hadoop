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

package ph.fingra.hadoop.dbms.parts.distribution.service;

import java.util.List;

import ph.fingra.hadoop.dbms.parts.distribution.domain.CountrySessionlengthAll;

public interface CountrySessionlengthService {
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthDay(
            List<CountrySessionlengthAll> in_volist) throws Exception;
    
    public int deleteCountrySessionlengthDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectCountrySessionlengthDayCountByKey(String year,
            String month, String day, String appkey, String country)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthWeek(
            List<CountrySessionlengthAll> in_volist) throws Exception;
    
    public int deleteCountrySessionlengthWeekByDate(String year, String week)
            throws Exception;
    
    public int selectCountrySessionlengthWeekCountByKey(String year,
            String week, String appkey, String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthMonth(
            List<CountrySessionlengthAll> in_volist) throws Exception;
    
    public int deleteCountrySessionlengthMonthByDate(String year, String month)
            throws Exception;
    
    public int selectCountrySessionlengthMonthCountByKey(String year,
            String month, String appkey, String country) throws Exception;
    
}
