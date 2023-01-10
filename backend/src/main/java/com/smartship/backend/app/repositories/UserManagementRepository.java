package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserManagementRepository extends JpaRepository<User, Long> {

    List<User> findByRole(User.ROLE role);

    List<User> findByShipId(long ship_id);
}
