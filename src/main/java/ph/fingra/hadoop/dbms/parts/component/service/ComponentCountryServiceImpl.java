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
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentCountryDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoCountryAll;

public class ComponentCountryServiceImpl implements ComponentCountryService {
    
    private static ComponentCountryService instance = null;
    
    static {
        instance = new ComponentCountryServiceImpl();
    }
    
    public static ComponentCountryService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_country_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentCountryDay(List<CompoCountryAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentCountryDao dao = session.getMapper(ComponentCountryDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoCountryAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoCountryAll insert = it.next();
                    dao.insertCompoCountryDay(insert);
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
    
    public int deleteComponentCountryDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentCountryDao dao = session.getMapper(ComponentCountryDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoCountryDayByKey(year, month, day, "", "", "");
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
    
    public int selectComponentCountryDayCountByKey(String year, String month,
            String day, String appkey, String componentkey, String country)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentCountryDao dao = session.getMapper(ComponentCountryDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoCountryDayCountByKey(year, month, day, appkey, componentkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_country_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentCountryWeek(List<CompoCountryAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentCountryDao dao = session.getMapper(ComponentCountryDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoCountryAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoCountryAll insert = it.next();
                    dao.insertCompoCountryWeek(insert);
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
    
    public int deleteComponentCountryWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentCountryDao dao = session.getMapper(ComponentCountryDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoCountryWeekByKey(year, week, "", "", "");
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
    
    public int selectComponentCountryWeekCountByKey(String year, String week,
            String appkey, String componentkey, String country)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentCountryDao dao = session.getMapper(ComponentCountryDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoCountryWeekCountByKey(year, week, appkey, componentkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_country_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentCountryMonth(List<CompoCountryAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentCountryDao dao = session.getMapper(ComponentCountryDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoCountryAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoCountryAll insert = it.next();
                    dao.insertCompoCountryMonth(insert);
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
    
    public int deleteComponentCountryMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentCountryDao dao = session.getMapper(ComponentCountryDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoCountryMonthByKey(year, month, "", "", "");
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
    
    public int selectComponentCountryMonthCountByKey(String year, String month,
            String appkey, String componentkey, String country)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentCountryDao dao = session.getMapper(ComponentCountryDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoCountryMonthCountByKey(year, month, appkey, componentkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
