package com.iqlearning.database.utils;

import java.util.List;

public class QuestionResult {
    private String text;
    private List<Double> result;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Double> getResult() {
        return result;
    }

    public void setResult(List<Double> result) {
        this.result = result;
    }
}
