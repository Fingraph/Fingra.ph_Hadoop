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
import ph.fingra.hadoop.dbms.parts.distribution.dao.CountryTimeDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.CountryTimeAll;

public class CountryTimeServiceImpl implements CountryTimeService {
    
    private static CountryTimeService instance = null;
    
    static {
        instance = new CountryTimeServiceImpl();
    }
    
    public static CountryTimeService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_time_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryTimeDay(List<CountryTimeAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryTimeDao dao = session.getMapper(CountryTimeDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountryTimeAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountryTimeAll insert = it.next();
                    dao.insertCountryTimeDay(insert);
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
    
    public int deleteCountryTimeDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryTimeDao dao = session.getMapper(CountryTimeDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountryTimeDayByKey(year, month, day, "", "");
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
    
    public int selectCountryTimeDayCountByKey(String year, String month,
            String day, String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountryTimeDao dao = session.getMapper(CountryTimeDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountryTimeDayCountByKey(year, month, day, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_time_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryTimeWeek(List<CountryTimeAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryTimeDao dao = session.getMapper(CountryTimeDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountryTimeAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountryTimeAll insert = it.next();
                    dao.insertCountryTimeWeek(insert);
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
    
    public int deleteCountryTimeWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryTimeDao dao = session.getMapper(CountryTimeDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountryTimeWeekByKey(year, week, "", "");
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
    
    public int selectCountryTimeWeekCountByKey(String year, String week,
            String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountryTimeDao dao = session.getMapper(CountryTimeDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountryTimeWeekCountByKey(year, week, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_time_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryTimeMonth(List<CountryTimeAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryTimeDao dao = session.getMapper(CountryTimeDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountryTimeAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountryTimeAll insert = it.next();
                    dao.insertCountryTimeMonth(insert);
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
    
    public int deleteCountryTimeMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryTimeDao dao = session.getMapper(CountryTimeDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountryTimeMonthByKey(year, month, "", "");
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
    
    public int selectCountryTimeMonthCountByKey(String year, String month,
            String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountryTimeDao dao = session.getMapper(CountryTimeDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountryTimeMonthCountByKey(year, month, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
