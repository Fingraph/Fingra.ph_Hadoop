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

# run daily map/reduce job #############################################

mode="day"
#target="2014-08-20"

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PreroleDriver logcount -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PreroleDriver pretransform -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PreroleDriver appnewusermerge -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PreroleDriver componentnewusermerge -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PreroleDriver basekeys -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver newuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver usersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver frequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver hoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver sessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver pageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver device -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver country -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver language -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver appversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver osversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver resolution -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countrynewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countryhoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countrysessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countrypageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentnewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentusersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentfrequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componenthoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentdevice -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentcountry -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentlanguage -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentappversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentosversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentresolution -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

# run daily dbms controll ##############################################

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.prerole.controller.LogsController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.prerole.controller.KeyLogController -Drunmode=$mode -Dtargetdate=$target

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.NewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.UserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.PageviewController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.TimeController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.FrequencyController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthSectionController -Drunmode=$mode -Dtargetdate=$target

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.DeviceController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.LanguageController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.AppversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.OsversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.ResolutionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryNewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountrySessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountrySessionlengthController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryPageviewController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryTimeController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountrySessionlengthSectionController -Drunmode=$mode -Dtargetdate=$target

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentNewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentUserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentSessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentTimeController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentFrequencyController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentDeviceController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentCountryController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentLanguageController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentAppversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentOsversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentResolutionController -Drunmode=$mode -Dtargetdate=$target

# run weekly map/reduce job ############################################

mode="week"
#target="2014-34"

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver newuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver usersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver frequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver hoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver sessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver pageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver device -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver country -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver language -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver appversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver osversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver resolution -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countrynewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countryhoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countrysessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countrypageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentnewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentusersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentfrequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componenthoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentdevice -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentcountry -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentlanguage -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentappversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentosversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentresolution -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

# run weekly dbms controll #############################################

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.NewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.UserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.PageviewController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.TimeController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.FrequencyController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthSectionController -Drunmode=$mode -Dtargetdate=$target

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.DeviceController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.LanguageController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.AppversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.OsversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.ResolutionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryNewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountrySessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountrySessionlengthController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryPageviewController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryTimeController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountrySessionlengthSectionController -Drunmode=$mode -Dtargetdate=$target

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentNewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentUserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentSessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentTimeController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentFrequencyController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentDeviceController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentCountryController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentLanguageController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentAppversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentOsversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentResolutionController -Drunmode=$mode -Dtargetdate=$target

# run monthly map/reduce job ###########################################

mode="month"
#target="2014-08"

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver newuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver usersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver frequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver hoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver sessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver pageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver device -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver country -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver language -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver appversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver osversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver resolution -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countrynewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countryhoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countrysessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver countrypageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentnewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentusersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentfrequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componenthoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentdevice -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentcountry -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentlanguage -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentappversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentosversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.ComponentDriver componentresolution -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

# run monthly dbms controll ############################################

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.NewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.UserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.PageviewController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.TimeController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.FrequencyController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthSectionController -Drunmode=$mode -Dtargetdate=$target

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.DeviceController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.LanguageController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.AppversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.OsversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.ResolutionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryNewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountrySessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountrySessionlengthController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryPageviewController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryTimeController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountrySessionlengthSectionController -Drunmode=$mode -Dtargetdate=$target

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentNewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentUserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentSessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentTimeController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentFrequencyController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentDeviceController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentCountryController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentLanguageController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentAppversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentOsversionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.component.controller.ComponentResolutionController -Drunmode=$mode -Dtargetdate=$target
