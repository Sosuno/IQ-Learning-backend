package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Long>{

    List<Question> getAllByOwner(Long owner);
    List<Question> getAllBySubject(Long subject);
    List<Question> getAllBySubjectAndOwner(Long subject,Long owner);
    List<Question> getAllByOwnerAndShareable(Long owner,boolean shareable);
    List<Question> getAllByShareable(boolean shareable);
    List<Question> getAllByOwnerAndChoiceTest(Long owner, boolean choice);
    List<Question> getAllBySubjectAndChoiceTest(Long subject, boolean choice);
    List<Question> getAllBySubjectAndOwnerAndShareableAndChoiceTest(Long subject,Long owner,boolean shareable, boolean choice);
    List<Question> getAllBySubjectAndOwnerAndShareable(Long subject,Long owner,boolean shareable);
    List<Question> getAllBySubjectAndOwnerAndChoiceTest(Long subject,Long owner, boolean choice);
    List<Question> getAllByQuestionContains(String question);

}
