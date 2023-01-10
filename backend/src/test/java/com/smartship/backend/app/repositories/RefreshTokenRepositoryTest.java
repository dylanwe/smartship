package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.RefreshToken;
import com.smartship.backend.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * @author Dylan Weijgertze
 */
@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
class RefreshTokenRepositoryTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        refreshTokenRepository.deleteAll();
    }

    @Test
    void itShouldNotFindByToken() {
        // Given
        String token = "fakeToken";

        // When
        boolean tokenExists = refreshTokenRepository.findByToken(token).isPresent();

        // Then
        assertFalse(tokenExists, "The token was found by it's id when it wasn't saved");
    }

    @Test
    void itShouldFindByToken() {
        // Given
        User bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");
        Instant time = Instant.now();
        RefreshToken refreshToken = new RefreshToken(bruce, "testToken", time);

        // When
        userRepository.save(bruce);
        refreshTokenRepository.save(refreshToken);
        RefreshToken expected = refreshTokenRepository.findByToken(refreshToken.getToken()).get();

        // Then
        assertEquals(expected, refreshToken, "Found refresh token did not equal the token we gave");
    }

    @Test
    void itShouldNotExistByUserId() {
        // Given
        long userId = 1;

        // When
        boolean tokenExists = refreshTokenRepository.findById(userId).isPresent();

        // Then
        assertFalse(tokenExists, "Token was found even though it shouldn't have existed");
    }

    @Test
    void itShouldExistByUserId() {
        // Given
        User bruce = userRepository.save(new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night"));
        Instant time = Instant.now();
        RefreshToken refreshToken = new RefreshToken(bruce, "testToken", time);

        // When
        userRepository.save(bruce);
        refreshTokenRepository.save(refreshToken);
        boolean tokenExists = refreshTokenRepository.existsByUserId(bruce.getId());

        // Then
        assertTrue(tokenExists, "The token should exist after saving it");
    }

    @Test
    void itShouldDeleteAllByUserId() {
        // Given
        User bruce = userRepository.save(new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night"));
        Instant time = Instant.now();
        RefreshToken refreshToken = new RefreshToken(bruce, "testToken", time);
        RefreshToken refreshToken2 = new RefreshToken(bruce, "testToken2", time);

        // When
        bruce = userRepository.save(bruce);
        refreshTokenRepository.save(refreshToken);
        refreshTokenRepository.save(refreshToken2);
        refreshTokenRepository.deleteAllByUserId(bruce.getId());
        boolean tokenExists = refreshTokenRepository.existsByUserId(bruce.getId());

        // Then
        assertFalse(tokenExists, "Deleting did not work because some tokens where still found for the user bruce");
    }
}