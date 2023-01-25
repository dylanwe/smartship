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

    /**
     * Get the dashboard for a user
     * @param {Object} user - The user object
     * @returns {Promise<Object>} - A promise that resolves to the user's dashboard
     */
    async getUserDashboard(user) {
        return await this.fetchJson(`/user/${user.id}`)
    }

    /**
     * Get data for a specific widget
     * @param {string} id - The id of the widget
     * @param {number} from - The starting timestamp in milliseconds
     * @param {number} to - The ending timestamp in milliseconds
     * @returns {Promise<Object>} - A promise that resolves to the widget's data
     */
    async getWidgetData(id, from, to) {
        from = Math.floor(from / 1000)
        to = Math.floor(to / 1000)
       return await this.fetchJson(`/widget/${id}?from=${from}&to=${to}`);
    }

    /**
     * Save a layout
     * @param {string} id - The id of the layout
     * @param {Array} layoutArray - An array of layout data
     * @returns {Promise<Object>} - A promise that resolves to the saved layout data
     */
    async saveLayout(id, layoutArray) {
        return await this.fetchJson(`/${id}`, {
            method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(layoutArray),
            credentials: 'include'
        })
    }


}