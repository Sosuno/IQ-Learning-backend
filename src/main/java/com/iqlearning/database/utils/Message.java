package com.iqlearning.database.utils;

import com.iqlearning.database.entities.Chat;

import java.sql.Timestamp;

public class Message {
    private Long id;
    private String message;
    private Long sender;
    private Long recipient;
    private Timestamp sent;
    private boolean read;

    public Message(String message) {
        this.message = message;
    }

    public Message(String message, Long sender, Long recipient, boolean read) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
        this.read = read;
    }
    public Message(String message, Long sender, Long recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;

    }
    public Message(Chat c) {
        this.id = c.getId();
        this.message = c.getMessage();
        this.sender = c.getSender();
        if (c.getUser1().equals(c.getSender()))recipient = c.getUser2();
        else recipient = c.getUser1();
        this.read = c.isRead();
        this.sent = c.getSentOn();

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

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
