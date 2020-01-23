package com.iqlearning.database.service;

import com.iqlearning.database.entities.Test;
import com.iqlearning.database.entities.TestResults;
import com.iqlearning.database.repository.TestRepository;
import com.iqlearning.database.repository.TestResultsRepository;
import com.iqlearning.database.repository.TestResultsPagingRepository;
import com.iqlearning.database.service.interfaces.ITestResults;
import com.iqlearning.database.service.interfaces.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
public class TestService implements ITestService, ITestResults{

    @Autowired
    TestRepository testRepository;
    @Autowired
    TestResultsRepository resultsRepository;

    @Autowired
    TestResultsPagingRepository testResultsPagingRepository;

    @Override
    public Test getTest(Long id) {
        Optional<Test> o = testRepository.findById(id);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public List<Test> getTestsByUserAndSubject(Long userId, Long subjectId) {
        return testRepository.getAllByOwnerAndSubjectOrderByLastEditedDesc(userId, subjectId);
    }

    @Override
    public List<Test> getTestsByUser(Long id) {
        return testRepository.getAllByOwnerOrderByLastEditedDesc(id);
    }

    @Override
    public List<Test> getSharedTestsBySubject(Long subjectId) {
        return testRepository.getAllBySubjectAndShareableOrderByLastEditedDesc(subjectId, true);
    }

    @Override
    public List<Test> getSharedTests() {
        return testRepository.getAllByShareableOrderByLastEdited(true);
    }

    @Override
    public Test saveTest(Test test) {
        return testRepository.save(test);
    }

    @Override
    public void deleteTest(Test test) {
        test.setOwner(1L);
        test.setShareable(false);
        testRepository.save(test);
    }

    @Override
    public void deleteTest(Long id) {
        deleteTest(getTest(id));
    }

    @Override
    public List<TestResults> getAllTestResults(Long testId) {
        return resultsRepository.getAllByTestId(testId);
    }

    @Override
    public List<TestResults> getTestResultsByOwner(Long testId, Long userId) {
        return resultsRepository.getAllByTestIdAndResultsOwner(testId,userId);
    }

    @Override
    public List<TestResults> getAllQuestionResults(Long questionId) {
        return resultsRepository.getAllByQuestionId(questionId);
    }

    @Override
    public List<TestResults> getQuestionResults(Long questionId, Long userId) {
        return resultsRepository.getAllByQuestionIdAndResultsOwner(questionId,userId);
    }

    @Override
    @Transactional
    public TestResults saveTestResults(TestResults s) {
        return resultsRepository.save(s);
    }

    @Override
    public void deleteTestResults(TestResults s) {
        resultsRepository.delete(s);
    }

    @Override
    public void deleteTestResults(Long id) {
        resultsRepository.deleteById(id);
    }

    @Override
    public List<TestResults> getAllTestResults(Long id, int limit) {
        return testResultsPagingRepository.findAllByTestIdOrderByCreatedDesc(id,new PageRequest(0,limit));
    }

    @Override
    public List<TestResults> getTestResultsByOwner(Long testId, Long userId, int limit) {
        return testResultsPagingRepository.findAllByTestIdAndResultsOwnerOrderByCreatedDesc(testId,userId,new PageRequest(0,limit));
    }

    @Override
    public List<TestResults> getQuestionResults(Long questionId, int limit) {
        return testResultsPagingRepository.findAllByQuestionIdOrderByCreatedDesc(questionId, new PageRequest(0,limit));
    }

    @Override
    public List<TestResults> getQuestionResultsByOwner(Long questionId, Long userId, int limit) {
        return testResultsPagingRepository.findAllByQuestionIdAndResultsOwnerOrderByCreatedDesc(questionId,userId, new PageRequest(0,limit));
    }
}
