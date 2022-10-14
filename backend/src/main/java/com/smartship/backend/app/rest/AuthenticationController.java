package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.config.GlobalConfig;
import com.smartship.backend.app.models.RefreshToken;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.RefreshTokenRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWToken;
import com.smartship.backend.app.utility.RefreshTokenUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@RestController
@RequestMapping(path = "/authentication")
public class AuthenticationController {

    UserRepository userRepository;
    RefreshTokenRepository refreshTokenRepository;
    RefreshTokenUtil refreshTokenUtil;
    GlobalConfig globalConfig;

    @Autowired
    public AuthenticationController(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository,
                                    RefreshTokenUtil refreshTokenUtil, GlobalConfig globalConfig) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenUtil = refreshTokenUtil;
        this.globalConfig = globalConfig;
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> loginUser(@RequestBody ObjectNode body) {
        // Convert request to JsonNode
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(body);

        // Get info from JSON request
        String email = node.findValue("email").asText();
        String password = node.findValue("password").asText();

        // Find user with given email
        User foundUser = userRepository.findByEmail(email).orElse(null);

        // validate password
        if (!email.isEmpty() && !password.isEmpty() && foundUser != null && BCrypt.checkpw(password,
                foundUser.getHashedPassword())) {
            // Create a new JWT token for the user
            JWToken jwToken = new JWToken(foundUser.getEmail(), foundUser.getId(), foundUser.getRole());
            String tokenString = jwToken.encode(globalConfig.issuer, globalConfig.getPassphrase(),
                    globalConfig.tokenDurationOfValidity);

            // Get the refresh token
            RefreshToken refreshToken = refreshTokenUtil.createRefreshToken(foundUser.getId());

            // Create body of request
            Map<String, String> requestBody = new Hashtable<>();
            requestBody.put("jwtToken", tokenString);
            requestBody.put("type", "Bearer");
            requestBody.put("refreshToken", refreshToken.getToken());

            return ResponseEntity.accepted()
                    .body(requestBody);
        } else {
            // Password was incorrect
            throw new NotAcceptableException("Wrong login information provided");
        }
    }

    @PostMapping(path = "refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody ObjectNode body) {
        // Convert request to JsonNode
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(body);

        String refreshToken = node.findValue("refreshToken").asText();

        // Get token and check if it's valid
        return refreshTokenRepository.findByToken(refreshToken)
                .map((token) -> refreshTokenUtil.verifyExpiration(token))
                .map(RefreshToken::getUser)
                .map(user -> {
                    JWToken jwToken = new JWToken(user.getEmail(), user.getId(), user.getRole());
                    String tokenString = jwToken.encode(globalConfig.issuer, globalConfig.getPassphrase(),
                            globalConfig.tokenDurationOfValidity);

                    // Create the body of the request
                    Map<String, String> requestBody = new HashMap<>();
                    requestBody.put("jwtToken", tokenString);

                    return ResponseEntity.ok().body(requestBody);
                })
                .orElseThrow(() -> new NotAcceptableException("Refresh token wasn't valid. Please make a new login " +
                        "request"));
    }
}
