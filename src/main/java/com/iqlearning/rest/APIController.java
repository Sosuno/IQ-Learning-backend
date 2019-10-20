package com.iqlearning.rest;

import com.iqlearning.context.activities.LoggedUser;
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

    @PostMapping("/user/token")
    public LoggedUser login(@RequestBody LoginForm loginForm) {

        return APIService.login(loginForm);
    }

    @PostMapping("/user/register")
    public ResponseEntity<RegisterForm> registerUser(@RequestBody RegisterForm registerForm) {

        if(APIService.registerUser(registerForm)) {
            return new ResponseEntity<>(registerForm, HttpStatus.OK);
        } else return new ResponseEntity<>(registerForm, HttpStatus.NOT_ACCEPTABLE);
    }


}
