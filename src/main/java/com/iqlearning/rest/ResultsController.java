package com.iqlearning.rest;

import com.iqlearning.database.entities.Question;
import com.iqlearning.database.entities.Test;
import com.iqlearning.database.entities.TestResults;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.TestService;
import com.iqlearning.database.service.interfaces.*;
import com.iqlearning.rest.resource.ResultForm;
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
public class ResultsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private ITestService testService;
    @Autowired
    private ITestResults testResultsService;

    @PutMapping("/results/add")
    public ResponseEntity<?> addTestResult(@RequestHeader Map<String, String> headers, @RequestBody ResultForm resultForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(testService.getTest(resultForm.getTestId()) == null) return new ResponseEntity<>("Test with given id doesn't exist", HttpStatus.BAD_REQUEST);
        List<TestResults> testResultsList = new ArrayList<>();
        List<TestResults> savedTestResultsList = new ArrayList<>();
        List<ResultForm.Result> results =  resultForm.getResultList();
        for(ResultForm.Result r : results) {
            if(questionService.get(r.getQuestionId()) == null) return new ResponseEntity<>("Question with given id doesn't exist", HttpStatus.BAD_REQUEST);
            if(testService.getTest(resultForm.getTestId()).getSubject() != questionService.get(r.getQuestionId()).getSubject()) {
                return new ResponseEntity<>("Test and question subjects don't match", HttpStatus.BAD_REQUEST);
            }
            if(r.getStudentId() == null)  {
                r.setStudentId(0L);
            }
            TestResults testResults = new TestResults(resultForm.getTestId(),r.getQuestionId(), r.getPoints(), user.getId(), r.getStudentId(), new Timestamp(System.currentTimeMillis()));
            testResultsList.add(testResults);
        }
        for(TestResults t : testResultsList) {
            TestResults savedResults = testResultsService.saveTestResults(t);
            if(savedResults != null) savedTestResultsList.add(savedResults);
        }
        return new ResponseEntity<>(savedTestResultsList, HttpStatus.OK);
    }

    @GetMapping("/results/get/test/{id}")
    public ResponseEntity<?> getResultsForTest(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(testService.getTest(id) == null) return new ResponseEntity<>("Test with given id doesn't exist", HttpStatus.BAD_REQUEST);
        if(!testService.getTest(id).isShareable() && (testService.getTest(id).getOwner() != user.getId())) {
            return new ResponseEntity<>("Specified test isn't shareable and you're not the owner", HttpStatus.BAD_REQUEST);
        }
        List<TestResults> testResultList = testResultsService.getAllTestResults(id);
        return new ResponseEntity<>(testResultList, HttpStatus.OK);
    }

    @GetMapping("/results/get/question/{id}")
    public ResponseEntity<?> getResultsForQuestion(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(questionService.get(id) == null) return new ResponseEntity<>("Question with given id doesn't exist", HttpStatus.BAD_REQUEST);
        if(!questionService.get(id).isShareable() && (questionService.get(id).getOwner() != user.getId())) {
            return new ResponseEntity<>("Specified question isn't shareable and you're not the owner", HttpStatus.BAD_REQUEST);
        }
        List<TestResults> testResultsList = testResultsService.getAllQuestionResults(id);
        return new ResponseEntity<>(testResultsList, HttpStatus.OK);
    }

    @GetMapping("/results/get/question/{id}/user/{uid}")
    public ResponseEntity<?> getResultsForQuestionForUser(@RequestHeader Map<String, String> headers, @PathVariable Long id, @PathVariable Long uid){
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(questionService.get(id) == null) return new ResponseEntity<>("Question with given id doesn't exist", HttpStatus.BAD_REQUEST);
        if(!questionService.get(id).isShareable() && (uid != user.getId())) {
            return new ResponseEntity<>("Specified question isn't shareable and you're not the owner", HttpStatus.BAD_REQUEST);
        }
        List<TestResults> testResultsList = testResultsService.getQuestionResults(id, uid);
        return new ResponseEntity<>(testResultsList, HttpStatus.OK);
    }

    @DeleteMapping("/results/delete/question/{id}")
    public ResponseEntity<?> deleteResultsForQuestion(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        Question question = questionService.get(id);
        if(question == null) return new ResponseEntity<>("Question not found", HttpStatus.BAD_REQUEST);
        List<TestResults> resultsToDelete = testResultsService.getQuestionResults(id,user.getId());
        if(resultsToDelete.isEmpty())  return new ResponseEntity<>("There are no results for given question, nothing to delete", HttpStatus.BAD_REQUEST);
        for(TestResults t : resultsToDelete) {
            testResultsService.deleteTestResults(t);
        }
        return new ResponseEntity<>( "Results deleted", HttpStatus.OK);
    }
    @DeleteMapping("/results/delete/test/{id}")
    public ResponseEntity<?> deleteResultsForTest(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        Test test = testService.getTest(id);
        if(test == null) return new ResponseEntity<>("Test not found", HttpStatus.BAD_REQUEST);
        List<TestResults> resultsToDelete = testResultsService.getTestResultsByOwner(id,user.getId());
        if(resultsToDelete.isEmpty())  return new ResponseEntity<>("There are no results for given question, nothing to delete", HttpStatus.UNAUTHORIZED);
        for(TestResults t : resultsToDelete) {
            testResultsService.deleteTestResults(t);
        }
        return new ResponseEntity<>( "Results deleted", HttpStatus.OK);
    }

    @PostMapping("/results/update")
    public ResponseEntity<?> editTestResult(@RequestHeader Map<String, String> headers, @RequestBody ResultForm resultForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(testService.getTest(resultForm.getTestId()) == null) return new ResponseEntity<>("Test with given id doesn't exist", HttpStatus.BAD_REQUEST);
        List<TestResults> savedTestResultsList = new ArrayList<>();
        List<ResultForm.Result> results =  resultForm.getResultList();
        List<TestResults> testResults = new ArrayList<>();
        int counter = 0;
        for(ResultForm.Result r : results) {
            if(questionService.get(r.getQuestionId()) == null) return new ResponseEntity<>("Question with given id doesn't exist", HttpStatus.BAD_REQUEST);
            if(testService.getTest(resultForm.getTestId()).getSubject() != questionService.get(r.getQuestionId()).getSubject()) {
                return new ResponseEntity<>("Test and question subjects don't match", HttpStatus.BAD_REQUEST);
            }
            if(testResultsService.getQuestionResults(r.getQuestionId(), user.getId()).isEmpty())  {
                return  new ResponseEntity<>("Results for question " + r.getQuestionId() + " don't exist", HttpStatus.BAD_REQUEST);
            }
            testResults.addAll(testResultsService.getQuestionResults(r.getQuestionId(), user.getId()));
            testResults.get(counter).setPoints(r.getPoints());
            counter++;
        }
        for(TestResults t : testResults) {
            TestResults savedResults = testResultsService.saveTestResults(t);
            if(savedResults != null) savedTestResultsList.add(savedResults);
        }
        return new ResponseEntity<>(savedTestResultsList, HttpStatus.OK);
    }


}
