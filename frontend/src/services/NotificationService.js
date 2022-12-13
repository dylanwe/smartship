
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

    async getUserNotificationsTypes(userId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/notifications/type/{notificationType}`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        );
    }

    async getUserNotificationsAscending(userId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/notifications/ascending`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        );
    }

    async getUserNotificationsSearch(userId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/notifications/search`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        );
    }


    async deleteUserNotifications(userId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/notifications/{notificationId}`,
            {
                method: 'DELETE',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        );
    }
}
