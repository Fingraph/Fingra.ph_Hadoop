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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoSessionAll;

public interface ComponentSessionService {
    
    // ------------------------------------------------------------------------
    //cp_compo_session_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentSessionDay(List<CompoSessionAll> in_volist)
            throws Exception;
    
    public int deleteComponentSessionDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectComponentSessionDayCountByKey(String year, String month,
            String day, String appkey, String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_session_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentSessionWeek(List<CompoSessionAll> in_volist)
            throws Exception;
    
    public int deleteComponentSessionWeekByDate(String year, String week)
            throws Exception;
    
    public int selectComponentSessionWeekCountByKey(String year, String week,
            String appkey, String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_session_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentSessionMonth(List<CompoSessionAll> in_volist)
            throws Exception;
    
    public int deleteComponentSessionMonthByDate(String year, String month)
            throws Exception;
    
    public int selectComponentSessionMonthCountByKey(String year, String month,
            String appkey, String componentkey) throws Exception;
    
}
