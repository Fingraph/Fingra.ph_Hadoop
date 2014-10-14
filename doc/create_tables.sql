SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `numbers_small`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `numbers_small` (
  `number` INT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Small Number';


-- -----------------------------------------------------
-- Table `numbers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `numbers` (
  `number` BIGINT NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Number';


-- -----------------------------------------------------
-- Table `times_dimension`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `times_dimension` (
  `time_id` INT NOT NULL COMMENT 'id',
  `time` TIME NOT NULL COMMENT 'time(hh:mm:ss)',
  `hour` VARCHAR(2) NOT NULL COMMENT 'hour(00~23)',
  PRIMARY KEY (`time_id`),
  UNIQUE INDEX `time_uk1_idx` (`time` ASC),
  UNIQUE INDEX `hour_uk1_idx` (`hour` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Time Table';


-- -----------------------------------------------------
-- Table `dates_dimension`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `dates_dimension` (
  `date_id` BIGINT NOT NULL COMMENT 'id',
  `date` DATE NOT NULL COMMENT 'date',
  `day` CHAR(10) NULL DEFAULT NULL COMMENT 'day',
  `day_of_week` INT NULL DEFAULT NULL COMMENT 'day of week(1:sunday)',
  `week_day` INT NULL DEFAULT NULL COMMENT 'week day(0:monday)',
  `day_of_month` INT NULL DEFAULT NULL COMMENT 'day of month',
  `day_of_year` INT NULL DEFAULT NULL COMMENT 'day of year',
  `previous_day` DATE NOT NULL COMMENT 'previous day',
  `next_day` DATE NOT NULL COMMENT 'next day',
  `weekend` CHAR(10) NOT NULL DEFAULT 'Weekday' COMMENT 'Weekday|Weekend',
  `week_of_year` CHAR(2) NULL DEFAULT NULL COMMENT 'week of year',
  `week_year` CHAR(4) NULL DEFAULT NULL COMMENT 'year(by week)',
  `month` CHAR(10) NULL DEFAULT NULL COMMENT 'month(english)',
  `month_of_year` CHAR(2) NULL DEFAULT NULL COMMENT 'month of year',
  `quarter_of_year` INT NULL DEFAULT NULL COMMENT 'quarter of year',
  `year` INT NULL DEFAULT NULL COMMENT 'year',
  PRIMARY KEY (`date_id`),
  UNIQUE INDEX `date_uk1_idx` (`date` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Date Table';


-- -----------------------------------------------------
-- Table `country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `country` (
  `iso2` CHAR(2) NOT NULL DEFAULT '' COMMENT 'iso2 country code',
  `short_name` VARCHAR(80) NOT NULL DEFAULT '' COMMENT 'short name',
  `long_name` VARCHAR(80) NOT NULL DEFAULT '' COMMENT 'long name',
  `iso3` CHAR(3) NULL DEFAULT NULL COMMENT 'iso3 country code',
  `numcode` VARCHAR(6) NULL DEFAULT NULL COMMENT 'number code',
  `un_member` VARCHAR(12) NULL DEFAULT NULL COMMENT 'un member',
  `calling_code` VARCHAR(8) NULL DEFAULT NULL COMMENT 'calling code',
  `cctld` VARCHAR(5) NULL DEFAULT NULL COMMENT 'cct id',
  PRIMARY KEY (`iso2`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Country Code';


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `category` (
  `categoryid` INT NOT NULL COMMENT 'category id',
  `categoryname` VARCHAR(100) NULL DEFAULT NULL COMMENT 'category name',
  `appstorecategory` VARCHAR(20) NULL DEFAULT NULL COMMENT 'appstore category',
  `googleplaycategory` VARCHAR(20) NULL DEFAULT NULL COMMENT 'googleplay category',
  PRIMARY KEY (`categoryid`),
  UNIQUE INDEX `categoryid_uk1_idx` (`categoryid` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'App Category';


-- -----------------------------------------------------
-- Table `sequence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sequence` (
  `name` VARCHAR(32) NOT NULL COMMENT 'sequence name',
  `currval` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT 'curr_val',
  PRIMARY KEY (`name`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Key Sequence';


-- -----------------------------------------------------
-- Table `member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `member` (
  `memberid` INT NOT NULL AUTO_INCREMENT COMMENT 'member id',
  `email` VARCHAR(100) NOT NULL COMMENT 'email',
  `name` VARCHAR(50) NOT NULL COMMENT 'name',
  `password` VARCHAR(100) NOT NULL COMMENT 'login password',
  `department` VARCHAR(50) NOT NULL,
  `phone` VARCHAR(50) NOT NULL,
  `status` TINYINT NOT NULL COMMENT '1:active , 9:delete',
  `joinstatus` TINYINT NOT NULL COMMENT '0:wait, 1:approval, 9:refuse',
  `created` DATETIME NOT NULL COMMENT 'create date',
  `modified` DATETIME NULL COMMENT 'modify date',
  `lastlogin` DATETIME NULL COMMENT 'last login time',
  PRIMARY KEY (`memberid`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Site Member';


-- -----------------------------------------------------
-- Table `app`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `app` (
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `platform` TINYINT NOT NULL COMMENT '1:iOS, 2:Android',
  `appname` VARCHAR(100) NOT NULL COMMENT 'app name',
  `appid` VARCHAR(100) NULL DEFAULT NULL COMMENT 'market identify id',
  `marketid` VARCHAR(100) NULL DEFAULT NULL COMMENT 'market account id',
  `categoryid` INT NOT NULL COMMENT 'category id',
  `status` TINYINT NULL DEFAULT NULL COMMENT 'status',
  `apptype` TINYINT NULL DEFAULT NULL COMMENT 'app type',
  `created` DATE NOT NULL COMMENT 'created date',
  `modified` DATE NULL DEFAULT NULL COMMENT 'modifed date',
  PRIMARY KEY (`appkey`),
  INDEX `category_app_fk1_idx` (`categoryid` ASC),
  UNIQUE INDEX `appkey_uk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'App Info';


-- -----------------------------------------------------
-- Table `appinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `appinfo` (
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `smallicon` VARCHAR(1000) NULL DEFAULT NULL COMMENT 'small icon',
  `mediumicon` VARCHAR(1000) NULL DEFAULT NULL COMMENT 'medium icon',
  `largeicon` VARCHAR(1000) NULL DEFAULT NULL COMMENT 'large icon',
  `created` DATE NULL DEFAULT NULL COMMENT 'create date',
  `updated` DATE NULL DEFAULT NULL COMMENT 'update date',
  `storeupdated` DATE NULL DEFAULT NULL COMMENT 'store update date',
  PRIMARY KEY (`appkey`),
  UNIQUE INDEX `appkey_uk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'App Additional Info';


-- -----------------------------------------------------
-- Table `component`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `component` (
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `groupkey` INT NOT NULL DEFAULT '0' COMMENT 'group key',
  `componentname` VARCHAR(100) NOT NULL COMMENT 'name',
  `componentimage` LONGBLOB NULL DEFAULT NULL COMMENT 'image',
  `imagename` VARCHAR(255) NULL DEFAULT NULL COMMENT 'image name',
  `imagepath` VARCHAR(255) NULL DEFAULT NULL COMMENT 'image path',
  `startdate` DATE NULL DEFAULT NULL COMMENT 'start date',
  `enddate` DATE NULL DEFAULT NULL COMMENT 'end date',
  `isdel` INT NOT NULL DEFAULT '0' COMMENT 'is del',
  `regdate` DATE NOT NULL COMMENT 'regist date',
  `deldate` DATE NULL DEFAULT NULL COMMENT 'delete date',
  PRIMARY KEY (`componentkey`),
  INDEX `app_component_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Component Info';


-- -----------------------------------------------------
-- Table `component_group`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `component_group` (
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `groupkey` INT NOT NULL COMMENT 'group key',
  `groupname` VARCHAR(100) NOT NULL COMMENT 'group name',
  `description` VARCHAR(500) NULL DEFAULT NULL COMMENT 'description',
  `isdel` INT NOT NULL DEFAULT '0' COMMENT 'is del',
  `regdate` DATETIME NULL DEFAULT NULL COMMENT 'regist date',
  `moddate` DATETIME NULL DEFAULT NULL COMMENT 'modify date',
  PRIMARY KEY (`appkey`, `groupkey`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Component Group';


-- -----------------------------------------------------
-- Table `app_log_first`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `app_log_first` (
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `date` DATE NOT NULL COMMENT 'date',
  PRIMARY KEY (`appkey`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Appkey First Log';


-- -----------------------------------------------------
-- Table `component_log_first`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `component_log_first` (
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `date` DATE NOT NULL COMMENT 'date',
  PRIMARY KEY (`appkey`, `componentkey`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Componentkey First Log';


-- -----------------------------------------------------
-- Table `st_logs_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_logs_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `total` BIGINT NOT NULL DEFAULT '0' COMMENT 'total count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`),
  INDEX `app_st_logs_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Logcount';


-- -----------------------------------------------------
-- Table `st_newuser_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_newuser_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`),
  INDEX `app_st_newuser_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Newuser';


-- -----------------------------------------------------
-- Table `st_newuser_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_newuser_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `week`, `appkey`),
  INDEX `app_st_newuser_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Newuser';


-- -----------------------------------------------------
-- Table `st_newuser_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_newuser_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `month`, `appkey`),
  INDEX `app_st_newuser_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Newuser';


-- -----------------------------------------------------
-- Table `st_user_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_user_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'activeuser count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`),
  INDEX `app_st_user_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Activeuser';


-- -----------------------------------------------------
-- Table `st_user_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_user_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'activeuser count',
  PRIMARY KEY (`year`, `week`, `appkey`),
  INDEX `app_st_user_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Activeuser';


-- -----------------------------------------------------
-- Table `st_user_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_user_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'activeuser count',
  PRIMARY KEY (`year`, `month`, `appkey`),
  INDEX `app_st_user_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Activeuser';


-- -----------------------------------------------------
-- Table `st_session_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_session_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`),
  INDEX `app_st_session_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Session';


-- -----------------------------------------------------
-- Table `st_session_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_session_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`),
  INDEX `app_st_session_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Session';


-- -----------------------------------------------------
-- Table `st_session_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_session_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`),
  INDEX `app_st_session_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Session';


-- -----------------------------------------------------
-- Table `st_sessionlength_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_sessionlength_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `sessionlength` DOUBLE NOT NULL DEFAULT '0' COMMENT 'sessionlength median',
  `totaltime` BIGINT NOT NULL DEFAULT '0' COMMENT 'total time',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`),
  INDEX `app_st_sessionlength_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Sessionlength Median';


-- -----------------------------------------------------
-- Table `st_sessionlength_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_sessionlength_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `sessionlength` DOUBLE NOT NULL DEFAULT '0' COMMENT 'sessionlength median',
  `totaltime` BIGINT NOT NULL DEFAULT '0' COMMENT 'total time',
  PRIMARY KEY (`year`, `week`, `appkey`),
  INDEX `app_st_sessionlength_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Sessionlength Median';


-- -----------------------------------------------------
-- Table `st_sessionlength_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_sessionlength_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `sessionlength` DOUBLE NOT NULL DEFAULT '0' COMMENT 'sessionlength median',
  `totaltime` BIGINT NOT NULL DEFAULT '0' COMMENT 'total time',
  PRIMARY KEY (`year`, `month`, `appkey`),
  INDEX `app_st_sessionlength_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Sessionlength Median';


-- -----------------------------------------------------
-- Table `st_pageview_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_pageview_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `pageview` BIGINT NOT NULL DEFAULT '0' COMMENT 'pageview count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`),
  INDEX `app_st_pageview_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Pageview';


-- -----------------------------------------------------
-- Table `st_pageview_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_pageview_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `pageview` BIGINT NOT NULL DEFAULT '0' COMMENT 'pageview count',
  PRIMARY KEY (`year`, `week`, `appkey`),
  INDEX `app_st_pageview_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Pageview';


-- -----------------------------------------------------
-- Table `st_pageview_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_pageview_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `pageview` BIGINT NOT NULL DEFAULT '0' COMMENT 'pageview count',
  PRIMARY KEY (`year`, `month`, `appkey`),
  INDEX `app_st_pageview_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Pageview';


-- -----------------------------------------------------
-- Table `st_time_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_time_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `zero_session` BIGINT NOT NULL DEFAULT '0' COMMENT '0hour session count',
  `one_session` BIGINT NOT NULL DEFAULT '0' COMMENT '1hour session count',
  `two_session` BIGINT NOT NULL DEFAULT '0' COMMENT '2hour session count',
  `three_session` BIGINT NOT NULL DEFAULT '0' COMMENT '3hour session count',
  `four_session` BIGINT NOT NULL DEFAULT '0' COMMENT '4hour session count',
  `five_session` BIGINT NOT NULL DEFAULT '0' COMMENT '5hour session count',
  `six_session` BIGINT NOT NULL DEFAULT '0' COMMENT '6hour session count',
  `seven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '7hour session count',
  `eight_session` BIGINT NOT NULL DEFAULT '0' COMMENT '8hour session count',
  `nine_session` BIGINT NOT NULL DEFAULT '0' COMMENT '9hour session count',
  `ten_session` BIGINT NOT NULL DEFAULT '0' COMMENT '10hour session count',
  `eleven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '11hour session count',
  `twelve_session` BIGINT NOT NULL DEFAULT '0' COMMENT '12hour session count',
  `thirteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '13hour session count',
  `fourteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '14hour session count',
  `fifteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '15hour session count',
  `sixteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '16hour session count',
  `seventeen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '17hour session count',
  `eighteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '18hour session count',
  `nineteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '19hour session count',
  `twenty_session` BIGINT NOT NULL DEFAULT '0' COMMENT '20hour session count',
  `twentyone_session` BIGINT NOT NULL DEFAULT '0' COMMENT '21hour session count',
  `twentytwo_session` BIGINT NOT NULL DEFAULT '0' COMMENT '22hour session count',
  `twentythree_session` BIGINT NOT NULL DEFAULT '0' COMMENT '23hour session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`),
  INDEX `app_st_time_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Hour Session';


-- -----------------------------------------------------
-- Table `st_time_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_time_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `zero_session` BIGINT NOT NULL DEFAULT '0' COMMENT '0hour session count',
  `one_session` BIGINT NOT NULL DEFAULT '0' COMMENT '1hour session count',
  `two_session` BIGINT NOT NULL DEFAULT '0' COMMENT '2hour session count',
  `three_session` BIGINT NOT NULL DEFAULT '0' COMMENT '3hour session count',
  `four_session` BIGINT NOT NULL DEFAULT '0' COMMENT '4hour session count',
  `five_session` BIGINT NOT NULL DEFAULT '0' COMMENT '5hour session count',
  `six_session` BIGINT NOT NULL DEFAULT '0' COMMENT '6hour session count',
  `seven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '7hour session count',
  `eight_session` BIGINT NOT NULL DEFAULT '0' COMMENT '8hour session count',
  `nine_session` BIGINT NOT NULL DEFAULT '0' COMMENT '9hour session count',
  `ten_session` BIGINT NOT NULL DEFAULT '0' COMMENT '10hour session count',
  `eleven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '11hour session count',
  `twelve_session` BIGINT NOT NULL DEFAULT '0' COMMENT '12hour session count',
  `thirteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '13hour session count',
  `fourteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '14hour session count',
  `fifteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '15hour session count',
  `sixteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '16hour session count',
  `seventeen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '17hour session count',
  `eighteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '18hour session count',
  `nineteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '19hour session count',
  `twenty_session` BIGINT NOT NULL DEFAULT '0' COMMENT '20hour session count',
  `twentyone_session` BIGINT NOT NULL DEFAULT '0' COMMENT '21hour session count',
  `twentytwo_session` BIGINT NOT NULL DEFAULT '0' COMMENT '22hour session count',
  `twentythree_session` BIGINT NOT NULL DEFAULT '0' COMMENT '23hour session count',
  PRIMARY KEY (`year`, `week`, `appkey`),
  INDEX `app_st_time_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Hour Session';


-- -----------------------------------------------------
-- Table `st_time_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_time_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `zero_session` BIGINT NOT NULL DEFAULT '0' COMMENT '0hour session count',
  `one_session` BIGINT NOT NULL DEFAULT '0' COMMENT '1hour session count',
  `two_session` BIGINT NOT NULL DEFAULT '0' COMMENT '2hour session count',
  `three_session` BIGINT NOT NULL DEFAULT '0' COMMENT '3hour session count',
  `four_session` BIGINT NOT NULL DEFAULT '0' COMMENT '4hour session count',
  `five_session` BIGINT NOT NULL DEFAULT '0' COMMENT '5hour session count',
  `six_session` BIGINT NOT NULL DEFAULT '0' COMMENT '6hour session count',
  `seven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '7hour session count',
  `eight_session` BIGINT NOT NULL DEFAULT '0' COMMENT '8hour session count',
  `nine_session` BIGINT NOT NULL DEFAULT '0' COMMENT '9hour session count',
  `ten_session` BIGINT NOT NULL DEFAULT '0' COMMENT '10hour session count',
  `eleven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '11hour session count',
  `twelve_session` BIGINT NOT NULL DEFAULT '0' COMMENT '12hour session count',
  `thirteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '13hour session count',
  `fourteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '14hour session count',
  `fifteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '15hour session count',
  `sixteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '16hour session count',
  `seventeen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '17hour session count',
  `eighteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '18hour session count',
  `nineteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '19hour session count',
  `twenty_session` BIGINT NOT NULL DEFAULT '0' COMMENT '20hour session count',
  `twentyone_session` BIGINT NOT NULL DEFAULT '0' COMMENT '21hour session count',
  `twentytwo_session` BIGINT NOT NULL DEFAULT '0' COMMENT '22hour session count',
  `twentythree_session` BIGINT NOT NULL DEFAULT '0' COMMENT '23hour session count',
  PRIMARY KEY (`year`, `month`, `appkey`),
  INDEX `app_st_time_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Hour Session';


-- -----------------------------------------------------
-- Table `st_frequency_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_frequency_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `freq_user_1` BIGINT NOT NULL DEFAULT '0' COMMENT '1 user count',
  `freq_user_2` BIGINT NOT NULL DEFAULT '0' COMMENT '2 user count',
  `freq_user_3_4` BIGINT NOT NULL DEFAULT '0' COMMENT '3~4 user count',
  `freq_user_5_6` BIGINT NOT NULL DEFAULT '0' COMMENT '5~6 user count',
  `freq_user_7_9` BIGINT NOT NULL DEFAULT '0' COMMENT '7~9 user count',
  `freq_user_10_14` BIGINT NOT NULL DEFAULT '0' COMMENT '10~14 user count',
  `freq_user_15_19` BIGINT NOT NULL DEFAULT '0' COMMENT '15~19 user count',
  `freq_user_20_49` BIGINT NOT NULL DEFAULT '0' COMMENT '20~49 user count',
  `freq_user_50_99` BIGINT NOT NULL DEFAULT '0' COMMENT '50~99 user count',
  `freq_user_100_499` BIGINT NOT NULL DEFAULT '0' COMMENT '100~499 user count',
  `freq_user_over_500` BIGINT NOT NULL DEFAULT '0' COMMENT '500~ user count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`),
  INDEX `app_st_frequency_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Frequency Section User';


-- -----------------------------------------------------
-- Table `st_frequency_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_frequency_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `freq_user_1` BIGINT NOT NULL DEFAULT '0' COMMENT '1 user count',
  `freq_user_2` BIGINT NOT NULL DEFAULT '0' COMMENT '2 user count',
  `freq_user_3_4` BIGINT NOT NULL DEFAULT '0' COMMENT '3~4 user count',
  `freq_user_5_6` BIGINT NOT NULL DEFAULT '0' COMMENT '5~6 user count',
  `freq_user_7_9` BIGINT NOT NULL DEFAULT '0' COMMENT '7~9 user count',
  `freq_user_10_14` BIGINT NOT NULL DEFAULT '0' COMMENT '10~14 user count',
  `freq_user_15_19` BIGINT NOT NULL DEFAULT '0' COMMENT '15~19 user count',
  `freq_user_20_49` BIGINT NOT NULL DEFAULT '0' COMMENT '20~49 user count',
  `freq_user_50_99` BIGINT NOT NULL DEFAULT '0' COMMENT '50~99 user count',
  `freq_user_100_499` BIGINT NOT NULL DEFAULT '0' COMMENT '100~499 user count',
  `freq_user_over_500` BIGINT NOT NULL DEFAULT '0' COMMENT '500~ user count',
  PRIMARY KEY (`year`, `week`, `appkey`),
  INDEX `app_st_frequency_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Frequency Section User';


-- -----------------------------------------------------
-- Table `st_frequency_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_frequency_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `freq_user_1` BIGINT NOT NULL DEFAULT '0' COMMENT '1 user count',
  `freq_user_2` BIGINT NOT NULL DEFAULT '0' COMMENT '2 user count',
  `freq_user_3_4` BIGINT NOT NULL DEFAULT '0' COMMENT '3~4 user count',
  `freq_user_5_6` BIGINT NOT NULL DEFAULT '0' COMMENT '5~6 user count',
  `freq_user_7_9` BIGINT NOT NULL DEFAULT '0' COMMENT '7~9 user count',
  `freq_user_10_14` BIGINT NOT NULL DEFAULT '0' COMMENT '10~14 user count',
  `freq_user_15_19` BIGINT NOT NULL DEFAULT '0' COMMENT '15~19 user count',
  `freq_user_20_49` BIGINT NOT NULL DEFAULT '0' COMMENT '20~49 user count',
  `freq_user_50_99` BIGINT NOT NULL DEFAULT '0' COMMENT '50~99 user count',
  `freq_user_100_499` BIGINT NOT NULL DEFAULT '0' COMMENT '100~499 user count',
  `freq_user_over_500` BIGINT NOT NULL DEFAULT '0' COMMENT '500~ user count',
  PRIMARY KEY (`year`, `month`, `appkey`),
  INDEX `app_st_frequency_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Frequency Section User';


-- -----------------------------------------------------
-- Table `st_sessionlength_section_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_sessionlength_section_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `under_three_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '~3sec session count',
  `three_ten_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10sec session count',
  `ten_thirty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30sec session count',
  `thirty_sixty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '30~60sec session count',
  `one_three_min` BIGINT NOT NULL DEFAULT '0' COMMENT '1~3min session count',
  `three_ten_min` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10min session count',
  `ten_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30min session count',
  `over_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '30min~ session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`),
  INDEX `app_st_sessionlength_section_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Sessionlength Section Session';


-- -----------------------------------------------------
-- Table `st_sessionlength_section_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_sessionlength_section_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `under_three_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '~3sec session count',
  `three_ten_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10sec session count',
  `ten_thirty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30sec session count',
  `thirty_sixty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '30~60sec session count',
  `one_three_min` BIGINT NOT NULL DEFAULT '0' COMMENT '1~3min session count',
  `three_ten_min` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10min session count',
  `ten_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30min session count',
  `over_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '30min~ session count',
  PRIMARY KEY (`year`, `week`, `appkey`),
  INDEX `app_st_sessionlength_section_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Sessionlength Section Session';


-- -----------------------------------------------------
-- Table `st_sessionlength_section_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_sessionlength_section_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `under_three_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '~3sec session count',
  `three_ten_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10sec session count',
  `ten_thirty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30sec session count',
  `thirty_sixty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '30~60sec session count',
  `one_three_min` BIGINT NOT NULL DEFAULT '0' COMMENT '1~3min session count',
  `three_ten_min` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10min session count',
  `ten_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30min session count',
  `over_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '30min~ session count',
  PRIMARY KEY (`year`, `month`, `appkey`),
  INDEX `app_st_sessionlength_section_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Sessionlength Section Session';


-- -----------------------------------------------------
-- Table `st_country_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_country_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `country`),
  INDEX `app_st_country_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Country User Session';


-- -----------------------------------------------------
-- Table `st_country_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_country_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `country`),
  INDEX `app_st_country_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Country User Session';


-- -----------------------------------------------------
-- Table `st_country_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_country_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `country`),
  INDEX `app_st_country_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Country User Session';


-- -----------------------------------------------------
-- Table `st_language_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_language_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `language` VARCHAR(10) NOT NULL COMMENT 'language',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `language`),
  INDEX `app_st_language_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Language User Session';


-- -----------------------------------------------------
-- Table `st_language_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_language_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `language` VARCHAR(10) NOT NULL COMMENT 'language',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `language`),
  INDEX `app_st_language_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Language User Session';


-- -----------------------------------------------------
-- Table `st_language_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_language_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `language` VARCHAR(10) NOT NULL COMMENT 'language',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `language`),
  INDEX `app_st_language_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Language User Session';


-- -----------------------------------------------------
-- Table `st_appversion_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_appversion_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `appversion` VARCHAR(50) NOT NULL COMMENT 'app version',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `appversion`),
  INDEX `app_st_appversion_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Appversion User Session';


-- -----------------------------------------------------
-- Table `st_appversion_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_appversion_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `appversion` VARCHAR(50) NOT NULL COMMENT 'app version',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `appversion`),
  INDEX `app_st_appversion_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Appversion User Session';


-- -----------------------------------------------------
-- Table `st_appversion_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_appversion_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `appversion` VARCHAR(50) NOT NULL COMMENT 'app version',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `appversion`),
  INDEX `app_st_appversion_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Appversion User Session';


-- -----------------------------------------------------
-- Table `st_osversion_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_osversion_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `osversion` VARCHAR(50) NOT NULL COMMENT 'os version',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `osversion`),
  INDEX `app_st_osversion_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Osversion User Session';


-- -----------------------------------------------------
-- Table `st_osversion_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_osversion_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `osversion` VARCHAR(50) NOT NULL COMMENT 'os version',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `osversion`),
  INDEX `app_st_osversion_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Osversion User Session';


-- -----------------------------------------------------
-- Table `st_osversion_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_osversion_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `osversion` VARCHAR(50) NOT NULL COMMENT 'os version',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `osversion`),
  INDEX `app_st_osversion_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Osversion User Session';


-- -----------------------------------------------------
-- Table `st_resolution_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_resolution_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `resolution` VARCHAR(20) NOT NULL COMMENT 'resolution',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `resolution`),
  INDEX `app_st_resolution_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Resolution User Session';


-- -----------------------------------------------------
-- Table `st_resolution_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_resolution_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `resolution` VARCHAR(20) NOT NULL COMMENT 'resolution',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `resolution`),
  INDEX `app_st_resolution_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Resolution User Session';


-- -----------------------------------------------------
-- Table `st_resolution_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_resolution_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `resolution` VARCHAR(20) NOT NULL COMMENT 'resolution',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `resolution`),
  INDEX `app_st_resolution_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Resolution User Session';


-- -----------------------------------------------------
-- Table `st_device_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_device_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `device` VARCHAR(50) NOT NULL COMMENT 'device',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `device`),
  INDEX `app_st_device_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Device User Session';


-- -----------------------------------------------------
-- Table `st_device_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_device_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `device` VARCHAR(50) NOT NULL COMMENT 'device',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `device`),
  INDEX `app_st_device_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Device User Session';


-- -----------------------------------------------------
-- Table `st_device_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_device_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `device` VARCHAR(50) NOT NULL COMMENT 'device',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `device`),
  INDEX `app_st_device_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Device User Session';


-- -----------------------------------------------------
-- Table `cd_country_newuser_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_newuser_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `country`),
  INDEX `cd_country_newuser_day_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Country Newuser';


-- -----------------------------------------------------
-- Table `cd_country_newuser_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_newuser_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `week`, `appkey`, `country`),
  INDEX `cd_country_newuser_week_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Country Newuser';


-- -----------------------------------------------------
-- Table `cd_country_newuser_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_newuser_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `month`, `appkey`, `country`),
  INDEX `cd_country_newuser_month_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Country Newuser';


-- -----------------------------------------------------
-- Table `cd_country_session_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_session_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `country`),
  INDEX `app_cd_country_session_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Country Session';


-- -----------------------------------------------------
-- Table `cd_country_session_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_session_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `country`),
  INDEX `app_cd_country_session_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Country Session';


-- -----------------------------------------------------
-- Table `cd_country_session_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_session_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `country`),
  INDEX `app_cd_country_session_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Country Session';

-- -----------------------------------------------------
-- Table `cd_country_sessionlength_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_sessionlength_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `sessionlength` DOUBLE NOT NULL DEFAULT '0' COMMENT 'sessionlength median',
  `totaltime` BIGINT NOT NULL DEFAULT '0' COMMENT 'total time',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `country`),
  INDEX `app_cd_country_sessionlength_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Country Sessionlength';


-- -----------------------------------------------------
-- Table `cd_country_sessionlength_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_sessionlength_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `sessionlength` DOUBLE NOT NULL DEFAULT '0' COMMENT 'sessionlength median',
  `totaltime` BIGINT NOT NULL DEFAULT '0' COMMENT 'total time',
  PRIMARY KEY (`year`, `week`, `appkey`, `country`),
  INDEX `app_cd_country_sessionlength_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Country Sessionlength';


-- -----------------------------------------------------
-- Table `cd_country_sessionlength_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_sessionlength_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `sessionlength` DOUBLE NOT NULL DEFAULT '0' COMMENT 'sessionlength median',
  `totaltime` BIGINT NOT NULL DEFAULT '0' COMMENT 'total time',
  PRIMARY KEY (`year`, `month`, `appkey`, `country`),
  INDEX `app_cd_country_sessionlength_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Country Sessionlength';


-- -----------------------------------------------------
-- Table `cd_country_pageview_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_pageview_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `pageview` BIGINT NOT NULL DEFAULT '0' COMMENT 'pageview count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `country`),
  INDEX `app_cd_country_pageview_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Country Pageview';


-- -----------------------------------------------------
-- Table `cd_country_pageview_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_pageview_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `pageview` BIGINT NOT NULL DEFAULT '0' COMMENT 'pageview count',
  PRIMARY KEY (`year`, `week`, `appkey`, `country`),
  INDEX `app_cd_country_pageview_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Country Pageview';


-- -----------------------------------------------------
-- Table `cd_country_pageview_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_pageview_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `pageview` BIGINT NOT NULL DEFAULT '0' COMMENT 'pageview count',
  PRIMARY KEY (`year`, `month`, `appkey`, `country`),
  INDEX `app_cd_country_pageview_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Country Pageview';


-- -----------------------------------------------------
-- Table `cd_country_time_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_time_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `zero_session` BIGINT NOT NULL DEFAULT '0' COMMENT '0hour session',
  `one_session` BIGINT NOT NULL DEFAULT '0' COMMENT '1hour session',
  `two_session` BIGINT NOT NULL DEFAULT '0' COMMENT '2hour session',
  `three_session` BIGINT NOT NULL DEFAULT '0' COMMENT '3hour session',
  `four_session` BIGINT NOT NULL DEFAULT '0' COMMENT '4hour session',
  `five_session` BIGINT NOT NULL DEFAULT '0' COMMENT '5hour session',
  `six_session` BIGINT NOT NULL DEFAULT '0' COMMENT '6hour session',
  `seven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '7hour session',
  `eight_session` BIGINT NOT NULL DEFAULT '0' COMMENT '8hour session',
  `nine_session` BIGINT NOT NULL DEFAULT '0' COMMENT '9hour session',
  `ten_session` BIGINT NOT NULL DEFAULT '0' COMMENT '10hour session',
  `eleven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '11hour session',
  `twelve_session` BIGINT NOT NULL DEFAULT '0' COMMENT '12hour session',
  `thirteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '13hour session',
  `fourteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '14hour session',
  `fifteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '15hour session',
  `sixteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '16hour session',
  `seventeen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '17hour session',
  `eighteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '18hour session',
  `nineteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '19hour session',
  `twenty_session` BIGINT NOT NULL DEFAULT '0' COMMENT '20hour session',
  `twentyone_session` BIGINT NOT NULL DEFAULT '0' COMMENT '21hour session',
  `twentytwo_session` BIGINT NOT NULL DEFAULT '0' COMMENT '22hour session',
  `twentythree_session` BIGINT NOT NULL DEFAULT '0' COMMENT '23hour session',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `country`),
  INDEX `app_cd_country_time_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Country Hour Session';


-- -----------------------------------------------------
-- Table `cd_country_time_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_time_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `zero_session` BIGINT NOT NULL DEFAULT '0' COMMENT '0hour session',
  `one_session` BIGINT NOT NULL DEFAULT '0' COMMENT '1hour session',
  `two_session` BIGINT NOT NULL DEFAULT '0' COMMENT '2hour session',
  `three_session` BIGINT NOT NULL DEFAULT '0' COMMENT '3hour session',
  `four_session` BIGINT NOT NULL DEFAULT '0' COMMENT '4hour session',
  `five_session` BIGINT NOT NULL DEFAULT '0' COMMENT '5hour session',
  `six_session` BIGINT NOT NULL DEFAULT '0' COMMENT '6hour session',
  `seven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '7hour session',
  `eight_session` BIGINT NOT NULL DEFAULT '0' COMMENT '8hour session',
  `nine_session` BIGINT NOT NULL DEFAULT '0' COMMENT '9hour session',
  `ten_session` BIGINT NOT NULL DEFAULT '0' COMMENT '10hour session',
  `eleven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '11hour session',
  `twelve_session` BIGINT NOT NULL DEFAULT '0' COMMENT '12hour session',
  `thirteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '13hour session',
  `fourteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '14hour session',
  `fifteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '15hour session',
  `sixteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '16hour session',
  `seventeen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '17hour session',
  `eighteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '18hour session',
  `nineteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '19hour session',
  `twenty_session` BIGINT NOT NULL DEFAULT '0' COMMENT '20hour session',
  `twentyone_session` BIGINT NOT NULL DEFAULT '0' COMMENT '21hour session',
  `twentytwo_session` BIGINT NOT NULL DEFAULT '0' COMMENT '22hour session',
  `twentythree_session` BIGINT NOT NULL DEFAULT '0' COMMENT '23hour session',
  PRIMARY KEY (`year`, `week`, `appkey`, `country`),
  INDEX `app_cd_country_time_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Country Hour Session';


-- -----------------------------------------------------
-- Table `cd_country_time_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_time_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `zero_session` BIGINT NOT NULL DEFAULT '0' COMMENT '0hour session',
  `one_session` BIGINT NOT NULL DEFAULT '0' COMMENT '1hour session',
  `two_session` BIGINT NOT NULL DEFAULT '0' COMMENT '2hour session',
  `three_session` BIGINT NOT NULL DEFAULT '0' COMMENT '3hour session',
  `four_session` BIGINT NOT NULL DEFAULT '0' COMMENT '4hour session',
  `five_session` BIGINT NOT NULL DEFAULT '0' COMMENT '5hour session',
  `six_session` BIGINT NOT NULL DEFAULT '0' COMMENT '6hour session',
  `seven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '7hour session',
  `eight_session` BIGINT NOT NULL DEFAULT '0' COMMENT '8hour session',
  `nine_session` BIGINT NOT NULL DEFAULT '0' COMMENT '9hour session',
  `ten_session` BIGINT NOT NULL DEFAULT '0' COMMENT '10hour session',
  `eleven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '11hour session',
  `twelve_session` BIGINT NOT NULL DEFAULT '0' COMMENT '12hour session',
  `thirteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '13hour session',
  `fourteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '14hour session',
  `fifteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '15hour session',
  `sixteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '16hour session',
  `seventeen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '17hour session',
  `eighteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '18hour session',
  `nineteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '19hour session',
  `twenty_session` BIGINT NOT NULL DEFAULT '0' COMMENT '20hour session',
  `twentyone_session` BIGINT NOT NULL DEFAULT '0' COMMENT '21hour session',
  `twentytwo_session` BIGINT NOT NULL DEFAULT '0' COMMENT '22hour session',
  `twentythree_session` BIGINT NOT NULL DEFAULT '0' COMMENT '23hour session',
  PRIMARY KEY (`year`, `month`, `appkey`, `country`),
  INDEX `app_cd_country_time_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Country Hour Session';


-- -----------------------------------------------------
-- Table `cd_country_sessionlength_section_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_sessionlength_section_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `under_three_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '~3sec session',
  `three_ten_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10sec session',
  `ten_thirty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30sec session',
  `thirty_sixty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '30~60sec session',
  `one_three_min` BIGINT NOT NULL DEFAULT '0' COMMENT '1~3min session',
  `three_ten_min` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10min session',
  `ten_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30min session',
  `over_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '30min~ session',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `country`),
  INDEX `app_cd_country_sessionlength_section_day_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Country Sessionlength Section Session';


-- -----------------------------------------------------
-- Table `cd_country_sessionlength_section_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_sessionlength_section_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `under_three_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '~3sec session',
  `three_ten_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10sec session',
  `ten_thirty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30sec session',
  `thirty_sixty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '30~60sec session',
  `one_three_min` BIGINT NOT NULL DEFAULT '0' COMMENT '1~3min session',
  `three_ten_min` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10min session',
  `ten_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30min session',
  `over_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '30min~ session',
  PRIMARY KEY (`year`, `week`, `appkey`, `country`),
  INDEX `app_cd_country_sessionlength_section_week_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Country Sesionlength Section Session';


-- -----------------------------------------------------
-- Table `cd_country_sessionlength_section_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cd_country_sessionlength_section_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `under_three_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '~3sec session',
  `three_ten_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10sec session',
  `ten_thirty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30sec session',
  `thirty_sixty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '30~60sec session',
  `one_three_min` BIGINT NOT NULL DEFAULT '0' COMMENT '1~3min session',
  `three_ten_min` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10min session',
  `ten_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30min session',
  `over_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '30min~ session',
  PRIMARY KEY (`year`, `month`, `appkey`, `country`),
  INDEX `app_cd_country_sessionlength_section_month_fk_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Country Sessionlength Section Session';


-- -----------------------------------------------------
-- Table `cp_compo_newuser_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_newuser_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_newuser_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_newuser_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Newuser';


-- -----------------------------------------------------
-- Table `cp_compo_newuser_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_newuser_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_newuser_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_newuser_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Newuser';


-- -----------------------------------------------------
-- Table `cp_compo_newuser_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_newuser_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_newuser_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_newuser_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Newuser';


-- -----------------------------------------------------
-- Table `cp_compo_user_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_user_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_user_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_user_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Activeuser';


-- -----------------------------------------------------
-- Table `cp_compo_user_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_user_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_user_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_user_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Activeuser';


-- -----------------------------------------------------
-- Table `cp_compo_user_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_user_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_user_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_user_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Activeuser';


-- -----------------------------------------------------
-- Table `cp_compo_session_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_session_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_session_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_session_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Session';


-- -----------------------------------------------------
-- Table `cp_compo_session_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_session_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_session_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_session_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Session';


-- -----------------------------------------------------
-- Table `cp_compo_session_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_session_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_session_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_session_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Session';


-- -----------------------------------------------------
-- Table `cp_compo_time_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_time_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `zero_session` BIGINT NOT NULL DEFAULT '0' COMMENT '0hour session count',
  `one_session` BIGINT NOT NULL DEFAULT '0' COMMENT '1hour session count',
  `two_session` BIGINT NOT NULL DEFAULT '0' COMMENT '2hour session count',
  `three_session` BIGINT NOT NULL DEFAULT '0' COMMENT '3hour session count',
  `four_session` BIGINT NOT NULL DEFAULT '0' COMMENT '4hour session count',
  `five_session` BIGINT NOT NULL DEFAULT '0' COMMENT '5hour session count',
  `six_session` BIGINT NOT NULL DEFAULT '0' COMMENT '6hour session count',
  `seven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '7hour session count',
  `eight_session` BIGINT NOT NULL DEFAULT '0' COMMENT '8hour session count',
  `nine_session` BIGINT NOT NULL DEFAULT '0' COMMENT '9hour session count',
  `ten_session` BIGINT NOT NULL DEFAULT '0' COMMENT '10hour session count',
  `eleven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '11hour session count',
  `twelve_session` BIGINT NOT NULL DEFAULT '0' COMMENT '12hour session count',
  `thirteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '13hour session count',
  `fourteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '14hour session count',
  `fifteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '15hour session count',
  `sixteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '16hour session count',
  `seventeen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '17hour session count',
  `eighteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '18hour session count',
  `nineteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '19hour session count',
  `twenty_session` BIGINT NOT NULL DEFAULT '0' COMMENT '20hour session count',
  `twentyone_session` BIGINT NOT NULL DEFAULT '0' COMMENT '21hour session count',
  `twentytwo_session` BIGINT NOT NULL DEFAULT '0' COMMENT '22hour session count',
  `twentythree_session` BIGINT NOT NULL DEFAULT '0' COMMENT '23hour session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_time_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_time_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Hour Session';


-- -----------------------------------------------------
-- Table `cp_compo_time_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_time_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `zero_session` BIGINT NOT NULL DEFAULT '0' COMMENT '0hour session count',
  `one_session` BIGINT NOT NULL DEFAULT '0' COMMENT '1hour session count',
  `two_session` BIGINT NOT NULL DEFAULT '0' COMMENT '2hour session count',
  `three_session` BIGINT NOT NULL DEFAULT '0' COMMENT '3hour session count',
  `four_session` BIGINT NOT NULL DEFAULT '0' COMMENT '4hour session count',
  `five_session` BIGINT NOT NULL DEFAULT '0' COMMENT '5hour session count',
  `six_session` BIGINT NOT NULL DEFAULT '0' COMMENT '6hour session count',
  `seven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '7hour session count',
  `eight_session` BIGINT NOT NULL DEFAULT '0' COMMENT '8hour session count',
  `nine_session` BIGINT NOT NULL DEFAULT '0' COMMENT '9hour session count',
  `ten_session` BIGINT NOT NULL DEFAULT '0' COMMENT '10hour session count',
  `eleven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '11hour session count',
  `twelve_session` BIGINT NOT NULL DEFAULT '0' COMMENT '12hour session count',
  `thirteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '13hour session count',
  `fourteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '14hour session count',
  `fifteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '15hour session count',
  `sixteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '16hour session count',
  `seventeen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '17hour session count',
  `eighteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '18hour session count',
  `nineteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '19hour session count',
  `twenty_session` BIGINT NOT NULL DEFAULT '0' COMMENT '20hour session count',
  `twentyone_session` BIGINT NOT NULL DEFAULT '0' COMMENT '21hour session count',
  `twentytwo_session` BIGINT NOT NULL DEFAULT '0' COMMENT '22hour session count',
  `twentythree_session` BIGINT NOT NULL DEFAULT '0' COMMENT '23hour session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_time_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_time_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Hour Session';


-- -----------------------------------------------------
-- Table `cp_compo_time_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_time_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `zero_session` BIGINT NOT NULL DEFAULT '0' COMMENT '0hour session count',
  `one_session` BIGINT NOT NULL DEFAULT '0' COMMENT '1hour session count',
  `two_session` BIGINT NOT NULL DEFAULT '0' COMMENT '2hour session count',
  `three_session` BIGINT NOT NULL DEFAULT '0' COMMENT '3hour session count',
  `four_session` BIGINT NOT NULL DEFAULT '0' COMMENT '4hour session count',
  `five_session` BIGINT NOT NULL DEFAULT '0' COMMENT '5hour session count',
  `six_session` BIGINT NOT NULL DEFAULT '0' COMMENT '6hour session count',
  `seven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '7hour session count',
  `eight_session` BIGINT NOT NULL DEFAULT '0' COMMENT '8hour session count',
  `nine_session` BIGINT NOT NULL DEFAULT '0' COMMENT '9hour session count',
  `ten_session` BIGINT NOT NULL DEFAULT '0' COMMENT '10hour session count',
  `eleven_session` BIGINT NOT NULL DEFAULT '0' COMMENT '11hour session count',
  `twelve_session` BIGINT NOT NULL DEFAULT '0' COMMENT '12hour session count',
  `thirteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '13hour session count',
  `fourteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '14hour session count',
  `fifteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '15hour session count',
  `sixteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '16hour session count',
  `seventeen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '17hour session count',
  `eighteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '18hour session count',
  `nineteen_session` BIGINT NOT NULL DEFAULT '0' COMMENT '19hour session count',
  `twenty_session` BIGINT NOT NULL DEFAULT '0' COMMENT '20hour session count',
  `twentyone_session` BIGINT NOT NULL DEFAULT '0' COMMENT '21hour session count',
  `twentytwo_session` BIGINT NOT NULL DEFAULT '0' COMMENT '22hour session count',
  `twentythree_session` BIGINT NOT NULL DEFAULT '0' COMMENT '23hour session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_time_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_time_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Hour Session';


-- -----------------------------------------------------
-- Table `cp_compo_frequency_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_frequency_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `freq_user_1` BIGINT NOT NULL DEFAULT '0' COMMENT '1 user count',
  `freq_user_2` BIGINT NOT NULL DEFAULT '0' COMMENT '2 user count',
  `freq_user_3_4` BIGINT NOT NULL DEFAULT '0' COMMENT '3~4 user count',
  `freq_user_5_6` BIGINT NOT NULL DEFAULT '0' COMMENT '5~6 user count',
  `freq_user_7_9` BIGINT NOT NULL DEFAULT '0' COMMENT '7~9 user count',
  `freq_user_10_14` BIGINT NOT NULL DEFAULT '0' COMMENT '10~14 user count',
  `freq_user_15_19` BIGINT NOT NULL DEFAULT '0' COMMENT '15~19 user count',
  `freq_user_20_49` BIGINT NOT NULL DEFAULT '0' COMMENT '20~49 user count',
  `freq_user_50_99` BIGINT NOT NULL DEFAULT '0' COMMENT '50~99 user count',
  `freq_user_100_499` BIGINT NOT NULL DEFAULT '0' COMMENT '100~499 user count',
  `freq_user_over_500` BIGINT NOT NULL DEFAULT '0' COMMENT '500~ user count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_frequency_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_frequency_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Frequency User';


-- -----------------------------------------------------
-- Table `cp_compo_frequency_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_frequency_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `freq_user_1` BIGINT NOT NULL DEFAULT '0' COMMENT '1 user count',
  `freq_user_2` BIGINT NOT NULL DEFAULT '0' COMMENT '2 user count',
  `freq_user_3_4` BIGINT NOT NULL DEFAULT '0' COMMENT '3~4 user count',
  `freq_user_5_6` BIGINT NOT NULL DEFAULT '0' COMMENT '5~6 user count',
  `freq_user_7_9` BIGINT NOT NULL DEFAULT '0' COMMENT '7~9 user count',
  `freq_user_10_14` BIGINT NOT NULL DEFAULT '0' COMMENT '10~14 user count',
  `freq_user_15_19` BIGINT NOT NULL DEFAULT '0' COMMENT '15~19 user count',
  `freq_user_20_49` BIGINT NOT NULL DEFAULT '0' COMMENT '20~49 user count',
  `freq_user_50_99` BIGINT NOT NULL DEFAULT '0' COMMENT '50~99 user count',
  `freq_user_100_499` BIGINT NOT NULL DEFAULT '0' COMMENT '100~499 user count',
  `freq_user_over_500` BIGINT NOT NULL DEFAULT '0' COMMENT '500~ user count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_frequency_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_frequency_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Frequency User';


-- -----------------------------------------------------
-- Table `cp_compo_frequency_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_frequency_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `freq_user_1` BIGINT NOT NULL DEFAULT '0' COMMENT '1 user count',
  `freq_user_2` BIGINT NOT NULL DEFAULT '0' COMMENT '2 user count',
  `freq_user_3_4` BIGINT NOT NULL DEFAULT '0' COMMENT '3~4 user count',
  `freq_user_5_6` BIGINT NOT NULL DEFAULT '0' COMMENT '5~6 user count',
  `freq_user_7_9` BIGINT NOT NULL DEFAULT '0' COMMENT '7~9 user count',
  `freq_user_10_14` BIGINT NOT NULL DEFAULT '0' COMMENT '10~14 user count',
  `freq_user_15_19` BIGINT NOT NULL DEFAULT '0' COMMENT '15~19 user count',
  `freq_user_20_49` BIGINT NOT NULL DEFAULT '0' COMMENT '20~49 user count',
  `freq_user_50_99` BIGINT NOT NULL DEFAULT '0' COMMENT '50~99 user count',
  `freq_user_100_499` BIGINT NOT NULL DEFAULT '0' COMMENT '100~499 user count',
  `freq_user_over_500` BIGINT NOT NULL DEFAULT '0' COMMENT '500~ user count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`),
  INDEX `app_cp_compo_frequency_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_frequency_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Frequency User';


-- -----------------------------------------------------
-- Table `cp_compo_country_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_country_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`, `country`),
  INDEX `app_cp_compo_country_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_country_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Country User Session';


-- -----------------------------------------------------
-- Table `cp_compo_country_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_country_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`, `country`),
  INDEX `app_cp_compo_country_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_country_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Country User Session';


-- -----------------------------------------------------
-- Table `cp_compo_country_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_country_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `country` VARCHAR(10) NOT NULL COMMENT 'country',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`, `country`),
  INDEX `app_cp_compo_country_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_country_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Country User Session';


-- -----------------------------------------------------
-- Table `cp_compo_language_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_language_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `language` VARCHAR(10) NOT NULL COMMENT 'language',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`, `language`),
  INDEX `app_cp_compo_language_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_language_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Language User Session';


-- -----------------------------------------------------
-- Table `cp_compo_language_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_language_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `language` VARCHAR(10) NOT NULL COMMENT 'language',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`, `language`),
  INDEX `app_cp_compo_language_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_language_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Language User Session';


-- -----------------------------------------------------
-- Table `cp_compo_language_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_language_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `language` VARCHAR(10) NOT NULL COMMENT 'language',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`, `language`),
  INDEX `app_cp_compo_language_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_language_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Language User Session';


-- -----------------------------------------------------
-- Table `cp_compo_appversion_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_appversion_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `appversion` VARCHAR(50) NOT NULL COMMENT 'app version',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`, `appversion`),
  INDEX `app_cp_compo_appversion_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_appversion_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Appversion User Session';


-- -----------------------------------------------------
-- Table `cp_compo_appversion_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_appversion_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `appversion` VARCHAR(50) NOT NULL COMMENT 'app version',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`, `appversion`),
  INDEX `app_cp_compo_appversion_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_appversion_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Appversion User Session';


-- -----------------------------------------------------
-- Table `cp_compo_appversion_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_appversion_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `appversion` VARCHAR(50) NOT NULL COMMENT 'app version',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`, `appversion`),
  INDEX `app_cp_compo_appversion_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_appversion_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Appversion User Session';


-- -----------------------------------------------------
-- Table `cp_compo_osversion_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_osversion_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `osversion` VARCHAR(50) NOT NULL COMMENT 'os version',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`, `osversion`),
  INDEX `app_cp_compo_osversion_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_osversion_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Osversion User Session';


-- -----------------------------------------------------
-- Table `cp_compo_osversion_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_osversion_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `osversion` VARCHAR(50) NOT NULL COMMENT 'os version',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`, `osversion`),
  INDEX `app_cp_compo_osversion_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_osversion_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Osversion User Session';


-- -----------------------------------------------------
-- Table `cp_compo_osversion_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_osversion_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `osversion` VARCHAR(50) NOT NULL COMMENT 'os version',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`, `osversion`),
  INDEX `app_cp_compo_osversion_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_osversion_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Osversion User Session';


-- -----------------------------------------------------
-- Table `cp_compo_resolution_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_resolution_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `resolution` VARCHAR(20) NOT NULL COMMENT 'resolution',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`, `resolution`),
  INDEX `app_cp_compo_resolution_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_resolution_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Resolution User Session';


-- -----------------------------------------------------
-- Table `cp_compo_resolution_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_resolution_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `resolution` VARCHAR(20) NOT NULL COMMENT 'resolution',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`, `resolution`),
  INDEX `app_cp_compo_resolution_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_resolution_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Resolution User Session';


-- -----------------------------------------------------
-- Table `cp_compo_resolution_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_resolution_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `resolution` VARCHAR(20) NOT NULL COMMENT 'resolution',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`, `resolution`),
  INDEX `app_cp_compo_resolution_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_resolution_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Monthly Component Resolution User Session';


-- -----------------------------------------------------
-- Table `cp_compo_device_day`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_device_day` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `device` VARCHAR(50) NOT NULL COMMENT 'device',
  `date` DATE NOT NULL COMMENT 'date',
  `dayofweek` INT NOT NULL COMMENT 'day of week',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `appkey`, `componentkey`, `device`),
  INDEX `app_cp_compo_device_day_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_device_day_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Daily Component Device User Session';


-- -----------------------------------------------------
-- Table `cp_compo_device_week`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_device_week` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `week` VARCHAR(2) NOT NULL COMMENT 'week of year',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `device` VARCHAR(50) NOT NULL COMMENT 'device',
  `fromdate` DATE NOT NULL COMMENT 'week start date',
  `todate` DATE NOT NULL COMMENT 'week end date',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `week`, `appkey`, `componentkey`, `device`),
  INDEX `app_cp_compo_device_week_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_device_week_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Weekly Component Device User Session';


-- -----------------------------------------------------
-- Table `cp_compo_device_month`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cp_compo_device_month` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `componentkey` VARCHAR(20) NOT NULL COMMENT 'component key',
  `device` VARCHAR(50) NOT NULL COMMENT 'device',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `appkey`, `componentkey`, `device`),
  INDEX `app_cp_compo_device_month_fk_idx` (`appkey` ASC),
  INDEX `component_cp_compo_device_month_fk_idx` (`componentkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Montly Component Device User Session';


-- -----------------------------------------------------
-- Table `st_newuser_hour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_newuser_hour` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `hour` VARCHAR(2) NOT NULL COMMENT 'hour',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `datehour` DATETIME NOT NULL COMMENT 'date hour',
  `newuser` BIGINT NOT NULL DEFAULT '0' COMMENT 'newuser count',
  PRIMARY KEY (`year`, `month`, `day`, `hour`, `appkey`),
  INDEX `st_newuser_hour_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Hourly Newuser';


-- -----------------------------------------------------
-- Table `st_user_hour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_user_hour` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `hour` VARCHAR(2) NOT NULL COMMENT 'hour',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `datehour` DATETIME NOT NULL COMMENT 'date hour',
  `user` BIGINT NOT NULL DEFAULT '0' COMMENT 'user count',
  PRIMARY KEY (`year`, `month`, `day`, `hour`, `appkey`),
  INDEX `st_user_hour_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Hourly Activeuser';


-- -----------------------------------------------------
-- Table `st_session_hour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_session_hour` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `hour` VARCHAR(2) NOT NULL COMMENT 'hour',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `datehour` DATETIME NOT NULL COMMENT 'date hour',
  `session` BIGINT NOT NULL DEFAULT '0' COMMENT 'session count',
  PRIMARY KEY (`year`, `month`, `day`, `hour`, `appkey`),
  INDEX `st_session_hour_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Hourly Session';


-- -----------------------------------------------------
-- Table `st_sessionlength_hour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_sessionlength_hour` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `hour` VARCHAR(2) NOT NULL COMMENT 'hour',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `datehour` DATETIME NOT NULL COMMENT 'date hour',
  `sessionlength` DOUBLE NOT NULL DEFAULT '0' COMMENT 'sessionlength median',
  `totaltime` BIGINT NOT NULL DEFAULT '0' COMMENT 'total time',
  PRIMARY KEY (`year`, `month`, `day`, `hour`, `appkey`),
  INDEX `st_sessionlength_hour_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Hourly Sessionlength';


-- -----------------------------------------------------
-- Table `st_pageview_hour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_pageview_hour` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `hour` VARCHAR(2) NOT NULL COMMENT 'hour',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `datehour` DATETIME NOT NULL COMMENT 'date hour',
  `pageview` BIGINT NOT NULL DEFAULT '0' COMMENT 'pageview count',
  PRIMARY KEY (`year`, `month`, `day`, `hour`, `appkey`),
  INDEX `st_pageview_hour_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Hourly Pageview';


-- -----------------------------------------------------
-- Table `st_frequency_hour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_frequency_hour` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `hour` VARCHAR(2) NOT NULL COMMENT 'hour',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `datehour` DATETIME NOT NULL COMMENT 'date hour',
  `freq_user_1` BIGINT NOT NULL DEFAULT '0' COMMENT '1 user',
  `freq_user_2` BIGINT NOT NULL DEFAULT '0' COMMENT '2 user',
  `freq_user_3_4` BIGINT NOT NULL DEFAULT '0' COMMENT '3~4 user',
  `freq_user_5_6` BIGINT NOT NULL DEFAULT '0' COMMENT '5~6 user',
  `freq_user_7_9` BIGINT NOT NULL DEFAULT '0' COMMENT '7~9 user',
  `freq_user_10_14` BIGINT NOT NULL DEFAULT '0' COMMENT '10~14 user',
  `freq_user_15_19` BIGINT NOT NULL DEFAULT '0' COMMENT '15~19 user',
  `freq_user_20_49` BIGINT NOT NULL DEFAULT '0' COMMENT '20~49 user',
  `freq_user_50_99` BIGINT NOT NULL DEFAULT '0' COMMENT '50~99 user',
  `freq_user_100_499` BIGINT NOT NULL DEFAULT '0' COMMENT '100~499 user',
  `freq_user_over_500` BIGINT NOT NULL DEFAULT '0' COMMENT '500~ user',
  PRIMARY KEY (`year`, `month`, `day`, `hour`, `appkey`),
  INDEX `st_frequency_hour_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Hourly Frequency User';


-- -----------------------------------------------------
-- Table `st_sessionlength_section_hour`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `st_sessionlength_section_hour` (
  `year` VARCHAR(4) NOT NULL COMMENT 'year',
  `month` VARCHAR(2) NOT NULL COMMENT 'month',
  `day` VARCHAR(2) NOT NULL COMMENT 'day',
  `hour` VARCHAR(2) NOT NULL COMMENT 'hour',
  `appkey` VARCHAR(20) NOT NULL COMMENT 'app key',
  `datehour` DATETIME NOT NULL COMMENT 'date hour',
  `under_three_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '~3sec session',
  `three_ten_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10sec session',
  `ten_thirty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30sec session',
  `thirty_sixty_sec` BIGINT NOT NULL DEFAULT '0' COMMENT '30~60sec session',
  `one_three_min` BIGINT NOT NULL DEFAULT '0' COMMENT '1~3min session',
  `three_ten_min` BIGINT NOT NULL DEFAULT '0' COMMENT '3~10min session',
  `ten_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '10~30min session',
  `over_thirty_min` BIGINT NOT NULL DEFAULT '0' COMMENT '30min~ session',
  PRIMARY KEY (`year`, `month`, `day`, `hour`, `appkey`),
  INDEX `st_sessionlength_section_hour_app_fk1_idx` (`appkey` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Hourly Sessionlength Section Session';


-- -----------------------------------------------------
-- procedure create_sequence
-- -----------------------------------------------------

DELIMITER $$
CREATE DEFINER=`ossuser`@`localhost` PROCEDURE `create_sequence`(IN the_name text)
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN 
   -- DELETE FROM sequence WHERE name=the_name; 
    INSERT INTO sequence VALUES (the_name, 0); 
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function nextval
-- -----------------------------------------------------

DELIMITER $$
CREATE DEFINER=`ossuser`@`localhost` FUNCTION `nextval`(the_name varchar(32)) RETURNS bigint(20) unsigned
    MODIFIES SQL DATA
    DETERMINISTIC
BEGIN 
    DECLARE 
      ret BIGINT UNSIGNED; 
      UPDATE sequence SET currval=currval+1 WHERE name=the_name; 
      SELECT currval INTO ret FROM sequence WHERE name=the_name limit 1; 
      RETURN ret; 
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function newAppkey
-- -----------------------------------------------------

DELIMITER $$
CREATE DEFINER=`ossuser`@`localhost` FUNCTION `newAppkey`() RETURNS varchar(20) CHARSET utf8
    DETERMINISTIC
BEGIN 
  DECLARE 
    ret varchar(20); 
    SELECT concat('fin',nextval('seq_appkey')) 
      INTO ret 
      FROM ( SELECT str_to_date(now(),'%Y-%m-%d') as datestr ) a; 
    RETURN ret; 
END$$

DELIMITER ;

-- -----------------------------------------------------
-- function newComponentkey
-- -----------------------------------------------------

DELIMITER $$
CREATE DEFINER=`ossuser`@`localhost` FUNCTION `newComponentkey`() RETURNS varchar(20) CHARSET utf8
    DETERMINISTIC
BEGIN 
  DECLARE 
    ret varchar(20); 
    SELECT concat('cmp',nextval('seq_componentkey')) 
      INTO ret 
      FROM ( SELECT str_to_date(now(),'%Y-%m-%d') as datestr ) a; 
    RETURN ret; 
END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
