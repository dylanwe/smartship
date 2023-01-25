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

    /**
     * This is a POST endpoint for creating a new notification for a specific user.
     *
     * @param body an ObjectNode containing the notification details, such as title, body and type
     * @param userId the user id for the user the notification is being created for
     * @param jwTokenInfo the JWT token information, used to check the user associated with the token
     * @return a ResponseEntity with the date of the created notification
     */
    @PostMapping
    public ResponseEntity<?> addNotification(@RequestBody ObjectNode body,
                                             @PathVariable Long userId,
                                             @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        //check if the user id in the path variable matches the user id in the JWT
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        //extract title, body and type from the request body
        String title = body.path("title").asText();
        String notificationBody = body.path("body").asText();
        String typeString = body.path("type").asText();
        Notification.TYPE type;

        //convert the type string to a Notification.TYPE enum
        if (typeString.equalsIgnoreCase("error")) {
            type = Notification.TYPE.Error;
        } else if (typeString.equalsIgnoreCase("info")) {
            type = Notification.TYPE.Info;
        } else {
            type = Notification.TYPE.Message;
        }

        //find the user by id and throw an exception if not found
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id %d wasn't found", userId)));

        //create a new notification and save it
        Notification notification = new Notification(title, notificationBody, false, LocalDateTime.now(), type, user);
        notificationRepository.save(notification);

        //create a response body and add the date of the notification
        Map<String, Object> responseBody = new HashMap<>();
        LocalDateTime date = notification.getDate();
        responseBody.put("date", date.toLocalDate());
        return ResponseEntity.ok().body(responseBody);
    }

    /**
     * This is a PUT endpoint that updates the read status of a notification
     *
     * @param userId         the id of the user who owns the notification
     * @param notificationId the id of the notification to update
     * @param jwTokenInfo    the JWT token information extracted from the request header
     * @return a response indicating that the update was successful
     */
    @PutMapping("/{notificationId}")
    public ResponseEntity<?> updateNotification(@PathVariable Long userId,
                                                @PathVariable Long notificationId,
                                                @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        //check if the user id in the path variable matches the user id in the JWT
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

    /**
     * Get all notifications for a specific user
     *
     * @param jwTokenInfo JWT token information extracted from request header
     * @param userId The id of the user for which to retrieve notifications
     * @return A list of notifications for the specified user, sorted by date in descending order
     */
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotificationsForUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo,
                                                                         @PathVariable Long userId) {
        //check if the user id in the JWT token matches the user id in the request path
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        // find the user with the specified id
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user id"));

        // Retrieve all notifications for the user, sort by date in descending order, and return as the response body
        return ResponseEntity.ok().body(
                notificationRepository.findByUserId(user.getId())
                        .stream()
                        .sorted(Comparator.comparing(Notification::getDate).reversed())
                        .collect(Collectors.toList())
        );
    }

    /**
     * find recent notifications
     *
     * @param jwTokenInfo JWT token for auth
     * @param userId id of the user
     * @return the all recent unread notifications
     */
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
