package com.smartship.backend.app.models;

import javax.persistence.*;

@Entity
@Table(name = "dashboardItems")
public class DashboardItem {
    @Id
    @GeneratedValue
    private Long id;

    private int x;
    private int y;
    private int width;
    private int height;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dashboard_id", referencedColumnName = "id")
    private Dashboard dashboard;

    @OneToOne
    @JoinColumn(name = "widget_id", referencedColumnName = "id")
    private Widget widget;



    public DashboardItem() {
    }

    public DashboardItem(int x, int y, int width, int height, Widget widget) {
         this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.widget = widget;
    }

    public boolean connectToLayout(Dashboard dashboard) {
        if (dashboard != null && this.getDashboard() == null) {
            // update both sides of the association
            dashboard.addToLayout(this);
            this.setDashboard(dashboard);
            return true;

        } else if (dashboard == null && this.getDashboard()!= null) {
            this.setDashboard(null);
            return true;
        }
        return false;
    }


    public Long getId() {
        return id;
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

    public Widget getWidget() {
        return widget;
    }


}
