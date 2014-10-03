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
import ph.fingra.hadoop.dbms.parts.distribution.dao.CountrySessionlengthDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.CountrySessionlengthAll;

public class CountrySessionlengthServiceImpl implements
        CountrySessionlengthService {
    
    private static CountrySessionlengthService instance = null;
    
    static {
        instance = new CountrySessionlengthServiceImpl();
    }
    
    public static CountrySessionlengthService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthDay(
            List<CountrySessionlengthAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthDao dao = session.getMapper(CountrySessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountrySessionlengthAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountrySessionlengthAll insert = it.next();
                    dao.insertCountrySessionlengthDay(insert);
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
    
    public int deleteCountrySessionlengthDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthDao dao = session.getMapper(CountrySessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountrySessionlengthDayByKey(year, month, day, "", "");
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
    
    public int selectCountrySessionlengthDayCountByKey(String year,
            String month, String day, String appkey, String country)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountrySessionlengthDao dao = session.getMapper(CountrySessionlengthDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountrySessionlengthDayCountByKey(year, month, day, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthWeek(
            List<CountrySessionlengthAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthDao dao = session.getMapper(CountrySessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountrySessionlengthAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountrySessionlengthAll insert = it.next();
                    dao.insertCountrySessionlengthWeek(insert);
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
    
    public int deleteCountrySessionlengthWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthDao dao = session.getMapper(CountrySessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountrySessionlengthWeekByKey(year, week, "", "");
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
    
    public int selectCountrySessionlengthWeekCountByKey(String year,
            String week, String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountrySessionlengthDao dao = session.getMapper(CountrySessionlengthDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountrySessionlengthWeekCountByKey(year, week, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthMonth(
            List<CountrySessionlengthAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthDao dao = session.getMapper(CountrySessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountrySessionlengthAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountrySessionlengthAll insert = it.next();
                    dao.insertCountrySessionlengthMonth(insert);
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
    
    public int deleteCountrySessionlengthMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthDao dao = session.getMapper(CountrySessionlengthDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountrySessionlengthMonthByKey(year, month, "", "");
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
    
    public int selectCountrySessionlengthMonthCountByKey(String year,
            String month, String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountrySessionlengthDao dao = session.getMapper(CountrySessionlengthDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountrySessionlengthMonthCountByKey(year, month, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
