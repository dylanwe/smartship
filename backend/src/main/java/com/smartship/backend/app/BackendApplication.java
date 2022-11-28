package com.smartship.backend.app;

import com.smartship.backend.app.models.*;
import com.smartship.backend.app.exceptions.NotAcceptableException;
import com.smartship.backend.app.repositories.NotificationSettingRepository;
import com.smartship.backend.app.repositories.DashboardRepository;
import com.smartship.backend.app.repositories.UserRepository;
import com.smartship.backend.app.repositories.WidgetRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final NotificationSettingRepository notificationSettingRepository;
    private final DashboardRepository dashboardRepository;
    private final WidgetRepository widgetRepository;

    @Autowired
    public BackendApplication(UserRepository userRepository, DashboardRepository dashboardRepository,
                              NotificationSettingRepository notificationSettingRepository, WidgetRepository widgetRepository) {
        this.userRepository = userRepository;
        this.notificationSettingRepository = notificationSettingRepository;
        this.dashboardRepository = dashboardRepository;
        this.widgetRepository = widgetRepository;

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
        this.addNotificationSettings();
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
    /**
     * Create initial dashboards
     */
    private void createInitialDashboards() {
        if (dashboardRepository.findAll().size() > 0) return;


        Ship shipOne = new Ship("07202515-a483-464c-b704-5671f104044b", "Ship 1");


       Widget widget = new Widget("<", "Engine Temperatures", "WidgetTemperature", 2, 2, 2, 2, 2, 2);
        widgetRepository.save(widget);

       Sensor sensorEngineOneTemp = new Sensor("bb7baec4-c049-45c5-81ce-2715801e6bff",
                "Engine 1 Temperature", Sensor.GROUP.Motor, Sensor.TYPE.Temperature, "Celsius");


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LocalDateTime dateTime = LocalDateTime.parse("13:10:00 15/07/2022", formatter);

       SensorData shipOneSensorData = new SensorData(67.68, dateTime, sensorEngineOneTemp, shipOne);

        Dashboard dashboard = new Dashboard(userRepository.findAll().get(0));
        DashboardItem dashboardItem = new DashboardItem(1, 3, 2, 2, widget);


        dashboardRepository.save(dashboard);
        dashboard.addToLayout(dashboardItem);

        System.out.println("Added a initial dashboard");

    }
}
