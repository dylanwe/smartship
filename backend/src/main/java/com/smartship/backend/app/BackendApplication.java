package com.smartship.backend.app;

import com.smartship.backend.app.exceptions.NotAcceptableException;
import com.smartship.backend.app.models.Dashboard;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.DashboardRepository;
import com.smartship.backend.app.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DashboardRepository dashboardRepository;

    @Autowired
    public BackendApplication(UserRepository userRepository, DashboardRepository dashboardRepository) {
        this.userRepository = userRepository;

        this.dashboardRepository = dashboardRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    /**
     * Add initial data
     *
     * @param args incoming main method arguments
     */
    @Override
    @Transactional
    public void run(String... args) {
        this.createInitialUsers();
        this.createInitialDashboards();
    }

    /**
     * Create initial users
     */
    private void createInitialUsers() {
        List<User> users = userRepository.findAll();
        if (users.size() > 0) return;

        System.out.println("Adding a initial users");
        // Encrypt the password
        String password = BCrypt.hashpw("secret", BCrypt.gensalt());

        User user = new User(
                "John",
                "Smith",
                "test@mail.com",
                password,
                LocalDate.now(),
                User.ROLE.Operator,
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        );

        userRepository.save(user);

    }

    /**
     * Create initial dashboards
     */
    private void createInitialDashboards() {
        if (dashboardRepository.findAll().size() > 0) return;

        dashboardRepository.save(new Dashboard(userRepository.findAll().get(0)));


    }
}
