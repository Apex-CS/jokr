CREATE DATABASE db_example;
CREATE user 'springuser'@'%' identified BY 'MySQL-P';
GRANT all ON db_example.* TO 'springuser'@'%';