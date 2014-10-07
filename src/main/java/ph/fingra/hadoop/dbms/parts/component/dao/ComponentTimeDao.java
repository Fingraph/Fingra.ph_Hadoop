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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoTimeAll;

public interface ComponentTimeDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_time_day
    // ------------------------------------------------------------------------
    
    public int insertCompoTimeDay(CompoTimeAll vo) throws Exception;
    
    public int deleteCompoTimeDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoTimeDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_time_week
    // ------------------------------------------------------------------------
    
    public int insertCompoTimeWeek(CompoTimeAll vo) throws Exception;
    
    public int deleteCompoTimeWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoTimeWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_time_month
    // ------------------------------------------------------------------------
    
    public int insertCompoTimeMonth(CompoTimeAll vo) throws Exception;
    
    public int deleteCompoTimeMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoTimeMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
}
