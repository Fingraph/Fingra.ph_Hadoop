#!/usr/bin/env bash

# ######################################################################
# Fingra.ph Hadoop Local mode test script
# 
# Information: configurations of running Hadoop locally.
# 
# 1. HADOOP_HOME/conf/core-site.xml
# <property>
#   <name>fs.default.name</name>
#   <value>file:///</value>
# </property>
# 
# 2. HADOOP_HOME/conf/hdfs-site.xml
# <property>
#   <name>dfs.replication</name>
#   <value>1</value>
# </property>
# 
# 3. HADOOP_HOME/conf/mapred-site.xml
# <property>
#   <name>mapred.job.tracker</name>
#   <value>local</value>
# </property>
# ######################################################################

export HADOOP_CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/log4j-datedFileAppender-1.0.2.jar:$HADOOP_CLASSPATH
export HADOOP_CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/classes:$HADOOP_CLASSPATH

export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/classes:.
#export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/slf4j-api-1.5.2.jar:$CLASSPATH
#export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/jcl-over-slf4j-1.7.2.jar:$CLASSPATH
#export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/slf4j-log4j12-1.7.2.jar:$CLASSPATH
export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/log4j-1.2.15.jar:$CLASSPATH
export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/log4j-datedFileAppender-1.0.2.jar:$CLASSPATH
export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/mybatis-3.2.7.jar:$CLASSPATH
export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/mysql-connector-java-5.1.26.jar:$CLASSPATH

# run hourly map/reduce job #############################################

mode="hour"
#target="2014-08-20-14"
target="2014-08-20-15"
#target="2014-08-20-16"
#target="2014-08-20-17"
#target="2014-08-20-18"

hadoop ph.fingra.hadoop.mapred.PerformanceDriver newuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver usersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver frequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver sessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver pageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

# run hourly dbms controll ##############################################

java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.NewuserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.UserController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.PageviewController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.FrequencyController -Drunmode=$mode -Dtargetdate=$target
java -cp $CLASSPATH ph.fingra.hadoop.dbms.parts.performance.controller.SessionlengthSectionController -Drunmode=$mode -Dtargetdate=$target
