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

import ph.fingra.hadoop.dbms.parts.distribution.domain.LanguageAll;

public interface LanguageService {
    
    // ------------------------------------------------------------------------
    //st_language_day
    // ------------------------------------------------------------------------
    
    public int insertBatchLanguageDay(List<LanguageAll> in_volist)
            throws Exception;
    
    public int deleteLanguageDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectLanguageDayCountByKey(String year, String month,
            String day, String appkey, String language) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_language_week
    // ------------------------------------------------------------------------
    
    public int insertBatchLanguageWeek(List<LanguageAll> in_volist)
            throws Exception;
    
    public int deleteLanguageWeekByDate(String year, String week)
            throws Exception;
    
    public int selectLanguageWeekCountByKey(String year, String week,
            String appkey, String language) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_language_month
    // ------------------------------------------------------------------------
    
    public int insertBatchLanguageMonth(List<LanguageAll> in_volist)
            throws Exception;
    
    public int deleteLanguageMonthByDate(String year, String month)
            throws Exception;
    
    public int selectLanguageMonthCountByKey(String year, String month,
            String appkey, String language) throws Exception;
    
}
