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
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentOsversionDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoOsversionAll;

public class ComponentOsversionServiceImpl implements ComponentOsversionService {
    
    private static ComponentOsversionService instance = null;
    
    static {
        instance = new ComponentOsversionServiceImpl();
    }
    
    public static ComponentOsversionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_osversion_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentOsversionDay(
            List<CompoOsversionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentOsversionDao dao = session.getMapper(ComponentOsversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoOsversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoOsversionAll insert = it.next();
                    dao.insertCompoOsversionDay(insert);
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
    
    public int deleteComponentOsversionDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentOsversionDao dao = session.getMapper(ComponentOsversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoOsversionDayByKey(year, month, day, "", "", "");
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
    
    public int selectComponentOsversionDayCountByKey(String year, String month,
            String day, String appkey, String componentkey, String osversion)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentOsversionDao dao = session.getMapper(ComponentOsversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoOsversionDayCountByKey(year, month, day, appkey, componentkey, osversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_osversion_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentOsversionWeek(
            List<CompoOsversionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentOsversionDao dao = session.getMapper(ComponentOsversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoOsversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoOsversionAll insert = it.next();
                    dao.insertCompoOsversionWeek(insert);
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
    
    public int deleteComponentOsversionWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentOsversionDao dao = session.getMapper(ComponentOsversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoOsversionWeekByKey(year, week, "", "", "");
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
    
    public int selectComponentOsversionWeekCountByKey(String year, String week,
            String appkey, String componentkey, String osversion)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentOsversionDao dao = session.getMapper(ComponentOsversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoOsversionWeekCountByKey(year, week, appkey, componentkey, osversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_osversion_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentOsversionMonth(
            List<CompoOsversionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentOsversionDao dao = session.getMapper(ComponentOsversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoOsversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoOsversionAll insert = it.next();
                    dao.insertCompoOsversionMonth(insert);
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
    
    public int deleteComponentOsversionMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentOsversionDao dao = session.getMapper(ComponentOsversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoOsversionMonthByKey(year, month, "", "", "");
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
    
    public int selectComponentOsversionMonthCountByKey(String year,
            String month, String appkey, String componentkey, String osversion)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentOsversionDao dao = session.getMapper(ComponentOsversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoOsversionMonthCountByKey(year, month, appkey, componentkey, osversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
