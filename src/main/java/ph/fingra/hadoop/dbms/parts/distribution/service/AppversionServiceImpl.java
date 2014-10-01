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
import ph.fingra.hadoop.dbms.parts.distribution.dao.AppversionDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.AppversionAll;

public class AppversionServiceImpl implements AppversionService {
    
    private static AppversionService instance = null;
    
    static {
        instance = new AppversionServiceImpl();
    }
    
    public static AppversionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_appversion_day
    // ------------------------------------------------------------------------
    
    public int insertBatchAppversionDay(List<AppversionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        AppversionDao dao = session.getMapper(AppversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<AppversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    AppversionAll insert = it.next();
                    dao.insertAppversionDay(insert);
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
    
    public int deleteAppversionDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        AppversionDao dao = session.getMapper(AppversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteAppversionDayByKey(year, month, day, "", "");
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
    
    public int selectAppversionDayCountByKey(String year, String month,
            String day, String appkey, String appversion) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        AppversionDao dao = session.getMapper(AppversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectAppversionDayCountByKey(year, month, day, appkey, appversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_appversion_week
    // ------------------------------------------------------------------------
    
    public int insertBatchAppversionWeek(List<AppversionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        AppversionDao dao = session.getMapper(AppversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<AppversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    AppversionAll insert = it.next();
                    dao.insertAppversionWeek(insert);
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
    
    public int deleteAppversionWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        AppversionDao dao = session.getMapper(AppversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteAppversionWeekByKey(year, week, "", "");
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
    
    public int selectAppversionWeekCountByKey(String year, String week,
            String appkey, String appversion) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        AppversionDao dao = session.getMapper(AppversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectAppversionWeekCountByKey(year, week, appkey, appversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_appversion_month
    // ------------------------------------------------------------------------
    
    public int insertBatchAppversionMonth(List<AppversionAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        AppversionDao dao = session.getMapper(AppversionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<AppversionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    AppversionAll insert = it.next();
                    dao.insertAppversionMonth(insert);
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
    
    public int deleteAppversionMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        AppversionDao dao = session.getMapper(AppversionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteAppversionMonthByKey(year, month, "", "");
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
    
    public int selectAppversionMonthCountByKey(String year, String month,
            String appkey, String appversion) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        AppversionDao dao = session.getMapper(AppversionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectAppversionMonthCountByKey(year, month, appkey, appversion);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
