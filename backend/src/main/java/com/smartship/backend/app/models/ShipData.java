package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;

@Entity
public class ShipData {
    @Id
    @GeneratedValue
    @JsonView(CustomJson.Shallow.class)
    private Long id;

    @JsonView(CustomJson.Shallow.class)
    private Double speed;

    @JsonView(CustomJson.Shallow.class)
    private String gps_Latitude;

    @JsonView(CustomJson.Shallow.class)
    private String gps_Longitude;

    @OneToOne
    @JsonIgnore
    private SensorData sensorData;

    public ShipData() {
    }

    public ShipData(Double speed, String gps_Latitude, String gps_Longitude,SensorData sensorData ) {
        this.speed = speed;
        this.gps_Latitude = gps_Latitude;
        this.gps_Longitude = gps_Longitude;
        this.sensorData = sensorData;

    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public String getGps_Latitude() {
        return gps_Latitude;
    }

    public void setGps_Latitude(String gps_Latitude) {
        this.gps_Latitude = gps_Latitude;
    }

    public String getGps_Longitude() {
        return gps_Longitude;
    }

    public void setGps_Longitude(String gps_Longitude) {
        this.gps_Longitude = gps_Longitude;
    }

    public SensorData getSensorData() {
        return sensorData;
    }

    public void setSensorData(SensorData sensorData) {
        this.sensorData = sensorData;
    }

}
