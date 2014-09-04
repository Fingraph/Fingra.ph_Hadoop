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

package ph.fingra.hadoop.common.domain;

import ph.fingra.hadoop.common.BaseObject;

public class SettingEntity extends BaseObject {
    
    private String hfs_input_path;
    private String origin_input_file;
    private String transform_input_file;
    private boolean delete_origin_file;
    private String hfs_output_path;
    private String hfs_database_path;
    private int hfs_database_appnewuser_backup_count;
    private int hfs_database_appnewuser_keep_month;
    private int hfs_database_componentnewuser_backup_count;
    private int hfs_database_componentnewuser_keep_month;
    private String lfs_result_path;
    private String program_log_path;
    private String program_errorlog_file;
    
    public String getHfs_input_path() {
        return hfs_input_path;
    }
    public void setHfs_input_path(String hfs_input_path) {
        this.hfs_input_path = hfs_input_path;
    }
    public String getOrigin_input_file() {
        return origin_input_file;
    }
    public void setOrigin_input_file(String origin_input_file) {
        this.origin_input_file = origin_input_file;
    }
    public String getTransform_input_file() {
        return transform_input_file;
    }
    public void setTransform_input_file(String transform_input_file) {
        this.transform_input_file = transform_input_file;
    }
    public boolean isDelete_origin_file() {
        return delete_origin_file;
    }
    public void setDelete_origin_file(boolean delete_origin_file) {
        this.delete_origin_file = delete_origin_file;
    }
    public String getHfs_output_path() {
        return hfs_output_path;
    }
    public void setHfs_output_path(String hfs_output_path) {
        this.hfs_output_path = hfs_output_path;
    }
    public String getHfs_database_path() {
        return hfs_database_path;
    }
    public void setHfs_database_path(String hfs_database_path) {
        this.hfs_database_path = hfs_database_path;
    }
    public int getHfs_database_appnewuser_backup_count() {
        return hfs_database_appnewuser_backup_count;
    }
    public void setHfs_database_appnewuser_backup_count(
            int hfs_database_appnewuser_backup_count) {
        this.hfs_database_appnewuser_backup_count = hfs_database_appnewuser_backup_count;
    }
    public int getHfs_database_appnewuser_keep_month() {
        return hfs_database_appnewuser_keep_month;
    }
    public void setHfs_database_appnewuser_keep_month(
            int hfs_database_appnewuser_keep_month) {
        this.hfs_database_appnewuser_keep_month = hfs_database_appnewuser_keep_month;
    }
    public int getHfs_database_componentnewuser_backup_count() {
        return hfs_database_componentnewuser_backup_count;
    }
    public void setHfs_database_componentnewuser_backup_count(
            int hfs_database_componentnewuser_backup_count) {
        this.hfs_database_componentnewuser_backup_count = hfs_database_componentnewuser_backup_count;
    }
    public int getHfs_database_componentnewuser_keep_month() {
        return hfs_database_componentnewuser_keep_month;
    }
    public void setHfs_database_componentnewuser_keep_month(
            int hfs_database_componentnewuser_keep_month) {
        this.hfs_database_componentnewuser_keep_month = hfs_database_componentnewuser_keep_month;
    }
    public String getLfs_result_path() {
        return lfs_result_path;
    }
    public void setLfs_result_path(String lfs_result_path) {
        this.lfs_result_path = lfs_result_path;
    }
    public String getProgram_log_path() {
        return program_log_path;
    }
    public void setProgram_log_path(String program_log_path) {
        this.program_log_path = program_log_path;
    }
    public String getProgram_errorlog_file() {
        return program_errorlog_file;
    }
    public void setProgram_errorlog_file(String program_errorlog_file) {
        this.program_errorlog_file = program_errorlog_file;
    }
}
