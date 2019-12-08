package com.iqlearning.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "articles_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "article_id")
    private Long articleId;
    @Column(name = "commentator")
    private Long commentator;
    @Column(name = "comment")
    private String comment;
    @Column(name = "upvotes")
    private int upvotes;
    @Column(name = "created_on")
    private Timestamp createdOn;

    public Comment(){}

    public Comment(Long articleId, Long commentator, String comment, int upvotes, Timestamp createdOn) {
        this.articleId = articleId;
        this.commentator = commentator;
        this.comment = comment;
        this.upvotes = upvotes;
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getCommentator() {
        return commentator;
    }

    public void setCommentator(Long commentator) {
        this.commentator = commentator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }
}