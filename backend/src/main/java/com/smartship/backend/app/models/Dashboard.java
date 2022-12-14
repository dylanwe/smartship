package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;
import java.util.*;

@Entity
public class Dashboard {
    @Id
    @GeneratedValue
    @JsonView(CustomJson.Shallow.class)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "dashboard")
    @JsonSerialize(using = CustomJson.ShallowSerializer.class)
    private Set<DashboardItem> layout = new HashSet<>();


    public Dashboard() {
    }

    public Dashboard(User user) {
        this.user = user;
    }


    public void addAllToLayout(DashboardItem[] items){
        for (DashboardItem dashboardItem : layout) {
          removeItem(dashboardItem);
        }

        for (DashboardItem item : items) {
            System.out.println(item.getId());
            addToLayout(item);
        }



    }

    /**
     * Associates the given item with this layout, if not yet associated
     *
     * @param item Dashboard item
     * @return whether a new association has been added
     */
    public boolean addToLayout(DashboardItem item) {
        if (item != null && item.getDashboard() == null) {
            // update both sides of the association
            return this.layout.add(item) && item.connectToLayout(this);
        }
        return false;
    }

    /**
     * Dissociates the given item with this layout
     *
     * @param item Dashboard item
     * @return whether an existing association has been removed
     */
    public boolean removeItem(DashboardItem item) {
        if (item != null && item.getDashboard() != null) {
            return this.layout.remove(item) && item.connectToLayout(null);
        }

        return false;
    }

    public DashboardItem findItemById(Long id) {
        return this.layout.stream().filter(c -> Objects.equals(c.getId(), id)).findFirst().orElse(null);
    }
    public DashboardItem findItem(DashboardItem item) {
        return this.layout.stream().filter(c -> Objects.equals(c,item)).findFirst().orElse(null);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }


    public Set<DashboardItem> getLayout() {
        return layout;
    }

    public void setLayout(Set<DashboardItem> layout) {
        this.layout = layout;
    }
}
