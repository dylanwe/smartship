package com.smartship.backend.app.rest;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.exceptions.NotAcceptableException;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.exceptions.UnauthorizedException;
import com.smartship.backend.app.models.NotificationPreference;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.NotificationPreferenceRepository;
import com.smartship.backend.app.repositories.NotificationSettingRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWTokenInfo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserRepository userRepository;
    private final NotificationPreferenceRepository notificationPreferenceRepository;
    private final NotificationSettingRepository notificationSettingRepository;

    @Autowired
    public UserController(UserRepository userRepository,
                          NotificationPreferenceRepository notificationPreferenceRepository,
                          NotificationSettingRepository notificationSettingRepository) {
        this.userRepository = userRepository;
        this.notificationPreferenceRepository = notificationPreferenceRepository;
        this.notificationSettingRepository = notificationSettingRepository;
    }

    @GetMapping(path = "")
    public ResponseEntity<List<User>> findAllUser() {
        List<User> users = userRepository.findAll();

        return ResponseEntity.ok().body(users);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<User> findUserById(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo,
                                             @PathVariable Long id) {
        // check if user is the same id as jwt if it's an operator
        if (jwTokenInfo.role() == User.ROLE.Operator)
            if (!id.equals(jwTokenInfo.userId()))
                // not the same
                throw new UnauthorizedException("User id doesn't match");


        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %s wasn't found", id)));

        return ResponseEntity.ok().body(foundUser);
    }

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @RequestBody User updatedUser) {
        User foundUser = userRepository.findById(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        jwTokenInfo.userId()
                )));

        User.validateNewUserInformation(updatedUser);

        foundUser.setFirstName(updatedUser.getFirstName());
        foundUser.setLastName(updatedUser.getLastName());
        foundUser.setEmail(updatedUser.getEmail());
        foundUser.setBirthday(updatedUser.getBirthday());
        foundUser.setBio(updatedUser.getBio());

        updatedUser = userRepository.save(foundUser);
        return ResponseEntity.ok().body(updatedUser);
    }

    @PutMapping(path = "password")
    public ResponseEntity<String> updateUserPassword(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @RequestBody ObjectNode body) {
        String password = body.path("oldPassword").asText();
        String newPassword = body.path("newPassword").asText();

        User foundUser = userRepository.findById(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        jwTokenInfo.userId()
                )));

        // validate the given password
        if (BCrypt.checkpw(password, foundUser.getHashedPassword())) {
            String newHashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // save the new password
            foundUser.setHashedPassword(newHashedPassword);
            userRepository.save(foundUser);

            return ResponseEntity.accepted().body("Password updated");
        } else {
            throw new NotAcceptableException("Provided password doesn't match account");
        }
    }

    @GetMapping(path = "notification-preferences")
    public ResponseEntity<List<NotificationPreference>> findAllNotificationPreferences(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        List<NotificationPreference> notificationPreferences =
                notificationPreferenceRepository.findAllByUserId(jwTokenInfo.userId());

        return ResponseEntity.ok().body(notificationPreferences);
    }

    @PutMapping(path = "notification-preferences")
    public ResponseEntity<?> updateNotificationPreferences(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @RequestBody ObjectNode[] requestBodies) {
        // create empty list
        List<NotificationPreference> notificationPreferences = new ArrayList<>();
        User user = userRepository.findById(jwTokenInfo.userId())
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with id %s wasn't found",
                        jwTokenInfo.userId()
                )));

        // go through iterable object
        for (ObjectNode body : requestBodies) {
            Long notificationPreferenceId = (body.get("notificationPreferenceId") != null) ? body.get(
                    "notificationPreferenceId").asLong() : null;
            Long notificationSettingId = body.get("notificationSettingId").asLong();
            boolean isEmailActive = body.get("isEmailActive").asBoolean();
            boolean isPushActive = body.get("isPushActive").asBoolean();

            NotificationPreference notificationPreference = new NotificationPreference(
                    isEmailActive,
                    isPushActive,
                    user,
                    notificationSettingRepository.findById(notificationSettingId).get()
            );

            if (notificationPreferenceId != null) {
                notificationPreference.setId(notificationPreferenceId);
            }

            notificationPreferences.add(notificationPreference);
        }

        // save all to repository
        List<NotificationPreference> savedPreferences =
                notificationPreferenceRepository.saveAll(notificationPreferences);

        return ResponseEntity.ok().body(savedPreferences);
    }
}
