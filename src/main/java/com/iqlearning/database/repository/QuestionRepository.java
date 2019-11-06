package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Long>{

    List<Question> getAllByOwnerOrderByLastEditedDesc(Long owner);
    List<Question> getAllBySubjectOrderByLastEditedDesc(Long subject);
    List<Question> getAllBySubjectAndOwnerOrderByLastEditedDesc(Long subject, Long owner);
    List<Question> getAllByOwnerAndShareableOrderByLastEditedDesc(Long owner, boolean shareable);
    List<Question> getAllByShareableOrderByLastEditedDesc(boolean shareable);
    List<Question> getAllByOwnerAndChoiceTestOrderByLastEditedDesc(Long owner, boolean choice);
    List<Question> getAllBySubjectAndChoiceTestOrderByLastEditedDesc(Long subject, boolean choice);
    List<Question> getAllBySubjectAndOwnerAndShareableAndChoiceTestOrderByLastEditedDesc(Long subject, Long owner, boolean shareable, boolean choice);
    List<Question> getAllBySubjectAndOwnerAndShareableOrderByLastEditedDesc(Long subject, Long owner, boolean shareable);
    List<Question> getAllBySubjectAndOwnerAndChoiceTestOrderByLastEditedDesc(Long subject, Long owner, boolean choice);
    List<Question> getAllByQuestionContainsOrderByLastEditedDesc(String question);


}
