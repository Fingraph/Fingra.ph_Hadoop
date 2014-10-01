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

import ph.fingra.hadoop.dbms.parts.distribution.domain.OsversionAll;

public interface OsversionDao {
    
    // ------------------------------------------------------------------------
    //st_osversion_day
    // ------------------------------------------------------------------------
    
    public int insertOsversionDay(OsversionAll vo) throws Exception;
    
    public int deleteOsversionDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("osversion") String osversion)
            throws Exception;
    
    public int selectOsversionDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("osversion") String osversion)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_osversion_week
    // ------------------------------------------------------------------------
    
    public int insertOsversionWeek(OsversionAll vo) throws Exception;
    
    public int deleteOsversionWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("osversion") String osversion) throws Exception;
    
    public int selectOsversionWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("osversion") String osversion) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_osversion_month
    // ------------------------------------------------------------------------
    
    public int insertOsversionMonth(OsversionAll vo) throws Exception;
    
    public int deleteOsversionMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("osversion") String osversion) throws Exception;
    
    public int selectOsversionMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("osversion") String osversion) throws Exception;
    
}
