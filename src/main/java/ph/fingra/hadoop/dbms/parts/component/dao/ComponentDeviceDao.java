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

package ph.fingra.hadoop.dbms.parts.component.dao;

import org.apache.ibatis.annotations.Param;

import ph.fingra.hadoop.dbms.parts.component.domain.CompoDeviceAll;

public interface ComponentDeviceDao {
    
    // ------------------------------------------------------------------------
    //cp_compo_device_day
    // ------------------------------------------------------------------------
    
    public int insertCompoDeviceDay(CompoDeviceAll vo) throws Exception;
    
    public int deleteCompoDeviceDayByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("device") String device) throws Exception;
    
    public int selectCompoDeviceDayCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("day") String day,
            @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("device") String device) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_device_week
    // ------------------------------------------------------------------------
    
    public int insertCompoDeviceWeek(CompoDeviceAll vo) throws Exception;
    
    public int deleteCompoDeviceWeekByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("device") String device) throws Exception;
    
    public int selectCompoDeviceWeekCountByKey(@Param("year") String year,
            @Param("week") String week, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("device") String device) throws Exception;
    
    // ------------------------------------------------------------------------
    //cp_compo_device_month
    // ------------------------------------------------------------------------
    
    public int insertCompoDeviceMonth(CompoDeviceAll vo) throws Exception;
    
    public int deleteCompoDeviceMonthByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("device") String device) throws Exception;
    
    public int selectCompoDeviceMonthCountByKey(@Param("year") String year,
            @Param("month") String month, @Param("appkey") String appkey,
            @Param("componentkey") String componentkey,
            @Param("device") String device) throws Exception;
    
}
