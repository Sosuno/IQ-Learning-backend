package com.iqlearning.database.entities;

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

    public Articles() {}

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
}