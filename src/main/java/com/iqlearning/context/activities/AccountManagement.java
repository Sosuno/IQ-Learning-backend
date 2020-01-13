package com.iqlearning.context.activities;

import com.iqlearning.database.utils.LoggedUser;
import com.iqlearning.database.utils.PasswordEncoderConfig;
import com.iqlearning.database.entities.Session;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.interfaces.ISessionService;
import com.iqlearning.database.service.interfaces.IUserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.List;

@Component
@Configurable
public class AccountManagement {

    private final IUserService service;
    private final ISessionService sessionService;
    private User user;
    private Session s;
    private LoggedUser loggedUser;
    private PasswordEncoderConfig hash;

    @Autowired
    public AccountManagement(IUserService service, ISessionService sessionService) {
        this.service = service;
        this.sessionService = sessionService;
        hash = new PasswordEncoderConfig();
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
            if(!hash.passwordEncoder().matches(password,user.getPassword())){
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

    public void logout(String sessionId){
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
        user = new User(name,surname,username,hash.passwordEncoder().encode(password),email,2, new Timestamp(System.currentTimeMillis()), 0);
        user = service.saveUser(user);
        s = sessionService.createSession(user.getId());
        loggedUser = new LoggedUser(user,s.getSessionID());

        return loggedUser;
    }

    public LoggedUser changePassword(String session, String newPassword) {
        user = service.getUserBySession(session);
        user.setPassword(hash.passwordEncoder().encode(newPassword));
        return new LoggedUser(service.saveUser(user), session);
    }

    public LoggedUser updateUser(LoggedUser loggedUser){
        user = service.getUserBySession(loggedUser.getSessionID());
        if(user.getName() != loggedUser.getName() && loggedUser.getName() != null) user.setName(loggedUser.getName());
        if(user.getSurname() != loggedUser.getSurname() && loggedUser.getSurname() != null) user.setSurname(loggedUser.getSurname());
        if(user.getEmail() != loggedUser.getEmail() && loggedUser.getEmail() != null) user.setEmail(loggedUser.getEmail());
        if(user.getAvatar() != loggedUser.getAvatar() && loggedUser.getAvatar() != null) user.setAvatar(loggedUser.getAvatar());
        if(user.getBio() != loggedUser.getBio() && loggedUser.getBio() != null) user.setBio(loggedUser.getBio());
        if(user.getLinkedIn() != loggedUser.getLinkedIn() && loggedUser.getLinkedIn() != null) user.setLinkedIn(loggedUser.getLinkedIn());
        if(user.getTwitter() != loggedUser.getTwitter() && loggedUser.getTwitter() != null)user.setTwitter(loggedUser.getTwitter());
        if(user.getReddit() != loggedUser.getReddit() && loggedUser.getReddit() != null)user.setReddit(loggedUser.getReddit());
        if(user.getYoutube() != (loggedUser.getYoutube())&& loggedUser.getYoutube() != null)user.setYoutube(loggedUser.getYoutube());

        return new LoggedUser(service.saveUser(user), loggedUser.getSessionID());
    }

    @PostConstruct
    private void init(){
        List<User> l = service.getAllUsers();
        for (User u : l){
            u.setPassword(hash.passwordEncoder().encode(u.getPassword()));
            service.saveUser(u);
        }
    }
}
