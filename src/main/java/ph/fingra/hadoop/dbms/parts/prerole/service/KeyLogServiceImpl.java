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

package ph.fingra.hadoop.dbms.parts.prerole.service;

import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import ph.fingra.hadoop.common.util.DateTimeUtil;
import ph.fingra.hadoop.dbms.common.ConnectionFactory;
import ph.fingra.hadoop.dbms.parts.prerole.dao.KeyLogDao;
import ph.fingra.hadoop.dbms.parts.prerole.domain.AppLogFirst;
import ph.fingra.hadoop.dbms.parts.prerole.domain.ComponentLogFirst;

public class KeyLogServiceImpl implements KeyLogService {
    
    private static KeyLogService instance = null;
    
    static {
        instance = new KeyLogServiceImpl();
    }
    
    public static KeyLogService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //app_log_first
    // ------------------------------------------------------------------------
    
    public int selectAppLogFirstCountByKey(String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        KeyLogDao dao = session.getMapper(KeyLogDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectAppLogFirstCountByKey(appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    public AppLogFirst selectAppLogFirst(String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        KeyLogDao dao = session.getMapper(KeyLogDao.class);
        
        AppLogFirst vo = null;
        
        try {
            vo = dao.selectAppLogFirst(appkey);
        }
        finally {
            session.close();
        }
        
        return vo;
    }
    
    public int renewalAppLogFirst(AppLogFirst new_vo, AppLogFirst old_vo)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        KeyLogDao dao = session.getMapper(KeyLogDao.class);
        
        boolean has_error = false;
        boolean is_modified = false;
        
        try {
            
            if (old_vo != null) {
                
                String old_date = old_vo.getYear()+"-"+old_vo.getMonth()+"-"+old_vo.getDay();
                String new_date = new_vo.getYear()+"-"+new_vo.getMonth()+"-"+new_vo.getDay();
                
                int days_num = DateTimeUtil.daysBetween(old_date, new_date, "yyyy-MM-dd");
                if (days_num < 0) {
                    dao.updateAppLogFirst(new_vo);
                    is_modified = true;
                }
                else {
                    //do nothing
                }
            }
            else {
                dao.insertAppLogFirst(new_vo);
                is_modified = true;
            }
            
            if (is_modified) {
                List<BatchResult> results = session.flushStatements();
                results.clear();
            }
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
    
    // ------------------------------------------------------------------------
    //component_log_first
    // ------------------------------------------------------------------------
    
    public int selectComponentLogFirstCountByKey(String appkey, String componentkey)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        KeyLogDao dao = session.getMapper(KeyLogDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectComponentLogFirstCountByKey(appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
    public ComponentLogFirst selectComponentLogFirst(String appkey,
            String componentkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        KeyLogDao dao = session.getMapper(KeyLogDao.class);
        
        ComponentLogFirst vo = null;
        
        try {
            vo = dao.selectComponentLogFirst(appkey, componentkey);
        }
        finally {
            session.close();
        }
        
        return vo;
    }
    
    public int renewalComponentLogFirst(ComponentLogFirst new_vo,
            ComponentLogFirst old_vo) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        KeyLogDao dao = session.getMapper(KeyLogDao.class);
        
        boolean has_error = false;
        boolean is_modified = false;
        
        try {
            
            if (old_vo != null) {
                
                String old_date = old_vo.getYear()+"-"+old_vo.getMonth()+"-"+old_vo.getDay();
                String new_date = new_vo.getYear()+"-"+new_vo.getMonth()+"-"+new_vo.getDay();
                
                int days_num = DateTimeUtil.daysBetween(old_date, new_date, "yyyy-MM-dd");
                if (days_num < 0) {
                    dao.updateComponentLogFirst(new_vo);
                    is_modified = true;
                }
                else {
                    
                    //do nothing
                }
            }
            else {
                dao.insertComponentLogFirst(new_vo);
                is_modified = true;
            }
            
            if (is_modified) {
                List<BatchResult> results = session.flushStatements();
                results.clear();
            }
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
    
}
