package com.smartship.backend.app.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue
    private Long notificationId;
    private String title;
    private String body;
    private LocalDate notificationDateTime;
    private TYPE type;


    public Notification() {
    }

    public Notification(Long id, String title, String body, LocalDate notificationDateTime, TYPE type) {
        this.notificationId = id;
        this.title = title;
        this.body = body;
        this.notificationDateTime = notificationDateTime;
        this.type = type;
    }

    public Notification(String title, String body, LocalDate notificationDateTime, TYPE type) {
        this.title = title;
        this.body = body;
        this.notificationDateTime = notificationDateTime;
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonSerialize(using = CustomJson.ShallowSerializer.class)
    private User users;

    public Long getId() {
        return notificationId;
    }

    public void setId(Long id) {
        this.notificationId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(LocalDate notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public enum TYPE {
        Error,
        Info,
        Message
    }
}
