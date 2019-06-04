USE calendar;
#CREATE DATABASE Calendar;

USE Calendar;

DROP TABLE Events;
CREATE TABLE Events(event_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, title VARCHAR(100), description VARCHAR(250), date Datetime, alertBefore INTEGER, place VARCHAR(30));

