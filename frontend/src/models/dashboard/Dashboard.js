export default class Dashboard {

    id
    layout = []
    #user

    constructor(id, layout) {
        this.id = id;
        this.layout = layout || [];
    }

    static createMockDashboard(id){
        return new Dashboard(id);
}

    addToLayout(widget){
        this.layout.push(widget);
    }


    get getLayout(){
        return this.layout;
    }


}