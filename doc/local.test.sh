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
#export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/slf4j-api-1.5.2.jar:$CLASSPATH
#export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/jcl-over-slf4j-1.7.2.jar:$CLASSPATH
#export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/slf4j-log4j12-1.7.2.jar:$CLASSPATH
export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/log4j-1.2.15.jar:$CLASSPATH
export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/log4j-datedFileAppender-1.0.2.jar:$CLASSPATH
export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/mybatis-3.2.7.jar:$CLASSPATH
export CLASSPATH=/data/workspace_oss/Fingraph_Hadoop/target/lib/mysql-connector-java-5.1.26.jar:$CLASSPATH

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
hadoop ph.fingra.hadoop.mapred.DistributionDriver language -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver appversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver osversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver resolution -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver countrynewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver countryhoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver countrysessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.ComponentDriver componentnewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentusersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentfrequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componenthoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

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
hadoop ph.fingra.hadoop.mapred.DistributionDriver language -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver appversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver osversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver resolution -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver countrynewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver countryhoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver countrysessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.ComponentDriver componentnewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentusersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentfrequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componenthoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

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
hadoop ph.fingra.hadoop.mapred.DistributionDriver language -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver appversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver osversion -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver resolution -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver countrynewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver countryhoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.DistributionDriver countrysessionlength -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

hadoop ph.fingra.hadoop.mapred.ComponentDriver componentnewuser -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentusersession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componentfrequency -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4
hadoop ph.fingra.hadoop.mapred.ComponentDriver componenthoursession -Drunmode=$mode -Dtargetdate=$target -Dnumreduce=4

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
