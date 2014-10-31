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
import ph.fingra.hadoop.dbms.parts.performance.dao.SessionlengthSectionDao;
import ph.fingra.hadoop.dbms.parts.performance.domain.SessionlengthSectionAll;

public class SessionlengthSectionServiceImpl implements
        SessionlengthSectionService {
    
    private static SessionlengthSectionService instance = null;
    
    static {
        instance = new SessionlengthSectionServiceImpl();
    }
    
    public static SessionlengthSectionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthSectionHour(
            List<SessionlengthSectionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionlengthSectionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionlengthSectionAll insert = it.next();
                    dao.insertSessionlengthSectionHour(insert);
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
    
    public int deleteSessionlengthSectionHourByDate(String year, String month,
            String day, String hour) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionlengthSectionHourByKey(year, month, day, hour, "");
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
    
    public int selectSessionlengthSectionHourCountByKey(String year,
            String month, String day, String hour, String appkey)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionlengthSectionHourCountByKey(year, month, day, hour, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_day
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthSectionDay(
            List<SessionlengthSectionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionlengthSectionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionlengthSectionAll insert = it.next();
                    dao.insertSessionlengthSectionDay(insert);
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
    
    public int deleteSessionlengthSectionDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionlengthSectionDayByKey(year, month, day, "");
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
    
    public int selectSessionlengthSectionDayCountByKey(String year,
            String month, String day, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionlengthSectionDayCountByKey(year, month, day, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_week
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthSectionWeek(
            List<SessionlengthSectionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionlengthSectionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionlengthSectionAll insert = it.next();
                    dao.insertSessionlengthSectionWeek(insert);
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
    
    public int deleteSessionlengthSectionWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionlengthSectionWeekByKey(year, week, "");
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
    
    public int selectSessionlengthSectionWeekCountByKey(String year,
            String week, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionlengthSectionWeekCountByKey(year, week, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_sessionlength_section_month
    // ------------------------------------------------------------------------
    
    public int insertBatchSessionlengthSectionMonth(
            List<SessionlengthSectionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<SessionlengthSectionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    SessionlengthSectionAll insert = it.next();
                    dao.insertSessionlengthSectionMonth(insert);
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
    
    public int deleteSessionlengthSectionMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteSessionlengthSectionMonthByKey(year, month, "");
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
    
    public int selectSessionlengthSectionMonthCountByKey(String year,
            String month, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        SessionlengthSectionDao dao = session.getMapper(SessionlengthSectionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectSessionlengthSectionMonthCountByKey(year, month, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
