package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.SensorData;
import com.smartship.backend.app.models.ShipData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    List<SensorData> findSensorDataByShipSensorId(String id);
    List<SensorData> findSensorDataByShipSensorIdAndTimeBetweenOrderByTimeAsc(String id,Long start, Long end);

}
