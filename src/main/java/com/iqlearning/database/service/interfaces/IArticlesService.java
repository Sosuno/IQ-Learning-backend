package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.entities.Articles;
import com.iqlearning.database.entities.Comment;
import com.iqlearning.database.entities.Tag;
import com.iqlearning.database.utils.ArticleComment;
import com.iqlearning.database.utils.FullArticle;

import java.util.List;

public interface IArticlesService {

    List<FullArticle> getAll();

    List<FullArticle> getUserArticles(Long userId);

    List<ArticleComment> getArticleComments(Long articleId);

    Articles saveArticle(Articles a);

    Comment saveComment(Comment c);

    Articles upvoteArticle(Long artId, Long userId);

    Comment upvoteComment(Long artId, Long userId);

    Iterable<Tag> getTags();

    Articles editTags(Long tagId, Long artId);

    Articles getArticle(Long Id);

    Comment getComment(Long id);

}
