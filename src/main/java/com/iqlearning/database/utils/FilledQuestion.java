package com.iqlearning.database.utils;

import com.iqlearning.database.entities.Answer;
import com.iqlearning.database.entities.Question;
import com.iqlearning.database.entities.Subject;

import java.sql.Timestamp;
import java.util.List;

public class FilledQuestion {

    private Long id;
    private Long owner;
    private Subject subject;
    private String question;
    private boolean choiceTest;
    private boolean shareable;
    private Timestamp created;
    private Timestamp lastEdited;
    private List<Answer> answers;
    private int correctAnswers;

    public FilledQuestion(){

    }

    public FilledQuestion(Question q) {
        id = q.getId();
        owner = q.getOwner();
        question = q.getQuestion();
        choiceTest = q.isChoice_test();
        shareable = q.isShareable();
        created = q.getCreated();
        lastEdited = q.getLastEdited();
        correctAnswers = 0;
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
        for (Answer a: answers) {
            if(a.isCorrect()) correctAnswers++;
        }
        this.answers = answers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
