
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

    async getUserNotificationsById(userId, notificationId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/notifications/${notificationId}`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        );
    }


}
