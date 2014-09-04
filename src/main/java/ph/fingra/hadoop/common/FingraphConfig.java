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

package ph.fingra.hadoop.common;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ph.fingra.hadoop.common.domain.DebugEntity;
import ph.fingra.hadoop.common.domain.SettingEntity;
import ph.fingra.hadoop.common.logger.ErrorLogger;

public class FingraphConfig {
    
    private SettingEntity setting;
    private DebugEntity debug;
    
    public SettingEntity getSetting() {
        return setting;
    }
    public DebugEntity getDebug() {
        return debug;
    }
    
    private boolean has_error;
    private String error;
    private String project_path;
    private String hadoop_user_path;
    
    public String getError() {
        return this.error;
    }
    public String getProject_path() {
        return project_path;
    }
    public String getHadoop_user_path() {
        return hadoop_user_path;
    }
    
    public FingraphConfig() throws IOException {
        
        this.has_error = false;
        this.error = "";
        
        String runPath = "";
        // find fingraph-config.xml in the run directory
        File run_root = new File(".");
        try {
            runPath = run_root.getCanonicalPath();
            //System.out.println(PROJECT_ROOT);
        }
        catch (IOException e) {
            this.has_error = true;
            this.error = e.getMessage();
            
            ErrorLogger.log(e.getMessage());
            throw e;
        }
        
        String configFilePath = runPath + "/fingraphoss-config.xml";
        
        if (!loadConfiguration(configFilePath)) {
            ErrorLogger.log(this.error);
            throw new IOException(this.error);
        }
    }
    
    private boolean loadConfiguration(String path) {
        
        Document doc = null;
        
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            doc = docBuilder.parse(path);
        }
        catch (SAXException saxe) {
            this.error = saxe.getMessage();
            return false;
        }
        catch (IOException ioe) {
            this.error = ioe.getMessage();
            return false;
        }
        catch (Exception e) {
            this.error = e.getMessage();
            return false;
        }
        
        if (doc == null) {
            this.error = "Parsed result document is null";
            return false;
        }
        
        Element doc_elem = doc.getDocumentElement();
        if (!doc_elem.getNodeName().equals("configuration")) {
            this.error = "Root element's tag name must be 'configuration'";
            return false;
        }
        
        String projectPath = getNodeValue(getNode(doc_elem, "project_path"));
        if (projectPath != null && !projectPath.isEmpty()) {
            this.project_path = projectPath;
        }
        else {
            this.error = "Not exist 'project_path' element";
            return false;
        }
        
        String hadoopUserPath = getNodeValue(getNode(doc_elem, "hadoop_user_path"));
        if (hadoopUserPath != null && !hadoopUserPath.isEmpty()) {
            this.hadoop_user_path = hadoopUserPath;
        }
        else {
            this.error = "Not exist 'hadoop_user_path' element";
            return false;
        }
        
        // get setting info
        Node setting_node = getNode(doc_elem, "setting");
        if (setting_node != null) {
            
            SettingEntity conf = getSettingInfo((Element)setting_node);
            if (this.has_error) {
                return false;
            }
            this.setting = conf;
        }
        else {
            this.error = "Not exist setting element";
            return false;
        }
        
        // get debug info
        Node debug_node = getNode(doc_elem, "debug");
        if (debug_node != null) {
            
            DebugEntity dbg = getDebugInfo((Element)debug_node);
            if (this.has_error) {
                return false;
            }
            this.debug = dbg;
        }
        else {
            this.error = "Not exist debug element";
            return false;
        }
        
        return true;
    }
    
    private SettingEntity getSettingInfo(Element elem) {
        
        SettingEntity entity = new SettingEntity();
        
        entity.setHfs_input_path(getNodeValueString(elem, "hfs_input_path"));
        if (this.has_error) return null;
        
        entity.setOrigin_input_file(getNodeValueString(elem, "origin_input_file"));
        if (this.has_error) return null;
        
        entity.setTransform_input_file(getNodeValueString(elem, "transform_input_file"));
        if (this.has_error) return null;
        
        entity.setDelete_origin_file(getNodeValueBool(elem, "delete_origin_file"));
        if (this.has_error) return null;
        
        entity.setHfs_output_path(getNodeValueString(elem, "hfs_output_path"));
        if (this.has_error) return null;
        
        entity.setHfs_database_path(getNodeValueString(elem, "hfs_database_path"));
        if (this.has_error) return null;
        
        entity.setHfs_database_appnewuser_backup_count(
                getNodeValueInt(elem, "hfs_database_appnewuser_backup_count"));
        if (this.has_error) return null;
        
        entity.setHfs_database_appnewuser_keep_month(
                getNodeValueInt(elem, "hfs_database_appnewuser_keep_month"));
        if (this.has_error) return null;
        
        entity.setHfs_database_componentnewuser_backup_count(
                getNodeValueInt(elem, "hfs_database_componentnewuser_backup_count"));
        if (this.has_error) return null;
        
        entity.setHfs_database_componentnewuser_keep_month(
                getNodeValueInt(elem, "hfs_database_componentnewuser_keep_month"));
        if (this.has_error) return null;
        
        entity.setLfs_result_path(getNodeValueString(elem, "lfs_result_path"));
        if (this.has_error) return null;
        
        entity.setProgram_log_path(getNodeValueString(elem, "program_log_path"));
        if (this.has_error) return null;
        
        entity.setProgram_errorlog_file(getNodeValueString(elem, "program_errorlog_file"));
        if (this.has_error) return null;
        
        return entity;
    }
    
    private DebugEntity getDebugInfo(Element elem) {
        
        DebugEntity entity = new DebugEntity();
        
        entity.setDebug_show_verbose(getNodeValueBool(elem, "debug_show_verbose"));
        if (this.has_error) return null;
        
        entity.setDebug_show_counter(getNodeValueBool(elem, "debug_show_counter"));
        if (this.has_error) return null;
        
        entity.setDebug_show_spenttime(getNodeValueBool(elem, "debug_show_spenttime"));
        if (this.has_error) return null;
        
        return entity;
    }
    
    private int getNodeValueInt(Element parent, String nodename) {
        
        int returnval = 0;
        
        String nodeval = getNodeValue(getNode(parent, nodename));
        if (nodeval != null && !nodeval.isEmpty()) {
            returnval = Integer.parseInt(nodeval);
        }
        else {
            this.has_error = true;
            this.error = "Not exist '" + nodename + "' element";
        }
        
        return returnval;
    }
    
    private String getNodeValueString(Element parent, String nodename) {
        
        String returnval = "";
        
        String nodeval = getNodeValue(getNode(parent, nodename));
        if (nodeval != null && !nodeval.isEmpty()) {
            returnval = nodeval;
        }
        else {
            this.has_error = true;
            this.error = "Not exist '" + nodename + "' element";
        }
        
        return returnval;
    }
    
    private boolean getNodeValueBool(Element parent, String nodename) {
        
        boolean returnval = false;
        
        String debugShowSpenttimeFlag = getNodeValue(getNode(parent, nodename));
        if (debugShowSpenttimeFlag != null && !debugShowSpenttimeFlag.isEmpty()) {
            returnval = Boolean.parseBoolean(debugShowSpenttimeFlag);
        }
        else {
            this.has_error = true;
            this.error = "Not exist '" + nodename + "' element";
        }
        
        return returnval;
    }
    
    private Node getNode(Element root, String nodename) {
        NodeList node_list = root.getElementsByTagName(nodename);
        
        if (node_list == null || node_list.getLength() == 0)
            return null;
        
        if (node_list.getLength() > 1)
            return null;
        
        return node_list.item(0);
    }
    
    private String getNodeValue(Node node) {
        if (node == null)
            return null;
        
        Node child = node.getFirstChild();
        
        while (child != null) {
            if (child.getNodeType() == Node.TEXT_NODE)
                return child.getNodeValue().trim();
            
            child = node.getNextSibling();
        }
        
        return null;
    }
    
    public static void main(String[] args) throws IOException {
        
        FingraphConfig config = new FingraphConfig();
        
        System.out.println(config.getProject_path());
        System.out.println(config.getHadoop_user_path());
        System.out.println(config.setting.toString());
        System.out.println(config.debug.toString());
    }
    
}
