export default class User {
    constructor(id, firstName, lastName, email, birthday, bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.bio = bio;
    }

    static createUserFromJson(data) {
        return new User(
            data.id,
            data.firstName,
            data.lastName,
            data.email,
            data.birthday,
            data.bio
        );
    }
}