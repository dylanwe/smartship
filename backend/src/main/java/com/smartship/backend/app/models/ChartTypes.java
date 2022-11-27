package com.smartship.backend.app.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChartTypes {

    @Id
    @GeneratedValue
    private Long id;

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

