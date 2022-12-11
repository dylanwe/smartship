export default class DashboardAdapter {
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
        const response = await fetch(this.RESOURCE_URL + url, options);

        if (response.ok) {
            return await response.json();
        } else {
            // response got an error
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }

    //TODO user must have dashboard
    async getUserDashboard(user) {
       return await this.fetchJson(`/user/${user.id}`)
    }


    async saveLayout(id, layoutArray) {
        return await this.fetchJson(`/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(layoutArray),
            credentials: 'include'
        })
    }


}