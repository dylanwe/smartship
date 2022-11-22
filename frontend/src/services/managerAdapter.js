export default class ManagerAdapter {
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
        console.log(this.RESOURCE_URL)
        return await this.fetchJson(this.RESOURCE_URL);
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
}