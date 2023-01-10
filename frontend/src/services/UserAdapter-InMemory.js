import User from "@/models/User";

/**
 * @Author: Dylan Weijgertze
 */
export class UserAdapterInMemory {
    lastId;
    users;

    /**
     * Create a mock user service
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

    getUsers() {
        return this.users;
    }
}