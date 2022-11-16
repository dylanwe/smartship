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
     * Finds all the accounts with the operator role
     *
     * @returns {Promise<*|null>}
     */
    async findOperators() {
        return await this.fetchJson(this.RESOURCE_URL + "/operators");
    }

    /**
     * Deletes an operator with the given id
     *
     * @param id
     * @returns {Promise<*|null>}
     */
    async deleteOperatorById(id) {
        return await this.fetchJson(this.RESOURCE_URL + "/" + id, {
            method: 'DELETE'
        });
    }

    /**
     * Adds an operator with the given data
     *
     * @returns {Promise<*|null>}
     */
    async addOperator(email, firstName, lastName, password) {
        return await this.fetchJson(this.RESOURCE_URL + '/operator', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({email, firstName, lastName, password})
        });
    }

    async updateUserInfo() {
        // TODO update user info
        // return this.fetchJson(`${this.RESOURCE_URL}`)
    }

    async updatePassword() {
        // TODO update password
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