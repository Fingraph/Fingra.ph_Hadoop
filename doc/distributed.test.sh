#!/bin/bash

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

export JAVA_HOME=/usr/local/java
export HADOOP_INSTALL=/home/hadoop/hadoop
export PROGRAM_HOME=/var/workspace_oss/Fingraph_Hadoop
export SDKLOG_PATH=/var/sdk_logs
export HDFS_USER=/user/hadoop

export PATH=$JAVA_HOME/bin:$HADOOP_INSTALL/bin:$PROGRAM_HOME:$PATH

export HADOOP_CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/log4j-datedFileAppender-1.0.2.jar:$HADOOP_CLASSPATH

export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop:.:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/fingraph-hadoop.jar:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/log4j-1.2.15.jar:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/log4j-datedFileAppender-1.0.2.jar:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/mybatis-3.2.7.jar:$CLASSPATH
export CLASSPATH=/var/workspace_oss/Fingraph_Hadoop/lib/mysql-connector-java-5.1.26.jar:$CLASSPATH

export HADOOP_JAR=/var/workspace_oss/Fingraph_Hadoop/fingraph-hadoop.jar

# test date(yyyy-MM-dd) and year, month
TARGET_DAY="XXXX-XX-XX"
TARGET_YEAR="XXXX"
TARGET_MONTH="XX"

SDKLOG_SAVE_PATH="$SDKLOG_PATH/"
DFS_INPUT_PATH="$HDFS_USER/input/$TARGET_YEAR/$TARGET_MONTH/"

SDKLOG_FILE_IPHONE="sdk_iphone_log.$TARGET_DAY.txt"
SDKLOG_FILE_ANDROID="sdk_android_log.$TARGET_DAY.txt"

# make hadoop dfs input directory
`hadoop dfs -mkdir $DFS_INPUT_PATH`

`hadoop dfs -rm $DFS_INPUT_PATH/*$SDKLOG_FILE_IPHONE`
`hadoop dfs -rm $DFS_INPUT_PATH/*$SDKLOG_FILE_ANDROID`
`hadoop dfs -put $SDKLOG_SAVE_PATH/*$SDKLOG_FILE_IPHONE $DFS_INPUT_PATH`
`hadoop dfs -put $SDKLOG_SAVE_PATH/*$SDKLOG_FILE_ANDROID $DFS_INPUT_PATH`

mode="day"
target=$TARGET_DAY

# test mapreduce job
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PreroleDriver pretransform -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver usersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.PerformanceDriver pageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop jar $HADOOP_JAR ph.fingra.hadoop.mapred.DistributionDriver country -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

# dbms save controller
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.UserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.PageviewController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.distribution.controller.CountryController -Drunmode=$mode -Dtargetdate=$target
