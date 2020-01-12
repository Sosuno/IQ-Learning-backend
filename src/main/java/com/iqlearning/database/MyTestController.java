package com.iqlearning.database;


import com.iqlearning.database.entities.*;
import com.iqlearning.database.service.ArticlesService;
import com.iqlearning.database.service.interfaces.*;
import com.iqlearning.database.utils.FullArticle;
import com.iqlearning.database.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MyTestController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISessionService sessionService;

    @Autowired
    private IAnswerService answerService;
    @Autowired
    private ISubjectService subjectService;
    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IChatService chat;

    @Autowired
    private ArticlesService art;

    private User u1;
    private Session s;

    @RequestMapping(value = "/test/articles/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<?> addArticle() {
        List<Articles> articles = new ArrayList<>();
        Articles a = new Articles();
        a.setOwner(1L);
        a.setTitle("Lorem Ipsum");
        a.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum" +
                " dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
                "deserunt mollit anim id est laborum.");

        articles.add(art.saveArticle(a));

        return articles;
    }



    @RequestMapping(value = "/test/articles/getAll", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<?> getAllArticles() {
        List<Articles> articles = new ArrayList<>();
        Articles a = new Articles();
        Articles b = new Articles();
        Articles c = new Articles();
        Articles d = new Articles();
        Articles e = new Articles();
        a.setOwner(1L);
        b.setOwner(1L);
        c.setOwner(1L);
        d.setOwner(1L);
        e.setOwner(1L);
        a.setTitle("Lorem Ipsum");
        a.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum" +
                " dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
                "deserunt mollit anim id est laborum.");
        b.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum" +
                " dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
                "deserunt mollit anim id est laborum.");
        c.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum" +
                " dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
                "deserunt mollit anim id est laborum.");
        d.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum" +
                " dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
                "deserunt mollit anim id est laborum.");
        e.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi" +
                " ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum" +
                " dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia " +
                "deserunt mollit anim id est laborum.");

        art.saveArticle(a);

        b.setTitle("kekeke");

        art.saveArticle(b);
        c.setTitle("keasdase");

        art.saveArticle(c);
        d.setTitle("keafadagfasdfe");

        art.saveArticle(d);
        e.setTitle("edasdgewdfe");

        art.saveArticle(e);
        return art.getAll();
    }


    @RequestMapping(value = "/test/articles/getByUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
public List<?> getAllUserArticles() {
        return art.getUserArticles(1L);
    }


    @RequestMapping(value = "/test/articles/getComments", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
public List<?> getAllComments() {
        return art.getArticleComments(1L);
    }

    @RequestMapping(value = "/test/articles/addComment", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
public Comment addComment() {
        Comment comment = new Comment();
        comment.setComment("blaskjdsfkjad;fa;f");
        comment.setArticleId(1L);
        comment.setCommentator(1L);

        return art.saveComment(comment);
    }

    @RequestMapping(value = "/test/articles/upvoteA", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<?> upvote() {
        //List<FullArticle> articles = articles = art.getAll();
        List<Articles> ret = new ArrayList<>();
        Articles aaaa  = art.upvoteArticle(1L,1L);
        art.upvoteArticle(1L,2L);
        art.upvoteArticle(1L,3L);
        ret.add(aaaa);


        return ret;
    }

    @RequestMapping(value = "/test/articles/upvoteC", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<?> upvote2() {
        //List<FullArticle> articles = articles = art.getAll();
        List<Comment> ret = new ArrayList<>();
        Comment aaaa  = art.upvoteComment(1L,1L);
        ret.add(aaaa);


        return ret;
    }

    @RequestMapping(value = "/test/articles/tags", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Iterable<?> getTags() {
        return art.getTags();
    }
    @RequestMapping
            (value = "/test/articles/editTags", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Articles tags() {

        art.editTags(3L,1L);

        return art.editTags(2L,1L);
    }


    @RequestMapping(value = "/test/chat/send", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<Message> sent() {
        List<Message> chatHistory = new ArrayList<>();
        Message m = new Message("Hi");
        m.setSender(1L);
        m.setRecipient(2L);
        chatHistory.add(chat.sendMessage(m));
        m.setMessage("dudududu");
        chatHistory.add(chat.sendMessage(m));
        m.setMessage("Hiya");
        m.setSender(2L);
        m.setRecipient(1L);
        chatHistory.add(chat.sendMessage(m));
        return chatHistory;
    }

    @RequestMapping(value = "/test/chat/history", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<Message> history() {
    return chat.getConversationHistory(1L,2L);
    }

    @RequestMapping(value = "/test/a/get", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<List<Answer>> getAnswers() {
        List<Answer> a = answerService.getAnswers(3L);
        List<List<Answer>> list = new ArrayList<>();
        list.add(a);
        Question q = new Question();
        q.setId(3L);
        a = answerService.getAnswers(q);
        list.add(a);
        a = new ArrayList<>();
        Answer as = answerService.getCorrectAnswer(4L);
        a.add(as);
        q.setId(4L);
        as = answerService.getCorrectAnswer(q);
        a.add(as);
        list.add(a);
        return list;
    }

    @RequestMapping(value = "/test/s/get", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<List<Subject>> getSubjects() {

        List<Subject> l = subjectService.getSubjectBy(1);
        List<List<Subject>> list = new ArrayList<>();
        list.add(l);
        l = subjectService.getAllSubjects();
        list.add(l);
        l = subjectService.getSubjectBy("Math");
        list.add(l);
        l = new ArrayList<>();
        Subject s = subjectService.getSubject(1L);
        l.add(s);
        s = subjectService.getSubjectBy("Biology", 2);
        l.add(s);
        list.add(l);
        return list;
    }

    @RequestMapping(value = "/test/q/get", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<List<Question>> getQuestions() {
        Subject s = subjectService.getSubject(1L);
        List<Question> l = questionService.getAllUserQuestions(1L);
        List<List<Question>> list = new ArrayList<>();
        list.add(l);
        l = questionService.getAllSharedQuestionsByUser(1L);
        list.add(l);
        l= questionService.getAllSharedQuestions();
        list.add(l);
        l= questionService.getAllSharedQuestionsForSubject(s);
        list.add(l);
        l= questionService.getAllSharedQuestionsForSubject(1L);
        list.add(l);
        l= questionService.getAllOpenQuestionsByUser(1L);
        list.add(l);
        l= questionService.getAllOpenQuestionsForSubject(s);
        list.add(l);
        l= questionService.getAllOpenQuestionsForSubject(1L);
        list.add(l);
        l= questionService.getAllQuestionForSubjectOfUser(1L,1L);
        list.add(l);
        l= questionService.getAllClosedQuestionsByUser(1L);
        list.add(l);
        l= questionService.getAllClosedQuestionsForSubjectForUser(s, 1L);
        list.add(l);
        l= questionService.getAllClosedQuestionsForSubjectForUser(1L,1L);
        list.add(l);
        l= questionService.searchQuestion("is");
        list.add(l);
        l= questionService.getAllShareableOfUserBySubject(1L,1L);
        list.add(l);
        l= questionService.getAllNonShareableOfUserBySubject(1L,1L);
        list.add(l);
        l= questionService.getAllNonShareableClosedOfUserBySubject(1L,1L);
        list.add(l);
        l= questionService.getAllNonShareableOpenOfUserBySubject(1L,1L);
        list.add(l);
        l= questionService.getAllShareableClosedOfUserBySubject(1L,1L);
        list.add(l);
        l= questionService.getAllShareableOpenOfUserBySubject(1L,1L);
        list.add(l);

        return list;
    }
}
