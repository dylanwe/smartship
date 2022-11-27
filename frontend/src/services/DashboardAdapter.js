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
        const response = await fetch(url, options);

        if (response.ok) {
            return await response.json();
        } else {
            // response got an error
            console.log(response, !response.bodyUsed ? await response.text() : "");
            return null;
        }
    }


    async getDashboard(userID){
        return await this.fetchJson(this.RESOURCE_URL + `/${userID}`, {method:"GET"})
    }
    async getAll(){
        return await this.fetchJson(this.RESOURCE_URL , {method:"GET"})
    }

    static createMockDashboard(){

    }

}