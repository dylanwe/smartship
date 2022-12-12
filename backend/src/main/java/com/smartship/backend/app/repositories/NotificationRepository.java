package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Notification;
import com.smartship.backend.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.title LIKE %:letters% OR n.body LIKE %:letters%")
    List<Notification> findByMessageContainsLetters(@Param("letters") String letters);

    List<Notification> findAllByOrderByNotificationDateTimeDesc();

    List<Notification> findAllByOrderByNotificationDateTimeAsc();

}
