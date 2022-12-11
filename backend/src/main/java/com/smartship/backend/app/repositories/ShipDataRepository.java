package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Sensor;
import com.smartship.backend.app.models.ShipData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipDataRepository extends JpaRepository<ShipData, Long> {}
