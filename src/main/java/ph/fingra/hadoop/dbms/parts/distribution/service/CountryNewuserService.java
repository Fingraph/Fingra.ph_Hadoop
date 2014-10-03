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

import ph.fingra.hadoop.dbms.parts.distribution.domain.CountryNewuserAll;

public interface CountryNewuserService {
    
    // ------------------------------------------------------------------------
    //cd_country_newuser_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryNewuserDay(List<CountryNewuserAll> in_volist)
            throws Exception;
    
    public int deleteCountryNewuserDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectCountryNewuserDayCountByKey(String year, String month,
            String day, String appkey, String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_newuser_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryNewuserWeek(List<CountryNewuserAll> in_volist)
            throws Exception;
    
    public int deleteCountryNewuserWeekByDate(String year, String week)
            throws Exception;
    
    public int selectCountryNewuserWeekCountByKey(String year, String week,
            String appkey, String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_newuser_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryNewuserMonth(List<CountryNewuserAll> in_volist)
            throws Exception;
    
    public int deleteCountryNewuserMonthByDate(String year, String month)
            throws Exception;
    
    public int selectCountryNewuserMonthCountByKey(String year, String month,
            String appkey, String country) throws Exception;
    
}
