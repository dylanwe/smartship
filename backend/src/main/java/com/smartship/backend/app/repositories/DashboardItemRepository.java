package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Dashboard;
import com.smartship.backend.app.models.DashboardItem;
import com.smartship.backend.app.models.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface DashboardItemRepository extends JpaRepository<DashboardItem, Long> {
    List<DashboardItem> findDashboardItemByDashboard(Dashboard dashboard);


}
