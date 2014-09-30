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

package ph.fingra.hadoop.dbms.parts.performance.dao;

import org.apache.ibatis.annotations.Param;

import ph.fingra.hadoop.dbms.parts.performance.domain.SessionlengthSectionAll;

public interface SessionlengthSectionDao {
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_day
    // ------------------------------------------------------------------------
    
    public int insertSessionlengthSectionDay(SessionlengthSectionAll vo)
            throws Exception;
    
    public int deleteSessionlengthSectionDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey) throws Exception;
    
    public int selectSessionlengthSectionDayCountByKey(
            @Param("year") String year, @Param("month") String month,
            @Param("day") String day, @Param("appkey") String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_week
    // ------------------------------------------------------------------------
    
    public int insertSessionlengthSectionWeek(SessionlengthSectionAll vo)
            throws Exception;
    
    public int deleteSessionlengthSectionWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectSessionlengthSectionWeekCountByKey(
            @Param("year") String year, @Param("week") String week,
            @Param("appkey") String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_month
    // ------------------------------------------------------------------------
    
    public int insertSessionlengthSectionMonth(SessionlengthSectionAll vo)
            throws Exception;
    
    public int deleteSessionlengthSectionMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectSessionlengthSectionMonthCountByKey(
            @Param("year") String year, @Param("month") String month,
            @Param("appkey") String appkey) throws Exception;
    
}
