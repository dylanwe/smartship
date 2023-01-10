package com.smartship.backend.app.rest;

import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.response.LoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Dylan Weijgertze
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void loginShouldBeDone() {
        // Given a user
        User bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                BCrypt.hashpw("secret", BCrypt.gensalt()),
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");
        userRepository.save(bruce);

        Map<String, String> json = new HashMap<>();
        json.put("email", bruce.getEmail());
        json.put("password", "secret");

        // try to log in
        LoginResponse response = restTemplate.postForObject("/api/v1/auth/login", json, LoginResponse.class);

        // check if content is correct
        assertNotNull(response.jwtToken(), "No JWT token was found in the response");
        assertNotNull(response.refreshToken(), "No refresh token was found in the response");
        assertEquals(bruce, response.user(), "The user bruce was not found in the response");
    }
}