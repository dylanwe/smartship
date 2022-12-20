
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

    async getNotificationDescending(userId){
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/notifications/descending`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        )
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

    async markNotificationAsRead(userId, notificationId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/notifications/${notificationId}/read`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
            }
        );
    }
}
