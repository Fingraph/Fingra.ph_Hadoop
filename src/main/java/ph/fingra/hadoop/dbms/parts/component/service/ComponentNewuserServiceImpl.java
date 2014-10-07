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
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentNewuserDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoNewuserAll;

public class ComponentNewuserServiceImpl implements ComponentNewuserService {
    
    private static ComponentNewuserService instance = null;
    
    static {
        instance = new ComponentNewuserServiceImpl();
    }
    
    public static ComponentNewuserService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_newuser_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentNewuserDay(List<CompoNewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentNewuserDao dao = session.getMapper(ComponentNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoNewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoNewuserAll insert = it.next();
                    dao.insertCompoNewuserDay(insert);
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
    
    public int deleteComponentNewuserDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentNewuserDao dao = session.getMapper(ComponentNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoNewuserDayByKey(year, month, day, "", "");
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
    
    public int selectComponentNewuserDayCountByKey(String year, String month,
            String day, String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentNewuserDao dao = session.getMapper(ComponentNewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoNewuserDayCountByKey(year, month, day, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_newuser_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentNewuserWeek(List<CompoNewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentNewuserDao dao = session.getMapper(ComponentNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoNewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoNewuserAll insert = it.next();
                    dao.insertCompoNewuserWeek(insert);
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
    
    public int deleteComponentNewuserWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentNewuserDao dao = session.getMapper(ComponentNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoNewuserWeekByKey(year, week, "", "");
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
    
    public int selectComponentNewuserWeekCountByKey(String year, String week,
            String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentNewuserDao dao = session.getMapper(ComponentNewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoNewuserWeekCountByKey(year, week, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_newuser_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentNewuserMonth(List<CompoNewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentNewuserDao dao = session.getMapper(ComponentNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoNewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoNewuserAll insert = it.next();
                    dao.insertCompoNewuserMonth(insert);
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
    
    public int deleteComponentNewuserMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentNewuserDao dao = session.getMapper(ComponentNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoNewuserMonthByKey(year, month, "", "");
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
    
    public int selectComponentNewuserMonthCountByKey(String year, String month,
            String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentNewuserDao dao = session.getMapper(ComponentNewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoNewuserMonthCountByKey(year, month, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
