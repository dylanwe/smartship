export default class User {
    static ROLE = Object.freeze({
        Operator: "Operator",
        Manager: "Manager",
        Admin: "Admin"
    });

    constructor(id, firstName, lastName, email, role, birthday, bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = User.ROLE[role];
        this.birthday = birthday;
        this.bio = bio;
    }
}