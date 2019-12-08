package com.iqlearning.database.service.interfaces;

import com.iqlearning.database.entities.Articles;
import com.iqlearning.database.entities.Comment;

import java.util.List;

public interface IArticlesService {

    List<Articles> getAll();

    List<Articles> getUserArticles(Long userId);

    List<Comment> getArticleComments(Long articleId);

}
