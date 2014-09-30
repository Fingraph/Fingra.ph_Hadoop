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
import ph.fingra.hadoop.dbms.parts.performance.dao.UserDao;
import ph.fingra.hadoop.dbms.parts.performance.domain.UserAll;

public class UserServiceImpl implements UserService {
    
    private static UserService instance = null;
    
    static {
        instance = new UserServiceImpl();
    }
    
    public static UserService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_user_day
    // ------------------------------------------------------------------------
    
    public int insertBatchUserDay(List<UserAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        UserDao dao = session.getMapper(UserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<UserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    UserAll insert = it.next();
                    dao.insertUserDay(insert);
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
    
    public int deleteUserDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        UserDao dao = session.getMapper(UserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteUserDayByKey(year, month, day, "");
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
    
    public int selectUserDayCountByKey(String year, String month, String day,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        UserDao dao = session.getMapper(UserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectUserDayCountByKey(year, month, day, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_user_week
    // ------------------------------------------------------------------------
    
    public int insertBatchUserWeek(List<UserAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        UserDao dao = session.getMapper(UserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<UserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    UserAll insert = it.next();
                    dao.insertUserWeek(insert);
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
    
    public int deleteUserWeekByDate(String year, String week) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        UserDao dao = session.getMapper(UserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteUserWeekByKey(year, week, "");
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
    
    public int selectUserWeekCountByKey(String year, String week,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        UserDao dao = session.getMapper(UserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectUserWeekCountByKey(year, week, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_user_month
    // ------------------------------------------------------------------------
    
    public int insertBatchUserMonth(List<UserAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        UserDao dao = session.getMapper(UserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<UserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    UserAll insert = it.next();
                    dao.insertUserMonth(insert);
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
    
    public int deleteUserMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        UserDao dao = session.getMapper(UserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteUserMonthByKey(year, month, "");
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
    
    public int selectUserMonthCountByKey(String year, String month,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        UserDao dao = session.getMapper(UserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectUserMonthCountByKey(year, month, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
