package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends CrudRepository<Test,Long> {

    List<Test> getAllByOwnerAndSubjectOrderByLastEditedDesc(Long userId, Long subjectId);
    List<Test> getAllByOwnerOrderByLastEditedDesc(Long id);
    List<Test> getAllBySubjectAndShareableOrderByLastEditedDesc(Long subjectId, boolean share);
    List<Test> getAllByShareableOrderByLastEdited(boolean share);
}
