INSERT INTO users(name, surname, username, password,email,status,creation_time,login_tries) VALUES
  ('Evik','Master','SystemMaster','admin','evik@iqlearning.corp',5,now(),0),
  ('John','Smith','johnny','john123','smith@gmail.com',0,now(),0),
  ('Jane','Doe','jane','jane123','doe@gmail.com',0,now(),0),
  ('Garrus','Vakarian','Archangel','calibrate','vakarian@normandy.com',0,now(),0);


INSERT INTO sessions VALUES
  ('1234-1234-1234-1234',1,now());

INSERT INTO subjects(name,year) VALUES
  ('Math', 1),
  ('Math',2),
  ('Math',7),
  ('Biology',2);

INSERT INTO questions(owner, subject,question,choice_test,shareable,created,last_edited) VALUES
  (1,1,'What is 1+1?',false,FALSE,now(),now()),
  (1,1,'What is 2+1?',false,TRUE ,now(),now()),
  (1,1,'Does 1+1 equals:',TRUE,FALSE,now(),now()),
  (1,1,'Does 1+3 equals:',TRUE ,TRUE ,now(),now());

INSERT INTO answers(question_id,answer,correct,created,last_edited) VALUES
  (3,'3',FALSE,now(),now()),
  (3,'10',FALSE,now(),now()),
  (3,'2',TRUE,now(),now()),
  (3,'8',FALSE,now(),now()),

  (4,'3',FALSE,now(),now()),
  (4,'4',TRUE,now(),now()),
  (4,'9',FALSE,now(),now()),
  (4,'1',FALSE,now(),now());

