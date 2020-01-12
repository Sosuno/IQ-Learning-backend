package com.iqlearning.database.utils;

import com.iqlearning.database.entities.User;

import java.util.Optional;

public class Owner {

    private Long id;
    private String username;

    public Owner(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Owner(Optional<User> o) {
        id = o.get().getId();
        username = o.get().getUsername();
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
}
