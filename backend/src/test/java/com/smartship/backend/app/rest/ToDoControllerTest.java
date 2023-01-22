package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.models.ToDo;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.ToDoRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.response.LoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Lisanne Lin
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ToDoControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ToDoRepository toDoRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        toDoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldAddToDo() {
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

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(loginResponse.jwtToken());

        String toDoName = "Check engine";
        String month = (LocalDate.now().getMonthValue() < 10 ? "0" + LocalDate.now().getMonthValue() :
                String.valueOf(LocalDate.now().getMonthValue()));
        String toDoDate = LocalDate.now().getDayOfMonth() + "-" + month + "-" + LocalDate.now().getYear();

        Map<String, String> json = new HashMap<>();
        json.put("name", toDoName);
        json.put("dueDate", toDoDate);

        HttpEntity<Map<String, String>> todoJsonWithAuth = new HttpEntity<>(json, headers);

        // create request
        ObjectNode response = restTemplate.postForObject("/api/v1/users/" + bruce.getId() + "/todos", todoJsonWithAuth, ObjectNode.class);

        System.out.println(response);

        assertEquals(toDoName, response.path("name").asText());
        assertEquals(LocalDate.now().getYear() + "-" + month + "-"+ LocalDate.now().getDayOfMonth(), response.path("dueAt").asText());
        assertEquals(LocalDate.now().getYear() + "-" + month + "-"+ LocalDate.now().getDayOfMonth(), response.path("createdAt").asText());

        //Check if user has to do
        assertEquals(1, toDoRepository.findAll().size());
    }

}
