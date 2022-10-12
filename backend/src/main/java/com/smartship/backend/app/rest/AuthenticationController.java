package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.config.GlobalConfig;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/authentication")
public class AuthenticationController {

    UserRepository userRepository;
    GlobalConfig globalConfig;

    @Autowired
    public AuthenticationController(UserRepository userRepository, GlobalConfig globalConfig) {
        this.userRepository = userRepository;
        this.globalConfig = globalConfig;
    }

    @PostMapping(path = "login")
    public ResponseEntity<User> loginUser(@RequestBody ObjectNode body) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(body);

        String email = node.findValue("email").asText();
        String password = node.findValue("password").asText();

        String passwordConfirmation = email.split("@")[0];

        if (!email.isEmpty() && !password.isEmpty() && password.equals(passwordConfirmation)) {
            User newUser = new User(
                    passwordConfirmation,
                    "Smith",
                    email,
                    password,
                    LocalDate.now(),
                    User.ROLE.Operator,
                    ""
            );
            User savedUser = userRepository.save(newUser);

            // token stuff
            JWToken jwToken = new JWToken(savedUser.getFirstname(), savedUser.getId(), savedUser.getRole().toString());
            String tokenString = jwToken.encode(globalConfig.issuer, globalConfig.getPassphrase(),
                    globalConfig.tokenDurationOfValidity);

            return ResponseEntity.accepted()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                    .body(savedUser);
        } else {
            throw new NotAcceptableException("Wrong login information provided");
        }
    }
}
