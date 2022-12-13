package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.UserManagementRepository;
import com.smartship.backend.app.utility.JWTokenInfo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/userManagement")
public class UserManagementController {

    private final UserManagementRepository userManagementRepository;

    @Autowired
    public UserManagementController(UserManagementRepository userManagementRepository) {
        this.userManagementRepository = userManagementRepository;
    }

    @GetMapping(path = "/{role}")
    public ResponseEntity<List<User>> findAllAccountsForRole(@PathVariable String role) {
        List<User> foundUsers = userManagementRepository.findByRole(User.ROLE.valueOf(role));

        return ResponseEntity.ok().body(foundUsers);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable long id) {

        User foundUser = userManagementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s wasn't found", id)));

        userManagementRepository.deleteById(id);

        return ResponseEntity.ok().body(foundUser);
    }

    @PostMapping(path = "/{role}")
    public ResponseEntity<User> addNewAccount(@RequestBody ObjectNode body, @PathVariable String role) {
        String email = body.path("email").asText();
        String firstName = body.path("firstName").asText();
        String lastName = body.path("lastName").asText();
        String password = BCrypt.hashpw(body.path("password").asText(), BCrypt.gensalt());

        User user = new User(
                firstName,
                lastName,
                email,
                password,
                LocalDate.now(),
                User.ROLE.valueOf(role),
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        );

        userManagementRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

    @PutMapping(path = "")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        User finalUpdatedUser = updatedUser;

        User foundUser = userManagementRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        finalUpdatedUser.getId()
                )));

        foundUser.setFirstName(updatedUser.getFirstName());
        foundUser.setLastName(updatedUser.getLastName());
        foundUser.setEmail(updatedUser.getEmail());

        updatedUser = userManagementRepository.save(foundUser);
        return ResponseEntity.ok().body(updatedUser);
    }

}
