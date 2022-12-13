package com.smartship.backend.app.rest;

import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.exceptions.UnauthorizedException;
import com.smartship.backend.app.models.Notification;
import com.smartship.backend.app.repositories.NotificationRepository;
import com.smartship.backend.app.utility.JWTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotificationsForUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        List<Notification> notifications = notificationRepository.findAllByOrderByNotificationDateTimeDesc(userId);
        return ResponseEntity.ok().body(notifications);
    }

    @GetMapping("/ascending")
    public ResponseEntity<List<Notification>> getAllNotificationsForUserAscending(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        List<Notification> notifications = notificationRepository.findAllByOrderByNotificationDateTimeAsc(userId);
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

    @GetMapping(path = "{notificationId}")
    public ResponseEntity<Notification> findNotificationById(@PathVariable Long notificationId) {
        Notification foundNotification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException(String.format("Notification with id %s wasn't found", notificationId)));

        return ResponseEntity.ok().body(foundNotification);
    }

    @DeleteMapping(path ="{notificationId}")
    public ResponseEntity<Notification> deleteNotification(@PathVariable Long notificationId, @PathVariable Long userId, @RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundException(String.format("Todo with id %d wasn't found", notificationId)));

        notificationRepository.deleteById(notificationId);

        return ResponseEntity.ok().body(notification);
    }
}
