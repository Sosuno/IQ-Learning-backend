package com.iqlearning.rest.resource;

public class TestForm {

    private Long id;
    private Boolean shareable;
    private Long subjectId;
    private Long[] questions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getShareable() {
        return shareable;
    }

    public void setShareable(Boolean shareable) {
        this.shareable = shareable;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long[] getQuestions() {
        return questions;
    }

    public void setQuestions(Long[] questions) {
        this.questions = questions;
    }
}
