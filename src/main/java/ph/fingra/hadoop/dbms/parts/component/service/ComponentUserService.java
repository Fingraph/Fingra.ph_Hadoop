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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoUserAll;

public interface ComponentUserService {
    
    // ------------------------------------------------------------------------
    //cp_compo_user_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentUserDay(List<CompoUserAll> in_volist)
            throws Exception;
    
    public int deleteComponentUserDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectComponentUserDayCountByKey(String year, String month,
            String day, String appkey, String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_user_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentUserWeek(List<CompoUserAll> in_volist)
            throws Exception;
    
    public int deleteComponentUserWeekByDate(String year, String week)
            throws Exception;
    
    public int selectComponentUserWeekCountByKey(String year, String week,
            String appkey, String componentkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_user_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentUserMonth(List<CompoUserAll> in_volist)
            throws Exception;
    
    public int deleteComponentUserMonthByDate(String year, String month)
            throws Exception;
    
    public int selectComponentUserMonthCountByKey(String year, String month,
            String appkey, String componentkey) throws Exception;
    
}
