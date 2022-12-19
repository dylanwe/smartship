package com.smartship.backend.app.rest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.exceptions.UnauthorizedException;
import com.smartship.backend.app.models.Notification;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.NotificationRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/users/{userId}/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationController(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> addNotification(@RequestBody ObjectNode body, @PathVariable Long userId, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        String title = body.path("title").asText();
        String notificationBody = body.path("body").asText();


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d wasn't found", userId)));

        return ResponseEntity.ok().body(notificationRepository.save(new Notification(title, notificationBody, LocalDate.now(), Notification.TYPE.Error, user)));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAlNotificationsForUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user id"));

        return ResponseEntity.ok().body(
                notificationRepository.findByUserId(user.getId())
        );
    }

    @GetMapping("/oldest")
    public ResponseEntity<List<Notification>> getOldestNotifications(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user id"));

        return ResponseEntity.ok().body(
                user.getNotification()
                        .stream()
                        .sorted(Comparator.comparing(Notification::getNotificationDateTime))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/type/{notificationType}")
    public ResponseEntity<List<Notification>> getNotificationsByType(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId, @PathVariable String notificationType) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        List<Notification> notifications = notificationRepository.findByNotificationType(notificationType);
        return ResponseEntity.ok().body(notifications);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Notification>> searchNotifications(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId, @RequestParam("letters") String letters) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        List<Notification> notifications = notificationRepository.findByMessageContainsLetters(letters);
        return ResponseEntity.ok().body(notifications);
    }
}
