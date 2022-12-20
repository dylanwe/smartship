package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.models.Ship;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.ShipRepository;
import com.smartship.backend.app.repositories.UserManagementRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1/userManagement")
public class UserManagementController {

    private final UserManagementRepository userManagementRepository;

    private final ShipRepository shipRepository;

    @Autowired
    public UserManagementController(UserManagementRepository userManagementRepository, ShipRepository shipRepository) {
        this.userManagementRepository = userManagementRepository;
        this.shipRepository = shipRepository;
    }

    @GetMapping(path = "/{role}")
    public ResponseEntity<List<User>> findAllAccountsForRole(@PathVariable String role) {
        List<User> foundUsers = userManagementRepository.findByRole(User.ROLE.valueOf(role));

        return ResponseEntity.ok().body(foundUsers);
    }

    @GetMapping(path = "/ship/{shipId}")
    public ResponseEntity<List<User>> findAllOperatorsForShip(@PathVariable long shipId) {
        List<User> foundUsers = userManagementRepository.findByShipId(shipId);

        return ResponseEntity.ok().body(foundUsers);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable long id) {

        User foundUser = userManagementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s wasn't found", id)));

        if (foundUser.getShip() != null) {
            //Remove the connection the user has with the ship
            foundUser.removeShip(foundUser.getShip());
            //Update the database, so it knows it has no connection anymore
            userManagementRepository.save(foundUser);
        }

        userManagementRepository.deleteById(id);

        return ResponseEntity.ok().body(foundUser);
    }

    @PostMapping(path = "/{role}")
    public ResponseEntity<User> addNewAccount(@RequestBody ObjectNode body, @PathVariable String role) {
        String email = body.path("email").asText();
        String firstName = body.path("firstName").asText();
        String lastName = body.path("lastName").asText();
        String password = BCrypt.hashpw(body.path("password").asText(), BCrypt.gensalt());
        String shipSmartId = body.path("assignedShip").asText();

        User user = userManagementRepository.save(new User(
                firstName,
                lastName,
                email,
                password,
                LocalDate.now(),
                User.ROLE.valueOf(role),
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        ));

        if (!Objects.equals(shipSmartId, "none")) {
            Ship ship = shipRepository.findBySmartShipId(shipSmartId).orElseThrow(
                    () -> new NotFoundException(shipSmartId)
            );
            user.connectToShip(ship);
        }

        userManagementRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

    @PutMapping(path = "")
    public ResponseEntity<User> updateUser(@RequestBody ObjectNode body) {

        JsonNode user = body.path("user");
        String shipSmartId = body.path("assignedShip").asText();

        long id = Long.parseLong(user.path("id").asText());
        String firstName = user.path("firstName").asText();
        String lastName = user.path("lastName").asText();
        String email = user.path("email").asText();

        User foundUser = userManagementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        id
                )));

        if (!Objects.equals(shipSmartId, "none")) {
            Ship ship = shipRepository.findBySmartShipId(shipSmartId).orElseThrow(
                    () -> new NotFoundException(shipSmartId)
            );

            //Remove the connection to the old ship
            foundUser.removeShip(foundUser.getShip());
            //Add the connection to the new ship
            foundUser.connectToShip(ship);
        }

        foundUser.setFirstName(firstName);
        foundUser.setLastName(lastName);
        foundUser.setEmail(email);

        User updatedUser = userManagementRepository.save(foundUser);

        return ResponseEntity.ok().body(updatedUser);
    }

}
