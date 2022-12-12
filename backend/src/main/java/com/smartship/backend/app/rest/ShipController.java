package com.smartship.backend.app.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartship.backend.app.exceptions.NotFoundException;
import com.smartship.backend.app.models.*;
import com.smartship.backend.app.repositories.*;
import com.smartship.backend.app.views.CustomJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/ships")
public class ShipController {

    private final ShipRepository shipRepository;
    private final SensorRepository sensorRepository;
    private final ShipDataRepository shipDataRepository;
    private final SensorDataRepository sensorDataRepository;
    private final ShipSensorRepository shipSensorRepository;

    @Autowired
    public ShipController(ShipRepository shipRepository, SensorRepository sensorRepository, ShipDataRepository shipDataRepository, SensorDataRepository sensorDataRepository, ShipSensorRepository shipSensorRepository) {
        this.shipRepository = shipRepository;
        this.sensorRepository = sensorRepository;
        this.shipDataRepository = shipDataRepository;
        this.sensorDataRepository = sensorDataRepository;
        this.shipSensorRepository = shipSensorRepository;
    }


    /**
     * Create a new ship
     * @param shipBody
     * @return
     */
    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestBody Ship shipBody) {

        Ship ship = shipRepository.save(shipBody);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ship.getId()).toUri();

        return ResponseEntity.created(location).body(ship);
    }

    /**
     * Get all ships
     * @return List of ships
     */
    @GetMapping
    @JsonView(CustomJson.Shallow.class)
    public ResponseEntity<List<Ship>> getAllShips() {
        return ResponseEntity.ok().body(shipRepository.findAll());
    }


    /**
     * Find ship by smartship id
     */
    @GetMapping("{smartShipId}")
    public ResponseEntity<Ship> findShip(@PathVariable String smartShipId) {
        Ship ship = shipRepository.findBySmartShipId(smartShipId).orElseThrow(
                () -> new NotFoundException(smartShipId)
        );
        return ResponseEntity.ok().body(ship);
    }


    /**
     * Digest ship & sensor data and store them
     * @param smartShipId Ship id
     * @param rawData     List of ship data objects
     * @return Ship instance
     */
    @PostMapping(path = "{smartShipId}")
    public ResponseEntity<Ship> addData(@PathVariable String smartShipId, @RequestBody ObjectNode... rawData) {
        //FIXME JWT role check (admin)

        // Map of unique sensors in given data List
        Map<String, Sensor> sensors = new HashMap<>();
        Map<String, ShipSensor> shipSensors = new HashMap<>();

        Ship ship = shipRepository.findBySmartShipId(smartShipId)
                .orElseThrow(() -> new NotFoundException(String.format("Ship with id %s is not found", smartShipId)));

        // Store parsed data
        List<SensorData> sensorData = new ArrayList<>();
        List<ShipData> shipData = new ArrayList<>();


        for (ObjectNode object : rawData) {

            String sensorId = object.path("SensorID").asText();
            String sensorName = object.path("SensorName").asText();
            Sensor.GROUP sensorGroup = Sensor.GROUP.valueOf(object.path("Group").asText());
            Sensor.TYPE sensorType = Sensor.TYPE.valueOf(object.path("Type").asText());
            String unit = object.get("Unit").asText();


            // Add object's corresponding sensor to map if not yet added.
            if (!sensors.containsKey(sensorName)) {
                Optional<Sensor> foundSensor = sensorRepository.findByName(sensorName);

                if (foundSensor.isEmpty()) {
                    sensors.put(sensorName, sensorRepository.save(new Sensor(sensorName, null, sensorGroup, sensorType, unit)));
                } else {
                    sensors.put(sensorName, foundSensor.get());
                }

            }

            if (!shipSensors.containsKey(sensorId)) {
                Optional<ShipSensor> foundShipSensor = shipSensorRepository.findById(sensorId);

                if (foundShipSensor.isEmpty()) {
                    shipSensors.put(sensorId, shipSensorRepository.save(new ShipSensor(sensorId, ship, sensors.get(sensorName))));
                } else {
                    shipSensors.put(sensorId, foundShipSensor.get());
                }
            }

            // Parse sensor data 13:10:00 15/07/2022
            System.out.println(Instant.parse(object.path("Time").asText()));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy").withZone(ZoneId.systemDefault());

            LocalDateTime time = LocalDateTime.parse(object.path("Time").asText(), formatter);
            ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
            Long epoch = time.atZone(zoneId).toEpochSecond();

            Double value = object.path("Value").asDouble();

            SensorData newSensorData = new SensorData(value, epoch, shipSensors.get(sensorId));
            sensorData.add(newSensorData);

            // Parse ship data
            Double speed = object.path("Speed").asDouble();
            String gpsLatitude = object.path("GPS-Latitude").asText();
            String gpsLongitude = object.path("GPS-Longitude").asText();


            shipData.add(new ShipData(speed, gpsLatitude, gpsLongitude, newSensorData));

        }

        // Save data to repos
        sensorDataRepository.saveAll(sensorData);
        shipDataRepository.saveAll(shipData);

        return ResponseEntity.ok().body(ship);
    }

    /**
     * Get all sensors of a ship
     * @param shipId
     * @return
     */
    @GetMapping("{shipId}/sensors")
    @JsonView(CustomJson.Summary.class)
    public ResponseEntity<List<ShipSensor>> getShipSensors(@PathVariable Long shipId) {
        return ResponseEntity.ok().body(shipSensorRepository.findShipSensorByShipId(shipId));
    }




}
