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

import ph.fingra.hadoop.dbms.parts.performance.domain.SessionlengthAll;

public interface SessionlengthDao {
    
    // ------------------------------------------------------------------------
    //st_sessionlength_hour
    // ------------------------------------------------------------------------
    
    public int insertSessionlengthHour(SessionlengthAll vo) throws Exception;
    
    public int deleteSessionlengthHourByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("hour") String hour, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectSessionlengthHourCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("hour") String hour, @Param("appkey") String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_day
    // ------------------------------------------------------------------------
    
    public int insertSessionlengthDay(SessionlengthAll vo) throws Exception;
    
    public int deleteSessionlengthDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey) throws Exception;
    
    public int selectSessionlengthDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_week
    // ------------------------------------------------------------------------
    
    public int insertSessionlengthWeek(SessionlengthAll vo) throws Exception;
    
    public int deleteSessionlengthWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectSessionlengthWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_month
    // ------------------------------------------------------------------------
    
    public int insertSessionlengthMonth(SessionlengthAll vo) throws Exception;
    
    public int deleteSessionlengthMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectSessionlengthMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey)
            throws Exception;
    
}
