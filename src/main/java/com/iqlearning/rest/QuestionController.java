package com.iqlearning.rest;

import com.iqlearning.context.activities.QuestionsManagement;
import com.iqlearning.context.objects.FilledQuestion;
import java.sql.Timestamp;

import com.iqlearning.database.entities.Answer;
import com.iqlearning.database.entities.Question;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.interfaces.IAnswerService;
import com.iqlearning.database.service.interfaces.ISessionService;
import com.iqlearning.database.service.interfaces.ISubjectService;
import com.iqlearning.database.service.interfaces.IUserService;
import com.iqlearning.database.service.interfaces.IQuestionService;
import com.iqlearning.rest.resource.IdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins=("http://localhost:3000"))
public class QuestionController {

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

    private QuestionsManagement que;

    @PutMapping("/question/add")
    public ResponseEntity<?> addQuestion(@RequestHeader Map<String, String> headers, @RequestBody FilledQuestion filledQuestion) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        filledQuestion.setOwner(user.getId());
        que = new QuestionsManagement(questionService, answerService, subjectService);
        if(filledQuestion.getSubject() == null || filledQuestion.getQuestion() == null
                || (filledQuestion.isChoiceTest() && filledQuestion.getAnswers() == null)) {
            return new ResponseEntity<>( filledQuestion, HttpStatus.BAD_REQUEST);
        }
        Question addedQuestion = que.addQuestion(filledQuestion);
        if(addedQuestion != null) {
            return new ResponseEntity<>(addedQuestion, HttpStatus.OK);
        } else return new ResponseEntity<>(filledQuestion, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/question/delete")
    public ResponseEntity<?> deleteQuestion(@RequestHeader Map<String, String> headers, @RequestBody IdForm idForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        List<Question> questionList = questionService.getAllUserQuestions(user.getId());
        List<FilledQuestion> filledQuestionList = que.getReadyQuestions(questionList);
        for (FilledQuestion q: filledQuestionList) {
            if(q.getId() == idForm.getId()) {
                for(Answer a: q.getAnswers()) {
                    answerService.deleteAnswer(a.getId());
                }
                questionService.deleteQuestion(idForm.getId());
                return new ResponseEntity<>( "Question deleted", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( "Bad question id", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/question/update")
    public ResponseEntity<?> updateQuestion(@RequestHeader Map<String, String> headers, @RequestBody FilledQuestion filledQuestion) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        List<Question> questionList = questionService.getAllUserQuestions(user.getId());
        List<FilledQuestion> filledQuestionList = que.getReadyQuestions(questionList);
        for (FilledQuestion q: filledQuestionList) {
            if(q.getId() == filledQuestion.getId()) {

                return new ResponseEntity<>( "Question updated", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>( "Bad question id" + filledQuestion.getId(), HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/question/get/user")
    public ResponseEntity<?> getQuestionsByUser(@RequestHeader Map<String, String> headers){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Question> questionList = questionService.getAllUserQuestions(user.getId());
        List<FilledQuestion> filledQuestionList = que.getReadyQuestions(questionList);
        if(filledQuestionList.isEmpty()) {
            return new ResponseEntity<>("Question list empty" , HttpStatus.OK);
        } else return new ResponseEntity<>(filledQuestionList , HttpStatus.OK);
    }

}
