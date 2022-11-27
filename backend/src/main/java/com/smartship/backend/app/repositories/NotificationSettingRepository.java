package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.NotificationSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {}
