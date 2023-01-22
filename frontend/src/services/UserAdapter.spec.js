import UserAdapterInMemory from "@/services/UserAdapter-InMemory";
import User from "@/models/User";

/**
 * @Author: Dylan Weijgertze
 */

const FIRST_ID = 10_000;
let userService;

beforeEach(function() {
    userService = new UserAdapterInMemory(FIRST_ID);
});

it('should update the first user', () => {
    const newUser = User.createSampleUser(FIRST_ID);
    userService.updateUserInfo(newUser);

    expect(userService.users[0].equals(newUser),
        'The stored user did not update')
        .toBeTruthy();
});
