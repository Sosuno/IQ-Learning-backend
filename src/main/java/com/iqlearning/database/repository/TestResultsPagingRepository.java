package com.iqlearning.database.repository;

import com.iqlearning.database.entities.TestResults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultsPagingRepository extends PagingAndSortingRepository<TestResults,Long> {

    List<TestResults> findAllByTestIdAndResultsOwnerOrderByCreatedDesc(Long id,Long owner, Pageable pageable);
    List<TestResults> findAllByQuestionIdAndResultsOwnerOrderByCreatedDesc(Long id,Long owner, Pageable pageable);
    List<TestResults> findAllByTestIdOrderByCreatedDesc(Long id, Pageable pageable);
    List<TestResults> findAllByQuestionIdOrderByCreatedDesc(Long id, Pageable pageable);
    List<TestResults> findAllByResultsOwnerOrderByCreatedDesc(Long id, Pageable pageable);
}
