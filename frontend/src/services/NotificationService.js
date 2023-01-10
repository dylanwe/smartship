export default class NotificationService {
    RESOURCE_URL;

    constructor(resourceUrl) {
        this.RESOURCE_URL = resourceUrl;
    }

    async getUserNotifications(userId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/notifications`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        );
    }

    async markNotificationAsRead(userId, notificationId) {
        const body = JSON.stringify({readNotification: true});

        return await fetch(
            `${this.RESOURCE_URL}/${userId}/notifications/${notificationId}`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: body
            }
        );
    }

    async recentNotifications(userId) {
        return await fetch(`${this.RESOURCE_URL}/${userId}/notifications/recent`);
    }
}
