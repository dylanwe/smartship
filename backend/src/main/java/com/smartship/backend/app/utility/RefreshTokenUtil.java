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

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public RefreshTokenUtil(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a new random refresh token
     *
     * @param userId The id of the user
     * @return The newly generated refresh token
     */
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDuration));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    /**
     * Check if the refresh token is expired
     *
     * @param token The token to check expiration of
     * @return The checked refresh token if it hasn't expired
     */
    public RefreshToken verifyExpiration(RefreshToken token) {
        // check if refresh is expired
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new NotAcceptableException("Refresh token was expired. Please make a new login request");
        }

        return token;
    }
}
