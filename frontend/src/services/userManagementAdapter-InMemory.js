import User from "@/models/User";

/**
 * @Author: Jan van der Henst
 */
export class userManagementAdapterInMemory {
    lastId;
    users;

    constructor(initId = 100) {
        this.lastId = initId;

        const initialUsers = [];
        for (let i = 0; i < 2; i++) {
            initialUsers.push(User.createSampleUser(this.lastId++))
        }
        this.users = initialUsers;
    }

    async findAccountForRole(role) {
        return this.users;
    }

    async deleteUserById(id) {
        this.users.shift();
    }

    async addAccount(email, firstName, lastName, password, role, assignedShip) {}

    async updateUser(user, assignedShip) {}

    async getOperatorsForShip(ship) {}
}