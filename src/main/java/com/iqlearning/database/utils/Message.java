package com.iqlearning.database.utils;

import java.sql.Timestamp;

public class Message {
    private String message;
    private Long sender;
    private Long recipient;
    private Timestamp sent;

    public Message(String message) {
        this.message = message;
    }

    public Message(String message, Long sender, Long recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public Long getRecipient() {
        return recipient;
    }

    public void setRecipient(Long recipient) {
        this.recipient = recipient;
    }

    public Timestamp getSent() {
        return sent;
    }

    public void setSent(Timestamp sent) {
        this.sent = sent;
    }
}
