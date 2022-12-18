import {UserAdapterInMemory} from "@/services/UserAdapter-InMemory";
import User from "@/models/User";

const FIRST_ID = 10_000;
let userService;

beforeEach(function() {
    userService = new UserAdapterInMemory(FIRST_ID);
});

it('should update the first user', () => {
    const newUser = User.createSampleUser(FIRST_ID);
    userService.updateUserInfo(newUser);

    expect(userService.users[0].equals(newUser))
        .toBeTruthy();
});
