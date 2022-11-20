package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long> {
    List<NotificationPreference> findAllByUserId(Long userId);
}
