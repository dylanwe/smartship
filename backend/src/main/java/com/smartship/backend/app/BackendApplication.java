package com.smartship.backend.app;

import com.smartship.backend.app.models.*;
import com.smartship.backend.app.repositories.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final NotificationSettingRepository notificationSettingRepository;
    private final DashboardRepository dashboardRepository;
    private final DashboardItemRepository dashboardItemRepository;
    private final WidgetRepository widgetRepository;
    private final ShipRepository shipRepository;
    private final SensorRepository sensorRepository;
    private final ShipSensorRepository shipSensorRepository;
    private final ShipDataRepository shipDataRepository;
    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public BackendApplication(UserRepository userRepository, DashboardRepository dashboardRepository,
                              NotificationSettingRepository notificationSettingRepository, DashboardItemRepository dashboardItemRepository, WidgetRepository widgetRepository, ShipRepository shipRepository, SensorRepository sensorRepository, ShipSensorRepository shipSensorRepository, ShipDataRepository shipDataRepository, SensorDataRepository sensorDataRepository) {

        this.userRepository = userRepository;
        this.notificationSettingRepository = notificationSettingRepository;
        this.dashboardRepository = dashboardRepository;
        this.dashboardItemRepository = dashboardItemRepository;
        this.widgetRepository = widgetRepository;
        this.shipRepository = shipRepository;
        this.sensorRepository = sensorRepository;
        this.shipSensorRepository = shipSensorRepository;
        this.shipDataRepository = shipDataRepository;
        this.sensorDataRepository = sensorDataRepository;
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
        shipOne = shipRepository.save(shipOne);

        User user = userRepository.findAll().get(0);
        shipOne.addUser(user);

        Sensor sensorEngineTemp1 = new Sensor("Engine 1 Temperature", "Engine 1", Sensor.GROUP.Motor, Sensor.TYPE.Temperature, "Celsius");
        Sensor sensorEngineTemp2 = new Sensor("Engine 2 Temperature", "Engine 2", Sensor.GROUP.Motor, Sensor.TYPE.Temperature, "Celsius");

        List<Sensor> sensors = sensorRepository.saveAll(List.of(sensorEngineTemp1, sensorEngineTemp2));

        Widget widgetEngineTemperatures = widgetRepository.save(new Widget("<", "Engine Temperatures", "WidgetTemperature", 1, 1, 1, 1, 1, 1));

        widgetEngineTemperatures.addSensor(sensorEngineTemp1);
        widgetEngineTemperatures.addSensor(sensorEngineTemp2);


        ShipSensor shipSensor1 = shipSensorRepository.save(new ShipSensor("bb7baec4-c049-45c5-81ce-2715801e6bff", shipOne, sensorEngineTemp1));
        ShipSensor shipSensor2 = shipSensorRepository.save(new ShipSensor("bb7baec4-c049-45c5-81ce-2715801e6bgg", shipOne, sensorEngineTemp2));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LocalDateTime dateTime = LocalDateTime.parse("13:10:00 15/07/2022", formatter);
        SensorData sensorData = sensorDataRepository.save(new SensorData(30.68, dateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
                , shipSensor1));
        SensorData sensorData2 = sensorDataRepository.save(new SensorData(67.68, dateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
                , shipSensor2));


        ShipData shipData = shipDataRepository.save(new ShipData(30.0, "123", "123", sensorData));
        ShipData shipData2 = shipDataRepository.save(new ShipData(55.0, "123", "123", sensorData2));


        Dashboard dashboard = dashboardRepository.save(new Dashboard(userRepository.findAll().get(0)));
        DashboardItem dashboardItem = dashboardItemRepository.save(new DashboardItem(0, 0, 2, 2, shipSensor1));


        dashboard.addToLayout(dashboardItem);


        System.out.println("Added a initial dashboard");

    }
}
