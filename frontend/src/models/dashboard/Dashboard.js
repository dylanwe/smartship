export default class Dashboard {

    id
    #showWidgetbar
    #editMode
    #isDraggable
    #isResizable

    constructor(id) {
        this.id = id;
    }

    static createMockDashboard(id) {
        return new Dashboard(id);
    }


    editMode() {
        this.editMode = !this.editMode;
        this.draggable = !this.draggable;
        this.resizable = !this.resizable;
    }



}