/**
 * @Author: Dylan Weijgertze
 */
export default class User {
    static ROLE = Object.freeze({
        Operator: "Operator",
        Manager: "Manager",
        Admin: "Admin"
    });

    /**
     * Create a new user
     * @param {number} id
     * @param {string} firstName
     * @param {string} lastName
     * @param {string} email
     * @param {string} role
     * @param {string} birthday
     * @param {string} bio
     * @param {Object} shipId
     */
    constructor(id, firstName, lastName, email, role, birthday, bio, ship) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = User.ROLE[role];
        this.birthday = birthday;
        this.bio = bio;
        this.ship = ship;
    }

    /**
     * Create a copy of the given user
     * @param {User} user
     * @return {User}
     */
    static copyUser(user) {
        return new User(
            user.id,
            user.firstName,
            user.lastName,
            user.email,
            user.role,
            user.birthday,
            user.bio,
            user.ship
        );
    }

    static createUserFromJson(userJson) {
        return new User(
            userJson.id,
            userJson.firstName,
            userJson.lastName,
            userJson.email,
            userJson.role,
            userJson.birthday,
            userJson.bio,
            userJson.ship
        );
    }

    static createSampleUser(id) {
        const firstNames = ["Harry","Ross",
            "Bruce","Cook",
            "Carolyn","Morgan",
            "Albert","Walker",
            "Randy","Reed",
            "Larry","Barnes",
            "Lois","Wilson",
            "Jesse","Campbell",
            "Ernest","Rogers",
            "Theresa","Patterson",
            "Henry","Simmons",
            "Michelle","Perry",
            "Frank","Butler",
            "Shirley"];
        const lastNames = ["Ruth","Jackson",
            "Debra","Allen",
            "Gerald","Harris",
            "Raymond","Carter",
            "Jacqueline","Torres",
            "Joseph","Nelson",
            "Carlos","Sanchez",
            "Ralph","Clark",
            "Jean","Alexander",
            "Stephen","Roberts",
            "Eric","Long",
            "Amanda","Scott",
            "Teresa","Diaz",
            "Wanda","Thomas"];

        const firstName = firstNames[(Math.random() * firstNames.length) | 0];
        const lastName = lastNames[(Math.random() * lastNames.length) | 0];
        const email = `${firstName.toLowerCase()}@mail.com`;
        return new User(
            id,
            firstName,
            lastName,
            email,
            User.ROLE.Operator,
            "12-07-2000",
            `My name is ${firstName}`,
            {id: 1}
        );
    }

    /**
     * Check if this user is the same as another user
     * @param {User} other
     */
    equals(other) {
        return (
            this.id === other.id &&
            this.firstName === other.firstName &&
            this.lastName === other.lastName &&
            this.email === other.email &&
            this.birthday === other.birthday &&
            this.bio === other.bio &&
            this.ship?.id === other.ship?.id
        );
    }
}