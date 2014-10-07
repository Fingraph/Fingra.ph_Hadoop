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

import ph.fingra.hadoop.dbms.parts.distribution.domain.CountryPageviewAll;

public interface CountryPageviewService {
    
    // ------------------------------------------------------------------------
    //cd_country_pageview_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryPageviewDay(
            List<CountryPageviewAll> in_volist) throws Exception;
    
    public int deleteCountryPageviewDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectCountryPageviewDayCountByKey(String year, String month,
            String day, String appkey, String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_pageview_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryPageviewWeek(
            List<CountryPageviewAll> in_volist) throws Exception;
    
    public int deleteCountryPageviewWeekByDate(String year, String week)
            throws Exception;
    
    public int selectCountryPageviewWeekCountByKey(String year, String week,
            String appkey, String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_pageview_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryPageviewMonth(
            List<CountryPageviewAll> in_volist) throws Exception;
    
    public int deleteCountryPageviewMonthByDate(String year, String month)
            throws Exception;
    
    public int selectCountryPageviewMonthCountByKey(String year, String month,
            String appkey, String country) throws Exception;
    
}
