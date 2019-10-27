package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Long>{
}
