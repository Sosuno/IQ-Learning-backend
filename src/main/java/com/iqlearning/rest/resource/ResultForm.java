package com.iqlearning.rest.resource;

import java.util.List;

public class ResultForm {

    private Long testId;

    private List<Result> resultList;

    public static class Result {
        private Long questionId;
        private double points;
        private Long studentId;

        public Long getQuestionId() {
            return questionId;
        }

        public void setQuestionId(Long questionId) {
            this.questionId = questionId;
        }

        public double getPoints() {
            return points;
        }

        public void setPoints(double points) {
            this.points = points;
        }

        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public Result(Long questionId, double points, Long studentId) {
            this.questionId = questionId;
            this.points = points;
            this.studentId = studentId;
        }


    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }





}
