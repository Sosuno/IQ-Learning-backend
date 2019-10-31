package com.iqlearning.rest;

import com.iqlearning.context.activities.AccountManagement;
import com.iqlearning.context.objects.LoggedUser;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.interfaces.ISessionService;
import com.iqlearning.database.service.interfaces.IUserService;
import com.iqlearning.rest.resource.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@CrossOrigin(origins=("http://localhost:3000"))
public class UserController {

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
        } else return auth(user.getSessionID(), user);
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterForm registerForm) {
        acc = new AccountManagement(userService,sessionService);
        LoggedUser user = acc.register(registerForm.getUsername(),registerForm.getPassword(),registerForm.getEmail(),registerForm.getName(),registerForm.getSurname());
        if(user.getId() == -1) {
            return new ResponseEntity<>( "Username taken", HttpStatus.NOT_ACCEPTABLE);
        } else if(user.getId() == -2) {
            return new ResponseEntity<>( "Email taken", HttpStatus.NOT_ACCEPTABLE);
        } else return auth(user.getSessionID(),user);
    }

    @DeleteMapping("/user/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization").split(" ")[1];
        acc = new AccountManagement(userService,sessionService);
        User u = userService.getUserBySession(token);
        if (u.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        acc.logout(token);
        if(sessionService.getSession(token) != null) {

            return new ResponseEntity<>( "Logout unsuccessful", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>( "Logout successful", HttpStatus.OK);
    }

    @GetMapping("/user/fetch")
    public ResponseEntity<?> getUserByToken(@RequestHeader Map<String, String> headers) {
        acc = new AccountManagement(userService,sessionService);
        String session = headers.get("authorization").split(" ")[1];
        LoggedUser loggedUser = acc.loginWithSession(session);
        if(loggedUser.getId() != -1) {
            return auth(loggedUser.getSessionID(),loggedUser);
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Session expired");
    }

    @PostMapping("/user/name")
    public ResponseEntity<?> editNameAndSurname(@RequestHeader Map<String, String> headers, @RequestBody UserForm userForm) {
        String token = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(token);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        else {
            if(userForm.getName() != null) user.setName(userForm.getName());
            if(userForm.getSurname() != null) user.setSurname(userForm.getSurname());
            return new ResponseEntity<>( userService.saveUser(user), HttpStatus.OK);
        }
    }

    @PostMapping("/user/username")
    public ResponseEntity<?> editUsername(@RequestHeader Map<String, String> headers, @RequestBody LoginForm loginForm) {
        String token = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(token);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        else {
            if(loginForm.getUsername() != null) user.setUsername(loginForm.getUsername());
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
        }
    }

    @PostMapping("/user/password")
    public ResponseEntity<?> editPassword(@RequestHeader Map<String, String> headers, @RequestBody PasswordForm passwordForm) {
        String token = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(token);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        else if(!passwordForm.getCurrentPass().equals(user.getPassword())) {
            return new ResponseEntity<>("Current password doesn't match" + user.getPassword(), HttpStatus.UNAUTHORIZED);
        } else {
            user.setPassword(passwordForm.getNewPass());
            return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
        }
    }

    private ResponseEntity<?> auth(String session, Object o) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization",
                session);
        Token token = new Token();
        token.setToken(session);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(o);
    }
}
