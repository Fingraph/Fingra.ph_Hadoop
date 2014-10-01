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

import ph.fingra.hadoop.dbms.parts.distribution.domain.AppversionAll;

public interface AppversionDao {
    
    // ------------------------------------------------------------------------
    //st_appversion_day
    // ------------------------------------------------------------------------
    
    public int insertAppversionDay(AppversionAll vo) throws Exception;
    
    public int deleteAppversionDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("appversion") String appversion)
            throws Exception;
    
    public int selectAppversionDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("appversion") String appversion)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_appversion_week
    // ------------------------------------------------------------------------
    
    public int insertAppversionWeek(AppversionAll vo) throws Exception;
    
    public int deleteAppversionWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("appversion") String appversion) throws Exception;
    
    public int selectAppversionWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("appversion") String appversion) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_appversion_month
    // ------------------------------------------------------------------------
    
    public int insertAppversionMonth(AppversionAll vo) throws Exception;
    
    public int deleteAppversionMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("appversion") String appversion) throws Exception;
    
    public int selectAppversionMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("appversion") String appversion) throws Exception;
    
}
