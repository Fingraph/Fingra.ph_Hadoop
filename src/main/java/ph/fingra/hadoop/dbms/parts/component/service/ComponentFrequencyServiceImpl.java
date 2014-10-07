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
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentFrequencyDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoFrequencyAll;

public class ComponentFrequencyServiceImpl implements ComponentFrequencyService {
    
    private static ComponentFrequencyService instance = null;
    
    static {
        instance = new ComponentFrequencyServiceImpl();
    }
    
    public static ComponentFrequencyService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_frequency_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentFrequencyDay(
            List<CompoFrequencyAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentFrequencyDao dao = session.getMapper(ComponentFrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoFrequencyAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoFrequencyAll insert = it.next();
                    dao.insertCompoFrequencyDay(insert);
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
    
    public int deleteComponentFrequencyDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentFrequencyDao dao = session.getMapper(ComponentFrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoFrequencyDayByKey(year, month, day, "", "");
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
    
    public int selectComponentFrequencyDayCountByKey(String year, String month,
            String day, String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentFrequencyDao dao = session.getMapper(ComponentFrequencyDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoFrequencyDayCountByKey(year, month, day, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_frequency_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentFrequencyWeek(
            List<CompoFrequencyAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentFrequencyDao dao = session.getMapper(ComponentFrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoFrequencyAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoFrequencyAll insert = it.next();
                    dao.insertCompoFrequencyWeek(insert);
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
    
    public int deleteComponentFrequencyWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentFrequencyDao dao = session.getMapper(ComponentFrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoFrequencyWeekByKey(year, week, "", "");
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
    
    public int selectComponentFrequencyWeekCountByKey(String year, String week,
            String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentFrequencyDao dao = session.getMapper(ComponentFrequencyDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoFrequencyWeekCountByKey(year, week, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_frequency_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentFrequencyMonth(
            List<CompoFrequencyAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentFrequencyDao dao = session.getMapper(ComponentFrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoFrequencyAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoFrequencyAll insert = it.next();
                    dao.insertCompoFrequencyMonth(insert);
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
    
    public int deleteComponentFrequencyMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentFrequencyDao dao = session.getMapper(ComponentFrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoFrequencyMonthByKey(year, month, "", "");
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
    
    public int selectComponentFrequencyMonthCountByKey(String year,
            String month, String appkey, String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentFrequencyDao dao = session.getMapper(ComponentFrequencyDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoFrequencyMonthCountByKey(year, month, appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
