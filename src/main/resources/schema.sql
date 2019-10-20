DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS users;
CREATE TABLE users(id serial PRIMARY KEY, name VARCHAR(50), surname VARCHAR(100),username VARCHAR(20) UNIQUE ,password VARCHAR(30),email VARCHAR(100) UNIQUE,
                   status int, creation_time TIMESTAMP, login_tries int);

CREATE TABLE sessions(session_id VARCHAR(100) PRIMARY KEY, user_id BIGINT UNIQUE ,   creation_time TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users (id));