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

import ph.fingra.hadoop.dbms.parts.performance.domain.FrequencyAll;

public interface FrequencyService {
    
    // ------------------------------------------------------------------------
    //st_frequency_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchFrequencyHour(List<FrequencyAll> in_volist)
            throws Exception;
    
    public int deleteFrequencyHourByDate(String year, String month, String day,
            String hour) throws Exception;
    
    public int selectFrequencyHourCountByKey(String year, String month,
            String day, String hour, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_frequency_day
    // ------------------------------------------------------------------------
    
    public int insertBatchFrequencyDay(List<FrequencyAll> in_volist)
            throws Exception;
    
    public int deleteFrequencyDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectFrequencyDayCountByKey(String year, String month,
            String day, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_frequency_week
    // ------------------------------------------------------------------------
    
    public int insertBatchFrequencyWeek(List<FrequencyAll> in_volist)
            throws Exception;
    
    public int deleteFrequencyWeekByDate(String year, String week)
            throws Exception;
    
    public int selectFrequencyWeekCountByKey(String year, String week,
            String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_frequency_month
    // ------------------------------------------------------------------------
    
    public int insertBatchFrequencyMonth(List<FrequencyAll> in_volist)
            throws Exception;
    
    public int deleteFrequencyMonthByDate(String year, String month)
            throws Exception;
    
    public int selectFrequencyMonthCountByKey(String year, String month,
            String appkey) throws Exception;
    
}
