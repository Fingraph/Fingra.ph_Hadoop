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

import ph.fingra.hadoop.dbms.parts.component.domain.CompoDeviceAll;

public interface ComponentDeviceService {
    
    // ------------------------------------------------------------------------
    //cp_compo_device_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentDeviceDay(List<CompoDeviceAll> in_volist)
            throws Exception;
    
    public int deleteComponentDeviceDayByDate(String year, String month,
            String day) throws Exception;
    
    public int selectComponentDeviceDayCountByKey(String year, String month,
            String day, String appkey, String componentkey, String device)
            throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_device_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentDeviceWeek(List<CompoDeviceAll> in_volist)
            throws Exception;
    
    public int deleteComponentDeviceWeekByDate(String year, String week)
            throws Exception;
    
    public int selectComponentDeviceWeekCountByKey(String year, String week,
            String appkey, String componentkey, String device) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_device_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentDeviceMonth(List<CompoDeviceAll> in_volist)
            throws Exception;
    
    public int deleteComponentDeviceMonthByDate(String year, String month)
            throws Exception;
    
    public int selectComponentDeviceMonthCountByKey(String year, String month,
            String appkey, String componentkey, String device) throws Exception;
    
}
