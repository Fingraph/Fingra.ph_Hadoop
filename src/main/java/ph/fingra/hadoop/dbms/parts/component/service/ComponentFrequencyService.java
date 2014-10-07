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

package ph.fingra.hadoop.dbms.parts.component.service;

import java.util.List;

import ph.fingra.hadoop.dbms.parts.component.domain.CompoFrequencyAll;

public interface ComponentFrequencyService {
    
    // ------------------------------------------------------------------------
    //cp_compo_frequency_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentFrequencyDay(
            List<CompoFrequencyAll> in_volist) throws Exception;
    
    public int deleteComponentFrequencyDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectComponentFrequencyDayCountByKey(String year, String month,
            String day, String appkey, String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_frequency_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentFrequencyWeek(
            List<CompoFrequencyAll> in_volist) throws Exception;
    
    public int deleteComponentFrequencyWeekByDate(String year, String week)
            throws Exception;
    
    public int selectComponentFrequencyWeekCountByKey(String year, String week,
            String appkey, String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_frequency_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentFrequencyMonth(
            List<CompoFrequencyAll> in_volist) throws Exception;
    
    public int deleteComponentFrequencyMonthByDate(String year, String month)
            throws Exception;
    
    public int selectComponentFrequencyMonthCountByKey(String year,
            String month, String appkey, String componentkey) throws Exception;
    
}
