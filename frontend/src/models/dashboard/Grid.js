export class Grid {
    /* eslint-disable */

    //Widget.js
    layout = [
        { "x": 0, "y": 0, "w": 3, "h": 2, "i": "test" },
        { "x": 1, "y": 0, "w": 1, "h": 1, "i": "1" },
        { "x": 0, "y": 0, "w": 1, "h": 1, "i": "2" },
        { "x": 0, "y": 0, "w": 2, "h": 1, "i": "3" }];
    numberOfColumns = 5;
    rowHeight = 100;
    draggable = true;
    resizable = true;
    responsive = true;
    maxRows = 5;

    editMode = false;
    constructor() {

    }
}