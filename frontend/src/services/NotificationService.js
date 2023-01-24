export default class NotificationService {
    RESOURCE_URL;

    constructor(resourceUrl) {
        this.RESOURCE_URL = resourceUrl;
    }
    // Send a GET request to the specified resource URL with the userId as a path variable
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
    // Send a PUT request to the specified resource URL with the userId and notificationId as a path variable
    // Request body is send with the readNotification field set to true
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
