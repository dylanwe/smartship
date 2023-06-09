package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserManagementRepository extends JpaRepository<User, Long> {

    List<User> findByRole(User.ROLE role);

    Optional<User> findByEmail(String email);

    List<User> findByShipId(long ship_id);
}
