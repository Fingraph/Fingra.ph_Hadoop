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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoLanguageAll;

public interface ComponentLanguageDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_language_day
    // ------------------------------------------------------------------------
    
    public int insertCompoLanguageDay(CompoLanguageAll vo) throws Exception;
    
    public int deleteCompoLanguageDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("language") String language) throws Exception;
    
    public int selectCompoLanguageDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("language") String language) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_language_week
    // ------------------------------------------------------------------------
    
    public int insertCompoLanguageWeek(CompoLanguageAll vo) throws Exception;
    
    public int deleteCompoLanguageWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("language") String language) throws Exception;
    
    public int selectCompoLanguageWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("language") String language) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_language_month
    // ------------------------------------------------------------------------
    
    public int insertCompoLanguageMonth(CompoLanguageAll vo) throws Exception;
    
    public int deleteCompoLanguageMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("language") String language) throws Exception;
    
    public int selectCompoLanguageMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("language") String language) throws Exception;
    
}
