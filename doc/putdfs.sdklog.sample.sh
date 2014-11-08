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

export JAVA_HOME=/data/java
export HADOOP_INSTALL=/data/hadoop
export PROGRAM_HOME=/data/workspace_oss/Fingraph_Hadoop
export SDKLOG_PATH=/data/sdk_logs
export HDFS_USER=/user/hadoop

export PATH=$JAVA_HOME/bin:$HADOOP_INSTALL/bin:$PROGRAM_HOME:$PATH

# current hour(00..23)
CUR_HOUR=$(date +%H)

# decision sdklog file: 00 hour > yesterday's sdklog file
#                       01..23 hour > today's sdklog file
if [ "$CUR_HOUR" = "00" ]
then
  TARGET_DAY=`date +%Y-%m-%d --date '1 days ago'`
  TARGET_YEAR=`date +%Y --date '1 days ago'`
  TARGET_MONTH=`date +%m --date '1 days ago'`
else
  TARGET_DAY=`date +%Y-%m-%d`
  TARGET_YEAR=`date +%Y`
  TARGET_MONTH=`date +%m`
fi

# shell log file
LOGFILE="$PROGRAM_HOME/run_putdfs.sdkolog.sample.log"

# source-path & target-path
SDKLOG_SAVE_PATH="$SDKLOG_PATH/"
DFS_INPUT_PATH="$HDFS_USER/input/$TARGET_YEAR/$TARGET_MONTH/"

SDKLOG_FILE_IPHONE="sdk_iphone_log.$TARGET_DAY.txt"
SDKLOG_FILE_ANDROID="sdk_android_log.$TARGET_DAY.txt"

echo "" >> $LOGFILE
echo "------------------------------------------------------------" >> $LOGFILE
echo "### run putdfs.sdklog.samples.sh.. [$(date +'%Y-%m-%d %T')]" >> $LOGFILE
echo "### put Hadoop hdfs Start.. target-date: $TARGET_DAY" >> $LOGFILE

# create hadoop input directories by year/month
`hadoop dfs -mkdir $DFS_INPUT_PATH >> $LOGFILE 2>> $LOGFILE`

# copy log files to hadoop file system
`hadoop dfs -rm $DFS_INPUT_PATH/*$SDKLOG_FILE_IPHONE >> $LOGFILE 2>> $LOGFILE`
`hadoop dfs -rm $DFS_INPUT_PATH/*$SDKLOG_FILE_ANDROID >> $LOGFILE 2>> $LOGFILE`
`hadoop dfs -put $SDKLOG_SAVE_PATH/*$SDKLOG_FILE_IPHONE $DFS_INPUT_PATH >> $LOGFILE 2>> $LOGFILE`
`hadoop dfs -put $SDKLOG_SAVE_PATH/*$SDKLOG_FILE_ANDROID $DFS_INPUT_PATH >> $LOGFILE 2>> $LOGFILE`

echo "### put Hadoop hdfs Complete.." >> $LOGFILE
echo "------------------------------------------------------------" >> $LOGFILE
