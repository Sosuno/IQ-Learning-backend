package com.iqlearning.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "question_id", nullable = false)
    private Long questionId;
    @Column(name = "answer", nullable = false)
    private String answer;
    @Column(name = "correct", nullable = false)
    private boolean correct;
    @Column(name = "created", nullable = false)
    private Timestamp created;
    @Column(name = "last_edited")
    private Timestamp lastEdited;


    public Answer(Long question_id, String answer, boolean correct) {
        this.questionId = question_id;
        this.answer = answer;
        this.correct = correct;
    }

    public Answer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestion_id() {
        return questionId;
    }

    public void setQuestion_id(Long question_id) {
        this.questionId = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
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


}
