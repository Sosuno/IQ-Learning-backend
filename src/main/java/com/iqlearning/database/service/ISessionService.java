package com.iqlearning.database.service;
/*****
 * Author: Ewa Jasinska
 */

import com.iqlearning.database.entities.Session;

public interface ISessionService {

    Session getSession(String id);
    Session createSession(Long userId);
    Session getSessionByUser(Long userId);
    void deleteSession(String id);
    Session updateSession(Session s);
}
