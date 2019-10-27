package com.iqlearning.database.entities;

import javax.persistence.*;

@Entity
@Table(name="answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "question_id", nullable = false)
    private Long question_id;
    @Column(name = "answer", nullable = false)
    private String answer;
    @Column(name = "correct", nullable = false)
    private boolean correct;

    public Answer(Long question_id, String answer, boolean correct) {
        this.question_id = question_id;
        this.answer = answer;
        this.correct = correct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
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


}
