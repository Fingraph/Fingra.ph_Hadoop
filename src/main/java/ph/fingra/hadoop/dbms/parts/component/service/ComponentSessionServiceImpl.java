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
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentSessionDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoSessionAll;

public class ComponentSessionServiceImpl implements ComponentSessionService {
    
    private static ComponentSessionService instance = null;
    
    static {
        instance = new ComponentSessionServiceImpl();
    }
    
    public static ComponentSessionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_session_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentSessionDay(List<CompoSessionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentSessionDao dao = session.getMapper(ComponentSessionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoSessionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoSessionAll insert = it.next();
                    dao.insertCompoSessionDay(insert);
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
    
    public int deleteComponentSessionDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentSessionDao dao = session.getMapper(ComponentSessionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoSessionDayByKey(year, month, day, "", "");
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
    
    public int selectComponentSessionDayCountByKey(String year, String month,
            String day, String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentSessionDao dao = session.getMapper(ComponentSessionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoSessionDayCountByKey(year, month, day, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_session_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentSessionWeek(List<CompoSessionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentSessionDao dao = session.getMapper(ComponentSessionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoSessionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoSessionAll insert = it.next();
                    dao.insertCompoSessionWeek(insert);
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
    
    public int deleteComponentSessionWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentSessionDao dao = session.getMapper(ComponentSessionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoSessionWeekByKey(year, week, "", "");
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
    
    public int selectComponentSessionWeekCountByKey(String year, String week,
            String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentSessionDao dao = session.getMapper(ComponentSessionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoSessionWeekCountByKey(year, week, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_session_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentSessionMonth(List<CompoSessionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentSessionDao dao = session.getMapper(ComponentSessionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoSessionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoSessionAll insert = it.next();
                    dao.insertCompoSessionMonth(insert);
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
    
    public int deleteComponentSessionMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentSessionDao dao = session.getMapper(ComponentSessionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoSessionMonthByKey(year, month, "", "");
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
    
    public int selectComponentSessionMonthCountByKey(String year, String month,
            String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentSessionDao dao = session.getMapper(ComponentSessionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoSessionMonthCountByKey(year, month, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
