package com.samuelmaia.api_educaweb.controllers;


import com.samuelmaia.api_educaweb.models.user.User;
import com.samuelmaia.api_educaweb.models.user.UserRepository;
import com.samuelmaia.api_educaweb.models.user.UserRequestPost;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    UserRepository userRepository;

    @PostMapping("/student")
    public ResponseEntity<User> registerStudent(@RequestBody UserRequestPost data){
        User user = new User(data);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
