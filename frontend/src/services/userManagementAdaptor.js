export default class UserManagementAdaptor {
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
     * Finds all the accounts with the given role
     *
     * @returns {Promise<*|null>}
     */
    async findAccountForRole(role) {
        return await this.fetchJson(this.RESOURCE_URL + "/" + role);
    }

    /**
     * Deletes a user with the given id
     *
     * @param id
     * @returns {Promise<*|null>}
     */
    async deleteUserById(id) {
        return await this.fetchJson(this.RESOURCE_URL + "/" + id, {
            method: 'DELETE'
        });
    }

    /**
     * Adds a new user with the given data
     *
     * @returns {Promise<*|null>}
     */
    async addAccount(email, firstName, lastName, password, role, assignedShip) {
        return await this.fetchJson(this.RESOURCE_URL + '/' + role, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({email, firstName, lastName, password, assignedShip})
        });
    }

    /**
     * Update a user and the assigned ship
     *
     * @param user
     * @param assignedShip
     * @returns {Promise<Response>}
     */
    async updateUser(user, assignedShip) {
         return await fetch(
            `${this.RESOURCE_URL}`,
            {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({user, assignedShip}),
                credentials: 'include'
            }
        );
    }

    /**
     * Get all the operators that are assigned to the given ship
     *
     * @param ship
     * @returns {Promise<*|null>}
     */
    async getOperatorsForShip(ship) {
        return await this.fetchJson(this.RESOURCE_URL + "/ship/" + ship.id);
    }

    /**
     * Find a user by the given email
     *
     * @param email
     * @returns {Promise<*|null>}
     */
    async findUserByEmail(email) {
        return await this.fetchJson(this.RESOURCE_URL + "/resetPassword/" + email);
    }

    /**
     * Update the password of the given user
     *
     * @param id
     * @param newPassword
     * @returns {Promise<*|null>}
     */
    async updateUserPassword(id, newPassword) {
        return await this.fetchJson(this.RESOURCE_URL + "/password",
            {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({id, newPassword}),
                credentials: 'include'
            }
        );
    }

}