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
import ph.fingra.hadoop.dbms.parts.distribution.dao.OsversionDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.OsversionAll;

public class OsversionServiceImpl implements OsversionService {
    
    private static OsversionService instance = null;
    
    static {
        instance = new OsversionServiceImpl();
    }
    
    public static OsversionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_osversion_day
    // ------------------------------------------------------------------------
    
    public int insertBatchOsversionDay(List<OsversionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        OsversionDao dao = session.getMapper(OsversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<OsversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    OsversionAll insert = it.next();
                    dao.insertOsversionDay(insert);
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
    
    public int deleteOsversionDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        OsversionDao dao = session.getMapper(OsversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteOsversionDayByKey(year, month, day, "", "");
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
    
    public int selectOsversionDayCountByKey(String year, String month,
            String day, String appkey, String osversion) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        OsversionDao dao = session.getMapper(OsversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectOsversionDayCountByKey(year, month, day, appkey, osversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_osversion_week
    // ------------------------------------------------------------------------
    
    public int insertBatchOsversionWeek(List<OsversionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        OsversionDao dao = session.getMapper(OsversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<OsversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    OsversionAll insert = it.next();
                    dao.insertOsversionWeek(insert);
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
    
    public int deleteOsversionWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        OsversionDao dao = session.getMapper(OsversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteOsversionWeekByKey(year, week, "", "");
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
    
    public int selectOsversionWeekCountByKey(String year, String week,
            String appkey, String osversion) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        OsversionDao dao = session.getMapper(OsversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectOsversionWeekCountByKey(year, week, appkey, osversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_osversion_month
    // ------------------------------------------------------------------------
    
    public int insertBatchOsversionMonth(List<OsversionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        OsversionDao dao = session.getMapper(OsversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<OsversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    OsversionAll insert = it.next();
                    dao.insertOsversionMonth(insert);
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
    
    public int deleteOsversionMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        OsversionDao dao = session.getMapper(OsversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteOsversionMonthByKey(year, month, "", "");
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
    
    public int selectOsversionMonthCountByKey(String year, String month,
            String appkey, String osversion) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        OsversionDao dao = session.getMapper(OsversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectOsversionMonthCountByKey(year, month, appkey, osversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
