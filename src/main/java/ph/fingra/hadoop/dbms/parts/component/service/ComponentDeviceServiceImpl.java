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

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import ph.fingra.hadoop.dbms.common.ConnectionFactory;
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentDeviceDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoDeviceAll;

public class ComponentDeviceServiceImpl implements ComponentDeviceService {
    
    private static ComponentDeviceService instance = null;
    
    static {
        instance = new ComponentDeviceServiceImpl();
    }
    
    public static ComponentDeviceService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_device_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentDeviceDay(List<CompoDeviceAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentDeviceDao dao = session.getMapper(ComponentDeviceDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoDeviceAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoDeviceAll insert = it.next();
                    dao.insertCompoDeviceDay(insert);
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
    
    public int deleteComponentDeviceDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentDeviceDao dao = session.getMapper(ComponentDeviceDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoDeviceDayByKey(year, month, day, "", "", "");
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
    
    public int selectComponentDeviceDayCountByKey(String year, String month,
            String day, String appkey, String componentkey, String device)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentDeviceDao dao = session.getMapper(ComponentDeviceDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoDeviceDayCountByKey(year, month, day, appkey, componentkey, device);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_device_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentDeviceWeek(List<CompoDeviceAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentDeviceDao dao = session.getMapper(ComponentDeviceDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoDeviceAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoDeviceAll insert = it.next();
                    dao.insertCompoDeviceWeek(insert);
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
    
    public int deleteComponentDeviceWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentDeviceDao dao = session.getMapper(ComponentDeviceDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoDeviceWeekByKey(year, week, "", "", "");
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
    
    public int selectComponentDeviceWeekCountByKey(String year, String week,
            String appkey, String componentkey, String device) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentDeviceDao dao = session.getMapper(ComponentDeviceDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoDeviceWeekCountByKey(year, week, appkey, componentkey, device);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_device_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentDeviceMonth(List<CompoDeviceAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentDeviceDao dao = session.getMapper(ComponentDeviceDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoDeviceAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoDeviceAll insert = it.next();
                    dao.insertCompoDeviceMonth(insert);
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
    
    public int deleteComponentDeviceMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentDeviceDao dao = session.getMapper(ComponentDeviceDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoDeviceMonthByKey(year, month, "", "", "");
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
    
    public int selectComponentDeviceMonthCountByKey(String year, String month,
            String appkey, String componentkey, String device) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentDeviceDao dao = session.getMapper(ComponentDeviceDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoDeviceMonthCountByKey(year, month, appkey, componentkey, device);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
