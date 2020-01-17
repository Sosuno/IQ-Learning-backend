package com.iqlearning.chat;

import java.sql.Timestamp;

public class Message {
    private Long from;
    private Long to;
    private String content;
    private Timestamp sentOn;

    public Message(){}

    public Message(Long from, Long to, String content, Timestamp sentOn) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.sentOn = sentOn;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSentOn() {
        return sentOn;
    }

    public void setSentOn(Timestamp sentOn) {
        this.sentOn = sentOn;
    }
}