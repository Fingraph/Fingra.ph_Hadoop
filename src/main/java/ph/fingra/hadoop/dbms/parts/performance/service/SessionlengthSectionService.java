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

package ph.fingra.hadoop.dbms.parts.performance.service;

import java.util.List;

import ph.fingra.hadoop.dbms.parts.performance.domain.SessionlengthSectionAll;

public interface SessionlengthSectionService {
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthSectionHour(
            List<SessionlengthSectionAll> in_volist) throws Exception;
    
    public int deleteSessionlengthSectionHourByDate(String year, String month,
            String day, String hour) throws Exception;
    
    public int selectSessionlengthSectionHourCountByKey(String year,
            String month, String day, String hour, String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_day
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthSectionDay(
            List<SessionlengthSectionAll> in_volist) throws Exception;
    
    public int deleteSessionlengthSectionDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectSessionlengthSectionDayCountByKey(String year,
            String month, String day, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_week
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthSectionWeek(
            List<SessionlengthSectionAll> in_volist) throws Exception;
    
    public int deleteSessionlengthSectionWeekByDate(String year, String week)
            throws Exception;
    
    public int selectSessionlengthSectionWeekCountByKey(String year,
            String week, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_month
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthSectionMonth(
            List<SessionlengthSectionAll> in_volist) throws Exception;
    
    public int deleteSessionlengthSectionMonthByDate(String year, String month)
            throws Exception;
    
    public int selectSessionlengthSectionMonthCountByKey(String year,
            String month, String appkey) throws Exception;
    
}
