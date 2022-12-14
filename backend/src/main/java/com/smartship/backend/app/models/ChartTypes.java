package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChartTypes {

    @Id
    @GeneratedValue
    @JsonView(CustomJson.Shallow.class)
    private Long id;

    @JsonView(CustomJson.Shallow.class)
    private TYPE chart;


    public ChartTypes() {
    }

    public Long getId() {
        return id;
    }
    public TYPE getChart() {
        return chart;
    }

    public enum TYPE {
        LINE,
        BAR,
        PIE
    }

}

