import Notification from "@/models/notifications/Notification";

export default class NotificationServices {
    notifications;

    constructor() {
        this.notifications = [
            Notification.createMockNotification(1),
            Notification.createMockNotification(2),
            Notification.createMockNotification(3)
        ];
    }

    async findAllNotification() {
        return this.notifications;
    }
}