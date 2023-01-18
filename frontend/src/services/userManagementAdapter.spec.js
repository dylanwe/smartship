import {userManagementAdapterInMemory} from "@/services/userManagementAdapter-InMemory";
import User from "@/models/User";

/**
 * @Author: Jan van der Henst
 */

const FIRST_ID = 10_000;
let userManagementService;

beforeEach(function() {
    userManagementService = new userManagementAdapterInMemory(FIRST_ID);
});

it('should add a new user', () => {
    expect(userManagementService.users).toHaveLength(2);

    const newUser = User.createSampleUser(3000);
    userManagementService.addAccount(newUser.email, newUser.firstName, newUser.lastName, "password123", newUser.role, 2);

    expect(userManagementService.users).toHaveLength(3);
    expect(userManagementService.users[2].firstName).toBe(newUser.firstName);
});

it('should find all users for ship', () => {
    const newUser = User.createSampleUser(3000);
    userManagementService.addAccount(newUser.email, newUser.firstName, newUser.lastName, "password123", newUser.role, 2);

    const foundUsers = userManagementService.getOperatorsForShip(2);

    expect(foundUsers).toHaveLength(1);
});