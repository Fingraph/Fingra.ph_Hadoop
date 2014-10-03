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

import ph.fingra.hadoop.dbms.parts.distribution.domain.CountrySessionlengthSectionAll;

public interface CountrySessionlengthSectionDao {
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_section_day
    // ------------------------------------------------------------------------
    
    public int insertCountrySessionlengthSectionDay(
            CountrySessionlengthSectionAll vo) throws Exception;
    
    public int deleteCountrySessionlengthSectionDayByKey(
            @Param("year") String year, @Param("month") String month,
            @Param("day") String day, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
    public int selectCountrySessionlengthSectionDayCountByKey(
            @Param("year") String year, @Param("month") String month,
            @Param("day") String day, @Param("appkey") String appkey,
            @Param("country") String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_section_week
    // ------------------------------------------------------------------------
    
    public int insertCountrySessionlengthSectionWeek(
            CountrySessionlengthSectionAll vo) throws Exception;
    
    public int deleteCountrySessionlengthSectionWeekByKey(
            @Param("year") String year, @Param("week") String week,
            @Param("appkey") String appkey, @Param("country") String country)
            throws Exception;
    
    public int selectCountrySessionlengthSectionWeekCountByKey(
            @Param("year") String year, @Param("week") String week,
            @Param("appkey") String appkey, @Param("country") String country)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_section_month
    // ------------------------------------------------------------------------
    
    public int insertCountrySessionlengthSectionMonth(
            CountrySessionlengthSectionAll vo) throws Exception;
    
    public int deleteCountrySessionlengthSectionMonthByKey(
            @Param("year") String year, @Param("month") String month,
            @Param("appkey") String appkey, @Param("country") String country)
            throws Exception;
    
    public int selectCountrySessionlengthSectionMonthCountByKey(
            @Param("year") String year, @Param("month") String month,
            @Param("appkey") String appkey, @Param("country") String country)
            throws Exception;
    
}
