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
    @Column(name = "title")
    private String title;
    @Column(name = "contents")
    private String content;
    @Column(name = "upvotes")
    private int upvotes;
    @Column(name = "created_on")
    private Timestamp createdOn = new Timestamp(System.currentTimeMillis());
    @Column(name = "edited_on")
    private Timestamp editedOn = new Timestamp(System.currentTimeMillis());
    @Type(type = "long-array")
    @Column(name = "upvoted_by",  columnDefinition = "bigint[]")
    private Long[] upvotedBy;
    @Type(type = "long-array")
    @Column(name = "tags", columnDefinition = "bigint[]")
    private Long[] tags;
    @Column(name="image")
    private byte[] image;

    public Articles() {}

    public Articles(Long owner, String content,String title, int upvotes, Timestamp createdOn, Long[] upvotedBy, Long[] tags) {
        this.owner = owner;
        this.content = content;
        this.upvotes = upvotes;
        this.createdOn = createdOn;
        this.editedOn = createdOn;
        this.upvotedBy = upvotedBy;
        this.tags = tags;
        image = null;
        this.title = title;
    }

    public Articles(Long owner, String content, int upvotes, Timestamp createdOn) {
        this.owner = owner;
        this.content = content;
        this.upvotes = upvotes;
        this.createdOn = createdOn;
        image = null;
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

    public Timestamp getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(Timestamp editedOn) {
        this.editedOn = editedOn;
    }
}