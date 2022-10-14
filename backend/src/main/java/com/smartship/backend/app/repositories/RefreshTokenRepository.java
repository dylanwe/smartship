package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.RefreshToken;
import com.smartship.backend.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    Long deleteByUser(User user);
}
