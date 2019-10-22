package com.iqlearning.rest;

import com.iqlearning.context.activities.AccountManagement;
import com.iqlearning.context.activities.LoggedUser;
import com.iqlearning.database.service.ISessionService;
import com.iqlearning.database.service.IUserService;
import com.iqlearning.rest.resource.LoginForm;
import com.iqlearning.rest.resource.RegisterForm;
import com.iqlearning.rest.resource.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    private IUserService userService;
    @Autowired
    private ISessionService sessionService;

    private AccountManagement acc;


    @PostMapping("/user/token")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {

        acc = new AccountManagement(userService,sessionService);
        LoggedUser user = acc.login(loginForm.getUsername(), loginForm.getPassword());
        if(user == null) {
            return new ResponseEntity<>("Username not found", HttpStatus.FORBIDDEN);
        } else if(user.getId() == -1) {
            return new ResponseEntity<>("Bad password", HttpStatus.FORBIDDEN);
        } else if(user.getId() == -2) {
            return new ResponseEntity<>("Account suspended", HttpStatus.FORBIDDEN);
        } else return auth(user.getSessionID());
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterForm registerForm) {
        acc = new AccountManagement(userService,sessionService);
        LoggedUser user = acc.register(registerForm.getUsername(),registerForm.getPassword(),registerForm.getEmail(),registerForm.getName(),registerForm.getSurname());
        if(user.getId() == -1) {
            return new ResponseEntity<>( "Username taken", HttpStatus.NOT_ACCEPTABLE);
        } else if(user.getId() == -2) {
            return new ResponseEntity<>( "Email taken", HttpStatus.NOT_ACCEPTABLE);
        } else return auth(user.getSessionID());

    }

    @PostMapping("/user/refresh")
    public ResponseEntity<?> getUserByToken(@RequestBody Token token) {
        acc = new AccountManagement(userService,sessionService);
        LoggedUser loggedUser = acc.loginWithSession(token.getToken());
        if(loggedUser.getId() != -1) {
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization",
                    loggedUser.getSessionID());
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(loggedUser);
        } else return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> auth(String session) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization",
                session);
        Token token = new Token();
        token.setToken(session);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(token);
    }
}
