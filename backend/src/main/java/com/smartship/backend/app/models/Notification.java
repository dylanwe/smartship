package com.smartship.backend.app.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String body;
    private LocalDate notificationDateTime;
    private TYPE type;


    public Notification() {
    }

    public Notification(Long id, String title, String body, LocalDate notificationDateTime, TYPE type) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
