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

import ph.fingra.hadoop.dbms.parts.distribution.domain.DeviceAll;

public interface DeviceService {
    
    // ------------------------------------------------------------------------
    //st_device_day
    // ------------------------------------------------------------------------
    
    public int insertBatchDeviceDay(List<DeviceAll> in_volist)
            throws Exception;
    
    public int deleteDeviceDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectDeviceDayCountByKey(String year, String month, String day,
            String appkey, String device) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_device_week
    // ------------------------------------------------------------------------
    
    public int insertBatchDeviceWeek(List<DeviceAll> in_volist)
            throws Exception;
    
    public int deleteDeviceWeekByDate(String year, String week)
            throws Exception;
    
    public int selectDeviceWeekCountByKey(String year, String week,
            String appkey, String device) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_device_month
    // ------------------------------------------------------------------------
    
    public int insertBatchDeviceMonth(List<DeviceAll> in_volist)
            throws Exception;
    
    public int deleteDeviceMonthByDate(String year, String month)
            throws Exception;
    
    public int selectDeviceMonthCountByKey(String year, String month,
            String appkey, String device) throws Exception;
    
}
