package com.smartship.backend.app.rest;

import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "{id}")
    public User findUserById(@PathVariable long id) {
        return userRepository.findUserById(id);
    }
}
