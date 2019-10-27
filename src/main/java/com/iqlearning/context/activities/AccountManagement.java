package com.iqlearning.context.activities;

import com.iqlearning.context.Objects.LoggedUser;
import com.iqlearning.database.entities.Session;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.interfaces.ISessionService;
import com.iqlearning.database.service.interfaces.IUserService;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Configurable
public class AccountManagement {

    private final IUserService service;
    private final ISessionService sessionService;
    private User user;
    private Session s;
    private LoggedUser loggedUser;

    @Autowired
    public AccountManagement(IUserService service, ISessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
    }

    /****
     * returns legend:
     *  LOGGED USER
     *      null -> no user with such username
     *  LOGGED USER ID:
     *      -1   -> wrong password
     *      -2   -> account blocked
     *
     */
    public LoggedUser login(String username, String password) {
        user = service.getUserByUsername(username);
        if(user.getId() == -1) return null;
        if(user.getLoginTries() < 3) {
            if(!user.getPassword().equals(password)){
                user.setLoginTries(user.getLoginTries()+1);
                if(user.getLoginTries() == 3) user.setStatus(1);
                service.saveUser(user);
                loggedUser = new LoggedUser();
                loggedUser.setId((long) -1);
            }else {
                if(user.getLoginTries() != 0){
                    user.setLoginTries(0);
                }
                s = sessionService.getSessionByUser(user.getId());
                if(s!=null)sessionService.deleteSession(s.getSessionID());
                s = sessionService.createSession(user.getId());
                loggedUser = new LoggedUser(user,s.getSessionID());
                changeStatus(2);
            }
        }else {
            loggedUser = new LoggedUser();
            loggedUser.setId((long) -2);
        }
        return loggedUser;
    }

    public LoggedUser loginWithSession(String sessionId) {
        user = service.getUserBySession(sessionId);
        loggedUser = new LoggedUser(user, sessionId);
        return loggedUser;
    }

    public void logout(String sessionId, String username){
        user = service.getUserByUsername(username);
        changeStatus(0);
        sessionService.deleteSession(sessionId);
    }

    private void changeStatus(int i){
        user.setStatus(i);
        service.saveUser(user);
    }

    /****
     * ID RETURN LEGEND:
     *      -1 --> username taken
     *      -2 --> email in use
     *
     */
    public LoggedUser register(String username,String password, String email, String name,String surname) {
        if(service.checkIfUserExists(username)) {
            loggedUser = new LoggedUser();
            loggedUser.setId((long) -1);
            return loggedUser;
        }
        if (service.checkIfEmailInUse(email)){
            loggedUser = new LoggedUser();
            loggedUser.setId((long) -2);
            return loggedUser;
        }
        user = new User(name,surname,username,password,email,2, new Timestamp(System.currentTimeMillis()), 0);
        user = service.saveUser(user);
        s = sessionService.createSession(user.getId());
        loggedUser = new LoggedUser(user,s.getSessionID());

        return loggedUser;
    }

    public LoggedUser register(String username,String password, String email) {
        return register(username,password,email,"","");
    }




}
