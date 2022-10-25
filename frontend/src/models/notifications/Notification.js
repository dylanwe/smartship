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
     * @param {string} text
     * @param {Type} notificationType
     */
    constructor(id, status, date, title, text, notificationType) {
        this.id = id;
        this.status = status;
        this.date = date;
        this.title = title;
        this.text = text;
        this.notifiicationType = notificationType;

    }

    static createMockNotification(id) {
        const titles = [
            "Engine alert",
            "Man overboard",
            "Pirates spotted in the area",
            "Your workday is almost over"
        ];
        const text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut " +
            "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud";
        const formatter = new Intl.DateTimeFormat('en-us',{
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: 'numeric',
            minute: 'numeric',
        })
        const date = new Date();
        const randomTitle = titles[Math.floor(Math.random() * titles.length)];
        const random = Math.floor(Math.random() * Object.keys(this.Type).length);
        const randomType = this.Type[Object.keys(this.Type)[random]];

        return new Notification(id, this.Status.UNREAD, formatter.format(date), randomTitle, text, randomType);
    }
}