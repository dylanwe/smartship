package com.smartship.backend.app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Ship {
    @Id
//    @GeneratedValue
    private String id;

    private String name;

    @OneToMany
    private Set<SensorData> sensorData;

    @OneToMany
    private Set<ShipData> shipData;

    public Ship(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ship(String name) {
        this.name = name;
    }

    public Ship() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<SensorData> getSensorData() {
        return sensorData;
    }

    public Set<ShipData> getShipData() {
        return shipData;
    }
}
