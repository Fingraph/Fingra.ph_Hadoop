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

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import ph.fingra.hadoop.dbms.common.ConnectionFactory;
import ph.fingra.hadoop.dbms.parts.performance.dao.SessionDao;
import ph.fingra.hadoop.dbms.parts.performance.domain.SessionAll;

public class SessionServiceImpl implements SessionService {
    
    private static SessionService instance = null;
    
    static {
        instance = new SessionServiceImpl();
    }
    
    public static SessionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_session_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionHour(List<SessionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionDao dao = session.getMapper(SessionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionAll insert = it.next();
                    dao.insertSessionHour(insert);
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
    
    public int deleteSessionHourByDate(String year, String month, String day,
            String hour) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionDao dao = session.getMapper(SessionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionHourByKey(year, month, day, hour, "");
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
    
    public int selectSessionHourCountByKey(String year, String month,
            String day, String hour, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionDao dao = session.getMapper(SessionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionHourCountByKey(year, month, day, hour, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_session_day
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionDay(List<SessionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionDao dao = session.getMapper(SessionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionAll insert = it.next();
                    dao.insertSessionDay(insert);
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
    
    public int deleteSessionDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionDao dao = session.getMapper(SessionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionDayByKey(year, month, day, "");
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
    
    public int selectSessionDayCountByKey(String year, String month,
            String day, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionDao dao = session.getMapper(SessionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionDayCountByKey(year, month, day, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_session_week
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionWeek(List<SessionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionDao dao = session.getMapper(SessionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionAll insert = it.next();
                    dao.insertSessionWeek(insert);
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
    
    public int deleteSessionWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionDao dao = session.getMapper(SessionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionWeekByKey(year, week, "");
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
    
    public int selectSessionWeekCountByKey(String year, String week,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionDao dao = session.getMapper(SessionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionWeekCountByKey(year, week, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_session_month
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionMonth(List<SessionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionDao dao = session.getMapper(SessionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionAll insert = it.next();
                    dao.insertSessionMonth(insert);
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
    
    public int deleteSessionMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionDao dao = session.getMapper(SessionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionMonthByKey(year, month, "");
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
    
    public int selectSessionMonthCountByKey(String year, String month,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionDao dao = session.getMapper(SessionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionMonthCountByKey(year, month, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
