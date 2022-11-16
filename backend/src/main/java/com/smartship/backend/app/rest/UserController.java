package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.exceptions.NotAcceptableException;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWTokenInfo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserRepository userRepository;

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
    public ResponseEntity<User> findUserById(@PathVariable Long id) {

        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s wasn't found", id)));

        return ResponseEntity.ok().body(foundUser);
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @RequestBody User updatedUser) {
        User foundUser = userRepository.findById(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        jwTokenInfo.userId()
                )));

        foundUser.setFirstName(updatedUser.getFirstName());
        foundUser.setLastName(updatedUser.getLastName());
        foundUser.setEmail(updatedUser.getEmail());
        // TODO save birthday
        foundUser.setBio(updatedUser.getBio());

        updatedUser = userRepository.save(foundUser);
        return ResponseEntity.ok().body(updatedUser);
    }

    @PutMapping(path = "password")
    public ResponseEntity<String> updateUserPassword(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @RequestBody ObjectNode body) {
        String password = body.path("oldPassword").asText();
        String newPassword = body.path("newPassword").asText();

        User foundUser = userRepository.findById(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        jwTokenInfo.userId()
                )));

        // check the given password
        if (BCrypt.checkpw(password, foundUser.getHashedPassword())) {
            String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // save the new password
            foundUser.setHashedPassword(newHashedPassword);
            userRepository.save(foundUser);

            return ResponseEntity.accepted().body("Password updated");
        } else {
            throw new NotAcceptableException("Provided password doesn't match account");
        }
    }

    @GetMapping(path = "notification-prefrences")
    public ResponseEntity<?> findAllNotificationPreferences(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        // TODO get all notification settings
        return ResponseEntity.ok().body("Ayyyyy");
    }
}
