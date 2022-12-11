package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.SensorData;
import com.smartship.backend.app.models.ShipData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {}
