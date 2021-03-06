package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Articles;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticlesRepository extends CrudRepository<Articles, Long> {

    List<Articles> getAllByOwnerOrderByUpvotesDesc(Long userId);
    List<Articles> getAllByOrderByUpvotesDesc();

    List<Articles> getAllByTagsInAndTagsIsNotNullOrderByUpvotes(Long[] tags);

}
