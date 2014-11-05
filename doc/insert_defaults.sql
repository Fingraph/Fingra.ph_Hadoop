
-- -----------------------------------------------------
-- insert numbers_small
-- -----------------------------------------------------
INSERT INTO numbers_small VALUES (0),(1),(2),(3),(4),(5),(6),(7),(8),(9);


-- -----------------------------------------------------
-- insert numbers
-- -----------------------------------------------------
INSERT INTO numbers
 SELECT thousands.number * 1000 + hundreds.number * 100 + tens.number * 10 + ones.number
 FROM numbers_small thousands, numbers_small hundreds, numbers_small tens, numbers_small ones
 LIMIT 1000000;


-- -----------------------------------------------------
-- insert dates_dimension
-- -----------------------------------------------------
INSERT INTO dates_dimension (date_id, date)
 SELECT number, DATE_ADD( '2014-01-01', INTERVAL number DAY )
 FROM numbers
 WHERE DATE_ADD( '2014-01-01', INTERVAL number DAY ) BETWEEN '2014-01-01' AND '2024-12-31'
 ORDER BY number;


-- -----------------------------------------------------
-- update dates_dimension
-- -----------------------------------------------------
UPDATE dates_dimension SET
 day             = DATE_FORMAT( date, "%W" ),
 day_of_week     = DAYOFWEEK(date),
 week_day        = WEEKDAY(date),
 day_of_month    = DATE_FORMAT( date, "%d" ),
 day_of_year     = DATE_FORMAT( date, "%j" ),
 previous_day    = DATE_ADD(date, INTERVAL -1 DAY),
 next_day        = DATE_ADD(date, INTERVAL 1 DAY),
 weekend         = IF( DATE_FORMAT( date, "%W" ) IN ('Saturday','Sunday'), 'Weekend', 'Weekday'),
 week_of_year    = DATE_FORMAT( date, "%v" ),
 week_year       = DATE_FORMAT( date, "%x" ),
 month           = DATE_FORMAT( date, "%M"),
 month_of_year   = DATE_FORMAT( date, "%m"),
 quarter_of_year = QUARTER(date),
 year            = DATE_FORMAT( date, "%Y" );


-- -----------------------------------------------------
-- insert times_dimension
-- -----------------------------------------------------
INSERT INTO times_dimension (time_id, time, hour) VALUES
 ('0', '00:00:00', '00'),
 ('1', '01:00:00', '01'),
 ('2', '02:00:00', '02'),
 ('3', '03:00:00', '03'),
 ('4', '04:00:00', '04'),
 ('5', '05:00:00', '05'),
 ('6', '06:00:00', '06'),
 ('7', '07:00:00', '07'),
 ('8', '08:00:00', '08'),
 ('9', '09:00:00', '09'),
 ('10', '10:00:00', '10'),
 ('11', '11:00:00', '11'),
 ('12', '12:00:00', '12'),
 ('13', '13:00:00', '13'),
 ('14', '14:00:00', '14'),
 ('15', '15:00:00', '15'),
 ('16', '16:00:00', '16'),
 ('17', '17:00:00', '17'),
 ('18', '18:00:00', '18'),
 ('19', '19:00:00', '19'),
 ('20', '20:00:00', '20'),
 ('21', '21:00:00', '21'),
 ('22', '22:00:00', '22'),
 ('23', '23:00:00', '23');


-- -----------------------------------------------------
-- insert category
-- -----------------------------------------------------
INSERT INTO category (categoryid, categoryname) VALUES
 (1, 'Books'), (2, 'Business'), (3, 'Education'), (4, 'Entertainment'),
 (5, 'Finance'), (6, 'Games'), (7, 'Health & Fitness'), (8, 'Lifestyle'),
 (9, 'Medical'), (10, 'Music'), (11, 'Navigation'), (12, 'News'),
 (13, 'Photo & Video'), (14, 'Productivity'), (15, 'Reference'), (16, 'Social Networking'),
 (17, 'Sports'), (18, 'Travel'), (19, 'Utilities'), (20, 'Weather'),
 (21, 'Widget');


-- -----------------------------------------------------
-- insert sequence
-- -----------------------------------------------------
INSERT INTO sequence (name, currval) VALUES
 ('seq_appkey', '0'),
 ('seq_componentkey', '0');

