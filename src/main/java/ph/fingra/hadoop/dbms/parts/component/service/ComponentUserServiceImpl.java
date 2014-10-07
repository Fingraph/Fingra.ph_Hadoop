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
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentUserDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoUserAll;

public class ComponentUserServiceImpl implements ComponentUserService {
    
    private static ComponentUserService instance = null;
    
    static {
        instance = new ComponentUserServiceImpl();
    }
    
    public static ComponentUserService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_user_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentUserDay(List<CompoUserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentUserDao dao = session.getMapper(ComponentUserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoUserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoUserAll insert = it.next();
                    dao.insertCompoUserDay(insert);
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
    
    public int deleteComponentUserDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentUserDao dao = session.getMapper(ComponentUserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoUserDayByKey(year, month, day, "", "");
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
    
    public int selectComponentUserDayCountByKey(String year, String month,
            String day, String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentUserDao dao = session.getMapper(ComponentUserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoUserDayCountByKey(year, month, day, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_user_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentUserWeek(List<CompoUserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentUserDao dao = session.getMapper(ComponentUserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoUserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoUserAll insert = it.next();
                    dao.insertCompoUserWeek(insert);
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
    
    public int deleteComponentUserWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentUserDao dao = session.getMapper(ComponentUserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoUserWeekByKey(year, week, "", "");
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
    
    public int selectComponentUserWeekCountByKey(String year, String week,
            String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentUserDao dao = session.getMapper(ComponentUserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoUserWeekCountByKey(year, week, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_user_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentUserMonth(List<CompoUserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentUserDao dao = session.getMapper(ComponentUserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoUserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoUserAll insert = it.next();
                    dao.insertCompoUserMonth(insert);
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
    
    public int deleteComponentUserMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentUserDao dao = session.getMapper(ComponentUserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoUserMonthByKey(year, month, "", "");
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
    
    public int selectComponentUserMonthCountByKey(String year, String month,
            String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentUserDao dao = session.getMapper(ComponentUserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoUserMonthCountByKey(year, month, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
