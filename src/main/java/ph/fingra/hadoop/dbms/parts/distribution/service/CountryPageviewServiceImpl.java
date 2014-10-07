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
import ph.fingra.hadoop.dbms.parts.distribution.dao.CountryPageviewDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.CountryPageviewAll;

public class CountryPageviewServiceImpl implements CountryPageviewService {
    
    private static CountryPageviewService instance = null;
    
    static {
        instance = new CountryPageviewServiceImpl();
    }
    
    public static CountryPageviewService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_pageview_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryPageviewDay(
            List<CountryPageviewAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryPageviewDao dao = session.getMapper(CountryPageviewDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountryPageviewAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountryPageviewAll insert = it.next();
                    dao.insertCountryPageviewDay(insert);
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
    
    public int deleteCountryPageviewDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryPageviewDao dao = session.getMapper(CountryPageviewDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountryPageviewDayByKey(year, month, day, "", "");
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
    
    public int selectCountryPageviewDayCountByKey(String year, String month,
            String day, String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountryPageviewDao dao = session.getMapper(CountryPageviewDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountryPageviewDayCountByKey(year, month, day, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_pageview_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryPageviewWeek(
            List<CountryPageviewAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryPageviewDao dao = session.getMapper(CountryPageviewDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountryPageviewAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountryPageviewAll insert = it.next();
                    dao.insertCountryPageviewWeek(insert);
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
    
    public int deleteCountryPageviewWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryPageviewDao dao = session.getMapper(CountryPageviewDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountryPageviewWeekByKey(year, week, "", "");
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
    
    public int selectCountryPageviewWeekCountByKey(String year, String week,
            String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountryPageviewDao dao = session.getMapper(CountryPageviewDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountryPageviewWeekCountByKey(year, week, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_pageview_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryPageviewMonth(
            List<CountryPageviewAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryPageviewDao dao = session.getMapper(CountryPageviewDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountryPageviewAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountryPageviewAll insert = it.next();
                    dao.insertCountryPageviewMonth(insert);
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
    
    public int deleteCountryPageviewMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryPageviewDao dao = session.getMapper(CountryPageviewDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountryPageviewMonthByKey(year, month, "", "");
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
    
    public int selectCountryPageviewMonthCountByKey(String year, String month,
            String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountryPageviewDao dao = session.getMapper(CountryPageviewDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountryPageviewMonthCountByKey(year, month, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
