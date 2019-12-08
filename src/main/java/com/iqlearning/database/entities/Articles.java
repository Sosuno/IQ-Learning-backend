package com.iqlearning.database.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "articles")
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "owner")
    private Long owner;
    @Column(name = "contents")
    private String content;
    @Column(name = "upvotes")
    private int upvotes;
    @Column(name = "created_on")
    private Timestamp createdOn;
    @Column(name = "upvoted_by")
    @Type(type = "com.iqlearning.database.utils.GenericArrayUserType")
    private Long[] upvotedBy;
    @Column(name = "tags")
    @Type(type = "com.iqlearning.database.utils.GenericArrayUserType")
    private Long[] tags;

    public Articles() {}

    public Articles(Long owner, String content, int upvotes, Timestamp createdOn, Long[] upvotedBy, Long[] tags) {
        this.owner = owner;
        this.content = content;
        this.upvotes = upvotes;
        this.createdOn = createdOn;
        this.upvotedBy = upvotedBy;
        this.tags = tags;
    }

    public Articles(Long owner, String content, int upvotes, Timestamp createdOn) {
        this.owner = owner;
        this.content = content;
        this.upvotes = upvotes;
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
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

    public Long[] getTags() {
        return tags;
    }

    public void setTags(Long[] tags) {
        this.tags = tags;
    }
}