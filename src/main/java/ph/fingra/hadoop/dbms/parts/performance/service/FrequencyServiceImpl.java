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
import ph.fingra.hadoop.dbms.parts.performance.dao.FrequencyDao;
import ph.fingra.hadoop.dbms.parts.performance.domain.FrequencyAll;

public class FrequencyServiceImpl implements FrequencyService {
    
    private static FrequencyService instance = null;
    
    static {
        instance = new FrequencyServiceImpl();
    }
    
    public static FrequencyService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_frequency_day
    // ------------------------------------------------------------------------
    
    public int insertBatchFrequencyDay(List<FrequencyAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        FrequencyDao dao = session.getMapper(FrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<FrequencyAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    FrequencyAll insert = it.next();
                    dao.insertFrequencyDay(insert);
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
    
    public int deleteFrequencyDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        FrequencyDao dao = session.getMapper(FrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteFrequencyDayByKey(year, month, day, "");
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
    
    public int selectFrequencyDayCountByKey(String year, String month,
            String day, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        FrequencyDao dao = session.getMapper(FrequencyDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectFrequencyDayCountByKey(year, month, day, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_frequency_week
    // ------------------------------------------------------------------------
    
    public int insertBatchFrequencyWeek(List<FrequencyAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        FrequencyDao dao = session.getMapper(FrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<FrequencyAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    FrequencyAll insert = it.next();
                    dao.insertFrequencyWeek(insert);
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
    
    public int deleteFrequencyWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        FrequencyDao dao = session.getMapper(FrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteFrequencyWeekByKey(year, week, "");
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
    
    public int selectFrequencyWeekCountByKey(String year, String week,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        FrequencyDao dao = session.getMapper(FrequencyDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectFrequencyWeekCountByKey(year, week, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_frequency_month
    // ------------------------------------------------------------------------
    
    public int insertBatchFrequencyMonth(List<FrequencyAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        FrequencyDao dao = session.getMapper(FrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<FrequencyAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    FrequencyAll insert = it.next();
                    dao.insertFrequencyMonth(insert);
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
    
    public int deleteFrequencyMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        FrequencyDao dao = session.getMapper(FrequencyDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteFrequencyMonthByKey(year, month, "");
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
    
    public int selectFrequencyMonthCountByKey(String year, String month,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        FrequencyDao dao = session.getMapper(FrequencyDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectFrequencyMonthCountByKey(year, month, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
