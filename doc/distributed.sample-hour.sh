#!/usr/bin/env bash

###
 # Copyright 2014 tgrape Inc.
 # 
 # Licensed under the Apache License, Version 2.0 (the "License");
 # you may not use this file except in compliance with the License.
 # You may obtain a copy of the License at
 # 
 #     http://www.apache.org/licenses/LICENSE-2.0
 # 
 # Unless required by applicable law or agreed to in writing, software
 # distributed under the License is distributed on an "AS IS" BASIS,
 # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 # See the License for the specific language governing permissions and
 # limitations under the License.
 ##

# ######################################################################
# Fingra.ph Hadoop Pseudo-distributed, Fully distributed mode test script
# 
# Information: configurations of running Hadoop locally.
# [hadoop-data-path] => ex) /home/hadoop/hadoop-data
# 
# 1. HADOOP_HOME/conf/core-site.xml
# <property>
#   <name>fs.default.name</name>
#   <value>hdfs://localhost:9000</value>
# </property>
# <property>
#   <name>hadoop.tmp.dir</name>
#   <value>[hadoop-data-path]/hadoop-${user.name}</value>
# </property>
# 
# 2. HADOOP_HOME/conf/hdfs-site.xml
# <property>
#   <name>dfs.name.dir</name>
#   <value>[hadoop-data-path]/dfs/name</value>
# </property>
# <property>
#   <name>dfs.name.edits.dir</name>
#   <value>${dfs.name.dir}</value>
# </property>
# <property>
#   <name>dfs.data.dir</name>
#   <value>[hadoop-data-path]/dfs/data</value>
# </property>
# <property>
#   <name>dfs.replication</name>
#   <value>1</value>
# </property>
# 
# 3. HADOOP_HOME/conf/mapred-site.xml
# <property>
#   <name>mapred.job.tracker</name>
#   <value>localhost:9001</value>
# </property>
# <property>
#   <name>mapred.local.dir</name>
#   <value>${hadoop.tmp.dir}/mapred/local</value>
# </property>
# <property>
#   <name>mapred.system.dir</name>
#   <value>${hadoop.tmp.dir}/mapred/system</value>
# </property>
# ######################################################################

export JAVA_HOME=/usr/local/java
export HADOOP_INSTALL=/home/hadoop/hadoop

export PATH=$JAVA_HOME/bin:$HADOOP_INSTALL/bin:$PATH

export HADOOP_CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/log4j-datedFileAppender-1.0.2.jar:$HADOOP_CLASSPATH

export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop:.:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/fingraph-hadoop.jar:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/log4j-1.2.15.jar:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/log4j-datedFileAppender-1.0.2.jar:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/mybatis-3.2.7.jar:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/mysql-connector-java-5.1.26.jar:$CLASSPATH

export HADOOP_JAR=/var/workspace_oss/Fingraph_Hadoop/fingraph-hadoop.jar

# run hourly map/reduce job #############################################

mode="hour"
#target="2014-08-20-15"

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver newuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver usersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver frequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver sessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver pageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

# run hourly dbms controll ##############################################

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.NewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.UserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.PageviewController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.FrequencyController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthSectionController -Drunmode=$mode -Dtargetdate=$target
