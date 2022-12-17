import User from "./User";

// test data
const USER_ID = 412;
const USER_FIRST_NAME = "Bruce";
const USER_LAST_NAME = "Wayne";
const USER_EMAIL = "bruce@wayne.com";
const USER_ROLE = User.ROLE.Operator;
const USER_BIRTHDAY = "12-07-2000";
const USER_BIO = "I am the night";

// instance for testing
let user;

beforeEach(() => {
    user = new User(
        USER_ID,
        USER_FIRST_NAME,
        USER_LAST_NAME,
        USER_EMAIL,
        USER_ROLE,
        USER_BIRTHDAY,
        USER_BIO
    );
});

it('constructs a proper user', () => {
    expect(user.id,
        'user id was not set properly')
        .toBe(USER_ID);
    expect(user.firstName,
        'user first name was not set properly')
        .toBe(USER_FIRST_NAME);
    expect(user.lastName,
        'user last name was not set properly')
        .toBe(USER_LAST_NAME);
    expect(user.email,
        'user email was not set properly')
        .toBe(USER_EMAIL);
    expect(user.role,
        'user id was not set properly')
        .toBe(USER_ROLE);
    expect(user.birthday,
        'user birthday was not set properly')
        .toBe(USER_BIRTHDAY);
    expect(user.bio,
        'user bio was not set properly')
        .toBe(USER_BIO);
})

it('should create a copy of a user', () => {
    const userCopy = User.copyUser(user);

    expect(userCopy.id,
        'user id was not set properly')
        .toBe(user.id);
    expect(userCopy.firstName,
        'user first name was not set properly')
        .toBe(user.firstName);
    expect(userCopy.lastName,
        'user last name was not set properly')
        .toBe(user.lastName);
    expect(userCopy.email,
        'user email was not set properly')
        .toBe(user.email);
    expect(userCopy.role,
        'user id was not set properly')
        .toBe(user.role);
    expect(userCopy.birthday,
        'user birthday was not set properly')
        .toBe(user.birthday);
    expect(userCopy.bio,
        'user bio was not set properly')
        .toBe(user.bio);
})
