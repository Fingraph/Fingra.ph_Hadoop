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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoCountryAll;

public interface ComponentCountryService {
    
    // ------------------------------------------------------------------------
    //cp_compo_country_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentCountryDay(List<CompoCountryAll> in_volist)
            throws Exception;
    
    public int deleteComponentCountryDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectComponentCountryDayCountByKey(String year, String month,
            String day, String appkey, String componentkey, String country)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_country_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentCountryWeek(List<CompoCountryAll> in_volist)
            throws Exception;
    
    public int deleteComponentCountryWeekByDate(String year, String week)
            throws Exception;
    
    public int selectComponentCountryWeekCountByKey(String year, String week,
            String appkey, String componentkey, String country)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_country_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentCountryMonth(List<CompoCountryAll> in_volist)
            throws Exception;
    
    public int deleteComponentCountryMonthByDate(String year, String month)
            throws Exception;
    
    public int selectComponentCountryMonthCountByKey(String year, String month,
            String appkey, String componentkey, String country)
            throws Exception;
    
}
