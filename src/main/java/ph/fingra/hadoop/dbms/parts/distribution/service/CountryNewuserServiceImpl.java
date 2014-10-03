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
import ph.fingra.hadoop.dbms.parts.distribution.dao.CountryNewuserDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.CountryNewuserAll;

public class CountryNewuserServiceImpl implements CountryNewuserService {
    
    private static CountryNewuserService instance = null;
    
    static {
        instance = new CountryNewuserServiceImpl();
    }
    
    public static CountryNewuserService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_newuser_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryNewuserDay(List<CountryNewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryNewuserDao dao = session.getMapper(CountryNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountryNewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountryNewuserAll insert = it.next();
                    dao.insertCountryNewuserDay(insert);
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
    
    public int deleteCountryNewuserDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryNewuserDao dao = session.getMapper(CountryNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountryNewuserDayByKey(year, month, day, "", "");
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
    
    public int selectCountryNewuserDayCountByKey(String year, String month,
            String day, String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountryNewuserDao dao = session.getMapper(CountryNewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountryNewuserDayCountByKey(year, month, day, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_newuser_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryNewuserWeek(List<CountryNewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryNewuserDao dao = session.getMapper(CountryNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountryNewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountryNewuserAll insert = it.next();
                    dao.insertCountryNewuserWeek(insert);
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
    
    public int deleteCountryNewuserWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryNewuserDao dao = session.getMapper(CountryNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountryNewuserWeekByKey(year, week, "", "");
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
    
    public int selectCountryNewuserWeekCountByKey(String year, String week,
            String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountryNewuserDao dao = session.getMapper(CountryNewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountryNewuserWeekCountByKey(year, week, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_newuser_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountryNewuserMonth(List<CountryNewuserAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryNewuserDao dao = session.getMapper(CountryNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountryNewuserAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountryNewuserAll insert = it.next();
                    dao.insertCountryNewuserMonth(insert);
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
    
    public int deleteCountryNewuserMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountryNewuserDao dao = session.getMapper(CountryNewuserDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountryNewuserMonthByKey(year, month, "", "");
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
    
    public int selectCountryNewuserMonthCountByKey(String year, String month,
            String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountryNewuserDao dao = session.getMapper(CountryNewuserDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountryNewuserMonthCountByKey(year, month, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
