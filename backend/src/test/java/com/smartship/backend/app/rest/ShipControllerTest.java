package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.models.Ship;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.NotificationRepository;
import com.smartship.backend.app.repositories.ShipRepository;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jesaja Pavlovic
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ShipControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        shipRepository.deleteAll();
        userRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        // create ship
        Ship ship = new Ship("07202515-a483-464c-b704-5671f104044b", "Ship Name");
        shipRepository.save(ship);
    }

    @Test
    void itShouldAddShipSensorAndData() {
         Ship ship = shipRepository.findAll().get(0);

        //create request body
        Map<String, String> body = new HashMap<>();
        body.put("SensorID", "bb7baec4-c049-45c5-81ce");
        body.put("Group", "Motor");
        body.put("SensorName", "Engine 2 Temperature");
        body.put("Ship", "07202515-a483-464c-b704-5671f104044b");
        body.put("Time", "13:10:00 15/07/2022");
        body.put("Type", "Temperature");
        body.put("Value", "60");
        body.put("Unit", "Celsius");
        body.put("Speed", "12");
        body.put("GPS-Latitude", "N52°3'53.754");
        body.put("GPS-Longitude", "N52°3'53.554");

        ResponseEntity<ObjectNode> response = restTemplate.exchange("http://localhost:8087/api/v1/ships/" + ship.getSmartShipId(), HttpMethod.POST, new HttpEntity<>(List.of(body)), ObjectNode.class);

        assertEquals(1, Objects.requireNonNull(response.getBody()).path("id").asLong(), "No data has been added");

    }

}
