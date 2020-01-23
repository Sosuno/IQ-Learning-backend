package com.iqlearning.database.repository;

import com.iqlearning.database.entities.TestResults;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TestResultsRepository extends CrudRepository<TestResults,Long> {

    List<TestResults> getAllByTestId(Long testId);
    List<TestResults> getAllByTestIdAndResultsOwner(Long testId, Long ownerId);
    List<TestResults> getAllByQuestionId(Long questionId);
    List<TestResults> getAllByQuestionIdAndResultsOwner(Long questionId, Long ownerId);
}