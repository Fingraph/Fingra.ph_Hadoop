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

import java.util.List;

import ph.fingra.hadoop.dbms.parts.performance.domain.PageviewAll;

public interface PageviewService {
    
    // ------------------------------------------------------------------------
    //st_pageview_hour
    // ------------------------------------------------------------------------
    
    public int insertBatchPageviewHour(List<PageviewAll> in_volist)
            throws Exception;
    
    public int deletePageviewHourByDate(String year, String month, String day,
            String hour) throws Exception;
    
    public int selectPageviewHourCountByKey(String year, String month,
            String day, String hour, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_pageview_day
    // ------------------------------------------------------------------------
    
    public int insertBatchPageviewDay(List<PageviewAll> in_volist)
            throws Exception;
    
    public int deletePageviewDayByDate(String year, String month, String day)
            throws Exception;
    
    public int selectPageviewDayCountByKey(String year, String month,
            String day, String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_pageview_week
    // ------------------------------------------------------------------------
    
    public int insertBatchPageviewWeek(List<PageviewAll> in_volist)
            throws Exception;
    
    public int deletePageviewWeekByDate(String year, String week)
            throws Exception;
    
    public int selectPageviewWeekCountByKey(String year, String week,
            String appkey) throws Exception;
    
    // ------------------------------------------------------------------------
    //st_pageview_month
    // ------------------------------------------------------------------------
    
    public int insertBatchPageviewMonth(List<PageviewAll> in_volist)
            throws Exception;
    
    public int deletePageviewMonthByDate(String year, String month)
            throws Exception;
    
    public int selectPageviewMonthCountByKey(String year, String month,
            String appkey) throws Exception;
    
}
