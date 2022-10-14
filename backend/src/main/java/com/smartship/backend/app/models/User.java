package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NamedQuery(name = "find_all_users", query = "select u from User u")
@NamedQuery(name = "find_by_email", query = "select u from User u where u.email = :email")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    @Column(unique=true)
    private String email;
    @JsonIgnore
    private String hashedPassword;
    private LocalDate birthday;
    private ROLE role;
    private String bio;

    public User() {
    }

    public User(String firstname, String lastname, String email, String password, LocalDate birthday, ROLE role, String bio) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.hashedPassword = password;
        this.birthday = birthday;
        this.role = role;
        this.bio = bio;
    }

    public User(Long id, String firstname, String lastname, String email, String password, LocalDate birthday,
                ROLE role, String bio) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.hashedPassword = password;
        this.birthday = birthday;
        this.role = role;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public enum ROLE {
        Admin,
        Moderator,
        Operator
    }
}
