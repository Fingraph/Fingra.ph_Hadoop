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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoSessionAll;

public interface ComponentSessionDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_session_day
    // ------------------------------------------------------------------------
    
    public int insertCompoSessionDay(CompoSessionAll vo) throws Exception;
    
    public int deleteCompoSessionDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoSessionDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_session_week
    // ------------------------------------------------------------------------
    
    public int insertCompoSessionWeek(CompoSessionAll vo) throws Exception;
    
    public int deleteCompoSessionWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoSessionWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_session_month
    // ------------------------------------------------------------------------
    
    public int insertCompoSessionMonth(CompoSessionAll vo) throws Exception;
    
    public int deleteCompoSessionMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoSessionMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
}
