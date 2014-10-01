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

package ph.fingra.hadoop.dbms.parts.distribution.service;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import ph.fingra.hadoop.dbms.common.ConnectionFactory;
import ph.fingra.hadoop.dbms.parts.distribution.dao.ResolutionDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.ResolutionAll;

public class ResolutionServiceImpl implements ResolutionService {
    
    private static ResolutionService instance = null;
    
    static {
        instance = new ResolutionServiceImpl();
    }
    
    public static ResolutionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_resolution_day
    // ------------------------------------------------------------------------
    
    public int insertBatchResolutionDay(List<ResolutionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ResolutionDao dao = session.getMapper(ResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<ResolutionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    ResolutionAll insert = it.next();
                    dao.insertResolutionDay(insert);
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
    
    public int deleteResolutionDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ResolutionDao dao = session.getMapper(ResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteResolutionDayByKey(year, month, day, "", "");
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
    
    public int selectResolutionDayCountByKey(String year, String month,
            String day, String appkey, String resolution) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ResolutionDao dao = session.getMapper(ResolutionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectResolutionDayCountByKey(year, month, day, appkey, resolution);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_resolution_week
    // ------------------------------------------------------------------------
    
    public int insertBatchResolutionWeek(List<ResolutionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ResolutionDao dao = session.getMapper(ResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<ResolutionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    ResolutionAll insert = it.next();
                    dao.insertResolutionWeek(insert);
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
    
    public int deleteResolutionWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ResolutionDao dao = session.getMapper(ResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteResolutionWeekByKey(year, week, "", "");
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
    
    public int selectResolutionWeekCountByKey(String year, String week,
            String appkey, String resolution) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ResolutionDao dao = session.getMapper(ResolutionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectResolutionWeekCountByKey(year, week, appkey, resolution);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_resolution_month
    // ------------------------------------------------------------------------
    
    public int insertBatchResolutionMonth(List<ResolutionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ResolutionDao dao = session.getMapper(ResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<ResolutionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    ResolutionAll insert = it.next();
                    dao.insertResolutionMonth(insert);
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
    
    public int deleteResolutionMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ResolutionDao dao = session.getMapper(ResolutionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteResolutionMonthByKey(year, month, "", "");
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
    
    public int selectResolutionMonthCountByKey(String year, String month,
            String appkey, String resolution) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ResolutionDao dao = session.getMapper(ResolutionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectResolutionMonthCountByKey(year, month, appkey, resolution);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
