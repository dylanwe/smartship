export default class Notification {
    static Status = Object.freeze({
        READ: "READ",
        UNREAD: "UNREAD"
    });

    static Type = Object.freeze({
        INFO: "INFO",
        ERROR: "ERROR",
        DEFAULT: "DEFAULT"
    })

    /**
     * @param {number} id
     * @param {Status} status
     * @param {string} date
     * @param {string} title
     * @param {string} body
     * @param {Type} notificationType
     */
    constructor(id, status, date, title, body, notificationType) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.title = title;
        this.body = body;
        this.notifiicationType = notificationType;

    }
}