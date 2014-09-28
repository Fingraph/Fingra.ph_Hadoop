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

package ph.fingra.hadoop.dbms.parts.prerole.dao;

import org.apache.ibatis.annotations.Param;

import ph.fingra.hadoop.dbms.parts.prerole.domain.AppLogFirst;
import ph.fingra.hadoop.dbms.parts.prerole.domain.ComponentLogFirst;

public interface KeyLogDao {
    
    // ------------------------------------------------------------------------
    //app_log_first
    // ------------------------------------------------------------------------
    
    public int selectAppLogFirstCountByKey(@Param("appkey") String appkey)
            throws Exception;
    
    public AppLogFirst selectAppLogFirst(@Param("appkey") String appkey)
            throws Exception;
    
    public int insertAppLogFirst(AppLogFirst vo) throws Exception;
    
    public int updateAppLogFirst(AppLogFirst vo) throws Exception;
    
    // ------------------------------------------------------------------------
    //component_log_first
    // ------------------------------------------------------------------------
    
    public int selectComponentLogFirstCountByKey(@Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public ComponentLogFirst selectComponentLogFirst(@Param("appkey") String appkey,
            @Param("componentkey") String componentkey) throws Exception;
    
    public int insertComponentLogFirst(ComponentLogFirst vo) throws Exception;
    
    public int updateComponentLogFirst(ComponentLogFirst vo) throws Exception;
    
}
