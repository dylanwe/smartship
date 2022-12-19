package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartship.backend.app.exceptions.UnprocessableEntityException;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String hashedPassword;
    private LocalDate birthday;
    private ROLE role;
    private String bio;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user" )
    private List<ToDo> toDos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonSerialize(using = CustomJson.ShallowSerializer.class)
    private List<Notification> notification;

    public User() {
    }

    public User(String firstName, String lastName, String email, String hashedPassword, LocalDate birthday, ROLE role, String bio) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.birthday = birthday;
        this.role = role;
        this.bio = bio;
    }

    public User(Long id, String firstName, String lastName, String email, String hashedPassword, LocalDate birthday,
                ROLE role, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.birthday = birthday;
        this.role = role;
        this.bio = bio;
    }

    /**
     * Validate the information of a user
     *
     * @param user
     */
    public static void validateNewUserInformation(User user) {
        if (user.getFirstName().length() < 2)
            throw new UnprocessableEntityException("First name should be longer than 1 characters");
        if (user.getLastName().length() < 2)
            throw new UnprocessableEntityException("Last name should be longer than 1 characters");
        if (user.getBirthday().isAfter(LocalDate.now()) && user.getBirthday().isEqual(LocalDate.now()))
            throw new UnprocessableEntityException("Birthday can't be in the future");

        String emailPattern = "^(.+)@(\\S+)$";
        if (user.getEmail().length() < 4 && !user.getEmail().matches(emailPattern))
            throw new UnprocessableEntityException("E-mail was wrong");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", birthday=" + birthday +
                ", role=" + role +
                ", bio='" + bio + '\'' +
                ", toDos=" + toDos +
                '}';
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<ToDo> getToDos() {
        return toDos;
    }
//    public Set<Notification> getNotifications() {
//        return notification;
//    }


    public List<Notification> getNotification() {
        return notification;
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }

    public enum ROLE {
        Admin,
        Manager,
        Operator
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
