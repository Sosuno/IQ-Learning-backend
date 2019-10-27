package com.iqlearning.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "owner", nullable = false)
    private Long owner;
    @Column(name = "subject", nullable = false)
    private Long subject;
    @Column(name = "question", nullable = false)
    private String question;
    @Column(name = "choice_test", nullable = false)
    private boolean choiceTest;
    @Column(name = "shareable", nullable = false)
    private boolean shareable;

    public Question(Long owner, Long subject, String question, boolean choice_test, boolean shareable) {
        this.owner = owner;
        this.subject = subject;
        this.question = question;
        this.choiceTest = choice_test;
        this.shareable = shareable;
    }
    public Question() {

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isChoice_test() {
        return choiceTest;
    }

    public void setChoice_test(boolean choice_test) {
        this.choiceTest = choice_test;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }
}
