package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends CrudRepository<Subject,Long>{

    Optional<Subject> getAllByNameAndYear(String name, int year);
    List<Subject> getAllByName(String name);
    List<Subject> getAllByYear(int year);
}
