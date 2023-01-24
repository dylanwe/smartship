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

    findAccountForRole(role) {
        return this.users;
    }

    deleteUserById(id) {
        this.users.shift();
    }

    addAccount(email, firstName, lastName, password, role, assignedShip) {
        this.users.push(new User(10, firstName, lastName, email, role, "12-07-2000", "this be bio", {id: assignedShip}));
    }

    updateUser(user, assignedShip) {}

    getOperatorsForShip(ship) {
        const usersForShip = [];
        for (let i = 0; i < this.users.length; i++) {
            if (this.users[i].ship.id === ship) {
                usersForShip.push(this.users[i])
            }
        }
        return usersForShip;
    }
}