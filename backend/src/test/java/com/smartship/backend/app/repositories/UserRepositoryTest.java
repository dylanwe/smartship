package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void itShouldRemoveAUser() {
        // given
        int id = 1;

        // when
        userRepository.deleteById(id);

        // then
        User actual = userRepository.findById(id);
        assertEquals(null, actual);
    }

    @Test
    void itShouldAddAUser() {
        // given
        User newUser = new User(
                "John",
                "Smith",
                "johnsmith@mail.com",
                "secret",
                LocalDate.now(),
                User.ROLE.Operator,
                "Hey there"
        );

        int initialSize = userRepository.findAll().size();

        // when
        userRepository.save(newUser);

        // then
        int expected = initialSize + 1;
        assertEquals(expected, userRepository.findAll().size());
    }

    @Test
    void findAll() {
        // given
        int usersSize = userRepository.findAll().size();

        // then
        int expected = 1;
        assertEquals(expected, usersSize);
    }

    @Test
    void itShouldFindById() {
        // given
        int lastId = userRepository.findAll().size();

        // no users found
        if (lastId == 0) return;

        // when
        User foundUser = userRepository.findById(lastId);

        // then
        assertEquals(lastId, foundUser.getId());
    }
}