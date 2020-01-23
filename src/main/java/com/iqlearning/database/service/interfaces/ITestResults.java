package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.entities.TestResults;

import java.awt.print.Pageable;
import java.util.List;

public interface ITestResults {

    List<TestResults> getAllTestResults(Long Id);

    List<TestResults> getTestResultsByOwner(Long testId, Long userId);
    List<TestResults> getAllQuestionResults(Long questionId);
    List<TestResults> getQuestionResults(Long questionId, Long userId);

    TestResults saveTestResults(TestResults s);

    void deleteTestResults(TestResults s);
    void deleteTestResults(Long id);

    List<TestResults> getAllTestResults(Long id, int limit);
    List<TestResults> getTestResultsByOwner(Long testId, Long userId, int limit);
    List<TestResults> getQuestionResults(Long questionId, int limit);
    List<TestResults> getQuestionResultsByOwner(Long questionId, Long userId, int limit);

    List<TestResults> getRandomResultsByOwner(Long userId, int limit);

}
