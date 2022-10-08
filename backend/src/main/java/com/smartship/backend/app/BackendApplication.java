package com.smartship.backend.app;

import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    // add repos for creating initial users
    UserRepository userRepository;

    @Autowired
    public BackendApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    /**
     * Add initial data
     * @param args incoming main method arguments
     * @throws Exception
     */
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.createInitialUsers();
    }

    /**
     * Create initial users
     */
    private void createInitialUsers() {
        List<User> users = userRepository.findAll();
        if (users.size() > 0) return;

        System.out.println("Adding a initial users");
        userRepository.save(
                new User(
                        "Takehiko",
                        "Inoue",
                        "takehiko@hiko.com",
                        "hidden",
                        LocalDate.now(),
                        User.ROLE.Operator,
                        "See everything in it's entirety... effortlessly. That is what it means to truly see."
                )
        );
    }
}
