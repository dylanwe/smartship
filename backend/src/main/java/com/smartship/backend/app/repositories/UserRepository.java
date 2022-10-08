package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    User findById(long id);
    List<User> findAll();
    User save(User user);
    User deleteById(long id);
}
