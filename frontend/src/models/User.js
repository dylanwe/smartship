export default class User {
    static ROLE = Object.freeze({
        Operator: "Operator",
        Manager: "Manager",
        Admin: "Admin"
    });

    /**
     *
     * @param {number} id
     * @param {string} firstName
     * @param {string} lastName
     * @param {string} email
     * @param {ROLE} role
     * @param {string} birthday
     * @param {string} bio
     */
    constructor(id, firstName, lastName, email, role, birthday, bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = User.ROLE[role];
        this.birthday = birthday;
        this.bio = bio;
    }

    static copyUser(user) {
        return new User(
            user.id,
            user.firstName,
            user.lastName,
            user.email,
            user.role,
            user.birthday,
            user.bio
        );
    }
}