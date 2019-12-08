package com.iqlearning.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user1")
    private Long user1;
    @Column(name = "user2")
    private Long user2;
    @Column(name = "sender")
    private Long sender;
    @Column(name = "message")
    private String message;
    @Column(name = "sentOn")
    private Timestamp sentOn;

    public Chat() {}

    public Chat(Long user1, Long user2, Long sender, String message, Timestamp sentOn) {
        this.user1 = user1;
        this.user2 = user2;
        this.sender = sender;
        this.message = message;
        this.sentOn = sentOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser1() {
        return user1;
    }

    public void setUser1(Long user1) {
        this.user1 = user1;
    }

    public Long getUser2() {
        return user2;
    }

    public void setUser2(Long user2) {
        this.user2 = user2;
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
}
