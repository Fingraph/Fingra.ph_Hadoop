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

package ph.fingra.hadoop.dbms.parts.distribution.service;

import java.util.List;

import ph.fingra.hadoop.dbms.parts.distribution.domain.AppversionAll;

public interface AppversionService {
    
    // ------------------------------------------------------------------------
    //st_appversion_day
    // ------------------------------------------------------------------------
    
    public int insertBatchAppversionDay(List<AppversionAll> in_volist)
            throws Exception;
    
    public int deleteAppversionDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectAppversionDayCountByKey(String year, String month,
            String day, String appkey, String appversion) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_appversion_week
    // ------------------------------------------------------------------------
    
    public int insertBatchAppversionWeek(List<AppversionAll> in_volist)
            throws Exception;
    
    public int deleteAppversionWeekByDate(String year, String week)
            throws Exception;
    
    public int selectAppversionWeekCountByKey(String year, String week,
            String appkey, String appversion) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_appversion_month
    // ------------------------------------------------------------------------
    
    public int insertBatchAppversionMonth(List<AppversionAll> in_volist)
            throws Exception;
    
    public int deleteAppversionMonthByDate(String year, String month)
            throws Exception;
    
    public int selectAppversionMonthCountByKey(String year, String month,
            String appkey, String appversion) throws Exception;
    
}
