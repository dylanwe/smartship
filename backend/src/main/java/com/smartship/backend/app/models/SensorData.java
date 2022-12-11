package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class SensorData {
    @Id
    @GeneratedValue
    @JsonView(CustomJson.Shallow.class)
    private Long id;
    @JsonView(CustomJson.Shallow.class)
    private Double val;
    @JsonView(CustomJson.Shallow.class)
    private LocalDateTime time;

    @ManyToOne
    @JsonIgnore
    private ShipSensor shipSensor;

    @OneToOne
//    @JsonView(CustomJson.Shallow.class)
    @JsonIgnore
    private ShipData shipData;


    public SensorData(Double val, LocalDateTime time, ShipSensor shipSensor) {
        this.val = val;
        this.time = time;
        this.shipSensor = shipSensor;
        }

    public SensorData() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVal() {
        return val;
    }

    public void setVal(Double val) {
        this.val = val;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public ShipSensor getShipSensor() {
        return shipSensor;
    }

    public void setShipSensor(ShipSensor shipSensor) {
        this.shipSensor = shipSensor;
    }

    public ShipData getShipData() {
        return shipData;
    }

    public void setShipData(ShipData shipData) {
        this.shipData = shipData;
    }
}
