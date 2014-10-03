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

import ph.fingra.hadoop.dbms.parts.distribution.domain.CountrySessionlengthSectionAll;

public interface CountrySessionlengthSectionService {
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_section_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthSectionDay(
            List<CountrySessionlengthSectionAll> in_volist) throws Exception;
    
    public int deleteCountrySessionlengthSectionDayByDate(String year,
            String month, String day) throws Exception;
    
    public int selectCountrySessionlengthSectionDayCountByKey(String year,
            String month, String day, String appkey, String country)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_section_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthSectionWeek(
            List<CountrySessionlengthSectionAll> in_volist) throws Exception;
    
    public int deleteCountrySessionlengthSectionWeekByDate(String year,
            String week) throws Exception;
    
    public int selectCountrySessionlengthSectionWeekCountByKey(String year,
            String week, String appkey, String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_section_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthSectionMonth(
            List<CountrySessionlengthSectionAll> in_volist) throws Exception;
    
    public int deleteCountrySessionlengthSectionMonthByDate(String year,
            String month) throws Exception;
    
    public int selectCountrySessionlengthSectionMonthCountByKey(String year,
            String month, String appkey, String country) throws Exception;
    
}
