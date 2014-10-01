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

import ph.fingra.hadoop.dbms.parts.distribution.domain.ResolutionAll;

public interface ResolutionDao {
    
    // ------------------------------------------------------------------------
    //st_resolution_day
    // ------------------------------------------------------------------------
    
    public int insertResolutionDay(ResolutionAll vo) throws Exception;
    
    public int deleteResolutionDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("resolution") String resolution)
            throws Exception;
    
    public int selectResolutionDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("resolution") String resolution)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_resolution_week
    // ------------------------------------------------------------------------
    
    public int insertResolutionWeek(ResolutionAll vo) throws Exception;
    
    public int deleteResolutionWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("resolution") String resolution) throws Exception;
    
    public int selectResolutionWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("resolution") String resolution) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_resolution_month
    // ------------------------------------------------------------------------
    
    public int insertResolutionMonth(ResolutionAll vo) throws Exception;
    
    public int deleteResolutionMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("resolution") String resolution) throws Exception;
    
    public int selectResolutionMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("resolution") String resolution) throws Exception;
    
}
