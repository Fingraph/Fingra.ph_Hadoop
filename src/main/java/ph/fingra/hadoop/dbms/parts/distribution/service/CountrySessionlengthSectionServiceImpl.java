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
import ph.fingra.hadoop.dbms.parts.distribution.dao.CountrySessionlengthSectionDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.CountrySessionlengthSectionAll;

public class CountrySessionlengthSectionServiceImpl implements
        CountrySessionlengthSectionService {
    
    private static CountrySessionlengthSectionService instance = null;
    
    static {
        instance = new CountrySessionlengthSectionServiceImpl();
    }
    
    public static CountrySessionlengthSectionService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_section_day
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthSectionDay(
            List<CountrySessionlengthSectionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthSectionDao dao = session.getMapper(CountrySessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountrySessionlengthSectionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountrySessionlengthSectionAll insert = it.next();
                    dao.insertCountrySessionlengthSectionDay(insert);
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
    
    public int deleteCountrySessionlengthSectionDayByDate(String year,
            String month, String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthSectionDao dao = session.getMapper(CountrySessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountrySessionlengthSectionDayByKey(year, month, day, "", "");
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
    
    public int selectCountrySessionlengthSectionDayCountByKey(String year,
            String month, String day, String appkey, String country)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountrySessionlengthSectionDao dao = session.getMapper(CountrySessionlengthSectionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountrySessionlengthSectionDayCountByKey(year, month, day, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_section_week
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthSectionWeek(
            List<CountrySessionlengthSectionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthSectionDao dao = session.getMapper(CountrySessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountrySessionlengthSectionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountrySessionlengthSectionAll insert = it.next();
                    dao.insertCountrySessionlengthSectionWeek(insert);
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
    
    public int deleteCountrySessionlengthSectionWeekByDate(String year,
            String week) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthSectionDao dao = session.getMapper(CountrySessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountrySessionlengthSectionWeekByKey(year, week, "", "");
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
    
    public int selectCountrySessionlengthSectionWeekCountByKey(String year,
            String week, String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountrySessionlengthSectionDao dao = session.getMapper(CountrySessionlengthSectionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountrySessionlengthSectionWeekCountByKey(year, week, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cd_country_sessionlength_section_month
    // ------------------------------------------------------------------------
    
    public int insertBatchCountrySessionlengthSectionMonth(
            List<CountrySessionlengthSectionAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthSectionDao dao = session.getMapper(CountrySessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CountrySessionlengthSectionAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CountrySessionlengthSectionAll insert = it.next();
                    dao.insertCountrySessionlengthSectionMonth(insert);
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
    
    public int deleteCountrySessionlengthSectionMonthByDate(String year,
            String month) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        CountrySessionlengthSectionDao dao = session.getMapper(CountrySessionlengthSectionDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCountrySessionlengthSectionMonthByKey(year, month, "", "");
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
    
    public int selectCountrySessionlengthSectionMonthCountByKey(String year,
            String month, String appkey, String country) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        CountrySessionlengthSectionDao dao = session.getMapper(CountrySessionlengthSectionDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCountrySessionlengthSectionMonthCountByKey(year, month, appkey, country);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
