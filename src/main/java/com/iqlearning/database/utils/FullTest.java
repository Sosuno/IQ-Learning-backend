package com.iqlearning.database.utils;

import com.iqlearning.database.entities.Test;

import java.sql.Timestamp;
import java.util.List;

public class FullTest {

    private Long id;
    private Long owner;
    private Long subject;
    private List<FilledQuestion> questions;
    private boolean shareable;
    private Timestamp created;
    private Timestamp lastEdited;
    private String title;
    private int downloads;


    public Long getId() {
        return id;
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

    public List<FilledQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<FilledQuestion> questions) {
        this.questions = questions;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
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

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public FullTest(Test t, List<FilledQuestion> questionList) {
        this.id = t.getId();
        this.owner = t.getOwner();
        this.subject = t.getSubject();
        this.questions = questionList;
        this.shareable = t.isShareable();
        this.created = t.getCreated();
        this.lastEdited = t.getLastEdited();
        this.downloads = t.getDownloads();
        this.title = t.getTitle();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
