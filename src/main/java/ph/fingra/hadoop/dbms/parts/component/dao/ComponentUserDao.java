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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoUserAll;

public interface ComponentUserDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_user_day
    // ------------------------------------------------------------------------
    
    public int insertCompoUserDay(CompoUserAll vo) throws Exception;
    
    public int deleteCompoUserDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoUserDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_user_week
    // ------------------------------------------------------------------------
    
    public int insertCompoUserWeek(CompoUserAll vo) throws Exception;
    
    public int deleteCompoUserWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoUserWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_user_month
    // ------------------------------------------------------------------------
    
    public int insertCompoUserMonth(CompoUserAll vo) throws Exception;
    
    public int deleteCompoUserMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoUserMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
}
