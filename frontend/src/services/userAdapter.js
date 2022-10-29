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
     * Get the notification settings for the user with the given id
     *
     * @param {number} userId The user id
     * @returns The notification settings
     */
    async findNotificationSettings(userId) {
        // example stuff
        return Promise.resolve({
            for: userId,
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

    async updateUserInfo() {
        // TODO update user info
    }

    async updatePassword() {
        // TODO update password
    }

    async updateNotificationSettings() {
        // TODO update notification settings
    }
}