import User from "@/models/User";
import Dashboard from "@/models/Dashboard";

/**
 * @Author: Jesaja Pavlovic
 */

export default class DashboardAdapterInMemory {
    dashboards;
    users;
    lastId;
    sensorData;

    /**
     *
     * @param {number} startId
     * @param {UserAdapterInMemory} userAdapterInMemory
     */
    constructor(startId = 3000, userAdapterInMemory) {
        this.users = userAdapterInMemory.getUsers();
        this.lastId = startId;

        this.sensorData = [{id: 0, val: 30, time: 1000}, {id: 0, val: 60, time: 5000},{id: 0, val: 90, time: 9000}]
        this.dashboards = []

        for (let user of this.users) {
            this.dashboards.push({
                id: this.lastId++,
                user: user,
                layout: [{id: 1, x: 3, y: 3, h: 1, w: 2}]
            })
        }

    }


    /**
     * Get the dashboard for a user
     * @param {Object} user - The user object
     * @returns {Object} - user's dashboard
     */
    getUserDashboard(user) {
       return this.dashboards.find(dashboard => dashboard.user === user) || this.dashboards.push({
            id: this.lastId++,
            user: user,
            layout: []
        })
    }


    /**
     * Get data for a specific widget
     * @param {string} id - The id of the widget
     * @param {number} from - The starting timestamp in milliseconds
     * @param {number} to - The ending timestamp in milliseconds
     * @returns {Object} - widget's data
     */
    getWidgetData(id, from, to) {
        from = Math.floor(from / 1000)
        to = Math.floor(to / 1000)
        return this.sensorData.filter((sensor) => sensor.id === id && (sensor.time >= from || sensor.time <= to));
    }

    /**
     * Save a layout
     * @param {string} id - The id of the layout
     * @param {Array} layoutArray - An array of layout data
     * @returns {Object} -The saved layout data
     */
    saveLayout(id, layoutArray) {
        return this.dashboards.find(dashboard=>dashboard.id === id).layout = layoutArray;
    }
}