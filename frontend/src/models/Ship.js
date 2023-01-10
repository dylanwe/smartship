/**
 * @Author: Jan van der Henst
 */
export default class Ship {

    /**
     * Create a new user
     * @param {string} smartShipId
     * @param {string} name
     */
    constructor(smartShipId, name) {
        this.smartShipId = smartShipId;
        this.name = name;
    }

    static createSampleShip(id) {

        const smartShipIds = ["58921HD","190u5KLJ",
            "3480JD","429870NDK",
            "23490MND","487029JKLD",
            "44791NHD","4021NKJ",
            "0984DJHN","64981NDB"];

        const names = ["Ruth","Jackson",
            "Debra","Allen",
            "Gerald","Harris",
            "Raymond","Carter",
            "Jacqueline","Torres",
            "Joseph","Nelson",
            "Carlos","Sanchez",
            "Ralph","Clark",
            "Jean","Alexander",
            "Stephen","Roberts",
            "Eric","Long",
            "Amanda","Scott",
            "Teresa","Diaz",
            "Wanda","Thomas"];

        const smartShipId = smartShipIds[(Math.random() * smartShipIds.length) | 0] + id;
        const name = names[(Math.random() * names.length) | 0];

        return new Ship(
            smartShipId,
            name
        );
    }

}