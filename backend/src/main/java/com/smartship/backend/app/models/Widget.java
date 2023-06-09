package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Widget {


    @Id
    @GeneratedValue
    @JsonView(CustomJson.Shallow.class)
    private Long id;

    @JsonView(CustomJson.Shallow.class)
    private String componentName;
    @JsonView(CustomJson.Shallow.class)
    private String icon;
    @JsonView(CustomJson.Shallow.class)
    private String title;
    @JsonView(CustomJson.Shallow.class)
    private int minHeight;
    @JsonView(CustomJson.Shallow.class)
    private int maxHeight;
    @JsonView(CustomJson.Shallow.class)
    private int minWidth;
    @JsonView(CustomJson.Shallow.class)
    private int maxWidth;
    @JsonView(CustomJson.Shallow.class)
    private int defaultHeight;
    @JsonView(CustomJson.Shallow.class)
    private int defaultWidth;

    @OneToMany
    @JsonIgnore
    private Set<Sensor> sensors = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonView(CustomJson.Summary.class)
    private ChartTypes chartTypes;

    public Widget() {
    }

    public Widget(String icon, String title, String componentName, int minHeight, int maxHeight, int minWidth, int maxWidth, int defaultHeight, int defaultWidth) {
        this.icon = icon;
        this.title = title;
        this.componentName = componentName;


        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.defaultHeight = defaultHeight;
        this.defaultWidth = defaultWidth;

    }

    /**
     * Associates the given item with this layout, if not yet associated
     *
     * @param sensor Dashboard item
     * @return whether a new association has been added
     */

    public boolean addSensor(Sensor sensor) {
        if (sensor != null && sensor.getWidget() == null){
            return this.sensors.add(sensor) && sensor.addWidget(this);
        }
        return false;
    }



    public Long getId() {
        return id;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getComponentName() {
        return componentName;
    }

    public ChartTypes getChartTypes() {
        return chartTypes;
    }


    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }

    public Set<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        this.sensors = sensors;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }
}
