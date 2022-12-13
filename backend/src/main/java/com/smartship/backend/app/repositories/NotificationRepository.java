package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.title LIKE %:letters% OR n.body LIKE %:letters%")
    List<Notification> findByMessageContainsLetters(@Param("letters") String letters);

    @Query("SELECT n FROM Notification n WHERE n.notificationType = :notificationType")
    List<Notification> findByNotificationType(@Param("notificationType") String notificationType);

    List<Notification> findAllByOrderByNotificationDateTimeDesc(Long userId);

    List<Notification> findAllByOrderByNotificationDateTimeAsc(Long userId);



}
