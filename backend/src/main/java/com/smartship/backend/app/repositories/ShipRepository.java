package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    Optional<Ship> findBySmartShipId(String smartShipId);
}
