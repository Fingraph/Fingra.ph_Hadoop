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

package ph.fingra.hadoop.dbms.parts.performance.service;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import ph.fingra.hadoop.dbms.common.ConnectionFactory;
import ph.fingra.hadoop.dbms.parts.performance.dao.PageviewDao;
import ph.fingra.hadoop.dbms.parts.performance.domain.PageviewAll;

public class PageviewServiceImpl implements PageviewService {
    
    private static PageviewService instance = null;
    
    static {
        instance = new PageviewServiceImpl();
    }
    
    public static PageviewService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_pageview_day
    // ------------------------------------------------------------------------
    
    public int insertBatchPageviewDay(List<PageviewAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        PageviewDao dao = session.getMapper(PageviewDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<PageviewAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    PageviewAll insert = it.next();
                    dao.insertPageviewDay(insert);
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
    
    public int deletePageviewDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        PageviewDao dao = session.getMapper(PageviewDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deletePageviewDayByKey(year, month, day, "");
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
    
    public int selectPageviewDayCountByKey(String year, String month,
            String day, String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        PageviewDao dao = session.getMapper(PageviewDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectPageviewDayCountByKey(year, month, day, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_pageview_week
    // ------------------------------------------------------------------------
    
    public int insertBatchPageviewWeek(List<PageviewAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        PageviewDao dao = session.getMapper(PageviewDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<PageviewAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    PageviewAll insert = it.next();
                    dao.insertPageviewWeek(insert);
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
    
    public int deletePageviewWeekByDate(String year, String week)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        PageviewDao dao = session.getMapper(PageviewDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deletePageviewWeekByKey(year, week, "");
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
    
    public int selectPageviewWeekCountByKey(String year, String week,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        PageviewDao dao = session.getMapper(PageviewDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectPageviewWeekCountByKey(year, week, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    // ------------------------------------------------------------------------
    //st_pageview_month
    // ------------------------------------------------------------------------
    
    public int insertBatchPageviewMonth(List<PageviewAll> in_volist)
            throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        PageviewDao dao = session.getMapper(PageviewDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<PageviewAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    PageviewAll insert = it.next();
                    dao.insertPageviewMonth(insert);
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
    
    public int deletePageviewMonthByDate(String year, String month)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        PageviewDao dao = session.getMapper(PageviewDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deletePageviewMonthByKey(year, month, "");
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
    
    public int selectPageviewMonthCountByKey(String year, String month,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        PageviewDao dao = session.getMapper(PageviewDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectPageviewMonthCountByKey(year, month, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
