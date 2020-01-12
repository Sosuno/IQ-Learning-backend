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


INSERT INTO articles(owner,title,contents,tags) VALUES
  (1,'Zabji mnie','Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.', null),
  (2,'I hate u','Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem.',null),
  (1,'bla bla bla','Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?','{1,2}');



INSERT INTO articles_comments(article_id,commentator,comment) VALUES
  (1,1,'Co za chujnia'),
  (1,2,'dupa'),
  (1,1,'damn'),
  (1,2,'oj'),
  (1,1,'bubu'),
  (1,3,'bubu?'),
  (1,1,'bubu.'),
  (2,1,'good stuff'),
  (2,1,'yup'),
  (2,1,'nie wiem'),
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

