package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.ManagerRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class ManagerController {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerController(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @GetMapping(path = "/operators")
    public ResponseEntity<List<User>> findAllOperators() {
        List<User> operators = managerRepository.findByRole(User.ROLE.Operator);

        return ResponseEntity.ok().body(operators);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable long id) {

        User foundUser = managerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s wasn't found", id)));

        managerRepository.deleteById(id);

        return ResponseEntity.ok().body(foundUser);
    }

    @PostMapping(path = "/operator")
    public ResponseEntity<User> addNewOperator(@RequestBody ObjectNode body) {

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
                User.ROLE.Operator,
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        );

        managerRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

}
