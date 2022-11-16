package com.smartship.backend.app.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
public class NotificationPreference {
    @Id
    @GeneratedValue
    private Long id;
    private Boolean isEmailActive;
    private Boolean isPushActive;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @OneToOne
    @JoinColumn(name = "notification_setting", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private NotificationSetting notificationSetting;

    public NotificationPreference() {
    }

    public NotificationPreference(Boolean isEmailActive, Boolean isPushActive, User user, NotificationSetting notificationSetting) {
        this.isEmailActive = isEmailActive;
        this.isPushActive = isPushActive;
        this.user = user;
        this.notificationSetting = notificationSetting;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEmailActive() {
        return isEmailActive;
    }

    public void setEmailActive(Boolean emailActive) {
        isEmailActive = emailActive;
    }

    public Boolean getPushActive() {
        return isPushActive;
    }

    public void setPushActive(Boolean pushActive) {
        isPushActive = pushActive;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NotificationSetting getNotificationSetting() {
        return notificationSetting;
    }

    public void setNotificationSetting(NotificationSetting notificationSetting) {
        this.notificationSetting = notificationSetting;
    }
}
