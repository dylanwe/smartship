package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.DashboardRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.response.LoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jesaja Pavlovic
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class DashboardControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DashboardRepository dashboardRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        dashboardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void itShouldCreateUserDashboard() {
        // Make a user
        User user = new User(
                "first",
                "last",
                "first@last.com",
                BCrypt.hashpw("secret", BCrypt.gensalt()),
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "my first and last");

        // save user
        user = userRepository.save(user);

        //login
        Map<String, String> loginJson = new HashMap<>();
        loginJson.put("email", user.getEmail());
        loginJson.put("password", "secret");
        LoginResponse loginResponse = restTemplate.postForObject("/api/v1/auth/login", loginJson, LoginResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginResponse.jwtToken());

        //set the headers in http entity
        HttpEntity<Map<String, String>> requestWithAuth = new HttpEntity<>(headers);

        // send request and receive response
        ObjectNode response = restTemplate.postForObject("/api/v1/dashboards", requestWithAuth, ObjectNode.class);


        assertEquals(1, dashboardRepository.findAll().size());
        assertEquals(user, dashboardRepository.findAll().get(0).getUser());
    }

}
