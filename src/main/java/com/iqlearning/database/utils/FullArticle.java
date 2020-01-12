package com.iqlearning.database.utils;

import com.iqlearning.database.entities.Articles;
import com.iqlearning.database.entities.Tag;

import java.sql.Timestamp;


public class FullArticle {

    private Long id;
    private Owner owner;
    private String title;
    private String content;
    private int upvotes;
    private Timestamp createdOn;
    private Long[] upvotedBy;
    private Tag[] tags;
    private byte[] image;

    public FullArticle(Articles a, Owner owner, Tag[] tags){
        this.id = a.getId();
        this.owner = owner;
        this.content = a.getContent();
        this.upvotes = a.getUpvotes();
        this.createdOn = a.getCreatedOn();
        this.upvotedBy = a.getUpvotedBy();
        this.image = a.getImage();
        this.title = a.getTitle();
        this.tags = tags;
    }
    public FullArticle(){}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
