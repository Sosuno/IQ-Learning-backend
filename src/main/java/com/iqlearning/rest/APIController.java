package com.iqlearning.rest;

import com.iqlearning.context.activities.LoggedUser;
import com.iqlearning.database.entities.User;
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
@CrossOrigin(origins=("http://localhost:3000"))
public class APIController {

    @Autowired
    private APIService APIService;

    @Autowired
    private IUserService userService;
    @Autowired
    private ISessionService sessionService;


    @PostMapping("/user/token")
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
        APIService.setServices(userService,sessionService);
        if(APIService.login(loginForm) != null) {
            return new ResponseEntity<>(APIService.login(loginForm), HttpStatus.OK);
        } else return new ResponseEntity<>(APIService.login(loginForm), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterForm registerForm) {
        APIService.setServices(userService,sessionService);
        if(APIService.registerUser(registerForm) != null) {
            return new ResponseEntity<>(APIService.registerUser(registerForm), HttpStatus.OK);
        } else return new ResponseEntity<>(APIService.registerUser(registerForm), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user/refresh")
    public ResponseEntity<User> getUserByToken(@RequestBody String token) {
        APIService.setServices(userService,sessionService);
        User user = userService.getUserBySession(token);
        if(user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }


}
