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

import ph.fingra.hadoop.dbms.parts.performance.domain.SessionAll;

public interface SessionDao {
    
    // ------------------------------------------------------------------------
    //st_session_hour
    // ------------------------------------------------------------------------
    
    public int insertSessionHour(SessionAll vo) throws Exception;
    
    public int deleteSessionHourByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("hour") String hour, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectSessionHourCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("hour") String hour, @Param("appkey") String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_session_day
    // ------------------------------------------------------------------------
    
    public int insertSessionDay(SessionAll vo) throws Exception;
    
    public int deleteSessionDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey) throws Exception;
    
    public int selectSessionDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_session_week
    // ------------------------------------------------------------------------
    
    public int insertSessionWeek(SessionAll vo) throws Exception;
    
    public int deleteSessionWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectSessionWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_session_month
    // ------------------------------------------------------------------------
    
    public int insertSessionMonth(SessionAll vo) throws Exception;
    
    public int deleteSessionMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectSessionMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey)
            throws Exception;
    
}
