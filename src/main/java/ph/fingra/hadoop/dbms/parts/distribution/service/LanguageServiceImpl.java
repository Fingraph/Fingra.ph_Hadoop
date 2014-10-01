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
import ph.fingra.hadoop.dbms.parts.distribution.dao.LanguageDao;
import ph.fingra.hadoop.dbms.parts.distribution.domain.LanguageAll;

public class LanguageServiceImpl implements LanguageService {
    
    private static LanguageService instance = null;
    
    static {
        instance = new LanguageServiceImpl();
    }
    
    public static LanguageService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_language_day
    // ------------------------------------------------------------------------
    
    public int insertBatchLanguageDay(List<LanguageAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        LanguageDao dao = session.getMapper(LanguageDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<LanguageAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    LanguageAll insert = it.next();
                    dao.insertLanguageDay(insert);
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
    
    public int deleteLanguageDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        LanguageDao dao = session.getMapper(LanguageDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteLanguageDayByKey(year, month, day, "", "");
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
    
    public int selectLanguageDayCountByKey(String year, String month,
            String day, String appkey, String language) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        LanguageDao dao = session.getMapper(LanguageDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectLanguageDayCountByKey(year, month, day, appkey, language);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_language_week
    // ------------------------------------------------------------------------
    
    public int insertBatchLanguageWeek(List<LanguageAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        LanguageDao dao = session.getMapper(LanguageDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<LanguageAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    LanguageAll insert = it.next();
                    dao.insertLanguageWeek(insert);
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
    
    public int deleteLanguageWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        LanguageDao dao = session.getMapper(LanguageDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteLanguageWeekByKey(year, week, "", "");
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
    
    public int selectLanguageWeekCountByKey(String year, String week,
            String appkey, String language) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        LanguageDao dao = session.getMapper(LanguageDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectLanguageWeekCountByKey(year, week, appkey, language);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_language_month
    // ------------------------------------------------------------------------
    
    public int insertBatchLanguageMonth(List<LanguageAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        LanguageDao dao = session.getMapper(LanguageDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<LanguageAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    LanguageAll insert = it.next();
                    dao.insertLanguageMonth(insert);
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
    
    public int deleteLanguageMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        LanguageDao dao = session.getMapper(LanguageDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteLanguageMonthByKey(year, month, "", "");
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
    
    public int selectLanguageMonthCountByKey(String year, String month,
            String appkey, String language) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        LanguageDao dao = session.getMapper(LanguageDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectLanguageMonthCountByKey(year, month, appkey, language);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
