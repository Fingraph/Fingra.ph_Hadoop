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

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import ph.fingra.hadoop.dbms.common.ConnectionFactory;
import ph.fingra.hadoop.dbms.parts.distribution.dao.DeviceDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.DeviceAll;

public class DeviceServiceImpl implements DeviceService {
    
    private static DeviceService instance = null;
    
    static {
        instance = new DeviceServiceImpl();
    }
    
    public static DeviceService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_device_day
    // ------------------------------------------------------------------------
    
    public int insertBatchDeviceDay(List<DeviceAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        DeviceDao dao = session.getMapper(DeviceDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<DeviceAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    DeviceAll insert = it.next();
                    dao.insertDeviceDay(insert);
                }
            }
            
            List<BatchResult> results = session.flushStatements();
            results.clear();
        }
        catch (Exception e) {
            has_error = true;
            session.rollback();
            session.close();
            throw e;
        }
        finally {
            if (has_error == false)
                session.commit();
            session.close();
        }
        
        return (has_error == false) ? 1 : 0;
    }
    
    public int deleteDeviceDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        DeviceDao dao = session.getMapper(DeviceDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteDeviceDayByKey(year, month, day, "", "");
            List<BatchResult> results = session.flushStatements();
            results.clear();
        }
        catch (Exception e) {
            has_error = true;
            session.rollback();
            session.close();
            throw e;
        }
        finally {
            if (has_error == false)
                session.commit();
            session.close();
        }
        
        return (has_error == false) ? 1 : 0;
    }
    
    public int selectDeviceDayCountByKey(String year, String month, String day,
            String appkey, String device) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        DeviceDao dao = session.getMapper(DeviceDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectDeviceDayCountByKey(year, month, day, appkey, device);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_device_week
    // ------------------------------------------------------------------------
    
    public int insertBatchDeviceWeek(List<DeviceAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        DeviceDao dao = session.getMapper(DeviceDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<DeviceAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    DeviceAll insert = it.next();
                    dao.insertDeviceWeek(insert);
                }
            }
            
            List<BatchResult> results = session.flushStatements();
            results.clear();
        }
        catch (Exception e) {
            has_error = true;
            session.rollback();
            session.close();
            throw e;
        }
        finally {
            if (has_error == false)
                session.commit();
            session.close();
        }
        
        return (has_error == false) ? 1 : 0;
    }
    
    public int deleteDeviceWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        DeviceDao dao = session.getMapper(DeviceDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteDeviceWeekByKey(year, week, "", "");
            List<BatchResult> results = session.flushStatements();
            results.clear();
        }
        catch (Exception e) {
            has_error = true;
            session.rollback();
            session.close();
            throw e;
        }
        finally {
            if (has_error == false)
                session.commit();
            session.close();
        }
        
        return (has_error == false) ? 1 : 0;
    }
    
    public int selectDeviceWeekCountByKey(String year, String week,
            String appkey, String device) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        DeviceDao dao = session.getMapper(DeviceDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectDeviceWeekCountByKey(year, week, appkey, device);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_device_month
    // ------------------------------------------------------------------------
    
    public int insertBatchDeviceMonth(List<DeviceAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        DeviceDao dao = session.getMapper(DeviceDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<DeviceAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    DeviceAll insert = it.next();
                    dao.insertDeviceMonth(insert);
                }
            }
            
            List<BatchResult> results = session.flushStatements();
            results.clear();
        }
        catch (Exception e) {
            has_error = true;
            session.rollback();
            session.close();
            throw e;
        }
        finally {
            if (has_error == false)
                session.commit();
            session.close();
        }
        
        return (has_error == false) ? 1 : 0;
    }
    
    public int deleteDeviceMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        DeviceDao dao = session.getMapper(DeviceDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteDeviceMonthByKey(year, month, "", "");
            List<BatchResult> results = session.flushStatements();
            results.clear();
        }
        catch (Exception e) {
            has_error = true;
            session.rollback();
            session.close();
            throw e;
        }
        finally {
            if (has_error == false)
                session.commit();
            session.close();
        }
        
        return (has_error == false) ? 1 : 0;
    }
    
    public int selectDeviceMonthCountByKey(String year, String month,
            String appkey, String device) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        DeviceDao dao = session.getMapper(DeviceDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectDeviceMonthCountByKey(year, month, appkey, device);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
