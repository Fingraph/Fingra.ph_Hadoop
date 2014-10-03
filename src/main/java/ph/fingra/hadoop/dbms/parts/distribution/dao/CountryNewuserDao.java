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

import ph.fingra.hadoop.dbms.parts.distribution.domain.CountryNewuserAll;

public interface CountryNewuserDao {
    
    // ------------------------------------------------------------------------
    //cd_country_newuser_day
    // ------------------------------------------------------------------------
    
    public int insertCountryNewuserDay(CountryNewuserAll vo) throws Exception;
    
    public int deleteCountryNewuserDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("country") String country)
            throws Exception;
    
    public int selectCountryNewuserDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("country") String country)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_newuser_week
    // ------------------------------------------------------------------------
    
    public int insertCountryNewuserWeek(CountryNewuserAll vo)
            throws Exception;
    
    public int deleteCountryNewuserWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
    public int selectCountryNewuserWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_newuser_month
    // ------------------------------------------------------------------------
    
    public int insertCountryNewuserMonth(CountryNewuserAll vo)
            throws Exception;
    
    public int deleteCountryNewuserMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
    public int selectCountryNewuserMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
}
