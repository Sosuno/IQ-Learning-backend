DROP TABLE IF EXISTS users;
CREATE TABLE users(id serial PRIMARY KEY, name VARCHAR(50), surname VARCHAR(100),username VARCHAR(20) UNIQUE ,password VARCHAR(30),email VARCHAR(100) UNIQUE,
  status int, creation_time TIMESTAMP, login_tries int);

INSERT INTO users(name, surname, username, password,email,status,creation_time,login_tries) VALUES('John','Smith','johnny','john123','smith@gmail.com',0,now(),0);






