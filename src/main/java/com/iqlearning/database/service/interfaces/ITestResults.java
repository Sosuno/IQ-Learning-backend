package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.entities.TestResults;

import java.util.List;

public interface ITestResults {

    List<TestResults> getAllTestResults(Long Id);
    List<TestResults> getTestResultsByOwner(Long testId, Long userId);
    List<TestResults> getAllQuestionResults(Long questionId);
    List<TestResults> getQuestionResults(Long questionId, Long userId);

    TestResults saveTestResults(TestResults s);

    void deleteTestResults(TestResults s);
    void deleteTestResults(Long id);

}
