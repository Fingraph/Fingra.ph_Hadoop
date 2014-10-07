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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoAppversionAll;

public interface ComponentAppversionService {
    
    // ------------------------------------------------------------------------
    //cp_compo_appversion_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentAppversionDay(
            List<CompoAppversionAll> in_volist) throws Exception;
    
    public int deleteComponentAppversionDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectComponentAppversionDayCountByKey(String year,
            String month, String day, String appkey, String componentkey,
            String appversion) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_appversion_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentAppversionWeek(
            List<CompoAppversionAll> in_volist) throws Exception;
    
    public int deleteComponentAppversionWeekByDate(String year, String week)
            throws Exception;
    
    public int selectComponentAppversionWeekCountByKey(String year,
            String week, String appkey, String componentkey, String appversion)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_appversion_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentAppversionMonth(
            List<CompoAppversionAll> in_volist) throws Exception;
    
    public int deleteComponentAppversionMonthByDate(String year, String month)
            throws Exception;
    
    public int selectComponentAppversionMonthCountByKey(String year,
            String month, String appkey, String componentkey,
            String appversion) throws Exception;
    
}
