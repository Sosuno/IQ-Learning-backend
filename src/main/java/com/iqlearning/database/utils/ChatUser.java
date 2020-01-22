package com.iqlearning.database.utils;

public class ChatUser {

    private Long id;
    private String username;
    private String avatar;

    public ChatUser(Long id, String username, String avatar) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
