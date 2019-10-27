package com.iqlearning.context.objects;


import com.iqlearning.database.entities.Answer;
import com.iqlearning.database.entities.Question;
import com.iqlearning.database.entities.Subject;

import java.util.List;

public class FilledQuestion {

    private Long id;
    private Long owner;
    private Subject subject;
    private String question;
    private boolean choiceTest;
    private boolean shareable;
    private List<Answer> answers;

    public FilledQuestion(){

    }

    public FilledQuestion(Question q) {
        id = q.getId();
        question = q.getQuestion();
        choiceTest = q.isChoice_test();
        shareable = q.isShareable();
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isChoiceTest() {
        return choiceTest;
    }

    public void setChoiceTest(boolean choiceTest) {
        this.choiceTest = choiceTest;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

}
