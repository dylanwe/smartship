package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Dylan Weijgertze
 */
@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void itShouldCheckIfUserEmailDoesNotExists() {
        // Given
        String email = "bruce@wayne.com";

        // When
        boolean exists = userRepository.existsByEmail(email);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    public void itShouldCheckThatUserEmailExists() {
        // Given
        User bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");

        // When
        userRepository.save(bruce);
        boolean exists = userRepository.existsByEmail("bruce@wayne.com");

        // Then
        assertThat(exists).isTrue();
    }
}