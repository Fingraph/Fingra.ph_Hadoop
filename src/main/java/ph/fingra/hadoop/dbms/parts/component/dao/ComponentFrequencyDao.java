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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoFrequencyAll;

public interface ComponentFrequencyDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_frequency_day
    // ------------------------------------------------------------------------
    
    public int insertCompoFrequencyDay(CompoFrequencyAll vo) throws Exception;
    
    public int deleteCompoFrequencyDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoFrequencyDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_frequency_week
    // ------------------------------------------------------------------------
    
    public int insertCompoFrequencyWeek(CompoFrequencyAll vo) throws Exception;
    
    public int deleteCompoFrequencyWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoFrequencyWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_frequency_month
    // ------------------------------------------------------------------------
    
    public int insertCompoFrequencyMonth(CompoFrequencyAll vo) throws Exception;
    
    public int deleteCompoFrequencyMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int selectCompoFrequencyMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
}
