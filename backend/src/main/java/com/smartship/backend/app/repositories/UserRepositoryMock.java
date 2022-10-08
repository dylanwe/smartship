package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Primary
public class UserRepositoryMock implements UserRepository {

    @Override
    public User findUserById(long userId) {
        LocalDate date = LocalDate.now();

        return new User(
                userId,
                "Takehiko",
                "Inoue",
                "takehiko@hiko.com",
                "hidden",
                date,
                User.ROLE.Operator,
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        );
    }
}
