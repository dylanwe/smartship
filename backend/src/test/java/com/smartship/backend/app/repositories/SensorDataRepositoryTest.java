package com.smartship.backend.app.repositories;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.smartship.backend.app.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Jesaja Pavlovic
 */
@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
class SensorDataRepositoryTest {

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



        shipSensor = new ShipSensor("bb7baec4-c049-45c5-81ce-2715801e6bff", shipOne,sensorEngineOneTemp );






    }


    @Test
    public void itShouldAddItemToLayout() {

        DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
        LocalDateTime dateTime = LocalDateTime.parse("13:10:00 15/07/2022", formatterr);



//        System.out.println(dateTime);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy").withZone(ZoneId.systemDefault());


//        shipOneSensorData = new SensorData(67.68, dateTime, shipSensor);;
//
//        SensorData test = sensorDataRepository.save(shipOneSensorData);
//
//        System.out.println(        sensorDataRepository.findSensorDataByShipSensorId(shipSensor.getId())
//        );
//        System.out.println(test.getTime());
    }


}