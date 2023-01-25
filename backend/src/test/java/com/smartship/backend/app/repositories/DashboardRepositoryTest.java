package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jesaja Pavlovic
 */
@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
class DashboardRepositoryTest {


    @Autowired
    private DashboardRepository dashboardRepository;
    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WidgetRepository widgetRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private SensorDataRepository sensorDataRepository;
    @Autowired
    private ShipSensorRepository shipSensorRepository;


    @BeforeEach
    void setup() {
        addTestDashboard();
        addTestShipData();
    }

    @AfterEach
    void tearDown() {
        shipSensorRepository.deleteAll();
        shipRepository.deleteAll();
        userRepository.deleteAll();
        widgetRepository.deleteAll();
        sensorRepository.deleteAll();
        sensorDataRepository.deleteAll();
        dashboardRepository.deleteAll();
    }


    @Test
    void itShouldFindADashboard() {
        List<Dashboard> dashboards = dashboardRepository.findAll();
        // Check if dashboard has been successfully created
        assertThat(dashboards.size())
                .withFailMessage("Expected to find 1, but found 0")
                .isEqualTo(1);
    }

    @Test
    public void itShouldAddItemToLayout() {
        // Get the first dashboard from the repository
        Dashboard dashboard = dashboardRepository.findAll().get(0);
        // Create a new dashboard item
        DashboardItem dashboardItem = new DashboardItem(1, 3, 2, 2, shipSensorRepository.findAll().get(0));

        // Assert that the dashboard's layout is initially empty
        assertThat(dashboard.getLayout().size()).isZero();

        dashboard.addToLayout(dashboardItem);


        // Assert that the dashboard's layout is now not empty
        assertThat(dashboard.getLayout().size())
                .withFailMessage("No dashboard item was added.")
                .isGreaterThan(0);

        // Assert that the dashboard item was added to the correct dashboard
        assertEquals(dashboardItem.getDashboard(), dashboard, "The two dashboards aren't the same");

    }

    @Test
    public void itShouldFindDashboardByUserId() {
        User user = userRepository.findAll().get(0);

        Optional<Dashboard> dashboard = dashboardRepository.findByUserId(user.getId());

        assertThat(dashboard)
                .withFailMessage("Expected to find dashboard by user id, but found nothing")
                .isPresent();
    }

    @Test
    public void itShouldNotFindDashboard() {
        // Make a user
        User bruce = new User(
                "Bruce",
                "Wayne",
                "eafaef@wayne.com",
                BCrypt.hashpw("secret", BCrypt.gensalt()),
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");

        // save user
        bruce = userRepository.save(bruce);

        Boolean dashboard = dashboardRepository.existsByUserId(bruce.getId());

        assertThat(dashboard)
                .withFailMessage("Expected to not find dashboard by user id, but found")
                .isFalse();
    }





    /**
     * This method adds test data for a user and a dashboard.
     *
     * The test data includes:
     * - A user
     * - A dashboard associated with the user.
     */
    private void addTestDashboard() {
        // Create a new user
        User bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");

        // Save the user to the repository
        userRepository.save(bruce);

        // Create a new dashboard associated with the user
        dashboardRepository.save(new Dashboard(bruce));
    }



    /**
     * This method adds test data for a ship, its sensor, its widget, and its sensor data.
     *
     * The test data includes:
     * - A ship with ID "07202515-a483-464c-b704-5671f104044b" and name "Ship 1"
     * - A sensor for the ship's engine temperature, with name "Engine 1 Temperature"
     * - A widget for displaying the engine temperatures, with name "Engine Temperatures"
     * - A ship sensor linking the ship and the sensor, with ID "bb7baec4-c049-45c5-81ce-2715801e6bff"
     * - Sensor data for the ship's sensor, with a value of 67.68 and a timestamp of "13:10:00 15/07/2022"
     */
    private void addTestShipData() {
        // Create a new ship
        Ship shipOne = new Ship("07202515-a483-464c-b704-5671f104044b", "Ship 1");
        shipRepository.save(shipOne);

        // Create a new sensor for the ship's engine temperature
        Sensor sensorEngineOneTemp = new Sensor("Engine 1 Temperature", "Engine 1", Sensor.GROUP.Motor, Sensor.TYPE.Temperature, "Celsius");
        sensorRepository.saveAll(List.of(sensorEngineOneTemp));

        // Create a new widget for displaying the engine temperatures
        Widget widgetEngineTemperatures = new Widget("<", "Engine Temperatures", "WidgetTemperature", 2, 2, 2, 2, 2, 2);
        widgetRepository.save(widgetEngineTemperatures);

        // Add the sensor to the widget
        widgetEngineTemperatures.addSensor(sensorEngineOneTemp);
        widgetEngineTemperatures.addSensor(sensorEngineOneTemp);

        // Create a new ship sensor linking the ship and the sensor
        ShipSensor shipSensor = new ShipSensor("bb7baec4-c049-45c5-81ce-2715801e6bff", shipOne, sensorEngineOneTemp);
        shipSensorRepository.save(shipSensor);

    }


}