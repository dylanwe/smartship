export default class UserAdapter {
    RESOURCE_URL;

    constructor(resourceUrl) {
        this.RESOURCE_URL = resourceUrl;
    }

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
     * Update the general user information
     *
     * @param {string} firstName
     * @param {string} lastName
     * @param {string} email
     * @param {string} bio
     * @param {string} birthday
     * @return {Promise<*|null>}
     */
    async updateUserInfo(firstName, lastName, email, bio, birthday) {
        const userInfo = {firstName, lastName, email, bio, birthday};

        const updatedUser = await this.fetchJson(
            `${this.RESOURCE_URL}`,
            {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(userInfo),
                credentials: 'include'
            }
        );

        localStorage.setItem("user", JSON.stringify(updatedUser));
        return updatedUser;
    }

    /**
     * Update the password of the logged-in user
     *
     * @param {string} oldPassword
     * @param {string} newPassword
     * @return {Promise<Response<any, Record<string, any>, number>>}
     */
    async updateUserPassword(oldPassword, newPassword) {
        return await fetch(
            `${this.RESOURCE_URL}/password`,
            {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({oldPassword, newPassword}),
                credentials: 'include'
            }
        );
    }

    async updateNotificationSettings() {
        // TODO update notification settings
    }

    /**
     * Get the notification settings for the user with the given id
     *
     * @returns The notification settings
     */
    async findNotificationSettings() {
        // example stuff
        return Promise.resolve({
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
        });
    }
}