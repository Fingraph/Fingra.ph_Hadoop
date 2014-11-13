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

export PATH=$JAVA_HOME/bin:$HADOOP_INSTALL/bin:$PROGRAM_HOME:$PATH

DATE=$(date +%Y-%m-%d)
CUR_HOUR=$(date +%H)

# 00 hour > 23 hour at yesterday
# 01..23 hour > 1 hour ago from current hour at today
if [ "$CUR_HOUR" = "00" ]; then
  MODE="23 Hour (Yesterday)"
else
  MODE="1 Hour Ago From Current (Today)"
fi

# Shell script log file
LOGFILE="$PROGRAM_HOME/run_scheduled.sample.hour.log"

echo "" >> $LOGFILE
echo "------------------------------------------------------------" >> $LOGFILE
echo "### FILE : scheduled.sample.hour.sh" >> $LOGFILE
echo "### START : $DATE-$(date +"%T")" >> $LOGFILE
echo "### TARGET : $MODE" >> $LOGFILE

# upload sdklog to dfs
echo "### RUN : putdfs.sdklog.sample.sh" >> $LOGFILE
`cd $PROGRAM_HOME; /bin/bash ./putdfs.sdklog.sample.sh >> $LOGFILE 2>> $LOGFILE`

echo "### RUN : distributed.sample-hour.sh" >> $LOGFILE
`cd $PROGRAM_HOME; /bin/bash ./distributed.sample-hour.sh > ./run_hour.log 2>&1`

echo "### END : $DATE-$(date +"%T")" >> $LOGFILE
echo "------------------------------------------------------------" >> $LOGFILE
