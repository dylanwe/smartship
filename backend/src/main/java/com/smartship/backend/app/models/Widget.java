package com.smartship.backend.app.models;

import javax.persistence.*;

@Entity
@Table(name = "widgets")
public class Widget {


    @Id
    @GeneratedValue
    private Long id;
    private String icon;
    private String title;
    private String componentName;
    private int minHeight;
    private int maxHeight;
    private int minWidth;
    private int maxWidth;
    private int defaultHeight;
    private int defaultWidth;

    @OneToOne
    private DashboardItem dashboardItem;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public int getDefaultWidth() {
        return defaultWidth;
    }
}
