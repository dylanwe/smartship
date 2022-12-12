package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Sensor {
    @Id
    @GeneratedValue
    @JsonView(CustomJson.Shallow.class)
    private Long id;
    @JsonView(CustomJson.Shallow.class)
    private String name;
    @JsonView(CustomJson.Shallow.class)
    private String nameShort;
    @JsonView(CustomJson.Shallow.class)
    private GROUP groupName;
    @JsonView(CustomJson.Shallow.class)
    private TYPE type;
    @JsonView(CustomJson.Shallow.class)
    private String unit;

    @ManyToOne
    @JsonView(CustomJson.Shallow.class)
    private Widget widget;

    @OneToMany
    @JsonIgnore
    private Set<ShipSensor> shipSensors;


    public Sensor(String name, String nameShort, GROUP groupName, TYPE type, String unit) {
        this.name = name;
        this.nameShort = nameShort;
        this.groupName = groupName;
        this.type = type;
        this.unit = unit;
    }

    public Sensor() {
    }


    public boolean addWidget(Widget widget) {
        if (widget != null && this.getWidget() == null) {
            // update both sides of the association
            widget.addSensor(this);
            this.widget = widget;
            return true;
        }
        return false;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameShort() {
        return nameShort;
    }

    public void setNameShort(String nameShort) {
        this.nameShort = nameShort;
    }

    public GROUP getGroupName() {
        return groupName;
    }

    public void setGroupName(GROUP groupName) {
        this.groupName = groupName;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Widget getWidget() {
        return widget;
    }

    public Set<ShipSensor> getShipSensors() {
        return shipSensors;
    }

    public void setShipSensors(Set<ShipSensor> shipSensors) {
        this.shipSensors = shipSensors;
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
