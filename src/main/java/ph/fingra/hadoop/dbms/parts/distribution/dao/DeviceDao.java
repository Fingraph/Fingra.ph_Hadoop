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

import ph.fingra.hadoop.dbms.parts.distribution.domain.DeviceAll;

public interface DeviceDao {
    
    // ------------------------------------------------------------------------
    //st_device_day
    // ------------------------------------------------------------------------
    
    public int insertDeviceDay(DeviceAll vo) throws Exception;
    
    public int deleteDeviceDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("device") String device)
            throws Exception;
    
    public int selectDeviceDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey, @Param("device") String device)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //st_device_week
    // ------------------------------------------------------------------------
    
    public int insertDeviceWeek(DeviceAll vo) throws Exception;
    
    public int deleteDeviceWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("device") String device) throws Exception;
    
    public int selectDeviceWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("device") String device) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_device_month
    // ------------------------------------------------------------------------
    
    public int insertDeviceMonth(DeviceAll vo) throws Exception;
    
    public int deleteDeviceMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("device") String device) throws Exception;
    
    public int selectDeviceMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("device") String device) throws Exception;
    
}
