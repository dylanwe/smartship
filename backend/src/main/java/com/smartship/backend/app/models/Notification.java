package com.smartship.backend.app.models;

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
    private Boolean readNotification;
    private LocalDate notificationDateTime;
    private TYPE notificationType;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Notification() {
    }

    public Notification(String title, String body, Boolean readNotification, LocalDate notificationDateTime, TYPE notificationType, User user) {
        this.title = title;
        this.body = body;
        this.readNotification = readNotification;
        this.notificationDateTime = notificationDateTime;
        this.notificationType = notificationType;
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return notificationDateTime;
    }

    public TYPE getType() {
        return notificationType;
    }

    public boolean getReadNotification() {
        return readNotification;
    }

    public void setReadNotification(boolean readNotification) {
        this.readNotification = readNotification;
    }

    public enum TYPE {
        Error,
        Info,
        Message
    }
}