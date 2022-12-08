package com.smartship.backend.app;

import com.smartship.backend.app.models.NotificationSetting;
import com.smartship.backend.app.models.User;
import com.smartship.backend.app.repositories.NotificationSettingRepository;
import com.smartship.backend.app.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final NotificationSettingRepository notificationSettingRepository;

    @Autowired
    public BackendApplication(UserRepository userRepository,
                              NotificationSettingRepository notificationSettingRepository) {
        this.userRepository = userRepository;
        this.notificationSettingRepository = notificationSettingRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    /**
     * Add initial data
     * @param args incoming main method arguments
     */
    @Override
    @Transactional
    public void run(String... args) {
        createInitialUsers();
        addNotificationSettings();
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
        userRepository.save(
                new User(
                        "John",
                        "Smith",
                        "test@mail.com",
                        password,
                        LocalDate.now(),
                        User.ROLE.Operator,
                        "See everything in it's entirety... effortlessly. That is what it means to truly see."
                )
        );
    }

    private void addNotificationSettings() {
        List<NotificationSetting> settings = List.of(
          new NotificationSetting("Ship", "These are notifications to alert you on changes on the ship"),
          new NotificationSetting("Tasks", "These are notifications to remind you of any task and when someone assigns you a new task"),
          new NotificationSetting("Shifts", "These are notifications to remind you of an upcoming shift")
        );

        List<NotificationSetting> savedSettings = notificationSettingRepository.findAll();

        if (savedSettings.size() < settings.size()) {
            notificationSettingRepository.saveAll(settings);
        }
    }
}
