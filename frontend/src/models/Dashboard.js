export default class Dashboard {

    id
    // Grid settings
    maxColumns = 5;
    maxRows = 6;
    rowHeight = 100;
    verticalCompact;

    showWidgetLibrary;
    editMode;
    isDraggable;
    isResizable;

    constructor(id) {
        this.id = id;

        this.verticalCompact = true;
        this.isDraggable = false;
        this.isResizable = false;

        this.showWidgetLibrary = false;
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