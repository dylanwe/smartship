package com.smartship.backend.app.config;

import com.smartship.backend.app.models.*;
import com.smartship.backend.app.repositories.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Component
public class InitialDataImpl implements InitialData {
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
    public InitialDataImpl(UserRepository userRepository, DashboardRepository dashboardRepository,
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

    @Override
    public void addData() {
        createInitialUsers();
        addNotificationSettings();
        createInitialDashboards();
    }

    /**
     * Create initial users
     */
    private void createInitialUsers() {
        List<User> users = userRepository.findAll();
        if (users.size() > 0) return;

        System.out.println("Adding a initial users");
        // Encrypt the password
        String hashedPassword = BCrypt.hashpw("secret", BCrypt.gensalt());
        String hashedPassword2 = BCrypt.hashpw("manager", BCrypt.gensalt());
        String hashedPassword3 = BCrypt.hashpw("admin", BCrypt.gensalt());

        User user =  userRepository.save(new User(
                "John",
                "Smith",
                "test@mail.com",
                hashedPassword,
                LocalDate.now(),
                User.ROLE.Operator,
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        ));

        userRepository.save(new User(
                "Manager",
                "Smith",
                "manager@mail.com",
                hashedPassword2,
                LocalDate.now(),
                User.ROLE.Manager,
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        ));

        userRepository.save(new User(
                "Admin",
                "Smith",
                "admin@mail.com",
                hashedPassword3,
                LocalDate.now(),
                User.ROLE.Admin,
                "See everything in it's entirety... effortlessly. That is what it means to truly see."
        ));

        Ship ship =  shipRepository.save(new Ship("07202515-a483-464c-b704-5671f104044b", "Titanic"));

        user.connectToShip(ship);

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

        Ship shipOne = shipRepository.findBySmartShipId("07202515-a483-464c-b704-5671f104044b")
                .orElseThrow();
//                new Ship("07202515-a483-464c-b704-5671f104044b", "Ship 1");
//        shipOne = shipRepository.save(shipOne);

//        User user = userRepository.findAll().get(0);
//        user.connectToShip(shipOne);

        Sensor sensorEngineTemp1 = new Sensor("Engine 1 Temperature", "Engine 1", Sensor.GROUP.Motor, Sensor.TYPE.Temperature, "Celsius");
        Sensor sensorEngineTemp2 = new Sensor("Engine 2 Temperature", "Engine 2", Sensor.GROUP.Motor, Sensor.TYPE.Temperature, "Celsius");

        Sensor sensorBatteryTemp1 = new Sensor("Battery 1 Temperature", "Battery 1", Sensor.GROUP.Battery, Sensor.TYPE.Temperature, "Celsius");
        Sensor sensorBatteryTemp2 = new Sensor("Battery 2 Temperature", "Battery 2", Sensor.GROUP.Battery, Sensor.TYPE.Temperature, "Celsius");


        Sensor sensorEngineUsage1 = new Sensor("Engine 1 Usage", "Engine 1", Sensor.GROUP.Battery, Sensor.TYPE.Temperature, "%");

        List<Sensor> sensors = sensorRepository.saveAll(List.of(sensorEngineTemp1, sensorEngineTemp2, sensorBatteryTemp1, sensorBatteryTemp2, sensorEngineUsage1));

        Widget widgetTemperatures = new Widget("<", "Temperatures", "SmallLineChart", 1, 1, 1, 2, 1, 1);


        Widget widgetEngine = new Widget("<", "Engine", "LineChart", 2, 5, 2, 5, 2, 2);

        widgetRepository.saveAll(List.of(
                widgetTemperatures, widgetEngine
        ));
        // Dont move this below addsensor loop
        widgetEngine.addSensor(sensorEngineUsage1);

        // add sensors
        for (Sensor sensor : sensors) {
            widgetTemperatures.addSensor(sensor);
        }

        widgetRepository.saveAll(List.of(widgetTemperatures));

        List<ShipSensor> shipsSensors = List.of(
                shipSensorRepository.save(new ShipSensor("bb7baec4-c049-45c5-81ce-2715801e6bff", shipOne, sensorEngineTemp1)),
                shipSensorRepository.save(new ShipSensor("bb7baec4-c049-45c5-81ce-2715801e6bgg", shipOne, sensorEngineTemp2)),
                shipSensorRepository.save(new ShipSensor("bb7baec4-c049-45c5-81ce-2715801e6begz", shipOne, sensorBatteryTemp1)),
                shipSensorRepository.save(new ShipSensor("bb7baec4-c049-45c5-81ce-2715801e6begee", shipOne, sensorBatteryTemp2)),
                shipSensorRepository.save(new ShipSensor("bb7baec4-c049-45c5-81ce-2715801ebb6begee", shipOne, sensorEngineUsage1))
        );

        // Create a LocalDateTime instance using the random values
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LocalDateTime dateTime = LocalDateTime.parse("13:10:00 15/07/2022", formatter);
        for (ShipSensor shipSensor : shipsSensors) {
            for (int i = 0; i < 10; i++) {
                // Create a new Random instance
                Random random = new Random();

                // Get a random year, month, day, hour, minute, and second
                int year = random.nextInt(10_000) + 1;
                int month = random.nextInt(12) + 1;
                int day = random.nextInt(28) + 1;
                int hour = random.nextInt(24);
                int minute = random.nextInt(60);
                int second = random.nextInt(60);

                Double randomValue = new Random().nextDouble(100);
                SensorData sensorData = sensorDataRepository.save(new SensorData(randomValue, LocalDateTime.of(2022, month, day, hour, minute, second).atZone(ZoneId.systemDefault()).toEpochSecond(), shipSensor, 50.0));

                shipDataRepository.save(new ShipData(randomValue, "123", "123", sensorData));
            }
        }

        Dashboard dashboard = dashboardRepository.save(new Dashboard(userRepository.findAll().get(0)));
        DashboardItem dashboardItem = dashboardItemRepository.save(new DashboardItem(0, 0, 2, 2, shipsSensors.get(0)));

        userRepository.findAll().get(0).setDashboard(dashboard);

        dashboard.addToLayout(dashboardItem);
        System.out.println("Added a initial dashboard");
    }

}
