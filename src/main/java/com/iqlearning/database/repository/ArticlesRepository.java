package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Articles;
import com.iqlearning.database.entities.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticlesRepository extends CrudRepository<Articles, Long> {

    List<Articles> getAllByOwnerOrderByUpvotesDesc(Long userId);
    List<Articles> getAllByOwnerOrderByCreatedOnDesc(Long userId);
    List<Articles> getAllByOrderByUpvotesDesc();

    List<Articles> getAllByTagsInAndTagsIsNotNullOrderByUpvotes(Tag[] tags);

    @Query("UPDATE articles SET tags = :tags")
    Articles updateTags(@Param("tags") Tag[] tags);


}
