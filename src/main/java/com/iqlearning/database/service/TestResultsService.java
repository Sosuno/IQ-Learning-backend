package com.iqlearning.database.service;

import com.iqlearning.database.entities.TestResults;
import com.iqlearning.database.repository.TestResultsRepository;
import com.iqlearning.database.service.interfaces.ITestResults;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestResultsService implements ITestResults {
    @Autowired
    TestResultsRepository repo;

    @Override
    public List<TestResults> getAllTestResults(Long testId) {
        return repo.getAllByTestId(testId);
    }

    @Override
    public List<TestResults> getTestResultsByOwner(Long testId, Long userId) {
        return repo.getAllByTestIdAndResultsOwner(testId,userId);
    }

    @Override
    public List<TestResults> getAllQuestionResults(Long questionId) {
        return repo.getAllByQuestionId(questionId);
    }

    @Override
    public List<TestResults> getQuestionResults(Long questionId, Long userId) {
        return repo.getAllByQuestionIdAndResultsOwner(questionId,userId);
    }

    @Override
    public TestResults saveTestResults(TestResults s) {
        return repo.save(s);
    }

    @Override
    public void deleteTestResults(TestResults s) {
        repo.delete(s);
    }

    @Override
    public void deleteTestResults(Long id) {
        repo.deleteById(id);

    }
}
