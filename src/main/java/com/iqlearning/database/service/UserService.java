package com.iqlearning.database.service;
/*****
 * Author: Ewa Jasinska
 */
import com.iqlearning.database.entities.User;
import com.iqlearning.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository repo;

    @Override
    public User getUser(long id) {
        Optional<User> o = repo.findById(id);
        if( o.isPresent()){
            return o.get();
        }
        return new User(false);
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> o = repo.findByUsername(username);
        if( o.isPresent()){
            return o.get();
        }
        return new User(false);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> o = repo.findByEmail(email);
        if( o.isPresent()){
            return o.get();
        }
        return new User(false);
    }

    @Override
    public User getUserBySession(String sessionId) {
        Optional<Long> o = repo.getUserBySession(sessionId);
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
        return (List<User>)repo.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repo.save(user);
    }

    @Override
    public boolean deleteUser(long id) {
        repo.deleteById(id);
        return !repo.existsById(id);
    }

    private User checkListResults(List<Optional<User>> results) {
        User user;
        switch(results.size()){
            case 0: {
                user = new User(false);break;
            }
            case 1: {
                user = results.get(0).get();break;
            }
            default: {
                user = null;
            }
        }
        return user;
    }

}
