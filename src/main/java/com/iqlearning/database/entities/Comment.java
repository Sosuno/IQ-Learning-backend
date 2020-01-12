package com.iqlearning.database.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Time;
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
    private Timestamp createdOn = new Timestamp(System.currentTimeMillis());
    @Column(name = "edited_on")
    private Timestamp editedOn = new Timestamp(System.currentTimeMillis());
    @Type(type = "long-array")
    @Column(name = "upvoted_by", columnDefinition = "bigint[]")
    private Long[] upvotedBy;

    public Comment(){}

    public Comment(Long articleId, Long commentator, String comment, int upvotes, Timestamp createdOn, Long[] upvotedBy) {
        this.articleId = articleId;
        this.commentator = commentator;
        this.comment = comment;
        this.upvotes = upvotes;
        this.createdOn = createdOn;
        this.editedOn = createdOn;
        this.upvotedBy = upvotedBy;
    }

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

    public Long[] getUpvotedBy() {
        return upvotedBy;
    }

    public void setUpvotedBy(Long[] upvotedBy) {
        this.upvotedBy = upvotedBy;
    }

    public Timestamp getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(Timestamp editedOn) {
        this.editedOn = editedOn;
    }
}