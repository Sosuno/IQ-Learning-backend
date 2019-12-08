package com.iqlearning.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "tests_results")
public class TestResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "test_id", nullable = false)
    private Long testId;
    @Column(name = "question_id", nullable = false)
    private Long questionId;
    @Column(name = "points", nullable = false)
    private float points;
    @Column(name = "results_owner", nullable = false)
    private Long resultsOwner;
    @Column(name = "student_id")
    private Long studentId;
    @Column(name = "created")
    private Timestamp created;

    public TestResults() {}

    public TestResults(Long test_id, Long question_id, float points, Long resultsOwner) {
        this.testId = test_id;
        this.questionId = question_id;
        this.points = points;
        this.resultsOwner = resultsOwner;
    }
    /*
    * Do not use, until students implementation
    * */
    public TestResults(Long testId, Long questionId, float points, Long resultsOwner, Long studentId, Timestamp created) {
        this.testId = testId;
        this.questionId = questionId;
        this.points = points;
        this.resultsOwner = resultsOwner;
        this.studentId = studentId;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long test_id) {
        this.testId = test_id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long question_id) {
        this.questionId = question_id;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public Long getResultsOwner() {
        return resultsOwner;
    }

    public void setResultsOwner(Long resultsOwner) {
        this.resultsOwner = resultsOwner;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
