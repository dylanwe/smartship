package com.smartship.backend.app.repositories;
import com.smartship.backend.app.models.Notification;
import com.smartship.backend.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dusan Los
 */
@DataJpaTest
@ComponentScan({"com.smartship.backend.app.repositories"})
public class NotificationRepositoryTest {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
    User kanye, magnus;

    @BeforeEach
    void setup() {
        kanye = new User(
                "Kanye",
                "West",
                "kanye@west.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "808's and heartbreaks");

        magnus = userRepository.save(new User(
                "Magnus",
                "Carlsen",
                "magnus@carlsen.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "Knight to D4!"));
    }

    @AfterEach
    void teardown() {
        notificationRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void shouldNotFindAnyNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        // check if notifications is empty
        assertThat(notifications.size()).isEqualTo(0);
    }

    @Test
    void ShouldFindAllByUserIdAndType() {
        User kanye = userRepository.save(new User(
                "Kanye",
                "West",
                "kanye@west.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "808's and heartbreaks"));

        // make a couple of notifications, some from today and some in the past
        notificationRepository.saveAll(List.of(
                new Notification(
                        "Pirates nearby",
                        "Watch out",
                        false,
                        LocalDateTime.now(),
                        Notification.TYPE.Error,
                        kanye
                ),
                new Notification(
                        "Your workday is done",
                        "You worked for 8 hours",
                        false,
                        LocalDateTime.now(),
                        Notification.TYPE.Info,
                        kanye

                ),
                new Notification(
                        "Land in sight",
                        "Get the treasure",
                        true,
                        LocalDateTime.now().minusDays(4),
                        Notification.TYPE.Info,
                        kanye
                )
        ));

        // get all notifications from today
        List<Notification> notificationsFromToday = notificationRepository.
                findAllByUserIdAndNotificationType(kanye.getId(), Notification.TYPE.Info);

        // assert that we only have 2 notifications
        assertThat(notificationsFromToday.size()).isEqualTo(2);
    }


    @Test
    void ShouldFindAllByUserId() {
        User savedKanye = userRepository.save(kanye);
        User savedMagnus = userRepository.save(magnus);

        notificationRepository.saveAll(List.of(
                new Notification(
                        "Pirates nearby",
                        "Watch out",
                        false,
                        LocalDateTime.now(),
                        Notification.TYPE.Error,
                        savedKanye
                ),
                new Notification(
                        "Your workday is done",
                        "You worked for 8 hours",
                        false,
                        LocalDateTime.now(),
                        Notification.TYPE.Info,
                        savedKanye

                ),
                new Notification(
                        "Land in sight",
                        "Get the treasure",
                        true,
                        LocalDateTime.now().minusDays(4),
                        Notification.TYPE.Message,
                        savedMagnus
                )
        ));


        List<Notification> kanyeNotifications = notificationRepository.findByUserId(savedKanye.getId());
        List<Notification> magnusNotifications = notificationRepository.findByUserId(savedMagnus.getId());

        assertThat(kanyeNotifications.size()).isEqualTo(2);
        assertThat(magnusNotifications.size()).isEqualTo(1);
    }

    @Test
    void ShouldFindAllByUserIdAndRead() {
        User kanye = userRepository.save(new User(
                "Kanye",
                "West",
                "kanye@west.com",
                "secret",
                LocalDate.now().minusYears(5),
                User.ROLE.Operator,
                "808's and heartbreaks"));

        // make a couple of notifications, some read and some unread
        notificationRepository.saveAll(List.of(
                new Notification(
                        "Pirates nearby",
                        "Watch out",
                        true,
                        LocalDateTime.now(),
                        Notification.TYPE.Error,
                        kanye
                ),
                new Notification(
                        "Your workday is done",
                        "You worked for 8 hours",
                        false,
                        LocalDateTime.now(),
                        Notification.TYPE.Info,
                        kanye

                ),
                new Notification(
                        "Land in sight",
                        "Get the treasure",
                        true,
                        LocalDateTime.now().minusDays(4),
                        Notification.TYPE.Info,
                        kanye
                )
        ));

        // get all unread notifications
        List<Notification> unreadNotifications = notificationRepository.findAllByUserIdAndReadNotification(kanye.getId(), false);

        // assert that we only have 1 unread notifications
        assertThat(unreadNotifications.size());
    }

}
