package com.iqlearning.database;


import com.iqlearning.database.entities.*;
import com.iqlearning.database.service.interfaces.*;
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

    private User u1;
    private Session s;


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
