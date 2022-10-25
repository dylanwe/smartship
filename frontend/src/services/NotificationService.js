import Notification from "@/models/notifications/Notification";

export default class NotificationService {
    notifications;

    constructor() {
        this.notifications = [
            Notification.createMockNotification(1),
            Notification.createMockNotification(2),
            Notification.createMockNotification(3),
            Notification.createMockNotification(4)

        ];
    }

    async findAllNotification() {
        return this.notifications;
    }
}