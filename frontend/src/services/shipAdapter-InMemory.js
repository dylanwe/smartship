import Ship from "@/models/Ship";

/**
 * @Author: Jan van der Henst
 */
export class ShipAdapterInMemory {
    lastId;
    ships;

    constructor(initId = 100) {
        this.lastId = initId;

        const initialShips = [];
        for (let i = 0; i < 7; i++) {
            initialShips.push(Ship.createSampleShip(this.lastId++))
        }
        this.ships = initialShips;
    }

    async getAllShips() {
        return this.ships;
    }

    async getSensors(shipId) {}

    async getShipByShipId(smartShipId) {}

    async deleteShipById(id) {
        this.ships.shift();
    }

    async addShip(name, shipId) {}
}