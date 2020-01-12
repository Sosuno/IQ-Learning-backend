package com.iqlearning.database.repository;

import com.iqlearning.database.entities.Comment;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends CrudRepository<Comment,Long>{

    List<Comment> getAllByCommentatorOrderByUpvotesDesc(Long userId);
    List<Comment> getAllByCommentatorOrderByCreatedOnDesc(Long userId);
    List<Comment> getAllByArticleIdOrderByUpvotes(Long articleId);
    List<Comment> getAllByArticleIdOrderByCreatedOn(Long articleId);

    @Procedure(procedureName = "updateArticlesUpvotes")
    Comment updateUpvote(Long[] upvotes, Long id);
}
