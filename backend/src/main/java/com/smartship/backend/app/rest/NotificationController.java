package com.smartship.backend.app.rest;
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
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> addNotification(@RequestBody ObjectNode body,
                                             @PathVariable Long userId,
                                             @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        String title = body.path("title").asText();
        String notificationBody = body.path("body").asText();
        String typeString = body.path("type").asText();
        Notification.TYPE type;

        if (typeString.equalsIgnoreCase("error")) {
            type = Notification.TYPE.Error;
        } else if (typeString.equalsIgnoreCase("info")) {
            type = Notification.TYPE.Info;
        } else {
            type = Notification.TYPE.Message;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d wasn't found", userId)));

        Notification notification = new Notification(title, notificationBody, false, LocalDateTime.now(), type, user);
        notificationRepository.save(notification);

        Map<String, Object> responseBody = new HashMap<>();
        LocalDateTime date = notification.getDate();
        responseBody.put("date", date.toLocalDate());
        return ResponseEntity.ok().body(responseBody);
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<?> updateNotification(@PathVariable Long userId,
                                                @PathVariable Long notificationId,
                                                @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        // check if the user id in the JWT token matches the user id in the request path
        if (!userId.equals(jwTokenInfo.userId())) {
            throw new UnauthorizedException("User id doesn't match");
        }

        // find the notification with the specified id
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException("Notification not found"));

        // update the readNotification field with the value from the request body
        notification.setReadNotification(true);
        notificationRepository.save(notification);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotificationsForUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo,
                                                                         @PathVariable Long userId) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user id"));

        return ResponseEntity.ok().body(
                notificationRepository.findByUserId(user.getId())
                        .stream()
                        .sorted(Comparator.comparing(Notification::getNotificationDateTime).reversed())
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/descending")
    public ResponseEntity<List<Notification>> getNotificationDescending(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId) {
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

    @GetMapping("/ascending")
    public ResponseEntity<List<Notification>> getNotificationsAscending(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user id"));

        return ResponseEntity.ok().body(
                user.getNotification()
                        .stream()
                        .sorted(Comparator.comparing(Notification::getNotificationDateTime).reversed())
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

    @GetMapping("/recent")
    public ResponseEntity<Map<String, Object>> findRecent(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId) {

        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user id"));

        List<Notification> recentNotifications = notificationRepository.findTop2ByUserAndReadNotificationIsFalseOrderByNotificationDateTimeAsc(user);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("recentNotifications", recentNotifications);
        responseBody.put("totalUnread", notificationRepository.findAllByUserAndReadNotificationIsFalse(user).size());

        return ResponseEntity.ok().body(responseBody);
    }
}
