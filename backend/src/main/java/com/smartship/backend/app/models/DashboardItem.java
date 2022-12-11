package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @JsonView(CustomJson.Shallow.class)
    private int width;

    @JsonView(CustomJson.Shallow.class)
    private int height;

    @ManyToOne
    @JsonIgnore
    private Dashboard dashboard;

    @OneToOne
    @JsonView(CustomJson.Shallow.class)
    private ShipSensor shipSensor;


    public DashboardItem() {
    }

    public DashboardItem(int x, int y, int width, int height, ShipSensor shipSensor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
