package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Test;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends CrudRepository<Test,Long> {

    List<Test> getAllByOwnerAndSubjectOrderByLastEditedDesc(Long userId, Long subjectId);
    List<Test> getAllByOwnerOrderByLastEditedDesc(Long id);
    List<Test> getAllBySubjectAndShareableOrderByLastEditedDesc(Long subjectId, boolean share);
    List<Test> getAllByShareableOrderByLastEdited(boolean share);
}