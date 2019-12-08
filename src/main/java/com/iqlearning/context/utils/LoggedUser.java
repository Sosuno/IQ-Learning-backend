package com.iqlearning.context.utils;


import com.iqlearning.database.entities.User;

public class LoggedUser {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String avatar;
    private String bio;
    private String linkedIn;
    private String twitter;
    private String reddit;
    private String youtube;
    private String email;
    private String sessionID;

    public LoggedUser() {

    }
    public LoggedUser(User user, String sessionID) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.sessionID = sessionID;
        this.avatar = user.getAvatar();
        this.bio = user.getBio();
        this.linkedIn = user.getLinkedIn();
        this.twitter = user.getTwitter();
        this.reddit = user.getReddit();
        this.youtube = user.getYoutube();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getReddit() {
        return reddit;
    }

    public void setReddit(String reddit) {
        this.reddit = reddit;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }
}
