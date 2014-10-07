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
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentTimeDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoTimeAll;

public class ComponentTimeServiceImpl implements ComponentTimeService {
    
    private static ComponentTimeService instance = null;
    
    static {
        instance = new ComponentTimeServiceImpl();
    }
    
    public static ComponentTimeService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_time_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentTimeDay(List<CompoTimeAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentTimeDao dao = session.getMapper(ComponentTimeDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoTimeAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoTimeAll insert = it.next();
                    dao.insertCompoTimeDay(insert);
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
    
    public int deleteComponentTimeDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentTimeDao dao = session.getMapper(ComponentTimeDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoTimeDayByKey(year, month, day, "", "");
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
    
    public int selectComponentTimeDayCountByKey(String year, String month,
            String day, String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentTimeDao dao = session.getMapper(ComponentTimeDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoTimeDayCountByKey(year, month, day, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_time_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentTimeWeek(List<CompoTimeAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentTimeDao dao = session.getMapper(ComponentTimeDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoTimeAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoTimeAll insert = it.next();
                    dao.insertCompoTimeWeek(insert);
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
    
    public int deleteComponentTimeWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentTimeDao dao = session.getMapper(ComponentTimeDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoTimeWeekByKey(year, week, "", "");
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
    
    public int selectComponentTimeWeekCountByKey(String year, String week,
            String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentTimeDao dao = session.getMapper(ComponentTimeDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoTimeWeekCountByKey(year, week, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_time_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentTimeMonth(List<CompoTimeAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentTimeDao dao = session.getMapper(ComponentTimeDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoTimeAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoTimeAll insert = it.next();
                    dao.insertCompoTimeMonth(insert);
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
    
    public int deleteComponentTimeMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentTimeDao dao = session.getMapper(ComponentTimeDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoTimeMonthByKey(year, month, "", "");
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
    
    public int selectComponentTimeMonthCountByKey(String year, String month,
            String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentTimeDao dao = session.getMapper(ComponentTimeDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoTimeMonthCountByKey(year, month, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
