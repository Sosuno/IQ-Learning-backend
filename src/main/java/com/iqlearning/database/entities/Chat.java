package com.iqlearning.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "conversation")
    private Long conversation;
    @Column(name = "sender")
    private Long sender;
    @Column(name = "message")
    private String message;
    @Column(name = "sentOn")
    private Timestamp sentOn = new Timestamp(System.currentTimeMillis());
    @Column(name = "read")
    private boolean read;

    public Chat() {}

    public Chat(Long conversation, Long sender, String message, Timestamp sentOn, boolean read) {
        this.conversation = conversation;
        this.sender = sender;
        this.message = message;
        this.sentOn = sentOn;
        this.read = read;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getSentOn() {
        return sentOn;
    }

    public void setSentOn(Timestamp sentOn) {
        this.sentOn = sentOn;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public Long getConversation() {
        return conversation;
    }

    public void setConversation(Long conversation) {
        this.conversation = conversation;
    }
}
