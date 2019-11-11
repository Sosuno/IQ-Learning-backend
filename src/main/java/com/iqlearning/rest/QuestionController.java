package com.iqlearning.rest;

import com.iqlearning.context.activities.QuestionsManagement;
import com.iqlearning.context.objects.FilledQuestion;

import com.iqlearning.database.entities.Answer;
import com.iqlearning.database.entities.Question;
import com.iqlearning.database.entities.Subject;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.interfaces.IAnswerService;
import com.iqlearning.database.service.interfaces.ISessionService;
import com.iqlearning.database.service.interfaces.ISubjectService;
import com.iqlearning.database.service.interfaces.IUserService;
import com.iqlearning.database.service.interfaces.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
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
            return new ResponseEntity<>(filledQuestion, HttpStatus.BAD_REQUEST);
        }
        Question addedQuestion = que.addQuestion(filledQuestion);
        if(addedQuestion != null) {
            return new ResponseEntity<>(addedQuestion, HttpStatus.OK);
        } else return new ResponseEntity<>(filledQuestion, HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/question/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        Question questionToDelete = questionService.get(id);
        if(questionToDelete == null) {
            return new ResponseEntity<>("Question not found", HttpStatus.BAD_REQUEST);
        } else {
            FilledQuestion question = que.getReadyQuestion(questionToDelete);
            if(question.isChoiceTest()) {
                for (Answer a : question.getAnswers()) {
                    answerService.deleteAnswer(a.getId());
                }
            }
            questionService.deleteQuestion(id);
            return new ResponseEntity<>( "Question deleted", HttpStatus.OK);
        }
    }
    @PostMapping("/question/update")
    public ResponseEntity<?> updateQuestion(@RequestHeader Map<String, String> headers, @RequestBody FilledQuestion filledQuestion) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        Boolean isTrue = false;
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(filledQuestion.getId() == null) return new ResponseEntity<>("Question id is null", HttpStatus.BAD_REQUEST);
        if(filledQuestion.getOwner() == null) return new ResponseEntity<>("Owner id is null", HttpStatus.BAD_REQUEST);
        if(filledQuestion.getSubject() == null || filledQuestion.getQuestion() == null
                || (filledQuestion.isChoiceTest() && filledQuestion.getAnswers() == null)) {
            return new ResponseEntity<>(filledQuestion, HttpStatus.BAD_REQUEST);
        }
        if(filledQuestion.isChoiceTest()) {
            for(Answer a : filledQuestion.getAnswers()) {
                if(a.getAnswer() == null) return new ResponseEntity<>("Answer text is null", HttpStatus.BAD_REQUEST);
                if(a.isCorrect()) isTrue=true;
            }
            if(!isTrue) return new ResponseEntity<>("One answer must be the good answer", HttpStatus.BAD_REQUEST);
        }
        Question questionToUpdate = questionService.get(filledQuestion.getId());
        if(questionToUpdate == null) {
            return new ResponseEntity<>("Question not found", HttpStatus.BAD_REQUEST);
        }
        if(filledQuestion.getOwner() != user.getId()) {
            return new ResponseEntity<>("Owner id does not match logged user id", HttpStatus.BAD_REQUEST);
        }
        FilledQuestion updatedQuestion = que.getReadyQuestion(que.addQuestion(filledQuestion));
        if(updatedQuestion == null) {
            return new ResponseEntity<>(filledQuestion, HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(filledQuestion, HttpStatus.OK);
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
    @GetMapping("/question/get/date/{timestamp}")
    public ResponseEntity<?> getQuestionsByUser(@RequestHeader Map<String, String> headers, @PathVariable Timestamp timestamp){
        /*
        todo
         */
        return new ResponseEntity<>("todo",HttpStatus.NOT_ACCEPTABLE);
    }
    @GetMapping("/question/get/subject/{id}")
    public ResponseEntity<?> getQuestionsBySubject(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Question> questionListReady = new ArrayList<>();
        List<Question> questionList = questionService.getAllUserQuestions(user.getId());
        for(Question q : questionList) {
            if(q.getSubject() == id) {
                questionListReady.add(q);
            }
        }
        if(questionListReady.isEmpty()) {
            return new ResponseEntity<>("Question list empty", HttpStatus.BAD_REQUEST);
        }
        List<FilledQuestion> filledQuestionList = que.getReadyQuestions(questionListReady);
        return new ResponseEntity<>(filledQuestionList , HttpStatus.OK);
    }
    @GetMapping("/question/get/{id}")
    public ResponseEntity<?> getQuestionById(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }

        Question question = questionService.get(id);
        if(question == null) return new ResponseEntity<>("Question not found", HttpStatus.BAD_REQUEST);
        if(user.getId() != question.getId() && !question.isShareable()) {
            return new ResponseEntity<>("Question not shareable", HttpStatus.NOT_ACCEPTABLE);
        }
        FilledQuestion filledQuestion = que.getReadyQuestion(question);
        return new ResponseEntity<>(filledQuestion , HttpStatus.OK);
    }

    @GetMapping("/subject/get/{id}")
    public ResponseEntity<?> getSubjectById(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        Subject subject = subjectService.getSubject(id);
        if(subject == null) return new ResponseEntity<>("Subject not found", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(subject , HttpStatus.OK);
    }
    @GetMapping("/subject/get")
    public ResponseEntity<?> getAllSubjects(@RequestHeader Map<String, String> headers){
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Subject> subjectList = subjectService.getAllSubjects();
        if(subjectList.isEmpty()) return new ResponseEntity<>("Subject list empty", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(subjectList , HttpStatus.OK);
    }
    @GetMapping("/answers/get/{id}")
    public ResponseEntity<?> getAnswersByQuestionId(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        if (sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUserBySession(session);
        Question question = questionService.get(id);
        if(question == null) return new ResponseEntity<>("Question not found", HttpStatus.BAD_REQUEST);
        if(!question.isChoice_test()) return new ResponseEntity<>("Open questions have no answers", HttpStatus.BAD_REQUEST);
        if(user.getId() != question.getOwner() && !question.isShareable()) return new ResponseEntity<>("Question not shareable", HttpStatus.BAD_REQUEST);
        List<Answer> answerList = answerService.getAnswers(question);
        if (answerList.isEmpty()) return new ResponseEntity<>("Answer list empty", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(answerList, HttpStatus.OK);
    }

}
