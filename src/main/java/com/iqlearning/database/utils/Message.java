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
    private Long conversationId;
    private String recipientUsername;

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
        this.read = c.isRead();
        this.sent = c.getSentOn();
        this.conversationId = c.getConversation();
    }
    public Message(Chat c, Long recipient, String recipientUsername) {
        this.id = c.getId();
        this.message = c.getMessage();
        this.sender = c.getSender();
        this.read = c.isRead();
        this.sent = c.getSentOn();
        this.recipient = recipient;
        this.conversationId = c.getConversation();
        this.recipientUsername = recipientUsername;
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

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", message='").append(message).append('\'');
        sb.append(", sender='").append(sender).append('\'');
        sb.append(", recipient='").append(recipient).append('\'');
        sb.append(", sent='").append(sent).append('\'');
        sb.append(", read='").append(read).append('\'');
        sb.append(", conversationId='").append(conversationId).append('\'');
        sb.append(", recipientUsername='").append(recipientUsername).append('\'');
        sb.append('}');
        return sb.toString();
    }
}