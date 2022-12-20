package com.smartship.backend.app.repositories;

import com.smartship.backend.app.models.NotificationPreference;
import com.smartship.backend.app.models.NotificationSetting;
import com.smartship.backend.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Dylan Weijgertze
 */
@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
class NotificationPreferenceRepositoryTest {
    @Autowired
    private NotificationPreferenceRepository notificationPreferenceRepository;
    @Autowired
    private NotificationSettingRepository notificationSettingRepository;
    @Autowired
    private UserRepository userRepository;

    private Long bruceId;
    private Long timId;

    @BeforeEach
    void setup() {
        addTestUsers();
        addTestNotificationSettings();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        notificationPreferenceRepository.deleteAll();
        notificationSettingRepository.deleteAll();
    }

    @Test
    void shouldNotFindAnyPreferences() {
        List<NotificationPreference> brucePreferences = notificationPreferenceRepository.findAllByUserId(bruceId);
        List<NotificationPreference> timPreferences = notificationPreferenceRepository.findAllByUserId(timId);

        assertThat(brucePreferences.isEmpty()).isTrue();
        assertThat(timPreferences.isEmpty()).isTrue();
    }

    @Test
    void shouldAddAndFind() {
        User bruce = userRepository.findById(bruceId).get();
        NotificationSetting setting = notificationSettingRepository.findAll().get(0);
        NotificationPreference notificationPreference = new NotificationPreference(
                true,
                false,
                bruce,
                setting
        );

        notificationPreferenceRepository.save(notificationPreference);
        assertThat(notificationSettingRepository.findAll().isEmpty()).isFalse();

        NotificationPreference savedNotificationPreference = notificationPreferenceRepository.findAll().get(0);
        assertThat(savedNotificationPreference.getEmailActive()).isTrue();
        assertThat(savedNotificationPreference.getPushActive()).isFalse();


        savedNotificationPreference.setPushActive(true);
        notificationPreferenceRepository.save(savedNotificationPreference);
        assertThat(notificationPreferenceRepository.findAll().get(0).getPushActive()).isTrue();
    }

    private void addTestUsers() {
        User bruce = new User(
                "Bruce",
                "Wayne",
                "bruce@wayne.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "I am the night");

        User tim = new User(
                "Tim",
                "Drake",
                "tim@drake.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "sidekick");

        userRepository.saveAll(List.of(bruce, tim));
        bruceId = userRepository.findByEmail(bruce.getEmail()).get().getId();
        timId = userRepository.findByEmail(tim.getEmail()).get().getId();
    }

    private void addTestNotificationSettings() {
        NotificationSetting ship = new NotificationSetting(
                "Ship", "These are notifications to alert you on changes on the ship"
        );
        NotificationSetting task = new NotificationSetting(
                "Tasks", "These are notifications to remind you of any task and when someone assigns you a new task"
        );

        notificationSettingRepository.saveAll(List.of(ship, task));
    }
}