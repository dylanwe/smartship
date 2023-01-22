import User from "@/models/User";

/**
 * @Author: Dylan Weijgertze
 */
export default class UserAdapterInMemory {
    lastId;
    users;

    /**
     * Create a mock user service with 7 mock users
     * @param {number} initialId
     */
    constructor(initialId = 1000) {
        this.lastId = initialId;

        const initialUsers = [];
        for (let i = 0; i < 7; i++) {
            initialUsers.push(User.createSampleUser(this.lastId++))
        }
        this.users = initialUsers;
    }

    updateUserInfo(user) {
        let indexOfUser = this.users.findIndex(u => u.id === user.id);

        if (indexOfUser === -1) return null;

        this.users[indexOfUser] = user;
        return user;
    }

    updateUserPassword(userId, oldPassword, newPassword) {}

    updateNotificationSettings(preferences) {}

    findNotificationSettings() {}

    /**
     * @returns {User[]} list of users
     */
    getUsers() {
        return this.users;
    }
}