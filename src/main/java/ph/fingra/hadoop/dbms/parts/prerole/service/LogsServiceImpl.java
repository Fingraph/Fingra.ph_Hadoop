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

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import ph.fingra.hadoop.dbms.common.ConnectionFactory;
import ph.fingra.hadoop.dbms.parts.prerole.dao.LogsDao;
import ph.fingra.hadoop.dbms.parts.prerole.domain.LogsAll;

public class LogsServiceImpl implements LogsService {
    
    private static LogsService instance = null;
    
    static {
        instance = new LogsServiceImpl();
    }
    
    public static LogsService getInstance() {
        return instance;
    }
    
    // ------------------------------------------------------------------------
    //st_logs_day
    // ------------------------------------------------------------------------
    
    public int insertBatchLogsDay(List<LogsAll> in_volist) throws Exception {
        
        if (in_volist == null) {
            return 0;
        }
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        LogsDao dao = session.getMapper(LogsDao.class);
        
        boolean has_error = false;
        
        try {
            
            if (in_volist != null) {
                
                Iterator<LogsAll> it = in_volist.iterator();
                
                while (it.hasNext()) {
                    LogsAll insert = it.next();
                    dao.insertLogsDay(insert);
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
    
    public int deleteLogsDayByDate(String year, String month, String day)
            throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession(ExecutorType.BATCH, false);
        LogsDao dao = session.getMapper(LogsDao.class);
        
        boolean has_error = false;
        
        try {
            dao.deleteLogsDayByKey(year, month, day, "");
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
    
    public int selectLogsDayCountByKey(String year, String month, String day,
            String appkey) throws Exception {
        
        SqlSession session = ConnectionFactory.getSession().openSession();
        LogsDao dao = session.getMapper(LogsDao.class);
        
        int cnt = 0;
        
        try {
            cnt = dao.selectLogsDayCountByKey(year, month, day, appkey);
        }
        finally {
            session.close();
        }
        
        return cnt;
    }
    
}
