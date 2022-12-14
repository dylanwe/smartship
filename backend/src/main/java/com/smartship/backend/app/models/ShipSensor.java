package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "ship_sensors_find_by_ship_id",
                query = "select ss from ShipSensor ss where ss.ship.id=?1"),
})

@Entity
public class ShipSensor {
    @Id
    @JsonView(CustomJson.Shallow.class)
    private String id;

    @ManyToOne
//    @JsonView(CustomJson.Shallow.class)
    @JsonIgnore
    private Ship ship;

    @ManyToOne
    @JsonView(CustomJson.Shallow.class)
//    @JsonIgnore
    private Sensor sensor;

    @OneToMany(mappedBy = "shipSensor")
    @JsonIgnore
    private Set<DashboardItem> dashboardItems;

    @OneToMany
    @JsonSerialize(using = CustomJson.ShallowSerializer.class)
    private Set<SensorData> sensorData;


    public ShipSensor() {
    }

    public ShipSensor(String id, Ship ship, Sensor sensor) {
        this.id = id;
        this.ship = ship;
        this.sensor = sensor;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Set<SensorData> getSensorData() {
        return sensorData;
    }

    public void setSensorData(Set<SensorData> sensorData) {
        this.sensorData = sensorData;
    }

    public Set<DashboardItem> getDashboardItems() {
        return dashboardItems;
    }
}
