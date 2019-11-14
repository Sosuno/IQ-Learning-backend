package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.entities.Test;

import java.util.List;

public interface ITestService {

    List<Test> getTestsByUserAndSubject(Long userId, Long subjectId);
    List<Test> getTestsByUser(Long id);
    List<Test> getSharedTestsBySubject(Long subjectId);
    List<Test> getSharedTests();

    Test saveTest(Test test);

    void deleteTest(Test test);
    void deleteTest(Long id);
}
