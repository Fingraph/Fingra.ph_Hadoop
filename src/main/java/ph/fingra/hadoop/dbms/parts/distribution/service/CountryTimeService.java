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

import ph.fingra.hadoop.dbms.parts.distribution.domain.CountryTimeAll;

public interface CountryTimeService {
    
    // ------------------------------------------------------------------------
    //cd_country_time_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryTimeDay(List<CountryTimeAll> in_volist)
            throws Exception;
    
    public int deleteCountryTimeDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectCountryTimeDayCountByKey(String year, String month,
            String day, String appkey, String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_time_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryTimeWeek(List<CountryTimeAll> in_volist)
            throws Exception;
    
    public int deleteCountryTimeWeekByDate(String year, String week)
            throws Exception;
    
    public int selectCountryTimeWeekCountByKey(String year, String week,
            String appkey, String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_time_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryTimeMonth(List<CountryTimeAll> in_volist)
            throws Exception;
    
    public int deleteCountryTimeMonthByDate(String year, String month)
            throws Exception;
    
    public int selectCountryTimeMonthCountByKey(String year, String month,
            String appkey, String country) throws Exception;
    
}
