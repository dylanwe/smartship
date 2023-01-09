package com.smartship.backend.app.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.UserManagementRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
 * @author Jan van der Henst
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserManagementControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserManagementRepository userManagementRepository;

    @BeforeEach
    void tearDown() {
        userManagementRepository.deleteAll();
    }

    @Test
    void shouldAddNewOperatorAndManager() {
        Map<String, String> jsonOperator = new HashMap<>();
        jsonOperator.put("email", "JohnSmith@test.nl");
        jsonOperator.put("firstName", "John");
        jsonOperator.put("lastName", "Smith");
        jsonOperator.put("password", "secret");
        jsonOperator.put("assignedShip", "none");

        User responseOperator = restTemplate.postForObject("/api/v1/userManagement/Operator", jsonOperator, User.class);

        User operator = userManagementRepository.findByRole(User.ROLE.Operator).get(0);

        assertEquals(operator, responseOperator);
        assertEquals(User.ROLE.Operator, responseOperator.getRole());


        Map<String, String> jsonManger = new HashMap<>();
        jsonManger.put("email", "RegenBagon@test.nl");
        jsonManger.put("firstName", "Regen");
        jsonManger.put("lastName", "Bagon");
        jsonManger.put("password", "secret");
        jsonManger.put("assignedShip", "none");

        User responseManager = restTemplate.postForObject("/api/v1/userManagement/Manager", jsonManger, User.class);

        User manager = userManagementRepository.findByRole(User.ROLE.Manager).get(0);

        assertEquals(manager, responseManager);
        assertEquals(User.ROLE.Manager, responseManager.getRole());
    }

    @Test
    void shouldDeleteUserById() {
        User bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                BCrypt.hashpw("secret", BCrypt.gensalt()),
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");
        userManagementRepository.save(bruce);

        //Should have one user
        assertEquals(1, userManagementRepository.findAll().size());

        //Should delete the user
        restTemplate.delete("/api/v1/userManagement/" + userManagementRepository.findAll().get(0).getId());

        //Should have no more users
        assertEquals(0, userManagementRepository.findAll().size());
    }

    @Test
    void shouldUpdateUser() throws JsonProcessingException {
        User bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                BCrypt.hashpw("secret", BCrypt.gensalt()),
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");
        userManagementRepository.save(bruce);

        ObjectMapper objectMapper = new ObjectMapper();

        //Custom json user needed by the method
        String json = "{ \"id\":\"1\", \"firstName\":\"BruceBoi\", \"lastName\":\"Wayne\", \"email\":\"bruceBoi@wayne.com\" }";

        //Read the json String and convert it into a JsonNode
        JsonNode jsonUser = objectMapper.readTree(json);

        ObjectNode objectNode = new ObjectNode(JsonNodeFactory.instance);

        objectNode.put("assignedShip", "none");
        objectNode.put("user", jsonUser);

        assertEquals("Bruce", userManagementRepository.findAll().get(0).getFirstName());
        restTemplate.put("/api/v1/userManagement", objectNode);
        assertEquals("BruceBoi", userManagementRepository.findAll().get(0).getFirstName());

    }
}