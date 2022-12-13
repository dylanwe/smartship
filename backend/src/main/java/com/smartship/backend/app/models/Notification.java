package com.smartship.backend.app.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.smartship.backend.app.views.CustomJson;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue
    private Long notificationId;
    private String title;
    private String body;
    private LocalDate notificationDateTime;
    private TYPE notificationType;


    public Notification() {
    }

    public Notification(Long id, String title, String body, LocalDate notificationDateTime, TYPE notificationType) {
        this.notificationId = id;
        this.title = title;
        this.body = body;
        this.notificationDateTime = notificationDateTime;
        this.notificationType = notificationType;
    }

    public Notification(String title, String body, LocalDate notificationDateTime, TYPE notificationType) {
        this.title = title;
        this.body = body;
        this.notificationDateTime = notificationDateTime;
        this.notificationType = notificationType;
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

    public TYPE getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(TYPE notificationType) {
        this.notificationType = notificationType;
    }

    public enum TYPE {
        Error,
        Info,
        Message
    }
}
