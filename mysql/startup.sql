CREATE DATABASE jokr_db;
CREATE user 'springuser'@'%' identified BY 'MySQL-P';
GRANT all ON jokr_db.* TO 'springuser'@'%';
