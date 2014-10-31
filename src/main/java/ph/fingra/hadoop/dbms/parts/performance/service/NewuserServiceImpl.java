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
import ph.fingra.hadoop.dbms.parts.performance.dao.NewuserDao;
import ph.fingra.hadoop.dbms.parts.performance.domain.NewuserAll;

public class NewuserServiceImpl implements NewuserService {
    
    private static NewuserService instance = null;
    
    static {
        instance = new NewuserServiceImpl();
    }
    
    public static NewuserService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_newuser_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchNewuserHour(List<NewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<NewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    NewuserAll insert = it.next();
                    dao.insertNewuserHour(insert);
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
    
    public int deleteNewuserHourByDate(String year, String month, String day,
            String hour) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteNewuserHourByKey(year, month, day, hour, "");
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
    
    public int selectNewuserHourCountByKey(String year, String month,
            String day, String hour, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectNewuserHourCountByKey(year, month, day, hour, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_newuser_day
    // ------------------------------------------------------------------------
    
    public int insertBatchNewuserDay(List<NewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<NewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    NewuserAll insert = it.next();
                    dao.insertNewuserDay(insert);
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
    
    public int deleteNewuserDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteNewuserDayByKey(year, month, day, "");
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
    
    public int selectNewuserDayCountByKey(String year, String month,
            String day, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectNewuserDayCountByKey(year, month, day, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_newuser_week
    // ------------------------------------------------------------------------
    
    public int insertBatchNewuserWeek(List<NewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<NewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    NewuserAll insert = it.next();
                    dao.insertNewuserWeek(insert);
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
    
    public int deleteNewuserWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteNewuserWeekByKey(year, week, "");
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
    
    public int selectNewuserWeekCountByKey(String year, String week,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectNewuserWeekCountByKey(year, week, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_newuser_month
    // ------------------------------------------------------------------------
    
    public int insertBatchNewuserMonth(List<NewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<NewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    NewuserAll insert = it.next();
                    dao.insertNewuserMonth(insert);
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
    
    public int deleteNewuserMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteNewuserMonthByKey(year, month, "");
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
    
    public int selectNewuserMonthCountByKey(String year, String month,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        NewuserDao dao = session.getMapper(NewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectNewuserMonthCountByKey(year, month, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
