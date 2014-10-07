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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoOsversionAll;

public interface ComponentOsversionDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_osversion_day
    // ------------------------------------------------------------------------
    
    public int insertCompoOsversionDay(CompoOsversionAll vo) throws Exception;
    
    public int deleteCompoOsversionDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("osversion") String osversion) throws Exception;
    
    public int selectCompoOsversionDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("osversion") String osversion) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_osversion_week
    // ------------------------------------------------------------------------
    
    public int insertCompoOsversionWeek(CompoOsversionAll vo) throws Exception;
    
    public int deleteCompoOsversionWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("osversion") String osversion) throws Exception;
    
    public int selectCompoOsversionWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("osversion") String osversion) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_osversion_month
    // ------------------------------------------------------------------------
    
    public int insertCompoOsversionMonth(CompoOsversionAll vo) throws Exception;
    
    public int deleteCompoOsversionMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("osversion") String osversion) throws Exception;
    
    public int selectCompoOsversionMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("osversion") String osversion) throws Exception;
    
}
