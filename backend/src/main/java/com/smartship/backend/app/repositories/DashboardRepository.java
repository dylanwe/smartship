package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;


@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    Optional<Dashboard> findByUserId(Long userId);

    Boolean existsByUserId(Long userId);

}
