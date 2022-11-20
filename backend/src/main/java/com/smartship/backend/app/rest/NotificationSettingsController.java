package com.smartship.backend.app.rest;

import com.smartship.backend.app.models.NotificationSetting;
import com.smartship.backend.app.repositories.NotificationSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/notification-settings")
public class NotificationSettingsController {
    private final NotificationSettingRepository notificationSettingRepository;

    @Autowired
    public NotificationSettingsController(NotificationSettingRepository notificationSettingRepository) {
        this.notificationSettingRepository = notificationSettingRepository;
    }

    @GetMapping
    public ResponseEntity<List<NotificationSetting>> getAllNotificationSettings() {
        return ResponseEntity.ok().body(notificationSettingRepository.findAll());
    }
}
