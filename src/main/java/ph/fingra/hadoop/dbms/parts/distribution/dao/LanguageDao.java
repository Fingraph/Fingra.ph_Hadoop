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

package ph.fingra.hadoop.dbms.parts.distribution.dao;

import org.apache.ibatis.annotations.Param;

import ph.fingra.hadoop.dbms.parts.distribution.domain.LanguageAll;

public interface LanguageDao {
    
    // ------------------------------------------------------------------------
    //st_language_day
    // ------------------------------------------------------------------------
    
    public int insertLanguageDay(LanguageAll vo) throws Exception;
    
    public int deleteLanguageDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("language") String language)
            throws Exception;
    
    public int selectLanguageDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("language") String language)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_language_week
    // ------------------------------------------------------------------------
    
    public int insertLanguageWeek(LanguageAll vo) throws Exception;
    
    public int deleteLanguageWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("language") String language) throws Exception;
    
    public int selectLanguageWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("language") String language) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_language_month
    // ------------------------------------------------------------------------
    
    public int insertLanguageMonth(LanguageAll vo) throws Exception;
    
    public int deleteLanguageMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("language") String language) throws Exception;
    
    public int selectLanguageMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("language") String language) throws Exception;
    
}
