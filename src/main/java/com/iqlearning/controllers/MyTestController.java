package com.iqlearning.controllers;


import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MyTestController {

    @Autowired
    private IUserService userService;


    @RequestMapping(value = "/showUsers", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() {

        List<User> users =  userService.getAllUsers();

        return users;
    }
    @RequestMapping(value = "/showUser", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public User get1Users() {

        User u1 = userService.getUserByUsername("johnny");


        return u1;
    }
}
