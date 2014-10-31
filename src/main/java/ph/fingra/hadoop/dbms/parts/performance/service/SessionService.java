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

import ph.fingra.hadoop.dbms.parts.performance.domain.SessionAll;

public interface SessionService {
    
    // ------------------------------------------------------------------------
    //st_session_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionHour(List<SessionAll> in_volist)
            throws Exception;
    
    public int deleteSessionHourByDate(String year, String month, String day,
            String hour) throws Exception;
    
    public int selectSessionHourCountByKey(String year, String month,
            String day, String hour, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_session_day
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionDay(List<SessionAll> in_volist)
            throws Exception;
    
    public int deleteSessionDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectSessionDayCountByKey(String year, String month,
            String day, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_session_week
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionWeek(List<SessionAll> in_volist)
            throws Exception;
    
    public int deleteSessionWeekByDate(String year, String week)
            throws Exception;
    
    public int selectSessionWeekCountByKey(String year, String week,
            String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_session_month
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionMonth(List<SessionAll> in_volist)
            throws Exception;
    
    public int deleteSessionMonthByDate(String year, String month)
            throws Exception;
    
    public int selectSessionMonthCountByKey(String year, String month,
            String appkey) throws Exception;
    
}
