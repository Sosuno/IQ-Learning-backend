package com.iqlearning.database.service;
/*****
 * Author: Ewa Jasinska
 */
import com.iqlearning.database.entities.Session;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.repository.SessionRepository;
import com.iqlearning.database.repository.UserRepository;
import com.iqlearning.database.service.interfaces.IConversationService;
import com.iqlearning.database.service.interfaces.ISessionService;
import com.iqlearning.database.service.interfaces.IUserService;
import com.iqlearning.database.utils.LoggedUser;
import com.iqlearning.database.utils.PasswordEncoderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements IUserService,ISessionService {

    @Autowired
    private IConversationService convos;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    private User user;
    private Session s;
    private LoggedUser loggedUser;
    private PasswordEncoderConfig hash = new PasswordEncoderConfig();;

    @Override
    public User getUser(long id) {
        Optional<User> o = userRepository.findById(id);
        if( o.isPresent()){
            return o.get();
        }
        return new User(false);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> o = userRepository.findByUsername(username);
        if( o.isPresent()){
            return o.get();
        }
        return new User(false);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> o = userRepository.findByEmail(email);
        if( o.isPresent()){
            return o.get();
        }
        return new User(false);
    }

    @Override
    public User getUserBySession(String sessionId) {
        Optional<Long> o = userRepository.getUserBySession(sessionId);
        if(o.isPresent()){
            return getUser(o.get());
        }
        return new User(false);
    }

    @Override
    public boolean checkIfUserExists(String username) {
        return getUserByUsername(username).getId() != -1;
    }

    @Override
    public boolean checkIfEmailInUse(String email) {
        return getUserByEmail(email).getId() != -1;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean deleteUser(long id) {
        userRepository.deleteById(id);
        return !userRepository.existsById(id);
    }

    @Override
    public Session getSession(String id) {
        Optional<Session> o = sessionRepository.findById(id);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public Session createSession(Long userId) {
        Session s = new Session(UUID.randomUUID().toString(),userId, new Timestamp(System.currentTimeMillis()));
        return sessionRepository.save(s);
    }

    @Override
    public Session updateSession(Session s) {
        return sessionRepository.save(s);
    }

    @Override
    public Session getSessionByUser(Long userId) {
        Optional<Session> o = sessionRepository.getByUser(userId);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public void deleteSession(String id) {
        Session s = getSession(id);
        if (s != null){
            sessionRepository.delete(s);
        }
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

        user = getUserByUsername(username);
        if(user.getId() == -1) return null;
        if(user.getLoginTries() < 3) {
            if(!hash.passwordEncoder().matches(password,user.getPassword())){
                user.setLoginTries(user.getLoginTries()+1);
                if(user.getLoginTries() == 3) user.setStatus(1);
                saveUser(user);
                loggedUser = new LoggedUser();
                loggedUser.setId((long) -1);
            }else {
                if(user.getLoginTries() != 0){
                    user.setLoginTries(0);
                }
                s = getSessionByUser(user.getId());
                if(s!=null)deleteSession(s.getSessionID());
                s = createSession(user.getId());
                loggedUser = new LoggedUser(user,s.getSessionID(),convos.getAllUserConversation(user.getId()));
                changeStatus(2);
            }
        }else {
            loggedUser = new LoggedUser();
            loggedUser.setId((long) -2);
        }
        return loggedUser;
    }

    public LoggedUser loginWithSession(String sessionId) {
        user = getUserBySession(sessionId);
        loggedUser = new LoggedUser(user, sessionId,convos.getAllUserConversation(user.getId()));
        return loggedUser;
    }

    public void logout(String sessionId){
        deleteSession(sessionId);
    }

    private void changeStatus(int i){
        user.setStatus(i);
        saveUser(user);
    }

    /****
     * ID RETURN LEGEND:
     *      -1 --> username taken
     *      -2 --> email in use
     *
     */
    public LoggedUser register(String username,String password, String email, String name,String surname) {
        if(checkIfUserExists(username)) {
            loggedUser = new LoggedUser();
            loggedUser.setId((long) -1);
            return loggedUser;
        }
        if (checkIfEmailInUse(email)){
            loggedUser = new LoggedUser();
            loggedUser.setId((long) -2);
            return loggedUser;
        }
        user = new User(name,surname,username,hash.passwordEncoder().encode(password),email,2, new Timestamp(System.currentTimeMillis()), 0);
        user = saveUser(user);
        s = createSession(user.getId());
        loggedUser = new LoggedUser(user,s.getSessionID(),convos.getAllUserConversation(user.getId()));

        return loggedUser;
    }

    public LoggedUser changePassword(String session, String newPassword) {
        user = getUserBySession(session);
        user.setPassword(hash.passwordEncoder().encode(newPassword));
        return new LoggedUser(saveUser(user), session,convos.getAllUserConversation(user.getId()));
    }

    public LoggedUser updateUser(LoggedUser loggedUser){
        user = getUserBySession(loggedUser.getSessionID());
        if(user.getName() != loggedUser.getName() && loggedUser.getName() != null) user.setName(loggedUser.getName());
        if(user.getSurname() != loggedUser.getSurname() && loggedUser.getSurname() != null) user.setSurname(loggedUser.getSurname());
        if(user.getEmail() != loggedUser.getEmail() && loggedUser.getEmail() != null) user.setEmail(loggedUser.getEmail());
        if(user.getAvatar() != loggedUser.getAvatar() && loggedUser.getAvatar() != null) user.setAvatar(loggedUser.getAvatar());
        if(user.getBio() != loggedUser.getBio() && loggedUser.getBio() != null) user.setBio(loggedUser.getBio());
        if(user.getLinkedIn() != loggedUser.getLinkedIn() && loggedUser.getLinkedIn() != null) user.setLinkedIn(loggedUser.getLinkedIn());
        if(user.getTwitter() != loggedUser.getTwitter() && loggedUser.getTwitter() != null)user.setTwitter(loggedUser.getTwitter());
        if(user.getReddit() != loggedUser.getReddit() && loggedUser.getReddit() != null)user.setReddit(loggedUser.getReddit());
        if(user.getYoutube() != (loggedUser.getYoutube())&& loggedUser.getYoutube() != null)user.setYoutube(loggedUser.getYoutube());

        return new LoggedUser(saveUser(user), loggedUser.getSessionID(),convos.getAllUserConversation(user.getId()));
    }

    @PostConstruct
    private void init(){
        List<User> l = getAllUsers();
        for (User u : l){
            u.setPassword(hash.passwordEncoder().encode(u.getPassword()));
            saveUser(u);
        }
    }
}
