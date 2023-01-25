package com.smartship.backend.app.rest;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.NotificationRepository;
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
 * @author Dusan Los
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class NotificationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldAddNotification() {
        // Make a user
        User kanye = new User(
                "Kanye",
                "West",
                "kanye@west.com",
                BCrypt.hashpw("secret", BCrypt.gensalt()),
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "808's and heartbreaks");

        // save user
        kanye = userRepository.save(kanye);

        //login
        Map<String, String> loginJson = new HashMap<>();
        loginJson.put("email", kanye.getEmail());
        loginJson.put("password", "secret");
        LoginResponse loginResponse = restTemplate.postForObject("/api/v1/auth/login", loginJson, LoginResponse.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginResponse.jwtToken());

        // define the notification name, body, and type
        String notificationName = "Pirates nearby";
        String notificationBody = "Load the cannons";
        String notificationType = "Info";

        //create request body
        Map<String, String> json = new HashMap<>();
        json.put("title", notificationName);
        json.put("body", notificationBody);
        json.put("type", notificationType);

        //set the json object in http entity
        HttpEntity<Map<String, String>> notificationJsonWithAuth = new HttpEntity<>(json, headers);

        //create request
        restTemplate.postForObject("/api/v1/users/" + kanye.getId() + "/notifications", notificationJsonWithAuth, Map.class);

        //Check if user has notification
        assertEquals(1, notificationRepository.findAll().size());
        assertEquals(notificationName, notificationRepository.findAll().get(0).getTitle());
        assertEquals(notificationBody, notificationRepository.findAll().get(0).getBody());
        assertEquals(notificationType, notificationRepository.findAll().get(0).getNotificationType().toString());
    }

}
