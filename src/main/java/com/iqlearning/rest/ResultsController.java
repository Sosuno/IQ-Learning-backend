package com.iqlearning.rest;

import com.iqlearning.database.entities.TestResults;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.TestResultsService;
import com.iqlearning.database.service.interfaces.*;
import com.iqlearning.rest.resource.ResultForm;
import com.iqlearning.rest.utils.HeaderUtility;
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

    private HeaderUtility headerUtility = new HeaderUtility();


    @PutMapping("/results/add")
    public ResponseEntity<?> addTestResult(@RequestHeader Map<String, String> headers, @RequestBody ResultForm resultForm) {
        String session = headerUtility.getTokenFromHeader(headers);
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(testService.getTest(resultForm.getTestId()) == null) return new ResponseEntity<>("Test with given id doesn't exist", HttpStatus.BAD_REQUEST);
        List<TestResults> testResultsList = new ArrayList<>();
        List<TestResults> savedTestResultsList = new ArrayList<>();
        List<ResultForm.Result> results =  resultForm.getResultList();
        for(ResultForm.Result r : results) {
            TestResults testResults = new TestResults();
            testResults.setTestId(resultForm.getTestId());
            if(questionService.get(r.getQuestionId()) == null) return new ResponseEntity<>("Question with given id doesn't exist", HttpStatus.BAD_REQUEST);
            if(testService.getTest(resultForm.getTestId()).getSubject() != questionService.get(r.getQuestionId()).getSubject()) {
                return new ResponseEntity<>("Test and question subjects don't match", HttpStatus.BAD_REQUEST);
            }
            testResults.setQuestionId(r.getQuestionId());
            testResults.setPoints(r.getPoints());
            testResults.setStudentId(r.getStudentId());
            testResults.setResultsOwner(user.getId());
            testResults.setCreated(new Timestamp(System.currentTimeMillis()));
            testResultsList.add(testResults);
        }
        testResultsService = new TestResultsService();
        for(TestResults t : testResultsList) {
            TestResults savedResults = testResultsService.saveTestResults(t);
            if(savedResults != null) savedTestResultsList.add(savedResults);
        }
        return new ResponseEntity<>(savedTestResultsList, HttpStatus.OK);
    }

    @GetMapping("/results/get/test/{id}")
    public ResponseEntity<?> getResultsForTest(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headerUtility.getTokenFromHeader(headers);
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(testService.getTest(id) == null) return new ResponseEntity<>("Test with given id doesn't exist", HttpStatus.BAD_REQUEST);
        if(!testService.getTest(id).isShareable() && (testService.getTest(id).getOwner() != user.getId())) {
            return new ResponseEntity<>("Specified test isn't shareable and you're not the owner", HttpStatus.BAD_REQUEST);
        }
        List<TestResults> testResultList = testResultsService.getAllTestResults(id);
        if(testResultList.isEmpty()) return new ResponseEntity<>("No results for this test", HttpStatus.OK);
        return new ResponseEntity<>(testResultList, HttpStatus.OK);
    }

    @GetMapping("/results/get/question/{id}")
    public ResponseEntity<?> getResultsForQuestion(@RequestHeader Map<String, String> headers, @PathVariable Long id){
        String session = headerUtility.getTokenFromHeader(headers);
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(questionService.get(id) == null) return new ResponseEntity<>("Question with given id doesn't exist", HttpStatus.BAD_REQUEST);
        if(!questionService.get(id).isShareable() && (questionService.get(id).getOwner() != user.getId())) {
            return new ResponseEntity<>("Specified question isn't shareable and you're not the owner", HttpStatus.BAD_REQUEST);
        }
        List<TestResults> testResultsList = testResultsService.getAllQuestionResults(id);
        if(testResultsList.isEmpty()) return new ResponseEntity<>("No results for this test", HttpStatus.OK);
        return new ResponseEntity<>(testResultsList, HttpStatus.OK);
    }

    @GetMapping("/results/get/question/{id}/user/{uid}")
    public ResponseEntity<?> getResultsForQuestionForUser(@RequestHeader Map<String, String> headers, @PathVariable Long id, @PathVariable Long uid){
        String session = headerUtility.getTokenFromHeader(headers);
        User user = userService.getUserBySession(session);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(questionService.get(id) == null) return new ResponseEntity<>("Question with given id doesn't exist", HttpStatus.BAD_REQUEST);
        if(!questionService.get(id).isShareable() && (uid != user.getId())) {
            return new ResponseEntity<>("Specified question isn't shareable and you're not the owner", HttpStatus.BAD_REQUEST);
        }
        List<TestResults> testResultsList = testResultsService.getQuestionResults(id, uid);
        if(testResultsList.isEmpty()) return new ResponseEntity<>("No results for this test", HttpStatus.OK);
        return new ResponseEntity<>(testResultsList, HttpStatus.OK);
    }

}
