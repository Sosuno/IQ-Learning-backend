package com.iqlearning.rest;

import com.iqlearning.database.entities.*;
import com.iqlearning.database.service.interfaces.*;
import com.iqlearning.rest.resource.TestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins=("http://localhost:3000"))
public class TestController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISessionService sessionService;
    @Autowired
    private ISubjectService subjectService;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private ITestService testService;

    @PutMapping("/test/add")
    public ResponseEntity<?> addTest(@RequestHeader Map<String, String> headers, @RequestBody TestForm testForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(testForm.getQuestions().length == 0) return new ResponseEntity<>("Empty question list", HttpStatus.BAD_REQUEST);
        Long[] questionList = testForm.getQuestions();
        for(Long id : questionList) {
            if (questionService.get(id) == null) {
                return new ResponseEntity<>("Question " + id + " not found", HttpStatus.BAD_REQUEST);
            } else if (questionService.get(id).getOwner() != user.getId() && !questionService.get(id).isShareable()) {
                return new ResponseEntity<>("Question " + id + " is not shareable and is not owned by you", HttpStatus.BAD_REQUEST);
            } else if (questionService.get(id).getSubject() != testForm.getSubjectId()) {
                return new ResponseEntity<>("Question " + id + " is not of the same subject as test", HttpStatus.BAD_REQUEST);
            }
        }
        Test newTest = new Test(user.getId(), testForm.getSubjectId(), questionList, testForm.getShareable(), 0 );
        Test addedTest = testService.saveTest(newTest);
        if(addedTest != null) {
            return new ResponseEntity<>(addedTest, HttpStatus.OK);
        } else return new ResponseEntity<>("Saving test failed", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/test/delete/{id}")
    public ResponseEntity<?> deleteTest(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        Test testToDelete = testService.getTest(id);
        if(testToDelete == null) {
            return new ResponseEntity<>("Test not found", HttpStatus.BAD_REQUEST);
        } else if(testToDelete.getOwner() != user.getId()) {
            return new ResponseEntity<>("You are not the owner", HttpStatus.UNAUTHORIZED);
        } else {
            testService.deleteTest(testToDelete);
            if(testService.getTest(id) == null)
                return new ResponseEntity<>( "Question deleted", HttpStatus.OK);
            else return new ResponseEntity<>( "Database error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/test/update")
    public ResponseEntity<?> updateTest(@RequestHeader Map<String, String> headers, @RequestBody TestForm testForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(testForm.getQuestions().length == 0) return new ResponseEntity<>("Empty question list", HttpStatus.BAD_REQUEST);
        Long testId = testForm.getId();
        if(testService.getTest(testId) == null) return new ResponseEntity<>("Test you're trying to edit doesn't exist", HttpStatus.BAD_REQUEST);
        if(testService.getTest(testId).getOwner() == user.getId()) return new ResponseEntity<>("You're not the owner of the test you're trying to edit", HttpStatus.UNAUTHORIZED);
        Long[] questionList = testForm.getQuestions();
        for(Long id : questionList) {
            if (questionService.get(id) == null) {
                return new ResponseEntity<>("Question " + id + " not found", HttpStatus.BAD_REQUEST);
            } else if (questionService.get(id).getOwner() != user.getId() && !questionService.get(id).isShareable()) {
                return new ResponseEntity<>("Question " + id + " is not shareable and is not owned by you", HttpStatus.BAD_REQUEST);
            } else if (questionService.get(id).getSubject() != testForm.getSubjectId()) {
                return new ResponseEntity<>("Question " + id + " is not of the same subject as test", HttpStatus.BAD_REQUEST);
            }
        }
        Test editedTest = testService.getTest(testId);
        editedTest.setQuestions(questionList);
        editedTest.setShareable(testForm.getShareable());
        Test savedTest = testService.saveTest(editedTest);
        if(savedTest != null) {
            return new ResponseEntity<>(savedTest, HttpStatus.OK);
        } else return new ResponseEntity<>("Saving test failed", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/tests/get/user")
    public ResponseEntity<?> getTestsByUser(@RequestHeader Map<String, String> headers){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Test> testList = testService.getTestsByUser(user.getId());
        if(testList.isEmpty()) {
            return new ResponseEntity<>("Question list empty" , HttpStatus.OK);
        } else return new ResponseEntity<>(testList, HttpStatus.OK);
    }

    @GetMapping("/tests/get/user/subject/{id}")
    public ResponseEntity<?> getTestsBySubject(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        Subject subject = subjectService.getSubject(id);
        if(subject.getId() == null) return new ResponseEntity<>("Invalid subject", HttpStatus.BAD_REQUEST);
        List<Test> testList = testService.getTestsByUserAndSubject(user.getId(), id);
        if(testList.isEmpty()) return new ResponseEntity<>("Test list empty", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(testList, HttpStatus.OK);
    }

    @GetMapping("/test/get/{id}")
    public ResponseEntity<?> getTestById(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }

        Test test = testService.getTest(id);
        if(test == null) return new ResponseEntity<>("Test not found", HttpStatus.BAD_REQUEST);
        if(test.getOwner() == user.getId()) return new ResponseEntity<>("You're not the owner", HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    @GetMapping("/tests/get/public")
    public ResponseEntity<?> getPublicQuestions(@RequestHeader Map<String, String> headers) {
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        List<Test> testList = testService.getSharedTests();
        if (testList.isEmpty()) return new ResponseEntity<>("No shared tests available", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(testList, HttpStatus.OK);
    }


    @GetMapping("/tests/get/public/subject/{id}")
    public ResponseEntity<?> getPublicQuestionsBySubject(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        if(sessionService.getSession(session) == null) {
            return new ResponseEntity<>("Session expired", HttpStatus.UNAUTHORIZED);
        }
        Subject subject = subjectService.getSubject(id);
        if(subject.getId() == null) return new ResponseEntity<>("Invalid subject", HttpStatus.BAD_REQUEST);
        List<Test> testList = testService.getSharedTestsBySubject(id);
        if (testList.isEmpty()) return new ResponseEntity<>("No shared tests available", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(testList, HttpStatus.OK);
    }

}
