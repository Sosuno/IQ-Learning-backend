package com.iqlearning.database.service;
/*****
 * Author: Ewa Jasinska
 */

import com.iqlearning.database.entities.Session;
import com.iqlearning.database.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService implements ISessionService{

    @Autowired
    private SessionRepository repo;

    @Override
    public Session getSession(String id) {
        Optional<Session> o = repo.findById(id);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public Session createSession(Long userId) {
        Session s = new Session(UUID.randomUUID().toString(),userId, new Timestamp(System.currentTimeMillis()));
        return repo.save(s);
    }

    @Override
    public Session updateSession(Session s) {
        return repo.save(s);
    }

    @Override
    public Session getSessionByUser(Long userId) {
        Optional<Session> o = repo.getByUser(userId);
        if (o.isPresent()) return o.get();
        return null;
    }

    @Override
    public void deleteSession(String id) {
        Session s = getSession(id);
        if (s != null){
            repo.delete(s);
        }
    }
}
