package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.exceptions.NotAcceptableException;
import com.smartship.backend.app.models.RefreshToken;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.RefreshTokenRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.response.LoginResponse;
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
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTokenUtil jwTokenUtil;
    private final RefreshTokenUtil refreshTokenUtil;

    @Autowired
    public AuthenticationController(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository,
                                    JWTokenUtil jwTokenUtil, RefreshTokenUtil refreshTokenUtil) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwTokenUtil = jwTokenUtil;
        this.refreshTokenUtil = refreshTokenUtil;
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> loginUser(@RequestBody ObjectNode body) {
        String email = body.path("email").asText();
        String password = body.path("password").asText();

        User foundUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotAcceptableException(
                        String.format("User with email %s wasn't found", email)));

        // Validate password
        if (BCrypt.checkpw(password, foundUser.getHashedPassword())) {
            String tokenString = jwTokenUtil.encode(foundUser.getEmail(), foundUser.getId(), foundUser.getRole());
            refreshTokenRepository.deleteAllByUserId(foundUser.getId());
            RefreshToken refreshToken = refreshTokenUtil.createRefreshToken(foundUser.getId());

            return ResponseEntity.accepted()
                    .body(new LoginResponse(tokenString, "Bearer", refreshToken.getToken(), foundUser));
        } else {
            throw new NotAcceptableException("Provided password wasn't correct with the given email");
        }
    }

    @PostMapping(path = "refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody ObjectNode body) {
        String refreshToken = body.path("refreshToken").asText();

        // Get token and check if it's valid
        return refreshTokenRepository.findByToken(refreshToken)
                .map(refreshTokenUtil::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String tokenString = jwTokenUtil.encode(user.getEmail(), user.getId(), user.getRole());

                    // Create the body of the request
                    Map<String, String> requestBody = new HashMap<>();
                    requestBody.put("jwtToken", tokenString);

                    return ResponseEntity.ok().body(requestBody);
                })
                .orElseThrow(() -> new NotAcceptableException("Refresh token wasn't valid. Please make a new login " +
                        "request"));
    }
}
