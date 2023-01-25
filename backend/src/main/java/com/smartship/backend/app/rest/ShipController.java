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

    private final NotificationRepository notificationRepository;

    @Autowired
    public ShipController(ShipRepository shipRepository, SensorRepository sensorRepository, ShipDataRepository shipDataRepository, SensorDataRepository sensorDataRepository, ShipSensorRepository shipSensorRepository, NotificationRepository notificationRepository) {
        this.shipRepository = shipRepository;
        this.sensorRepository = sensorRepository;
        this.shipDataRepository = shipDataRepository;
        this.sensorDataRepository = sensorDataRepository;
        this.shipSensorRepository = shipSensorRepository;
        this.notificationRepository = notificationRepository;
    }


    /**
     * This endpoint handles the POST requests to create a new ship
     * @param body
     * @return
     */
    @PostMapping
    public ResponseEntity<Ship> createShip(@RequestBody ObjectNode body) {

        String name = body.path("name").asText();
        String smartShipId = body.path("shipId").asText();

        Ship ship = new Ship(smartShipId,name);

        shipRepository.save(ship);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ship.getId()).toUri();

        return ResponseEntity.created(location).body(ship);
    }

    /**
     * This endpoint handles the GET requests to retrieve a list of all ships.
     *
     * @return list of all ships, with a status of OK.
     */
    @GetMapping
    @JsonView(CustomJson.Shallow.class)
    public ResponseEntity<List<Ship>> getAllShips() {
        return ResponseEntity.ok().body(shipRepository.findAll());
    }


    /**
     * This endpoint handles the GET requests to Find ship by smartship id
     */
    @GetMapping("{smartShipId}")
    public ResponseEntity<Ship> findShip(@PathVariable String smartShipId) {
        Ship ship = shipRepository.findBySmartShipId(smartShipId).orElseThrow(
                () -> new NotFoundException(smartShipId)
        );
        return ResponseEntity.ok().body(ship);
    }


    /**
     * This endpoint handles the POST requests to Digest ship & sensor data and store them
     * @param smartShipId Ship id
     * @param rawData     List of ship data objects
     * @return Ship instance
     */
    @PostMapping(path = "{smartShipId}")
    public ResponseEntity<Ship> addData(@PathVariable String smartShipId, @RequestBody ObjectNode... rawData) {

        // Map of unique sensors in given data List
        Map<String, Sensor> sensors = new HashMap<>();
        Map<String, ShipSensor> shipSensors = new HashMap<>();

        Ship ship = shipRepository.findBySmartShipId(smartShipId)
                .orElseThrow(() -> new NotFoundException(String.format("Ship with id %s is not found", smartShipId)));

        // Store parsed data
        List<SensorData> sensorData = new ArrayList<>();
        List<ShipData> shipData = new ArrayList<>();

        // Loop through the raw data objects
        for (ObjectNode object : rawData) {

            // Extract sensor data from the object
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

            // Parse sensor data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy").withZone(ZoneId.systemDefault());
            LocalDateTime time = LocalDateTime.parse(object.path("Time").asText(), formatter);
            ZoneId zoneId = ZoneId.systemDefault();
            Long epoch = time.atZone(zoneId).toEpochSecond();
            Double value = object.path("Value").asDouble();

            SensorData newSensorData = new SensorData(value, epoch, shipSensors.get(sensorId));
            sensorData.add(newSensorData);

            // Parse ship data
            Double speed = object.path("Speed").asDouble();
            String gpsLatitude = object.path("GPS-Latitude").asText();
            String gpsLongitude = object.path("GPS-Longitude").asText();

            shipData.add(new ShipData(speed, gpsLatitude, gpsLongitude, newSensorData));



            // Create notifications if sensor thresholds are exceeded
            ShipSensor shipSensor = shipSensors.get(sensorId);

            List<Notification> notifications = new ArrayList<>();
                if (shipSensor.getMaxThreshold() != null && shipSensor.getMaxThreshold() < newSensorData.getVal()) {
                    for (User user : ship.getUsers()) {
                    Notification notification = new Notification("Your sensor " + sensorName + " went over the maximum threshold",
                            sensorName + " went over the maximum threshold " + shipSensor.getMaxThreshold() + ". Send a mechanic to check on the sensor",
                            false, LocalDateTime.now(), Notification.TYPE.Error, user);
                        notifications.add(notification);
                }
            }
            if (shipSensor.getMinThreshold() != null && shipSensor.getMinThreshold() > newSensorData.getVal()) {
                for (User user : ship.getUsers()) {
                    Notification notification = new Notification("Your sensor " + sensorName + " went over the minimum threshold",
                            sensorName + " went over the minimum threshold " + shipSensor.getMinThreshold() + ". Send a mechanic to check on the sensor",
                            false, LocalDateTime.now(), Notification.TYPE.Error, user);
                    notifications.add(notification);
                }
            }
            notificationRepository.saveAll(notifications);
        }

        // Save data to repositories
        sensorDataRepository.saveAll(sensorData);
        shipDataRepository.saveAll(shipData);

        return ResponseEntity.ok().body(ship);
    }

    /**
     * This endpoint handles the GET requests to get all sensors of a ship
     * @param shipId The ID of the ship
     * @return
     */
    @GetMapping("{shipId}/sensors")
    @JsonView(CustomJson.Summary.class)
    public ResponseEntity<List<ShipSensor>> getShipSensors(@PathVariable Long shipId) {
        return ResponseEntity.ok().body(shipSensorRepository.findShipSensorByShipId(shipId));
    }


    /**
     * This endpoint handles the DELETE requests to delete a ship by its ID
     *  @param id The ID of the ship to delete.
     *  @return Deleted ship or a 404 Not Found error if the ship was not found.
     **/
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Ship> deleteShipById(@PathVariable long id) {
        // Find the ship by its ID
        Ship foundShip = shipRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Ship with id %s wasn't found", id)));

        // Remove the ship from all associated users
        for (int i = 0; i < foundShip.getUsers().size(); i++) {
            foundShip.getUsers().stream().toList().get(i).removeShip(foundShip);
            foundShip.removeUser(foundShip.getUsers().stream().toList().get(i));
        }

        // Delete the ship from the repository
        shipRepository.deleteById(id);

        // Return the deleted ship
        return ResponseEntity.ok().body(foundShip);
    }


    /**
     * This endpoint handles PUT requests to update a ship sensor by its ID.
     *
     * @param sensorId The ID of the ship sensor to update.
     * @param body The updated sensor data in JSON format.
     * @return Updated ship sensor, or a 404 Not Found error if the sensor was not found.
     */
    @PutMapping("/sensors/{sensorId}")
    @JsonView(CustomJson.Summary.class)
    public ResponseEntity<ShipSensor> updateSensor( @PathVariable String sensorId, @RequestBody ObjectNode body ) {
        // Find the ship sensor by its ID
        ShipSensor foundShipSensor = shipSensorRepository.findById(sensorId)
                .orElseThrow(() -> new NotFoundException(String.format("Ship Sensor with id %s wasn't found", sensorId)));

        // Update the min and max threshold values
        double min = body.get("minThreshold").asDouble();
        double max = body.get("maxThreshold").asDouble();

        foundShipSensor.setMinThreshold(body.get("minThreshold").isNull() ? null : min);
        foundShipSensor.setMaxThreshold(body.get("maxThreshold").isNull() ? null : max);

        // Save the updated sensor to the repository
        shipSensorRepository.save(foundShipSensor);

        // Return the updated sensor
        return ResponseEntity.ok().body(foundShipSensor);
    }

}
