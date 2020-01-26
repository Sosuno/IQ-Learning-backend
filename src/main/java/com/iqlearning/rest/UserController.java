package com.iqlearning.rest;


import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.UserService;
import com.iqlearning.database.utils.LoggedUser;
import com.iqlearning.database.utils.PasswordEncoderConfig;
import com.iqlearning.rest.resource.LoginForm;
import com.iqlearning.rest.resource.UserForm;
import com.iqlearning.rest.resource.PasswordForm;
import com.iqlearning.rest.resource.RegisterForm;
import com.iqlearning.rest.resource.Token;
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
    private UserService userService;

    private PasswordEncoderConfig hash = new PasswordEncoderConfig();;

    @PutMapping("/user/token")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {

        LoggedUser user = userService.login(loginForm.getUsername(), loginForm.getPassword());
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
        LoggedUser user = userService.register(registerForm.getUsername(),registerForm.getPassword(),registerForm.getEmail(),registerForm.getName(),registerForm.getSurname());
        if(user.getId() == -1) {
            return new ResponseEntity<>( "Username taken", HttpStatus.NOT_ACCEPTABLE);
        } else if(user.getId() == -2) {
            return new ResponseEntity<>( "Email taken", HttpStatus.NOT_ACCEPTABLE);
        } else return auth(user.getSessionID(),user);
    }

    @DeleteMapping("/user/logout")
    public ResponseEntity<?> logoutUser(@RequestHeader Map<String, String> headers) {
        String token = headers.get("authorization").split(" ")[1];
        User u = userService.getUserBySession(token);
        if (u.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        userService.logout(token);
        if(userService.getSession(token) != null) {
            return new ResponseEntity<>( "Logout unsuccessful", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>( "Logout successful", HttpStatus.OK);
    }

    @GetMapping("/user/fetch")
    public ResponseEntity<?> getUserByToken(@RequestHeader Map<String, String> headers) {
        String session = headers.get("authorization").split(" ")[1];
        LoggedUser loggedUser = userService.loginWithSession(session);
        if(loggedUser.getId() != -1) {
            return auth(loggedUser.getSessionID(),loggedUser);
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Session expired");
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> editUser(@RequestHeader Map<String, String> headers, @RequestBody UserForm userForm) {
        String token = headers.get("authorization").split(" ")[1];
        LoggedUser user = userService.loginWithSession(token);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        else {
            if(userForm.getUsername() != null) user.setUsername(userForm.getUsername());
            if(userForm.getEmail() != null) user.setEmail(userForm.getEmail());
            if(userForm.getName() != null) user.setName(userForm.getName());
            if(userForm.getSurname() != null) user.setSurname(userForm.getSurname());
            if(userForm.getAvatar() != null) user.setAvatar(userForm.getAvatar());
            if(userForm.getBio() != null) user.setBio(userForm.getBio());
            if(userForm.getLinkedIn() != null) user.setLinkedIn(userForm.getLinkedIn());
            if(userForm.getTwitter() != null) user.setTwitter(userForm.getTwitter());
            if(userForm.getReddit() != null) user.setReddit(userForm.getReddit());
            if(userForm.getYoutube() != null) user.setYoutube(userForm.getYoutube());
            return new ResponseEntity<>( userService.updateUser(user), HttpStatus.OK);
        }
    }

    @PutMapping("/user/password")
    public ResponseEntity<?> editPassword(@RequestHeader Map<String, String> headers, @RequestBody PasswordForm passwordForm) {
        String token = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(token);
        if(user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        else if(!hash.passwordEncoder().matches(passwordForm.getCurrentPass(), user.getPassword())) {
            return new ResponseEntity<>("Current password doesn't match", HttpStatus.BAD_REQUEST);
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
