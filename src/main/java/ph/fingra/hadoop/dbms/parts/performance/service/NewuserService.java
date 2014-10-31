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

import ph.fingra.hadoop.dbms.parts.performance.domain.NewuserAll;

public interface NewuserService {
    
    // ------------------------------------------------------------------------
    //st_newuser_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchNewuserHour(List<NewuserAll> in_volist)
            throws Exception;
    
    public int deleteNewuserHourByDate(String year, String month, String day,
            String hour) throws Exception;
    
    public int selectNewuserHourCountByKey(String year, String month,
            String day, String hour, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_newuser_day
    // ------------------------------------------------------------------------
    
    public int insertBatchNewuserDay(List<NewuserAll> in_volist)
            throws Exception;
    
    public int deleteNewuserDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectNewuserDayCountByKey(String year, String month,
            String day, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_newuser_week
    // ------------------------------------------------------------------------
    
    public int insertBatchNewuserWeek(List<NewuserAll> in_volist)
            throws Exception;
    
    public int deleteNewuserWeekByDate(String year, String week)
            throws Exception;
    
    public int selectNewuserWeekCountByKey(String year, String week,
            String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_newuser_month
    // ------------------------------------------------------------------------
    
    public int insertBatchNewuserMonth(List<NewuserAll> in_volist)
            throws Exception;
    
    public int deleteNewuserMonthByDate(String year, String month)
            throws Exception;
    
    public int selectNewuserMonthCountByKey(String year, String month,
            String appkey) throws Exception;
    
}
