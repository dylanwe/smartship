export default class User {
    constructor(id, firstname, lastname, email, birthday, bio) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthday = birthday;
        this.bio = bio;
    }

    static createUserFromJson(data) {
        return new User(
            data.id,
            data.firstname,
            data.lastname,
            data.email,
            data.birthday,
            data.bio
        );
    }
}