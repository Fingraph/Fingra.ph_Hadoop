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

package ph.fingra.hadoop.dbms.parts.component.service;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import ph.fingra.hadoop.dbms.common.ConnectionFactory;
import ph.fingra.hadoop.dbms.parts.component.dao.ComponentLanguageDao;
import ph.fingra.hadoop.dbms.parts.component.domain.CompoLanguageAll;

public class ComponentLanguageServiceImpl implements ComponentLanguageService {
    
    private static ComponentLanguageService instance = null;
    
    static {
        instance = new ComponentLanguageServiceImpl();
    }
    
    public static ComponentLanguageService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_language_day
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentLanguageDay(List<CompoLanguageAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentLanguageDao dao = session.getMapper(ComponentLanguageDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoLanguageAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoLanguageAll insert = it.next();
                    dao.insertCompoLanguageDay(insert);
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
    
    public int deleteComponentLanguageDayByDate(String year, String month,
            String day) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentLanguageDao dao = session.getMapper(ComponentLanguageDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoLanguageDayByKey(year, month, day, "", "", "");
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
    
    public int selectComponentLanguageDayCountByKey(String year, String month,
            String day, String appkey, String componentkey, String language)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentLanguageDao dao = session.getMapper(ComponentLanguageDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoLanguageDayCountByKey(year, month, day, appkey, componentkey, language);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_language_week
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentLanguageWeek(
            List<CompoLanguageAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentLanguageDao dao = session.getMapper(ComponentLanguageDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoLanguageAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoLanguageAll insert = it.next();
                    dao.insertCompoLanguageWeek(insert);
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
    
    public int deleteComponentLanguageWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentLanguageDao dao = session.getMapper(ComponentLanguageDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoLanguageWeekByKey(year, week, "", "", "");
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
    
    public int selectComponentLanguageWeekCountByKey(String year, String week,
            String appkey, String componentkey, String language)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentLanguageDao dao = session.getMapper(ComponentLanguageDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoLanguageWeekCountByKey(year, week, appkey, componentkey, language);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //cp_compo_language_month
    // ------------------------------------------------------------------------
    
    public int insertBatchComponentLanguageMonth(
            List<CompoLanguageAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentLanguageDao dao = session.getMapper(ComponentLanguageDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<CompoLanguageAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    CompoLanguageAll insert = it.next();
                    dao.insertCompoLanguageMonth(insert);
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
    
    public int deleteComponentLanguageMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        ComponentLanguageDao dao = session.getMapper(ComponentLanguageDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteCompoLanguageMonthByKey(year, month, "", "", "");
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
    
    public int selectComponentLanguageMonthCountByKey(String year,
            String month, String appkey, String componentkey, String language)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        ComponentLanguageDao dao = session.getMapper(ComponentLanguageDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectCompoLanguageMonthCountByKey(year, month, appkey, componentkey, language);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
