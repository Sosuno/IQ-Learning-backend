package com.iqlearning.database.entities;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tests")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "owner")
    private Long owner;
    @Column(name = "subject")
    private Long subject;
    @Column(name = "questions")
    @Type(type = "com.iqlearning.database.utils.GenericArrayUserType")
    private Long[] questions;
    @Column(name = "shareable")
    private boolean shareable;
    @Column(name = "created")
    private Timestamp created;
    @Column(name = "last_edited")
    private Timestamp lastEdited;
    @Column(name = "downloads")
    private int downloads;

    public Test(Long owner, Long subject, Long[] questions, boolean shareable, int downloads) {
        this.owner = owner;
        this.subject = subject;
        this.questions = questions;
        this.shareable = shareable;
        this.downloads = downloads;
    }

    public Test(Long owner, Long subject, Long[] questions, boolean shareable, int downloads, Timestamp created, Timestamp lastEdited) {
        this.owner = owner;
        this.subject = subject;
        this.questions = questions;
        this.shareable = shareable;
        this.downloads = downloads;
        this.created = created;
        this.lastEdited = lastEdited;
    }

    public Test() {

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

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long subject) {
        this.subject = subject;
    }

    public Long[] getQuestions() {
        return questions;
    }

    public void setQuestions(Long[] questions) {
        this.questions = questions;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(Timestamp lastEdited) {
        this.lastEdited = lastEdited;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }
}
