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

package ph.fingra.hadoop.dbms.parts.performance.service;

import java.util.List;

import ph.fingra.hadoop.dbms.parts.performance.domain.UserAll;

public interface UserService {
    
    // ------------------------------------------------------------------------
    //st_user_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchUserHour(List<UserAll> in_volist) throws Exception;
    
    public int deleteUserHourByDate(String year, String month, String day,
            String hour) throws Exception;
    
    public int selectUserHourCountByKey(String year, String month, String day,
            String hour, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_user_day
    // ------------------------------------------------------------------------
    
    public int insertBatchUserDay(List<UserAll> in_volist) throws Exception;
    
    public int deleteUserDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectUserDayCountByKey(String year, String month, String day,
            String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_user_week
    // ------------------------------------------------------------------------
    
    public int insertBatchUserWeek(List<UserAll> in_volist) throws Exception;
    
    public int deleteUserWeekByDate(String year, String week) throws Exception;
    
    public int selectUserWeekCountByKey(String year, String week,
            String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_user_month
    // ------------------------------------------------------------------------
    
    public int insertBatchUserMonth(List<UserAll> in_volist) throws Exception;
    
    public int deleteUserMonthByDate(String year, String month)
            throws Exception;
    
    public int selectUserMonthCountByKey(String year, String month,
            String appkey) throws Exception;
    
}
