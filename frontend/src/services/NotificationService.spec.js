import NotificationServiceInMemory from "@/services/NotificationService-InMemory";
import UserAdapterInMemory from "@/services/UserAdapter-InMemory";

/**
 * @Author Dusan Los
 */

const FIRST_ID_USERS = 10_000;
const FIRST_ID_NOTIFICATION = 30_000;
let userService;
let notificationService;
let dateOfToday;

beforeEach(function () {
    userService = new UserAdapterInMemory(FIRST_ID_USERS);
    notificationService = new NotificationServiceInMemory(FIRST_ID_NOTIFICATION, userService);

    //format date
    const date = new Date();
    const year = date.getFullYear();
    const month = ((date.getMonth() + 1 < 10) ? `0${date.getMonth() + 1}` : date.getMonth() + 1);
    const day = date.getDate();
    dateOfToday = `${year}-${month}-${day}`;
});

it('user should have no notifications at the beginning', () => {
    const user = userService.getUsers()[0];
    const notifications = notificationService.getUserNotifications(user.id);

    expect(notifications.length,
        "Can't find a notification")
        .toBe(0)
});

it('should add notification to the first user', () => {
    const user = userService.getUsers()[0];
    const name = "Emergency in the engine room";
    const body = "Get an engineer down to the engine room";
    const type = "error"
    const notifications = notificationService.getUserNotifications(user.id);
    notificationService.saveNotification(user.id, name, body, type);

    // get the last notification of the first user
    const newestUserNotification = notificationService.getUserNotifications(user.id)[notificationService.getUserNotifications(user.id).length - 1];

    // check if name of the notification is correct
    expect(newestUserNotification.name,
        'The notification is not saved, name is not correct')
        .toBe(name);

    // check stored date of new notification
    expect(newestUserNotification.body,
        'The notification is not saved, body is not correct')
        .toBe(body);

    // check stored date of new notification
    expect(newestUserNotification.type,
        'The notificationn is not saved, type is not correct')
        .toBe(type);

    // check new length of notifications
    expect(notifications.length,
        "Can't find notification")
        .toBe(1)
});

it('should get user notification by notification id', () => {
    const user = userService.getUsers()[0];
    const name = "Emergency in the engine room";
    const body = "Get an engineer down to the engine room";
    const type = "error";

    // save a notification
    notificationService.saveNotification(user.id, name, body, type);

    // get the notification by its id
    const foundNotification = notificationService.getUserNotificationById(user.id, FIRST_ID_NOTIFICATION);

    // check if gotten notification is the same as the saved one
    expect(foundNotification.name,
        "name did not match with the saved notification")
        .toBe(name);

    // check if gotten notification is the same as the saved one
    expect(foundNotification.body,
        "body did not match with the saved notification")
        .toBe(body);

    // check if gotten notification is the same as the saved one
    expect(foundNotification.type,
        "type did not match with the saved notification")
        .toBe(type);

    // check found id
    expect(foundNotification.id,
        "can't find notification by id")
        .toBe(FIRST_ID_NOTIFICATION);
});

it('should filter notifications by type', function () {
    const user = userService.getUsers()[0];

    //initialize multiple notifications with different types
    const notification1 = notificationService.saveNotification(user.id, "Check Engine", "Check engine room", "Error");
    const notification2 = notificationService.saveNotification(user.id, "Low Battery", "Recharge", "Warning");
    const notification3 = notificationService.saveNotification(user.id, "Maintenance Due", "Get to work", "Info");
    const notification4 = notificationService.saveNotification(user.id, "Fuel Level Low", "Time to fill up", "Warning");

    // filter notifications by type "Warning"
    const filteredNotifications = notificationService.filterNotificationsByType(user.id, "Warning");

    // expect filtered notifications to contain only "Low Battery" and "Fuel Level Low"
    expect(filteredNotifications.length).toBe(2);
    expect(filteredNotifications[0].name).toBe("Low Battery");
    expect(filteredNotifications[1].name).toBe("Fuel Level Low");
});

