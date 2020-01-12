package com.iqlearning.database.utils;

import com.iqlearning.database.entities.Comment;

import java.sql.Timestamp;

public class ArticleComment {

    private Long id;
    private Owner commentator;
    private String comment;
    private int upvotes;
    private Timestamp createdOn;
    private Long[] upvotedBy;


    public ArticleComment(Comment a, Owner owner){
        this.id = a.getId();
        this.commentator = owner;
        this.comment = a.getComment();
        this.upvotes = a.getUpvotes();
        this.createdOn = a.getCreatedOn();
        this.upvotedBy = a.getUpvotedBy();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Owner getCommentator() {
        return commentator;
    }

    public void setCommentator(Owner commentator) {
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
}
