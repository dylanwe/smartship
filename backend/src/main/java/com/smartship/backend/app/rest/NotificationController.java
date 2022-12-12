package com.smartship.backend.app.rest;

import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.models.Notification;
import com.smartship.backend.app.repositories.NotificationRepository;
import com.smartship.backend.app.utility.JWTokenInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping(path = "")
    public ResponseEntity<List<Notification>> findAllNotifications(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo) {
        List<Notification> notifications = notificationRepository.findAllByUserId(jwTokenInfo.userId());
        return ResponseEntity.ok().body(notifications);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<Notification>> findAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();

        return ResponseEntity.ok().body(notifications);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Notification> findNotificationById(@PathVariable Long id) {
        Notification foundNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Notification with id %s wasn't found", id)));

        return ResponseEntity.ok().body(foundNotification);
    }

}
