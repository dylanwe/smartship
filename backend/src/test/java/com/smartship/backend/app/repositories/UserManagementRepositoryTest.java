package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Ship;
import com.smartship.backend.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jan van der Henst
 */
@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
class UserManagementRepositoryTest {

    @Autowired
    private UserManagementRepository userManagementRepository;

    @Autowired
    private ShipRepository shipRepository;


    @BeforeEach
    void setup() {
        addTestShipData();
        addTestUserData();
    }

    @AfterEach
    void tearDown() {
        userManagementRepository.deleteAll();
        shipRepository.deleteAll();
    }

    @Test
    void shouldFindUsersByRole() {
        List<User> operators = userManagementRepository.findByRole(User.ROLE.Operator);
        List<User> managers = userManagementRepository.findByRole(User.ROLE.Manager);

        assertEquals(2, operators.size());
        assertEquals(1, managers.size());
    }

    @Test
    void shouldFindUsersByShip() {
        List<User> usersAssignedToShip = userManagementRepository.findByShipId
                (shipRepository.findBySmartShipId("123ABC").get().getId());

        assertEquals(2, usersAssignedToShip.size());
    }

    private void addTestShipData() {
        shipRepository.save(new Ship("123ABC", "Ship 1"));
    }

    private void addTestUserData() {
        Ship ship = shipRepository.findBySmartShipId("123ABC").orElseThrow();

        User operatorJohn = userManagementRepository.save(new User(
                "John",
                "Smith",
                "JohnSmith@test.nl",
                "secret",
                LocalDate.now(),
                User.ROLE.Operator,
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        ));

        User operatorBovy = userManagementRepository.save(new User(
                "Bovy",
                "Jackson",
                "BovyJackson@test.nl",
                "secret",
                LocalDate.now(),
                User.ROLE.Operator,
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        ));

        userManagementRepository.save(new User(
                "Regen",
                "Jones",
                "RegenJones@test.nl",
                "secret",
                LocalDate.now(),
                User.ROLE.Manager,
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        ));

        operatorJohn.connectToShip(ship);
        operatorBovy.connectToShip(ship);
    }

}