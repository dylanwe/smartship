import User from "@/models/User";

export default class UserAdapter {

    /**
     * Do the request with the URL to fetch the JSON
     *
     * @param url From where you want to request
     * @param options What you would like to send to that URL
     * @returns {Promise<null|any>} The JSON response
     */
    async fetchJson(url, options = null) {
        const response = await fetch(url, options);

        if (response.ok) {
            return await response.json();
        } else {
            // response got an error
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    /**
     * Find a User by its id
     *
     * @param {number} userId The users id
     * @returns {Promise<User>} The User with the given id
     */
    async findUserById(userId) {
        const data = await this.fetchJson(`http://localhost:8087/users/${userId}`);
        return User.createUserFromJson(data);
    }

    /**
     * Get the notification settings for the user with the given id
     *
     * @param {number} userId The user id
     * @returns The notification settings
     */
    async findNotificationSettings(userId) {
        // example stuff
        const user = await this.findUserById(userId);

        return {
            for: user.firstname,
            notifications: [
                {
                    id: 1,
                    name: "Ship",
                    description: "These are notifications to alert you on changes of the ship.",
                    options: [
                        {
                            name: "In App",
                            on: false,
                        },
                        {
                            name: "E-mail",
                            on: true,
                        },
                    ]
                },
                {
                    id: 2,
                    name: "Tasks",
                    description: "These are notifications to remind you of any task and when someone assigns you a new task.",
                    options: [
                        {
                            name: "In App",
                            on: true,
                        },
                        {
                            name: "E-mail",
                            on: false,
                        },
                    ]
                },
            ]
        };
    }
}