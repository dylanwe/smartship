package com.smartship.backend.app.utility;

import com.smartship.backend.app.models.RefreshToken;
import com.smartship.backend.app.repositories.RefreshTokenRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.rest.NotAcceptableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenUtil {
    @Value("${smart.app.jwtRefreshExpirationMs}")
    private long refreshTokenDuration;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        // check if refresh is expired
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new NotAcceptableException("Refresh token was expired. Please make a new login request");
        }

        return token;
    }
}
