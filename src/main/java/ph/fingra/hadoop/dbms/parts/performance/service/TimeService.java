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

import ph.fingra.hadoop.dbms.parts.performance.domain.TimeAll;

public interface TimeService {
    
    // ------------------------------------------------------------------------
    //st_time_day
    // ------------------------------------------------------------------------
    
    public int insertBatchTimeDay(List<TimeAll> in_volist) throws Exception;
    
    public int deleteTimeDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectTimeDayCountByKey(String year, String month, String day,
            String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_time_week
    // ------------------------------------------------------------------------
    
    public int insertBatchTimeWeek(List<TimeAll> in_volist) throws Exception;
    
    public int deleteTimeWeekByDate(String year, String week) throws Exception;
    
    public int selectTimeWeekCountByKey(String year, String week,
            String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_time_month
    // ------------------------------------------------------------------------
    
    public int insertBatchTimeMonth(List<TimeAll> in_volist) throws Exception;
    
    public int deleteTimeMonthByDate(String year, String month)
            throws Exception;
    
    public int selectTimeMonthCountByKey(String year, String month,
            String appkey) throws Exception;
    
}
