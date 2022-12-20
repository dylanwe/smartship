package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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


    private User bruce;
    private Ship shipOne;
    private Widget widget;
    private Sensor sensorEngineOneTemp;
    private ShipSensor shipSensor;
    private SensorData shipOneSensorData;
    private Dashboard dashboard;
    private DashboardItem dashboardItem;

    @BeforeEach
    void setup() {
        dashboardRepository.deleteAll();

        bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");

        userRepository.save(bruce);


        dashboard = new Dashboard(bruce);

        dashboardRepository.save(dashboard);


        shipOne = new Ship("07202515-a483-464c-b704-5671f104044b", "Ship 1");

        shipRepository.save(shipOne);

        widget = new Widget("<", "Engine Temperatures", "WidgetTemperature", 2, 2, 2, 2, 2, 2);


        sensorEngineOneTemp =new Sensor("Engine 1 Temperature", "Engine 1", Sensor.GROUP.Motor, Sensor.TYPE.Temperature, "Celsius");

        sensorRepository.saveAll(List.of(sensorEngineOneTemp));

        Widget widgetEngineTemperatures = new Widget("<", "Engine Temperatures", "WidgetTemperature", 2, 2, 2, 2, 2, 2);
        widgetRepository.save(widgetEngineTemperatures);


        widgetEngineTemperatures.addSensor(sensorEngineOneTemp);
        widgetEngineTemperatures.addSensor(sensorEngineOneTemp);



        ShipSensor shipSensor = new ShipSensor("bb7baec4-c049-45c5-81ce-2715801e6bff", shipOne,sensorEngineOneTemp );




//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
//        LocalDateTime dateTime = LocalDateTime.parse("13:10:00 15/07/2022", formatter);
//
//        shipOneSensorData = new SensorData(67.68, dateTime, shipSensor);

    }


    @Test
    public void itShouldAddItemToLayout() {

        dashboard = new Dashboard(bruce);
        dashboardItem = new DashboardItem(1, 3, 2, 2, shipSensor);

        boolean added = dashboard.addToLayout(dashboardItem);
        dashboardRepository.save(dashboard);

        assertThat(added).isTrue();
    }

    @Test
    public void itShoulAddUserToShip() {
        boolean added = shipOne.addUser(bruce);
        assertThat(added).isTrue();
    }


    @Test
    public void itShouldCreateASensorAndLinkToWidget() {
        boolean added = shipOne.addUser(bruce);
        assertThat(added).isTrue();
    }


}