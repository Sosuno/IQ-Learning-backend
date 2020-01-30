INSERT INTO users(name, surname, username, password,email,status,creation_time,login_tries) VALUES
  ('Evik','Master','SystemMaster','admin','evik@iqlearning.corp',5,now(),0),
  ('John','Smith','johnny','john123','smith@gmail.com',0,now(),0),
  ('Jane','Doe','jane','jane123','doe@gmail.com',0,now(),0),
  ('Garrus','Vakarian','Archangel','calibrate','vakarian@normandy.com',0,now(),0),
  ('Gosia','Z','FochMaiden','qweqwe123','gosia.xq@gmail.com',5,now(),0),
  ('stefan','potezny','stef','123','stefo@st.st',5,now(),0);

INSERT INTO sessions VALUES
  ('1234-1234-1234-1234',1,now()),
  ('1111-2222-3333-4444',6,now());

INSERT INTO subjects(name,year) VALUES
  ('Math', 1),
  ('Math',2),
  ('Math',7),
  ('Biology',2);

INSERT INTO questions(owner, subject,question,choice_test,shareable,created,last_edited) VALUES
  (1,1,'What is 1+1?',false,FALSE,now(),now()),
  (1,1,'What is 2+1?',false,TRUE ,now(),now()),
  (1,1,'Does 1+1 equals:',TRUE,FALSE,now(),now()),
  (1,1,'Does 1+3 equals:',TRUE ,TRUE ,now(),now()),
  (6,2,'Which one is executed first?',true ,TRUE ,now(),now()),
  (6,2,'Is 1/2 greater than 1/3?',true ,TRUE ,now(),now()),
  (6,2,'2+2*2 equals ', TRUE,true,now(),now()),
  (6,2,'What is the greatest common denominator for 140, 375 and 330?', TRUE,true,now(),now()),
  (6,2,'Which one is a prime number?', TRUE,true,now(),now()),
  (6,2,'Mark had 32 euros, Amy had 88 zlotys, Megan had 13 pounds and Sam had 55 dollars. If Mark, Amy and Megan were to buy dollars with their money and give them to Sam, he would have 184 dollars. 1 euro costs 1,1 dollars and one zloty costs 0,3 dollars. Calculate how many pounds you can buy with one dollar.', FALSE,true,now(),now()),
  (6,2,'Ann had 14 apples, John had 27 oranges, Susan had 13 pears and Gary had X grapes. Combined price of all their fruits was 139$. If Ann were to sell 3 apples and Susan were to buy 2 pears, the combined price would not change. If Gary ate 3 grapes and John bought 7 more oranges, the price would rise by 7$. Calculate the price of each of the fruits and the number of grapes.', FALSE,true,now(),now());

INSERT INTO answers(question_id,answer,correct,created,last_edited) VALUES
  (3,'3',FALSE,now(),now()),
  (3,'10',FALSE,now(),now()),
  (3,'2',TRUE,now(),now()),
  (3,'8',FALSE,now(),now()),

  (4,'3',FALSE,now(),now()),
  (4,'4',TRUE,now(),now()),
  (4,'9',FALSE,now(),now()),
  (4,'1',FALSE,now(),now()),

  (5,'Division',FALSE,now(),now()),
  (5,'Addition',FALSE,now(),now()),
  (5,'Subtraction',FALSE,now(),now()),
  (5,'Exponentiation',TRUE,now(),now()),

  (6,'No',FALSE,now(),now()),
  (6,'Yes',TRUE,now(),now()),

  (7,'8',FALSE,now(),now()),
  (7,'6',TRUE,now(),now()),
  (7,'4',FALSE,now(),now()),
  (7,'10',FALSE,now(),now()),

  (8,'7',FALSE,now(),now()),
  (8,'3',FALSE,now(),now()),
  (8,'5',TRUE,now(),now()),

  (9,'14',FALSE,now(),now()),
  (9,'15',FALSE,now(),now()),
  (9,'23',TRUE,now(),now()),
  (9,'27',FALSE,now(),now());

INSERT INTO article_tags(tag) VALUES
  ('Educationl'),
  ('Math'),
  ('New Ideas');


INSERT INTO articles(owner,title,contents,tags,description) VALUES
  (1,'Awesome title','TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdCwgc2VkIGRvIGVpdXNtb2QgdGVtcG9yIGluY2lkaWR1bnQgdXQgbGFib3JlIGV0IGRvbG9yZSBtYWduYSBhbGlxdWEuIFV0IGVuaW0gYWQgbWluaW0gdmVuaWFtLCBxdWlzIG5vc3RydWQgZXhlcmNpdGF0aW9uIHVsbGFtY28gbGFib3JpcyBuaXNpIHV0IGFsaXF1aXAgZXggZWEgY29tbW9kbyBjb25zZXF1YXQuIER1aXMgYXV0ZSBpcnVyZSBkb2xvciBpbiByZXByZWhlbmRlcml0IGluIHZvbHVwdGF0ZSB2ZWxpdCBlc3NlIGNpbGx1bSBkb2xvcmUgZXUgZnVnaWF0IG51bGxhIHBhcmlhdHVyLiBFeGNlcHRldXIgc2ludCBvY2NhZWNhdCBjdXBpZGF0YXQgbm9uIHByb2lkZW50LCBzdW50IGluIGN1bHBhIHF1aSBvZmZpY2lhIGRlc2VydW50IG1vbGxpdCBhbmltIGlkIGVzdCBsYWJvcnVtLg==', null,'some description'),
  (2,'Awesome title1','TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdCwgc2VkIGRvIGVpdXNtb2QgdGVtcG9yIGluY2lkaWR1bnQgdXQgbGFib3JlIGV0IGRvbG9yZSBtYWduYSBhbGlxdWEuIFV0IGVuaW0gYWQgbWluaW0gdmVuaWFtLCBxdWlzIG5vc3RydWQgZXhlcmNpdGF0aW9uIHVsbGFtY28gbGFib3JpcyBuaXNpIHV0IGFsaXF1aXAgZXggZWEgY29tbW9kbyBjb25zZXF1YXQuIER1aXMgYXV0ZSBpcnVyZSBkb2xvciBpbiByZXByZWhlbmRlcml0IGluIHZvbHVwdGF0ZSB2ZWxpdCBlc3NlIGNpbGx1bSBkb2xvcmUgZXUgZnVnaWF0IG51bGxhIHBhcmlhdHVyLiBFeGNlcHRldXIgc2ludCBvY2NhZWNhdCBjdXBpZGF0YXQgbm9uIHByb2lkZW50LCBzdW50IGluIGN1bHBhIHF1aSBvZmZpY2lhIGRlc2VydW50IG1vbGxpdCBhbmltIGlkIGVzdCBsYWJvcnVtLg==','{1,2}','some description'),
  (1,'Awesome title 2','TG9yZW0gaXBzdW0gZG9sb3Igc2l0IGFtZXQsIGNvbnNlY3RldHVyIGFkaXBpc2NpbmcgZWxpdCwgc2VkIGRvIGVpdXNtb2QgdGVtcG9yIGluY2lkaWR1bnQgdXQgbGFib3JlIGV0IGRvbG9yZSBtYWduYSBhbGlxdWEuIFV0IGVuaW0gYWQgbWluaW0gdmVuaWFtLCBxdWlzIG5vc3RydWQgZXhlcmNpdGF0aW9uIHVsbGFtY28gbGFib3JpcyBuaXNpIHV0IGFsaXF1aXAgZXggZWEgY29tbW9kbyBjb25zZXF1YXQuIER1aXMgYXV0ZSBpcnVyZSBkb2xvciBpbiByZXByZWhlbmRlcml0IGluIHZvbHVwdGF0ZSB2ZWxpdCBlc3NlIGNpbGx1bSBkb2xvcmUgZXUgZnVnaWF0IG51bGxhIHBhcmlhdHVyLiBFeGNlcHRldXIgc2ludCBvY2NhZWNhdCBjdXBpZGF0YXQgbm9uIHByb2lkZW50LCBzdW50IGluIGN1bHBhIHF1aSBvZmZpY2lhIGRlc2VydW50IG1vbGxpdCBhbmltIGlkIGVzdCBsYWJvcnVtLg==','{1,2}','some description');


INSERT INTO articles_comments(article_id,commentator,comment) VALUES
  (1,1,'It is a comment '),
  (1,2,'It is a comment 1'),
  (1,1,'It is a comment 2'),
  (1,2,'It is a comment 3'),
  (1,1,'It is a comment 4'),
  (1,3,'It is a comment 5'),
  (1,1,'It is a comment 6'),
  (2,1,'It is a comment 7'),
  (2,1,'It is a comment'),
  (2,1,'It is a comment 8'),
  (2,1,'out'),
  (2,1,'aaaaaa'),
  (2,1,'bbbbb'),
  (2,1,'ccccc'),
  (3,1,'asdasdasd'),
  (3,1,'kill me'),
  (3,1,'kill me please'),
  (3,1,'i cant'),
  (3,1,'aaaaaaaaaaaaaaaa'),
  (3,1,'no i bedzie problem ze znakami specjalnymi'),
  (3,1,'kurwa');

INSERT INTO tests(owner,subject,questions,shareable) VALUES
    (6,2,'{5,6,7,8,9,10,11}',true);

INSERT INTO conversations(user1,user2) VALUES
  (1,2),
  (2,3);

INSERT INTO chat(conversation,sender,message) VALUES
  (1,1,'ups5'),
  (1,1,'ups7') ,
  (1,2,'ups6') ,
  (1,2,'ups5') ,
  (2,3,'ups4') ,
  (1,2,'ups3') ,
  (2,2,'ups2') ,
  (2,3,'ups1');

INSERT INTO tests_results(test_id,question_id,points,results_owner) VALUES
  (1,5,1,1),
  (1,6,1,1),
  (1,7,1,1),
  (1,8,1,1),
  (1,9,1,1),
  (1,5,1,1),
  (1,6,1,1),
  (1,7,1,1),
  (1,8,1,1),
  (1,9,1,1),
  (1,5,1,1),
  (1,6,1,1),
  (1,7,1,1),
  (1,8,1,1),
  (1,9,1,1),
  (1,5,1,1),
  (1,6,1,1),
  (1,7,1,1),
  (1,8,1,1),
  (1,9,1,1),
  (1,5,1,1),
  (1,6,1,1),
  (1,7,1,1),
  (1,8,1,1),
  (1,9,1,1);

