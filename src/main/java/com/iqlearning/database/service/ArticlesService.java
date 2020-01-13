package com.iqlearning.database.service;

import com.iqlearning.database.entities.Articles;
import com.iqlearning.database.entities.Comment;
import com.iqlearning.database.entities.Tag;
import com.iqlearning.database.repository.ArticlesRepository;
import com.iqlearning.database.repository.CommentsRepository;
import com.iqlearning.database.repository.TagRepository;
import com.iqlearning.database.repository.UserRepository;
import com.iqlearning.database.service.interfaces.IArticlesService;
import com.iqlearning.database.utils.ArticleComment;
import com.iqlearning.database.utils.FullArticle;
import com.iqlearning.database.utils.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ArticlesService implements IArticlesService{

    @Autowired
    CommentsRepository commentsRepo;
    @Autowired
    TagRepository tagRepo;
    @Autowired
    ArticlesRepository articlesRepo;
    @Autowired
    UserRepository userRepo;


    @Override
    public List<FullArticle> getAll() {
        List<Articles> articles = articlesRepo.getAllByOrderByUpvotesDesc();

        return prepareArticles(articles);
    }

    @Override
    public List<FullArticle> getUserArticles(Long userId) {
        List<Articles> articles = articlesRepo.getAllByOwnerOrderByUpvotesDesc(userId);

        return prepareArticles(articles);
    }

    @Override
    public List<ArticleComment> getArticleComments(Long articleId) {
        List<ArticleComment> comments = new ArrayList<>();

        for(Comment c: commentsRepo.getAllByArticleIdOrderByUpvotes(articleId)) {
            Owner owner = new Owner(userRepo.findById(c.getCommentator()));
            comments.add(new ArticleComment(c,owner));
        }
        return comments;
    }

    @Override
    public Articles saveArticle(Articles a) {
        if(a.getId() != null) {
            Optional<Articles> o = articlesRepo.findById(a.getId());
            if (o.isPresent()) {
                Articles article = o.get();
                if (!Arrays.equals(a.getTags(), article.getTags()) && article.getTags() != null)
                    a.setTags(article.getTags());
                if (!a.getContent().equals(article.getContent()) && article.getContent() != null)
                    a.setContent(article.getContent());
                if (!a.getTitle().equals(article.getTitle()) && article.getContent() != null)
                    a.setTitle(article.getTitle());
                if (!(a.getUpvotes() != article.getUpvotes()) && article.getUpvotedBy() != null) {
                    a.setUpvotedBy(article.getUpvotedBy());
                    a.setUpvotes(article.getUpvotes());
                }
                a.setEditedOn(new Timestamp(System.currentTimeMillis()));
            } else return null;
        }
        return articlesRepo.save(a);
    }

    @Override
    public Comment saveComment(Comment c) {
        if(c.getId() != null) {
            Optional<Comment> o = commentsRepo.findById(c.getId());
            if (o.isPresent()) {
                Comment comment = o.get();
                if (!c.getComment().equals(comment.getComment()) && comment.getComment() != null)
                    c.setComment(comment.getComment());
                if (!(c.getUpvotes() != comment.getUpvotes()) && comment.getUpvotedBy() != null) {
                    c.setUpvotedBy(comment.getUpvotedBy());
                    c.setUpvotes(comment.getUpvotes());
                }
                c.setEditedOn(new Timestamp(System.currentTimeMillis()));
            } else return null;
        }
        return commentsRepo.save(c);
    }

    /*
    * return null - article does not exists
    * */
    @Override
    public Articles upvoteArticle(Long artId, Long userId) {
        Optional<Articles> o = articlesRepo.findById(artId);
        if (o.isPresent()) {
            Articles article = o.get();
            List<Long> upvotes = new ArrayList<>();
            if(article.getUpvotedBy() != null) {
                for (Long id : article.getUpvotedBy()) {
                    if (!id.equals(userId)) upvotes.add(id);
                }
                if(article.getUpvotedBy().length == upvotes.size()) upvotes.add(userId);
            }else upvotes.add(userId);


            if(upvotes.size() > 0) article.setUpvotedBy(upvotes.toArray(new Long[upvotes.size()]));
            else article.setUpvotedBy(null);

            article.setUpvotes(upvotes.size());
            return articlesRepo.save(article);
        }
        else return null;
    }

    @Override
    public Comment upvoteComment(Long comId, Long userId) {
        Optional<Comment> o = commentsRepo.findById(comId);
        if (o.isPresent()) {
            Comment comment = o.get();
            List<Long> upvotes = new ArrayList<>();
            if(comment.getUpvotedBy() != null) {
                for (Long id : comment.getUpvotedBy()) {
                    if (!id.equals(userId)) upvotes.add(id);
                }
                if(comment.getUpvotedBy().length == upvotes.size()) upvotes.add(userId);
            }else upvotes.add(userId);


            if(upvotes.size() > 0) comment.setUpvotedBy(upvotes.toArray(new Long[upvotes.size()]));
            else comment.setUpvotedBy(null);
            comment.setUpvotes(upvotes.size());
            return commentsRepo.save(comment);
        }
        else return null;
    }

    @Override
    public Articles editTags(Long tagId, Long artId) {
        Optional<Articles> o = articlesRepo.findById(artId);
        if(o.isPresent()) {
            Articles article = o.get();
            List<Long> tags = new ArrayList<>();
            if(article.getTags() != null) {
                for (Long id : article.getTags()) {
                    if (!id.equals(tagId)) tags.add(id);
                }
                if(article.getTags().length == tags.size()) tags.add(tagId);

            }else tags.add(tagId);

            if(tags.size() > 0) article.setTags(tags.toArray(new Long[tags.size()]));
            else article.setTags(null);
            return articlesRepo.save(article);
        }
        else return null;
    }

    @Override
    public Iterable<Tag> getTags() {
        return tagRepo.findAll();
    }

    @Override
    public Articles getArticle(Long Id) {
        Optional<Articles>  o = articlesRepo.findById(Id);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public Comment getComment(Long id) {
        Optional<Comment>  o = commentsRepo.findById(id);
        if (o.isPresent()) return o.get();
        return null;
    }

    private Tag getTag(Long t){
        Optional<Tag> o = tagRepo.findById(t);
        if (o.isPresent()) return o.get();
        return null;
    }

    private List<FullArticle> prepareArticles(List<Articles> articles) {
        List<FullArticle> readyArticles = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        for (Articles a: articles) {
            Owner owner = new Owner(userRepo.findById(a.getOwner()));
            if(a.getTags() != null) {
                for (Long t : a.getTags()) {
                    tags.add(getTag(t));
                }
                readyArticles.add(new FullArticle(a, owner, tags.toArray(new Tag[tags.size()])));
                tags.clear();
            }else readyArticles.add(new FullArticle(a, owner, null));
        }
        return  readyArticles;
    }
}
