export default class Dashboard {
    // constructor(id, x, y, width, height,widget) {
    //     this.id = id;
    //     this.x = x;
    //     this.y = y;
    //     this.width = width;
    //     this.height =height;
    //     this.widget = widget;
    //
    //
    // }
    constructor(id) {
        this.id = id;
        // Grid options
        this.numberOfColumns = 5;
        this.draggable = false;
        this.resizable = false;
        this.index = 0;
        // Widget edit
        this.editMode = false;
        this.showWidgetbar = false;
        this.layout = [];
    }

    static createMockDashboard(id){
        return new Dashboard(id);
}
}