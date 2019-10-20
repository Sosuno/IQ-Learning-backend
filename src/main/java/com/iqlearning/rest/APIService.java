package com.iqlearning.rest;

import com.iqlearning.context.activities.AccountManagement;
import com.iqlearning.context.activities.LoggedUser;
import com.iqlearning.database.service.ISessionService;
import com.iqlearning.database.service.IUserService;
import com.iqlearning.rest.resource.LoginForm;
import com.iqlearning.rest.resource.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class APIService {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISessionService sessionService;

    List <LoggedUser> users = new ArrayList<>();
    AccountManagement acc = new AccountManagement(userService,sessionService);


    public LoggedUser login(LoginForm loginForm) {


        users.add(acc.login(loginForm.getUsername(), loginForm.getPassword()));

        return users.get(users.size());
    }


    public boolean registerUser(RegisterForm registerForm) {

        int size = users.size();
        users.add(acc.register(registerForm.getUsername(),registerForm.getPassword(),registerForm.getEmail(),registerForm.getName(),registerForm.getSurname()));

        if(size != users.size()) {
            return false;
        } else {
            return true;
        }
    }

}