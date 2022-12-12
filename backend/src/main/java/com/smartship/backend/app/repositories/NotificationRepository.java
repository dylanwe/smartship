package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByUserId(Long userId);

    Optional<Notification> findById(Long id);

    @Query("SELECT n FROM Notification n WHERE n.title LIKE %:letters% OR n.body LIKE %:letters%")
    List<Notification> findByMessageContainsLetters(@Param("letters") String letters);

    List<Notification> findAllByOrderByNotificationDateTimeDesc();

    List<Notification> findAllByOrderByNotificationDateTimeAsc();

}
