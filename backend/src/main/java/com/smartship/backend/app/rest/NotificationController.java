package com.smartship.backend.app.rest;

import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.exceptions.UnauthorizedException;
import com.smartship.backend.app.models.Notification;
import com.smartship.backend.app.models.ToDo;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.NotificationRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.utility.JWTokenInfo;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/notifications")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationController(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotificationsForUser(@RequestAttribute(value = JWTokenInfo.KEY) JWTokenInfo jwTokenInfo, @PathVariable Long userId) {
        //check user from jwt
        if (!userId.equals(jwTokenInfo.userId()))
            throw new UnauthorizedException("User id doesn't match");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Can't find user id"));
        return ResponseEntity.ok().body(
                user.getNotifications()
                        .stream()
                        .sorted(Comparator.comparing(Notification::getNotificationDateTime).reversed())
                        .collect(Collectors.toList())
        );
    }

//    @GetMapping(path = "")
//    public ResponseEntity<List<Notification>> findAllNotifications() {
//        List<Notification> notifications = notificationRepository.findAll();
//
//        return ResponseEntity.ok().body(notifications);
//    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Notification> findNotificationById(@PathVariable Long id) {
        Notification foundNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Notification with id %s wasn't found", id)));

        return ResponseEntity.ok().body(foundNotification);
    }

}
