DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS sessions;
DROP TABLE IF EXISTS users;

CREATE TABLE users(
                    id serial PRIMARY KEY,
                    name VARCHAR(50),
                    surname VARCHAR(100),
                    username VARCHAR(20) UNIQUE NOT NULL,
                    password VARCHAR(30) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    status int NOT NULL,
                    creation_time TIMESTAMP NOT NULL,
                    login_tries int
                  );

CREATE TABLE sessions(
                    session_id VARCHAR(100) PRIMARY KEY,
                    user_id BIGINT UNIQUE NOT NULL,
                    creation_time TIMESTAMP NOT NULL,
                    FOREIGN KEY (user_id) REFERENCES users (id)
                  );

CREATE TABLE questions(
                    id serial PRIMARY KEY,
                    owner BIGINT NOT NULL,
                    subject BIGINT NOT NULL,
                    question VARCHAR NOT NULL,
                    choice_test BOOLEAN NOT NULL,
                    shareable BOOLEAN NOT NULL,
                    created TIMESTAMP NOT NULL,
                    last_edited TIMESTAMP,
                    FOREIGN KEY (owner) REFERENCES users(id),
                    FOREIGN KEY (subject) REFERENCES subjects(id)
                    );

CREATE TABLE answers(
                    id SERIAL PRIMARY KEY,
                    question_id BIGINT NOT NULL,
                    answer VARCHAR NOT NULL,
                    correct BOOLEAN NOT NULL,
                    created TIMESTAMP NOT NULL,
                    last_edited TIMESTAMP,
                    FOREIGN KEY (question_id) REFERENCES questions(id)
                  );

CREATE TABLE subjects(
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(64) NOT NULL,
                    year INT NOT NULL
                  );


