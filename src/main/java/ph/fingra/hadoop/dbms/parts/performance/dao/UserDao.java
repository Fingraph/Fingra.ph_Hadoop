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

package ph.fingra.hadoop.dbms.parts.performance.dao;

import org.apache.ibatis.annotations.Param;

import ph.fingra.hadoop.dbms.parts.performance.domain.UserAll;

public interface UserDao {
    
    // ------------------------------------------------------------------------
    //st_user_hour
    // ------------------------------------------------------------------------
    
    public int insertUserHour(UserAll vo) throws Exception;
    
    public int deleteUserHourByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("hour") String hour, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectUserHourCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("hour") String hour, @Param("appkey") String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_user_day
    // ------------------------------------------------------------------------
    
    public int insertUserDay(UserAll vo) throws Exception;
    
    public int deleteUserDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey) throws Exception;
    
    public int selectUserDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_user_week
    // ------------------------------------------------------------------------
    
    public int insertUserWeek(UserAll vo) throws Exception;
    
    public int deleteUserWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectUserWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_user_month
    // ------------------------------------------------------------------------
    
    public int insertUserMonth(UserAll vo) throws Exception;
    
    public int deleteUserMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey)
            throws Exception;
    
    public int selectUserMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey)
            throws Exception;
    
}
