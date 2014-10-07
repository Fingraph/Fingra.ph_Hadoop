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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoLanguageAll;

public interface ComponentLanguageService {
    
    // ------------------------------------------------------------------------
    //cp_compo_language_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentLanguageDay(List<CompoLanguageAll> in_volist)
            throws Exception;
    
    public int deleteComponentLanguageDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectComponentLanguageDayCountByKey(String year, String month,
            String day, String appkey, String componentkey, String language)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_language_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentLanguageWeek(
            List<CompoLanguageAll> in_volist) throws Exception;
    
    public int deleteComponentLanguageWeekByDate(String year, String week)
            throws Exception;
    
    public int selectComponentLanguageWeekCountByKey(String year, String week,
            String appkey, String componentkey, String language)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_language_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentLanguageMonth(
            List<CompoLanguageAll> in_volist) throws Exception;
    
    public int deleteComponentLanguageMonthByDate(String year, String month)
            throws Exception;
    
    public int selectComponentLanguageMonthCountByKey(String year,
            String month, String appkey, String componentkey, String language)
            throws Exception;
    
}
