package com.smartship.backend.app.rest;

import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "")
    public ResponseEntity<List<User>> findAllUser() {
        List<User> users = userRepository.findAll();

        return ResponseEntity.ok().body(users);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<User> findUserById(@PathVariable long id) {
        User foundUser = userRepository.findById(id);

        if (foundUser == null) {
            throw new NullPointerException("User with id wasn't found");
        }

        return ResponseEntity.ok().body(foundUser);
    }
}
