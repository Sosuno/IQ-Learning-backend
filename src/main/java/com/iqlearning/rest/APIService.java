package com.iqlearning.rest;

import com.iqlearning.context.activities.AccountManagement;
import com.iqlearning.context.activities.LoggedUser;
import com.iqlearning.database.service.ISessionService;
import com.iqlearning.database.service.IUserService;
import com.iqlearning.rest.resource.LoginForm;
import com.iqlearning.rest.resource.RegisterForm;
import org.springframework.stereotype.Service;


@Service
public class APIService {

    AccountManagement acc;

    public void setServices(IUserService userService, ISessionService sessionService) {
        acc = new AccountManagement(userService,sessionService);
    }

    public String login(LoginForm loginForm) {

        LoggedUser loggedUser = acc.login(loginForm.getUsername(), loginForm.getPassword());

        if(loggedUser == null) {
            return "Username not found";
        } else if(loggedUser.getId() == -1) {
            return "Bad password";
        } else if(loggedUser.getId() == -2) {
            return "Account suspended";
        } else return "Success";

    }

    public String registerUser(RegisterForm registerForm) {

        LoggedUser newUser = acc.register(registerForm.getUsername(),registerForm.getPassword(),registerForm.getEmail(),registerForm.getName(),registerForm.getSurname());

        if(newUser.getId() == -1) {
            return "Username taken";
        } else if(newUser.getId() == -2) {
            return "Email taken";
        } else return "Success";
    }

    public LoggedUser getUserBySession(String token) {
        LoggedUser loggedUser = acc.loginWithSession(token);
        return loggedUser;
    }

}