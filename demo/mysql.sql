/*
	MySQL 
	DB 및 테이블 생성 SQL 문
*/
create database todoapp;
use todoapp;

CREATE TABLE todo (
  id varchar(45) NOT NULL,
  userId varchar(45) DEFAULT NULL,
  title varchar(45) DEFAULT NULL,
  done tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE user (
  id varchar(45) NOT NULL,
  username varchar(45) DEFAULT NULL,
  password varchar(45) DEFAULT NULL,
  role varchar(45) DEFAULT NULL,
  authProvider varchar(45) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username_UNIQUE (username)
);

