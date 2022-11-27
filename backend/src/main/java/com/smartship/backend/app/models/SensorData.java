package com.smartship.backend.app.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SensorData {
    @Id
    @GeneratedValue
    private Long id;

    private Double val;
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sensor sensor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ship ship;

    public SensorData(Double val, LocalDateTime time, Sensor sensor, Ship ship) {
        this.val = val;
        this.time = time;
        this.sensor = sensor;
        this.ship = ship;
    }

    public SensorData() {
    }

    public Long getId() {
        return id;
    }

    public Double getVal() {
        return val;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public Ship getShip() {
        return ship;
    }
}
