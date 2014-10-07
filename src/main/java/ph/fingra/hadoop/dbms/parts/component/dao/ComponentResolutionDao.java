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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoResolutionAll;

public interface ComponentResolutionDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_resolution_day
    // ------------------------------------------------------------------------
    
    public int insertCompoResolutionDay(CompoResolutionAll vo) throws Exception;
    
    public int deleteCompoResolutionDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("resolution") String resolution) throws Exception;
    
    public int selectCompoResolutionDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("resolution") String resolution) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_resolution_week
    // ------------------------------------------------------------------------
    
    public int insertCompoResolutionWeek(CompoResolutionAll vo)
            throws Exception;
    
    public int deleteCompoResolutionWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("resolution") String resolution) throws Exception;
    
    public int selectCompoResolutionWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("resolution") String resolution) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_resolution_month
    // ------------------------------------------------------------------------
    
    public int insertCompoResolutionMonth(CompoResolutionAll vo)
            throws Exception;
    
    public int deleteCompoResolutionMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("resolution") String resolution) throws Exception;
    
    public int selectCompoResolutionMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("resolution") String resolution) throws Exception;
    
}
