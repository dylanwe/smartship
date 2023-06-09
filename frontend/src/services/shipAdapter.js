
export default class ShipAdapter {
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

    async getAllShips() {
        return await this.fetchJson('')
    }

    async getSensors(shipId) {
        return await this.fetchJson(`/${shipId}/sensors`)
    }

    async updateSensor(sensorId,sensor){
        return await this.fetchJson(`/sensors/${sensorId}`,{  method: 'PUT',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(sensor)
        })
    }

    async getShipByShipId(smartShipId) {
        return await this.fetchJson(`/${smartShipId}`)
    }

    async deleteShipById(id) {
        return await this.fetchJson("/" + id, {
            method: 'DELETE'
        });
    }

    async addShip(name, shipId) {
        return await this.fetchJson('', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({name, shipId})
        });
    }
}