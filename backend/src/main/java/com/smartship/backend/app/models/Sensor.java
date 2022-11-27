package com.smartship.backend.app.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Sensor {
    @Id
    private String id;
    private String name;

    private GROUP groupName;
    private TYPE type;
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Widget widget;

    @OneToMany
    private Set<SensorData> sensorData;

    public Sensor(String id, String name, GROUP groupName, TYPE type, String unit) {
        this.id = id;
        this.name = name;
        this.groupName = groupName;
        this.type = type;
        this.unit = unit;
    }

    public Sensor() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public GROUP getGroupName() {
        return groupName;
    }

    public TYPE getType() {
        return type;
    }

    public String getUnit() {
        return unit;
    }

    public Widget getWidget() {
        return widget;
    }

    public Set<SensorData> getSensorData() {
        return sensorData;
    }

    public enum GROUP {
        Motor,
        Sea_Conditions,
        Battery,
        Fuel
    }
    public enum TYPE {
        Temperature,
        Quantity,
        RPM,
        Speed,
        Depletion_Rate
    }



}
