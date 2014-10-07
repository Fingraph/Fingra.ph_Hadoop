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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoAppversionAll;

public interface ComponentAppversionDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_appversion_day
    // ------------------------------------------------------------------------
    
    public int insertCompoAppversionDay(CompoAppversionAll vo) throws Exception;
    
    public int deleteCompoAppversionDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("appversion") String appversion) throws Exception;
    
    public int selectCompoAppversionDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("appversion") String appversion) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_appversion_week
    // ------------------------------------------------------------------------
    
    public int insertCompoAppversionWeek(CompoAppversionAll vo)
            throws Exception;
    
    public int deleteCompoAppversionWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("appversion") String appversion) throws Exception;
    
    public int selectCompoAppversionWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("appversion") String appversion) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_appversion_month
    // ------------------------------------------------------------------------
    
    public int insertCompoAppversionMonth(CompoAppversionAll vo)
            throws Exception;
    
    public int deleteCompoAppversionMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("appversion") String appversion) throws Exception;
    
    public int selectCompoAppversionMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("appversion") String appversion) throws Exception;
    
}
