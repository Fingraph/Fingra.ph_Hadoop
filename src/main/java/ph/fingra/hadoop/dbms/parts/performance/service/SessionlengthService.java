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

import ph.fingra.hadoop.dbms.parts.performance.domain.SessionlengthAll;

public interface SessionlengthService {
    
    // ------------------------------------------------------------------------
    //st_sessionlength_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthHour(List<SessionlengthAll> in_volist)
            throws Exception;
    
    public int deleteSessionlengthHourByDate(String year, String month,
            String day, String hour) throws Exception;
    
    public int selectSessionlengthHourCountByKey(String year, String month,
            String day, String hour, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_day
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthDay(List<SessionlengthAll> in_volist)
            throws Exception;
    
    public int deleteSessionlengthDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectSessionlengthDayCountByKey(String year, String month,
            String day, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_week
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthWeek(List<SessionlengthAll> in_volist)
            throws Exception;
    
    public int deleteSessionlengthWeekByDate(String year, String week)
            throws Exception;
    
    public int selectSessionlengthWeekCountByKey(String year, String week,
            String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_sessionlength_month
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthMonth(List<SessionlengthAll> in_volist)
            throws Exception;
    
    public int deleteSessionlengthMonthByDate(String year, String month)
            throws Exception;
    
    public int selectSessionlengthMonthCountByKey(String year, String month,
            String appkey) throws Exception;
    
}
