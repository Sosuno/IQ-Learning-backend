package com.iqlearning.database.service;

import com.iqlearning.database.repository.ArticlesRepository;
import com.iqlearning.database.repository.CommentsRepository;
import com.iqlearning.database.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService {

    @Autowired
    CommentsRepository commentsRepo;
    @Autowired
    TagRepository tagRepo;
    @Autowired
    ArticlesRepository articlesRepo;

}
