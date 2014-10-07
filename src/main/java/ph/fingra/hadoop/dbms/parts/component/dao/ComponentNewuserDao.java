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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoNewuserAll;

public interface ComponentNewuserDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_newuser_day
    // ------------------------------------------------------------------------
    
    public int insertCompoNewuserDay(CompoNewuserAll vo) throws Exception;
    
    public int deleteCompoNewuserDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoNewuserDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_newuser_week
    // ------------------------------------------------------------------------
    
    public int insertCompoNewuserWeek(CompoNewuserAll vo) throws Exception;
    
    public int deleteCompoNewuserWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoNewuserWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_newuser_month
    // ------------------------------------------------------------------------
    
    public int insertCompoNewuserMonth(CompoNewuserAll vo) throws Exception;
    
    public int deleteCompoNewuserMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoNewuserMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
}
