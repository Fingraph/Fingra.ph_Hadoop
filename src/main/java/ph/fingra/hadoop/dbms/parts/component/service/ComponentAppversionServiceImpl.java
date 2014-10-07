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
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentAppversionDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoAppversionAll;

public class ComponentAppversionServiceImpl implements
        ComponentAppversionService {
    
    private static ComponentAppversionService instance = null;
    
    static {
        instance = new ComponentAppversionServiceImpl();
    }
    
    public static ComponentAppversionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_appversion_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentAppversionDay(
            List<CompoAppversionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentAppversionDao dao = session.getMapper(ComponentAppversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoAppversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoAppversionAll insert = it.next();
                    dao.insertCompoAppversionDay(insert);
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
    
    public int deleteComponentAppversionDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentAppversionDao dao = session.getMapper(ComponentAppversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoAppversionDayByKey(year, month, day, "", "", "");
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
    
    public int selectComponentAppversionDayCountByKey(String year,
            String month, String day, String appkey, String componentkey,
            String appversion) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentAppversionDao dao = session.getMapper(ComponentAppversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoAppversionDayCountByKey(year, month, day, appkey, componentkey, appversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_appversion_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentAppversionWeek(
            List<CompoAppversionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentAppversionDao dao = session.getMapper(ComponentAppversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoAppversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoAppversionAll insert = it.next();
                    dao.insertCompoAppversionWeek(insert);
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
    
    public int deleteComponentAppversionWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentAppversionDao dao = session.getMapper(ComponentAppversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoAppversionWeekByKey(year, week, "", "", "");
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
    
    public int selectComponentAppversionWeekCountByKey(String year,
            String week, String appkey, String componentkey, String appversion)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentAppversionDao dao = session.getMapper(ComponentAppversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoAppversionWeekCountByKey(year, week, appkey, componentkey, appversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_appversion_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentAppversionMonth(
            List<CompoAppversionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentAppversionDao dao = session.getMapper(ComponentAppversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoAppversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoAppversionAll insert = it.next();
                    dao.insertCompoAppversionMonth(insert);
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
    
    public int deleteComponentAppversionMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentAppversionDao dao = session.getMapper(ComponentAppversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoAppversionMonthByKey(year, month, "", "", "");
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
    
    public int selectComponentAppversionMonthCountByKey(String year,
            String month, String appkey, String componentkey,
            String appversion) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentAppversionDao dao = session.getMapper(ComponentAppversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoAppversionMonthCountByKey(year, month, appkey, componentkey, appversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
