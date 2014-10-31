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

import ph.fingra.hadoop.dbms.parts.performance.domain.FrequencyAll;

public interface FrequencyDao {
    
    // ------------------------------------------------------------------------
    //st_frequency_hour
    // ------------------------------------------------------------------------
    
    public int insertFrequencyHour(FrequencyAll vo) throws Exception;
    
    public int deleteFrequencyHourByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("hour") String hour, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectFrequencyHourCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("hour") String hour, @Param("appkey") String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_frequency_day
    // ------------------------------------------------------------------------
    
    public int insertFrequencyDay(FrequencyAll vo) throws Exception;
    
    public int deleteFrequencyDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey) throws Exception;
    
    public int selectFrequencyDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_frequency_week
    // ------------------------------------------------------------------------
    
    public int insertFrequencyWeek(FrequencyAll vo) throws Exception;
    
    public int deleteFrequencyWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectFrequencyWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_frequency_month
    // ------------------------------------------------------------------------
    
    public int insertFrequencyMonth(FrequencyAll vo) throws Exception;
    
    public int deleteFrequencyMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectFrequencyMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey)
            throws Exception;
    
}
