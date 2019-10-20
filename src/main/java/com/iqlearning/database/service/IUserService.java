package com.iqlearning.database.service;

import com.iqlearning.database.entities.User;

import java.util.List;

public interface IUserService {

    User getUser(long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    boolean checkIfUserExists(String username);
    boolean checkIfEmailInUse(String email);
    List<User> getAllUsers();
    User saveUser(User user);
    boolean deleteUser(long id);

}
