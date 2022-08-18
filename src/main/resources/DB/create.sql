SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS users (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 phone VARCHAR,
 email VARCHAR,
 position VARCHAR,
 department VARCHAR
);

CREATE TABLE IF NOT EXISTS departments (
 id int PRIMARY KEY auto_increment,
 name VARCHAR,
 description VARCHAR,
 employee_no int
);

CREATE TABLE IF NOT EXISTS news (
 id int PRIMARY KEY auto_increment,
 general_news VARCHAR,
 department_news VARCHAR,
 departmentid INTEGER
);

CREATE TABLE IF NOT EXISTS users_departments (
 id int PRIMARY KEY auto_increment,
 departmentid INTEGER,
 userid INTEGER
);