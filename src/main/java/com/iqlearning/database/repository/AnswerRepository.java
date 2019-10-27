package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends CrudRepository<Answer,Long> {

    List<Answer> getAllByQuestionId(Long questionId);
    Optional<Answer> getAllByQuestionIdAndCorrect(Long questionId,boolean correct);
    void deleteAllByQuestionId(Long questionId);

}
