export default class NotificationServiceInMemory {
    userNotifications;
    users;
    lastId;
    dateOfToday;

    /**
     *
     * @param {number} startId
     * @param {UserAdapterInMemory} userAdapterInMemory
     */
    constructor(startId = 3000, userAdapterInMemory) {
        this.users = userAdapterInMemory.getUsers();
        this.lastId = startId;

        //format date
        const date = new Date();
        const year = date.getFullYear();
        const month = ((date.getMonth() + 1 < 10) ? `0${date.getMonth() + 1}` : date.getMonth() + 1);
        const day = date.getDate();

        this.dateOfToday = `${year}-${month}-${day}`;

        // give the first user notifications
        this.userNotifications = [
            {
                userId: this.users[0].id,
                notifications: []
            }
        ];
    }

    getUserNotifications(userId) {
        return this.userNotifications
            .find(user => user.userId === userId) // find the user
            ?.notifications; // get that users notifications if any
    }

    getUserNotificationById(userId, notificationId) {
        return this.userNotifications
            .find(user => user.userId === userId) // find the user
            ?.notifications.find(notification => notification.id === notificationId); //find the notifications with id
    }

    saveNotification(userId, name, body, type) {
        // make a new notification with today's date
        const newNotification = {
            id: this.lastId++,
            read: false,
            createdAt: this.dateOfToday,
            name: name,
            body: body,
            type: type
        };

        // find user, get his notifications and add this new notification to that user's notifications
        this.userNotifications
            .find(user => user.userId === userId) // find the user
            ?.notifications.push(newNotification); // add new to-do

        return newNotification;
    }


    filterNotificationsByType(userId, type) {
        // find the user's notifications
        const userNotifications = this.userNotifications
            .find(user => user.userId === userId)?.notifications;

        if (userNotifications) {
            // check if the type is not passed
            if(!type){
                return userNotifications;
            }
            // filter the notifications by the specified type
            const filteredNotifications = userNotifications.filter(notification => notification.type.toUpperCase() === type.toUpperCase());
            return filteredNotifications
        }
        return null;
    }
}