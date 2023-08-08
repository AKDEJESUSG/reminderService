--data for testing
INSERT INTO tbl_reminder (message, created, updated, time_unit, custom_time, schedule, active)
VALUES ('test 1', CURRENT_TIMESTAMP, null, 'WEEKEND', null, '08:00:00', true);
INSERT INTO tbl_reminder (message, created, updated, time_unit, custom_time, schedule, active)
VALUES ('test 2', CURRENT_TIMESTAMP, null, 'DAILY', null, '12:00:00', true);
INSERT INTO tbl_reminder (message, created, updated, time_unit, custom_time, schedule, active)
VALUES ('test 3', CURRENT_TIMESTAMP, null, 'WEEKDAYS', null, '06:00:00', true);
INSERT INTO tbl_reminder (message, created, updated, time_unit, custom_time, schedule, active)
VALUES ('test 4', CURRENT_TIMESTAMP, null, 'CUSTOM', 'MONDAY,TUESDAY', '15:00:00', true);
INSERT INTO tbl_reminder (message, created, updated, time_unit, custom_time, schedule, active)
VALUES ('test 5', CURRENT_TIMESTAMP, null, 'CUSTOM', 'SUNDAY', '04:00:00', true);
INSERT INTO tbl_reminder (message, created, updated, time_unit, custom_time, schedule, active)
VALUES ('test 6', CURRENT_TIMESTAMP , null, 'DATE', '31/12/2025', '13:00:00', true);