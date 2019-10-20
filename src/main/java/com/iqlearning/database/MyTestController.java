package com.iqlearning.database;


import com.iqlearning.context.activities.AccountManagement;
import com.iqlearning.context.activities.LoggedUser;
import com.iqlearning.database.entities.Session;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.ISessionService;
import com.iqlearning.database.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class MyTestController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISessionService sessionService;

    private User u1;
    private Session s;


    /**************
     * Accuont managaer
     */
    @RequestMapping(value = "/test/Acc/login", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<LoggedUser> login() {

        AccountManagement acc = new AccountManagement(userService,sessionService);
        List<LoggedUser> users = new ArrayList<>();
        users.add(acc.login("Archangel", "calibrate"));
        users.add(acc.login("FatherIssues","jacob"));
        users.add(acc.login("jane","jane"));

        return users;
    }
    @RequestMapping(value = "/test/Acc/session", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<LoggedUser> loginSession() {

        AccountManagement acc = new AccountManagement(userService,sessionService);
        List<LoggedUser> users = new ArrayList<>();
        users.add(acc.loginWithSession("1234-1234-1234-1234"));


        return users;
    }
    @RequestMapping(value = "/test/Acc/logout", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Session logout() {

        AccountManagement acc = new AccountManagement(userService,sessionService);
        s = sessionService.getSession("1234-1234-1234-1234");
        acc.logout(s.getSessionID(),userService.getUser(s.getUserID()).getUsername());
        acc.logout("123","Archangel");
        s = sessionService.getSession("1234-1234-1234-1234");
        return s;
    }

    /****************************************************************************************
     *  User tests~
     ***************************************************************************************/

    @RequestMapping(value = "/test/User/all", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() {

        List<User> users =  userService.getAllUsers();

        return users;
    }
    @RequestMapping(value = "/test/User/id", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public User getUser() {

        u1 = userService.getUser(10);

        return u1;
    }
    @RequestMapping(value = "/test/User/username", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public User getByUsername() {

        u1 = userService.getUserByUsername("johnny");
        return u1;
    }

    @RequestMapping(value = "/test/User/email", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public User getByEmail() {

        u1 = userService.getUserByEmail("doe@gmail.com");
        return u1;
    }
    @RequestMapping(value = "/test/User/add", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public User create() {

        User u2 = new User("Grunt","Urdnot","TankBreed","sheepaard","grunt@normandy.space",0,new Timestamp(System.currentTimeMillis()),0);
        u1 = userService.saveUser(u2);
        return u1;
    }
    @RequestMapping(value = "/test/User/change", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public User update() {
        u1 = userService.getUserByUsername("johnny");
        u1.setName("James");
        u1.setSurname("Vega");
        u1.setEmail("vega@normandy.space");
        u1 = userService.saveUser(u1);
        return u1;
    }
    @RequestMapping(value = "/test/User/check", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> available() {
        Map<String,String> checks = new TreeMap<>();
        checks.put("vega@normandy.space",userService.checkIfEmailInUse("vega@normandy.space") +"" );
        checks.put("liara@normandy.space",userService.checkIfEmailInUse("liara@normandy.space") +"" );
        checks.put("Archangel",userService.checkIfUserExists("Archangel") +"" );
        checks.put("TaliZorah",userService.checkIfEmailInUse("TaliZorah") +"" );
        return checks;
    }

    @RequestMapping(value = "/test/User/delete", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public User delete() {
        u1 = userService.getUserByUsername("johnny");
        userService.deleteUser(u1.getId());
        u1 = userService.getUserByUsername("johnny");
        return u1;
    }

    /****************************************************************************************
     *  Session tests~
     ***************************************************************************************/
    @RequestMapping(value = "/test/Session/create", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Session createSession() {

        s = sessionService.createSession((long) 2);
        return s;
    }
    @RequestMapping(value = "/test/Session/getByUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Session getSession1() {
        s = sessionService.getSessionByUser((long)1);
        return s;

    }
    @RequestMapping(value = "/test/Session/getByUUID", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Session getSession2() {
        s = sessionService.getSession("1234-1234-1234-1234");
        return s;
    }
    @RequestMapping(value = "/test/Session/change", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Session change() {
        s = sessionService.getSession("1234-1234-1234-1234");
        s.setUserID((long)3);
        s = sessionService.updateSession(s);
        return s;
    }

    @RequestMapping(value = "/test/Session/delete", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public Session deleteSession() {
        sessionService.deleteSession("1234-1234-1234-1234");
        s = sessionService.getSession("1234-1234-1234-1234");
        return s;
    }

}
