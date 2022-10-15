package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.config.GlobalConfig;
import com.smartship.backend.app.models.RefreshToken;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.RefreshTokenRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWTokenUtil;
import com.smartship.backend.app.utility.RefreshTokenUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenUtil refreshTokenUtil;
    private final GlobalConfig globalConfig;

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
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(body);

        // Get email and password from JSON request
        String email = node.findValue("email").asText();
        String password = node.findValue("password").asText();

        // Check email and password where given
        if (email.isEmpty() || password.isEmpty())
            throw new NotAcceptableException("Email or password where not provided");

        // Find user with given email
        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotAcceptableException(
                        String.format("User with email %s wasn't found", email)));

        // Validate password
        if (BCrypt.checkpw(password, foundUser.getHashedPassword())) {
            // Create a new JWT token for the user
            JWTokenUtil jwTokenUtil = new JWTokenUtil(foundUser.getEmail(), foundUser.getId(), foundUser.getRole());
            String tokenString = jwTokenUtil.encode(globalConfig.issuer, globalConfig.getPassphrase(),
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
            throw new NotAcceptableException("Provided password wasn't correct with the given email");
        }
    }

    @PostMapping(path = "refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody ObjectNode body) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.valueToTree(body);

        // Get refreshToken from JSON request
        String refreshToken = node.findValue("refreshToken").asText();

        // Get token and check if it's valid
        return refreshTokenRepository.findByToken(refreshToken)
                .map(refreshTokenUtil::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    JWTokenUtil jwTokenUtil = new JWTokenUtil(user.getEmail(), user.getId(), user.getRole());
                    String tokenString = jwTokenUtil.encode(globalConfig.issuer, globalConfig.getPassphrase(),
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
