#!/usr/bin/env bash

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

export HADOOP_CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/log4j-datedFileAppender-1.0.2.jar:$HADOOP_CLASSPATH
export HADOOP_CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/classes:$HADOOP_CLASSPATH

export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/classes:.

# run daily map/reduce job #############################################

mode="day"
target="2014-08-20"

hadoop ph.fingra.hadoop.mapred.PreroleDriver logcount -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PreroleDriver pretransform -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PreroleDriver appnewusermerge -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PreroleDriver componentnewusermerge -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PreroleDriver basekeys -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.PerformanceDriver newuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver usersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver frequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver hoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver sessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver pageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.DistributionDriver device -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver country -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.ComponentDriver componentnewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentusersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentfrequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componenthoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

# run weekly map/reduce job ############################################

mode="week"
target="2014-34"

hadoop ph.fingra.hadoop.mapred.PerformanceDriver newuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver usersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver frequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver hoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver sessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver pageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.DistributionDriver device -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver country -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.ComponentDriver componentnewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentusersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentfrequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componenthoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

# run monthly map/reduce job ###########################################

mode="month"
target="2014-08"

hadoop ph.fingra.hadoop.mapred.PerformanceDriver newuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver usersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver frequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver hoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver sessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.PerformanceDriver pageview -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.DistributionDriver device -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver country -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.ComponentDriver componentnewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentusersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentfrequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componenthoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

