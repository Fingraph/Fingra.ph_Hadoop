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
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentResolutionDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoResolutionAll;

public class ComponentResolutionServiceImpl implements
        ComponentResolutionService {
    
    private static ComponentResolutionService instance = null;
    
    static {
        instance = new ComponentResolutionServiceImpl();
    }
    
    public static ComponentResolutionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_resolution_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentResolutionDay(
            List<CompoResolutionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentResolutionDao dao = session.getMapper(ComponentResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoResolutionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoResolutionAll insert = it.next();
                    dao.insertCompoResolutionDay(insert);
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
    
    public int deleteComponentResolutionDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentResolutionDao dao = session.getMapper(ComponentResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoResolutionDayByKey(year, month, day, "", "", "");
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
    
    public int selectComponentResolutionDayCountByKey(String year,
            String month, String day, String appkey, String componentkey,
            String resolution) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentResolutionDao dao = session.getMapper(ComponentResolutionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoResolutionDayCountByKey(year, month, day, appkey, componentkey, resolution);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_resolution_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentResolutionWeek(
            List<CompoResolutionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentResolutionDao dao = session.getMapper(ComponentResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoResolutionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoResolutionAll insert = it.next();
                    dao.insertCompoResolutionWeek(insert);
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
    
    public int deleteComponentResolutionWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentResolutionDao dao = session.getMapper(ComponentResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoResolutionWeekByKey(year, week, "", "", "");
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
    
    public int selectComponentResolutionWeekCountByKey(String year,
            String week, String appkey, String componentkey, String resolution)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentResolutionDao dao = session.getMapper(ComponentResolutionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoResolutionWeekCountByKey(year, week, appkey, componentkey, resolution);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_resolution_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentResolutionMonth(
            List<CompoResolutionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentResolutionDao dao = session.getMapper(ComponentResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoResolutionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoResolutionAll insert = it.next();
                    dao.insertCompoResolutionMonth(insert);
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
    
    public int deleteComponentResolutionMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentResolutionDao dao = session.getMapper(ComponentResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoResolutionMonthByKey(year, month, "", "", "");
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
    
    public int selectComponentResolutionMonthCountByKey(String year,
            String month, String appkey, String componentkey,
            String resolution) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentResolutionDao dao = session.getMapper(ComponentResolutionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoResolutionMonthCountByKey(year, month, appkey, componentkey, resolution);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
