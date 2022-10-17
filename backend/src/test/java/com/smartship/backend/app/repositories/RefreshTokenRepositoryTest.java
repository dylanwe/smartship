package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.RefreshToken;
import com.smartship.backend.app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


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

    @BeforeEach
    void setup() {
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
        assertThat(tokenExists).isFalse();
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
        assertEquals(refreshToken, expected);
    }

    @Test
    void itShouldNotExistByUserId() {
        // Given
        long userId = 1;

        // When
        boolean tokenExists = refreshTokenRepository.findById(userId).isPresent();

        // Then
        assertThat(tokenExists).isFalse();
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
        assertThat(tokenExists).isTrue();
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

        // When
        bruce = userRepository.save(bruce);
        refreshTokenRepository.save(refreshToken);
        refreshTokenRepository.deleteAllByUserId(bruce.getId());
        boolean tokenExists = refreshTokenRepository.existsByUserId(bruce.getId());

        // Then
        assertThat(tokenExists).isFalse();
    }
}