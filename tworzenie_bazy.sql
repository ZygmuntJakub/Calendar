USE calendar;
#CREATE DATABASE Calendar;

USE Calendar;

CREATE TABLE Events(
event_id INTEGER NOT NULL PRIMARY KEY,
name VARCHAR(100),
description VARCHAR(100),
place VARCHAR(100),
start Datetime,
end Datetime,
alarm Datetime,
importance INTEGER
);

