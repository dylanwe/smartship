package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Ship {
    @Id
    @GeneratedValue
    @JsonView(CustomJson.Shallow.class)
    private Long id;

    @JsonView(CustomJson.Shallow.class)
    private String smartShipId;

    @JsonView(CustomJson.Shallow.class)
    private String name;

    @OneToMany(mappedBy = "ship")
    @JsonIgnore
    private Set<User> users = new HashSet<>();


    @OneToMany
    @JsonSerialize(using = CustomJson.ShallowSerializer.class)
    private Set<ShipSensor> shipSensors = new HashSet<>();


    public Ship(String smartShipId, String name) {
        this.smartShipId = smartShipId;
        this.name = name;
    }

    public Ship(String smartShipId) {
        this.smartShipId = smartShipId;
    }

    public Ship() {
    }

    /**
     * Associates the given item with this layout, if not yet associated
     *
     * @param user Dashboard item
     * @return whether a new association has been added
     */
    public boolean addUser(User user) {
        if (user != null && user.getShip() == null) {
            // update both sides of the association
            return this.users.add(user) && user.connectToShip(this);
        }
        return false;
    }

    /**
     * Dissociates the given item with this layout
     *
     * @param user Dashboard item
     * @return whether an existing association has been removed
     */
    public boolean removeUser(User user) {
        if (user != null && user.getShip() != null) {
            return this.users.remove(user) && user.connectToShip(null);
        }

        return false;
    }


    public Set<ShipSensor> getShipSensors() {
        return shipSensors;
    }

    public void setShipSensors(Set<ShipSensor> shipSensors) {
        this.shipSensors = shipSensors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmartShipId() {
        return smartShipId;
    }

    public void setSmartShipId(String smartShipId) {
        this.smartShipId = smartShipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", smartShipId='" + smartShipId + '\'' +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", shipSensors=" + shipSensors +
                '}';
    }
}
