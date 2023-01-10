package com.smartship.backend.app.utility;
import com.smartship.backend.app.models.Notification;
import com.smartship.backend.app.models.SensorData;
import com.smartship.backend.app.repositories.NotificationRepository;
import com.smartship.backend.app.repositories.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
public class ThresholdNotificationHandler {
    private final SensorDataRepository sensorDataRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public ThresholdNotificationHandler(SensorDataRepository sensorDataRepository, NotificationRepository notificationRepository) {
        this.sensorDataRepository = sensorDataRepository;
        this.notificationRepository = notificationRepository;
    }

    @Scheduled(fixedRate = 60000)  // runs every 60 seconds
    public void checkForThresholdExceeded() {
        // Retrieve the latest sensor data values
        List<SensorData> sensorDataList = sensorDataRepository.findAll();

        // Iterate over the sensor data and check if the values exceed the threshold
        for (SensorData sensorData : sensorDataList) {
            double threshold = sensorData.getThreshold();
            if (sensorData.getVal() > threshold) {
                // Create a new notification
                Notification notification = new Notification();
                notification.setTitle("Threshold exceeded");
                notification.setBody("Sensor value " + sensorData.getVal() + " has exceeded the threshold of " + threshold);

                // Convert the instant to LocalDateTime
                LocalDateTime notificationDateTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
                notification.setNotificationDateTime(notificationDateTime);

                // Save the notification to the database
                notificationRepository.save(notification);
            }
        }
    }
}


