package com.smartship.backend.app.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
}