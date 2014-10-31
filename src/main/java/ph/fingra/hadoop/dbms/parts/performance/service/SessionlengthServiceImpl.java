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
import ph.fingra.hadoop.dbms.parts.performance.dao.SessionlengthDao;
import ph.fingra.hadoop.dbms.parts.performance.domain.SessionlengthAll;

public class SessionlengthServiceImpl implements SessionlengthService {
    
    private static SessionlengthService instance = null;
    
    static {
        instance = new SessionlengthServiceImpl();
    }
    
    public static SessionlengthService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_sessionlength_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthHour(List<SessionlengthAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionlengthAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionlengthAll insert = it.next();
                    dao.insertSessionlengthHour(insert);
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
    
    public int deleteSessionlengthHourByDate(String year, String month,
            String day, String hour) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionlengthHourByKey(year, month, day, hour, "");
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
    
    public int selectSessionlengthHourCountByKey(String year, String month,
            String day, String hour, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionlengthHourCountByKey(year, month, day, hour, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_sessionlength_day
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthDay(List<SessionlengthAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionlengthAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionlengthAll insert = it.next();
                    dao.insertSessionlengthDay(insert);
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
    
    public int deleteSessionlengthDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionlengthDayByKey(year, month, day, "");
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
    
    public int selectSessionlengthDayCountByKey(String year, String month,
            String day, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionlengthDayCountByKey(year, month, day, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_sessionlength_week
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthWeek(List<SessionlengthAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionlengthAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionlengthAll insert = it.next();
                    dao.insertSessionlengthWeek(insert);
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
    
    public int deleteSessionlengthWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionlengthWeekByKey(year, week, "");
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
    
    public int selectSessionlengthWeekCountByKey(String year, String week,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionlengthWeekCountByKey(year, week, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_sessionlength_month
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthMonth(List<SessionlengthAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionlengthAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionlengthAll insert = it.next();
                    dao.insertSessionlengthMonth(insert);
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
    
    public int deleteSessionlengthMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionlengthMonthByKey(year, month, "");
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
    
    public int selectSessionlengthMonthCountByKey(String year, String month,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionlengthDao dao = session.getMapper(SessionlengthDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionlengthMonthCountByKey(year, month, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
