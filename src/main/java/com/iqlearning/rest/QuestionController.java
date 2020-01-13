package com.iqlearning.rest;

import com.iqlearning.database.entities.Answer;
import com.iqlearning.database.entities.Question;
import com.iqlearning.database.entities.Subject;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.QuestionService;
import com.iqlearning.database.service.SessionService;
import com.iqlearning.database.service.SubjectService;
import com.iqlearning.database.service.UserService;
import com.iqlearning.database.utils.FilledQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins=("http://localhost:3000"))
public class QuestionController {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private QuestionService questionService;


    @PutMapping("/question/add")
    public ResponseEntity<?> addQuestion(@RequestHeader Map<String, String> headers, @RequestBody FilledQuestion filledQuestion) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        filledQuestion.setOwner(user.getId());
        if(filledQuestion.getSubject() == null || filledQuestion.getQuestion() == null
                || (filledQuestion.isChoiceTest() && filledQuestion.getAnswers() == null)) {
            return new ResponseEntity<>(filledQuestion, HttpStatus.BAD_REQUEST);
        }
        boolean hasAtLeastOneCorrectAnswer = false;
        if(filledQuestion.isChoiceTest()) {
            for(Answer a : filledQuestion.getAnswers()) {
                if(a.isCorrect()) hasAtLeastOneCorrectAnswer = true;
            }
        }
        if(!filledQuestion.isChoiceTest()) hasAtLeastOneCorrectAnswer = true;
        if(!hasAtLeastOneCorrectAnswer) return new ResponseEntity<>("At least one answer must be correct", HttpStatus.BAD_REQUEST);
        Question addedQuestion = questionService.addQuestion(filledQuestion);
        if(addedQuestion == null) {
            return new ResponseEntity<>("Added question is null", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(addedQuestion, HttpStatus.OK);
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
            FilledQuestion question = questionService.getReadyQuestion(questionToDelete);
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
        if(filledQuestion.getSubject() == null || filledQuestion.getQuestion() == null
                || (filledQuestion.isChoiceTest() && filledQuestion.getAnswers() == null)) {
            return new ResponseEntity<>("Fill all required fields" + filledQuestion, HttpStatus.BAD_REQUEST);
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
        if(questionToUpdate.getOwner() != user.getId()) {
            return new ResponseEntity<>("Owner id does not match logged user id", HttpStatus.BAD_REQUEST);
        } else if(filledQuestion.getOwner() == null) filledQuestion.setOwner(user.getId());
        if(filledQuestion.getCreated() == null) filledQuestion.setCreated(questionToUpdate.getCreated());
        FilledQuestion updatedQuestion = questionService.getReadyQuestion(questionService.addQuestion(filledQuestion));
        if(updatedQuestion == null) {
            return new ResponseEntity<>("Update unsuccessful", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }

    @GetMapping("/questions/get/user")
    public ResponseEntity<?> getQuestionsByUser(@RequestHeader Map<String, String> headers){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Question> questionList = questionService.getAllUserQuestions(user.getId());
        List<FilledQuestion> filledQuestionList = questionService.getReadyQuestions(questionList);
        return new ResponseEntity<>(filledQuestionList , HttpStatus.OK);
    }

    @GetMapping("/questions/get/user/subject/{id}")
    public ResponseEntity<?> getQuestionsBySubject(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Question> questionList = questionService.getAllQuestionForSubjectOfUser(user.getId(),id);
        List<FilledQuestion> readyQuestionList = questionService.getReadyQuestions(questionList);
        return new ResponseEntity<>(readyQuestionList, HttpStatus.OK);
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
        FilledQuestion filledQuestion = questionService.getReadyQuestion(question);
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
        else return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @GetMapping("/subject/get")
    public ResponseEntity<?> getAllSubjects(@RequestHeader Map<String, String> headers){
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Subject> subjectList = subjectService.getAllSubjects();
        return new ResponseEntity<>(subjectList , HttpStatus.OK);
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
        List<Answer> answerList = questionService.getAnswers(question);
        return new ResponseEntity<>(answerList, HttpStatus.OK);
    }

    @GetMapping("/questions/get/public")
    public ResponseEntity<?> getPublicQuestions(@RequestHeader Map<String, String> headers) {
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Question> questionList = questionService.getAllSharedQuestions();
        List<FilledQuestion> readyQuestionList = questionService.getReadyQuestions(questionList);
        return new ResponseEntity<>(readyQuestionList, HttpStatus.OK);
    }

    @GetMapping("/questions/get/public/user")
    public ResponseEntity<?> getPublicQuestionsByUser(@RequestHeader Map<String, String> headers) {
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUserBySession(session);
        List<Question> questionList = questionService.getAllSharedQuestionsByUser(user.getId());
        List<FilledQuestion> readyQuestionList = questionService.getReadyQuestions(questionList);
        return new ResponseEntity<>(readyQuestionList, HttpStatus.OK);
    }

    @GetMapping("/questions/get/public/subject/{id}")
    public ResponseEntity<?> getPublicQuestionsBySubject(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        Subject subject = subjectService.getSubject(id);
        if(subject.getId() == null) return new ResponseEntity<>("Invalid subject", HttpStatus.BAD_REQUEST);
        List<Question> questionList = questionService.getAllSharedQuestionsForSubject(id);
        List<FilledQuestion> readyQuestionList = questionService.getReadyQuestions(questionList);
        return new ResponseEntity<>(readyQuestionList, HttpStatus.OK);
    }

    @GetMapping("/questions/get/public/user/subject/{id}")
    public ResponseEntity<?> getPublicQuestionsByUserAndSubject(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        User user = userService.getUserBySession(session);
        Subject subject = subjectService.getSubject(id);
        if(subject.getId() == null) return new ResponseEntity<>("Invalid subject", HttpStatus.BAD_REQUEST);
        List<Question> questionList = questionService.getAllShareableOfUserBySubject(user.getId(),id);
        List<FilledQuestion> readyQuestionList = questionService.getReadyQuestions(questionList);
        return new ResponseEntity<>(readyQuestionList, HttpStatus.OK);
    }
}
