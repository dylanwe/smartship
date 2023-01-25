package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.DashboardRepository;
import com.smartship.backend.app.repositories.ToDoRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.response.LoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @BeforeEach
    void setUp() {
        dashboardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        dashboardRepository.deleteAll();
        userRepository.deleteAll();
    }

//    @Test
    void shouldGetDashboardByUserId() throws URISyntaxException {
        // Make a user
        User bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                BCrypt.hashpw("secret", BCrypt.gensalt()),
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");

        // save user
        bruce = userRepository.save(bruce);

        //login
        Map<String, String> loginJson = new HashMap<>();
        loginJson.put("email", bruce.getEmail());
        loginJson.put("password", "secret");
        LoginResponse loginResponse = restTemplate.postForObject("/api/v1/auth/login", loginJson, LoginResponse.class);

        // give the header the correct JWT token of the user
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginResponse.jwtToken());

        // send request and receive response
        ResponseEntity<ObjectNode> response = restTemplate.exchange(RequestEntity.get(new URI("http://localhost:8087/api/v1/dashboards/user/" + bruce.getId())).headers(headers).build(), ObjectNode.class);

        System.out.println(userRepository.findAll().size());
        System.out.println(response);
        assertEquals(112, Objects.requireNonNull(response.getBody()).path("id").asLong(), "Ship could not be created");

    }

}
