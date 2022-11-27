package com.smartship.backend.app.models;

import javax.persistence.*;

@Entity
public class ShipData {
    @Id
    @GeneratedValue
    private Long id;

    private Double speed;

    private String gps_Latitude;
    private String gps_Longtitude;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ship ship;

    @ManyToOne(fetch = FetchType.LAZY)
    private SensorData sensorData;

    public ShipData() {
    }


    public Long getId() {
        return id;
    }

    public Double getSpeed() {
        return speed;
    }

    public String getGps_Latitude() {
        return gps_Latitude;
    }

    public String getGps_Longtitude() {
        return gps_Longtitude;
    }

    public Ship getShip() {
        return ship;
    }

    public SensorData getSensorData() {
        return sensorData;
    }
}
