DROP TABLE IF EXISTS users;
CREATE TABLE users(id serial PRIMARY KEY, name VARCHAR(50), surname VARCHAR(100),username VARCHAR(20) UNIQUE ,password VARCHAR(30),email VARCHAR(100) UNIQUE,
                   status int, creationTime TIMESTAMP, loginTries int);
