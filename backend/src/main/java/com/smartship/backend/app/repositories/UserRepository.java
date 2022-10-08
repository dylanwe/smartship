package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    User findUserById(long userId);
}
