package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Boolean existsByUserId(Long userid);

    void deleteAllByUserId(Long userId);
}
