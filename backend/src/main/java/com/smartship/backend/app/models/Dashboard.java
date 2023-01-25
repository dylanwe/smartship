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

    @OneToOne
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

    /**
     * Associates the given user with this dashboard, if not yet associated
     *
     * @param user user
     * @return whether a new association has been added
     */
    public boolean addUser(User user) {
        if (user != null && user.getDashboard() == null) {
            // update both sides of the association
            this.setUser(user);
            user.connectToDashboard(this);
            return true;
        }
        return false;
    }

    /**
     * Dissociates the given user with this dashboard
     *
     * @param user user
     * @return whether an existing association has been removed
     */
    public boolean removeUser(User user) {
        if (user != null && this.getUser() != null) {
            this.setUser(null);
            user.removeDashboard(this);
            return true;
        }
        return false;
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

    public void setUser(User user) {
        this.user = user;
    }

    public Set<DashboardItem> getLayout() {
        return layout;
    }

}
