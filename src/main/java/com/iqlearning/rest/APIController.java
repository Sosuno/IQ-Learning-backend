package com.iqlearning.rest;

import com.iqlearning.context.activities.LoggedUser;
import com.iqlearning.database.service.ISessionService;
import com.iqlearning.database.service.IUserService;
import com.iqlearning.rest.resource.LoginForm;
import com.iqlearning.rest.resource.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="http://localhost:8081")
public class APIController {

    @Autowired
    private APIService APIService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISessionService sessionService;

    @PostMapping("/user/token")
    public LoggedUser login(@RequestBody LoginForm loginForm) {
        APIService.setServices(userService,sessionService);
        return APIService.login(loginForm);
    }

    @CrossOrigin(origins="http://localhost:8081")
    @PostMapping("/user/register")
    public ResponseEntity<RegisterForm> registerUser(@RequestBody RegisterForm registerForm) {
        APIService.setServices(userService,sessionService);
        if(APIService.registerUser(registerForm)) {
            return new ResponseEntity<>(registerForm, HttpStatus.OK);
        } else return new ResponseEntity<>(registerForm, HttpStatus.NOT_ACCEPTABLE);
    }


}
