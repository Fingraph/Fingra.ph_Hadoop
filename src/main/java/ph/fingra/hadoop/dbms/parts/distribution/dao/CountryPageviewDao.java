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

package ph.fingra.hadoop.dbms.parts.distribution.dao;

import org.apache.ibatis.annotations.Param;

import ph.fingra.hadoop.dbms.parts.distribution.domain.CountryPageviewAll;

public interface CountryPageviewDao {
    
    // ------------------------------------------------------------------------
    //cd_country_pageview_day
    // ------------------------------------------------------------------------
    
    public int insertCountryPageviewDay(CountryPageviewAll vo)
            throws Exception;
    
    public int deleteCountryPageviewDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("country") String country)
            throws Exception;
    
    public int selectCountryPageviewDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("country") String country)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_pageview_week
    // ------------------------------------------------------------------------
    
    public int insertCountryPageviewWeek(CountryPageviewAll vo)
            throws Exception;
    
    public int deleteCountryPageviewWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
    public int selectCountryPageviewWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_pageview_month
    // ------------------------------------------------------------------------
    
    public int insertCountryPageviewMonth(CountryPageviewAll vo)
            throws Exception;
    
    public int deleteCountryPageviewMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
    public int selectCountryPageviewMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
}
