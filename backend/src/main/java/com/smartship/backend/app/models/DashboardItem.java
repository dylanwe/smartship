package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;

@Entity
public class DashboardItem {
    @Id
    @GeneratedValue
    @JsonView(CustomJson.Shallow.class)
    private Long id;

    @JsonView(CustomJson.Shallow.class)
    private int x;

    @JsonView(CustomJson.Shallow.class)
    private int y;

    // Width
    @JsonView(CustomJson.Shallow.class)
    private int w;

    // Height
    @JsonView(CustomJson.Shallow.class)
    private int h;

    @ManyToOne
    @JsonIgnore
    private Dashboard dashboard;

    @ManyToOne
    @JoinColumn(name = "shipSensor")
    @JsonView(CustomJson.Shallow.class)
    private ShipSensor shipSensor;


    public DashboardItem() {
    }

    public DashboardItem(int x, int y, int width, int height, ShipSensor shipSensor) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.shipSensor = shipSensor;
    }

    public boolean connectToLayout(Dashboard dashboard) {
        if (dashboard != null && this.getDashboard() == null) {
            // update both sides of the association
            dashboard.addToLayout(this);
            this.setDashboard(dashboard);
            return true;

        } else if (dashboard == null && this.getDashboard() != null) {
            this.setDashboard(null);
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard layout) {
        this.dashboard = layout;
    }

    public ShipSensor getShipSensor() {
        return shipSensor;
    }


}
