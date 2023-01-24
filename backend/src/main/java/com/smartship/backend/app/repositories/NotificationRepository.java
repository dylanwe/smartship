package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Notification;
import com.smartship.backend.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop2ByUserAndReadNotificationIsFalseOrderByNotificationDateTimeAsc(User user);

    List<Notification> findAllByUserAndReadNotificationIsFalse(User user);

    List<Notification> findByUserId(Long userId);
}
