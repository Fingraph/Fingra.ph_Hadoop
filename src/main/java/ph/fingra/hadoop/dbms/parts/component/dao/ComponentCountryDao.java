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

package ph.fingra.hadoop.dbms.parts.component.dao;

import org.apache.ibatis.annotations.Param;

import ph.fingra.hadoop.dbms.parts.component.domain.CompoCountryAll;

public interface ComponentCountryDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_country_day
    // ------------------------------------------------------------------------
    
    public int insertCompoCountryDay(CompoCountryAll vo) throws Exception;
    
    public int deleteCompoCountryDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("country") String country) throws Exception;
    
    public int selectCompoCountryDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("country") String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_country_week
    // ------------------------------------------------------------------------
    
    public int insertCompoCountryWeek(CompoCountryAll vo) throws Exception;
    
    public int deleteCompoCountryWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("country") String country) throws Exception;
    
    public int selectCompoCountryWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("country") String country) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_country_month
    // ------------------------------------------------------------------------
    
    public int insertCompoCountryMonth(CompoCountryAll vo) throws Exception;
    
    public int deleteCompoCountryMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("country") String country) throws Exception;
    
    public int selectCompoCountryMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("country") String country) throws Exception;
    
}
