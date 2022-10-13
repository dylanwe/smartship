package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.config.GlobalConfig;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWToken;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        // Convert request to JsonNode
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(body);

        // Get info from JSON request
        String email = node.findValue("email").asText();
        String password = node.findValue("password").asText();

        // Find user with given email
        User foundUser = userRepository.findByEmail(email);

        // validate password
        if (!email.isEmpty() && !password.isEmpty() && BCrypt.checkpw(password, foundUser.getHashedPassword())) {
            // Create a new JWT token for the user
            JWToken jwToken = new JWToken(foundUser.getFirstname(), foundUser.getId(), foundUser.getRole());
            String tokenString = jwToken.encode(globalConfig.issuer, globalConfig.getPassphrase(),
                    globalConfig.tokenDurationOfValidity);

            return ResponseEntity.accepted()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                    .body(foundUser);
        } else {
            // Password was incorrect
            throw new NotAcceptableException("Wrong login information provided");
        }
    }
}
