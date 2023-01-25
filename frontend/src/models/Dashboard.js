export default class Dashboard {


    /**
     * @class
     * @classdesc A class representing a dashboard layout manager.
     * @param {string} id - The id of the layout manager.
     */
    constructor(id) {
        /**
         * The id of the layout manager.
         * @type {string}
         */
        this.id = id;
        /**
         * A flag indicating whether the layout manager is in edit mode.
         * @type {boolean}
         */
        this.editMode = false;
        /**
         * A flag indicating whether the widget library is visible.
         * @type {boolean}
         */
        this.showWidgetLibrary = false;
        /**
         * The maximum number of columns in the layout.
         * @type {number}
         */
        this.maxColumns = 5;
        /**
         * The maximum number of rows in the layout.
         * @type {number}
         */
        this.maxRows = 6;
        /**
         * The height of each row in the layout.
         * @type {number}
         */
        this.rowHeight = 100;
        /**
         * A flag indicating whether the layout should be vertically compact.
         * @type {boolean}
         */
        this.verticalCompact = true;
        /**
         * A flag indicating whether the widgets are draggable in the layout.
         * @type {boolean}
         */
        this.isDraggable = false;
        /**
         * A flag indicating whether the widgets are resizable in the layout.
         * @type {boolean}
         */
        this.isResizable = false;
    }

    static createMockDashboard(id) {
        return new Dashboard(id);
    }

    /**
     * Enable and disable widget library
     */
    toggleWidgetLibrary(){
        this.showWidgetLibrary = !this.showWidgetLibrary;
    }

    /**
     * Enable and disable layout edit mode
     */
    toggleEditMode() {
        this.isDraggable = !this.isDraggable;
        this.isResizable = !this.isResizable;
        this.editMode = !this.editMode;
    }



}