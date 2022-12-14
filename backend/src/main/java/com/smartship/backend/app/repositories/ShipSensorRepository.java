package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Ship;
import com.smartship.backend.app.models.ShipSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipSensorRepository extends JpaRepository<ShipSensor, String> {
    List<ShipSensor> findShipSensorByShipId(Long shipId);
//    ShipSensor findShipSensorBy
}
