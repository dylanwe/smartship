export default class Grid {
    constructor(rows, cols, layout) {
        this.rows = rows;
        this.cols = cols;
        this.layout = layout;

        this.grid = this.initLayoutGrid();
    }

    /**
     * Fill a grid with all the current free and taken positions based of the layout
     * @returns {[]} the filled grid
     */
    initLayoutGrid() {
        let tempGrid = [];

        // fill the grid with empty positions
        for (let row = 0; row < this.rows; row++) {
            tempGrid[row] = [];
            for (let col = 0; col < this.cols; col++) {
                tempGrid[row][col] = false;
            }
        }

        // fill the grid with taken positions
        for (const obj of this.layout) {
            for (let row = 0; row < obj.h; row++){
                for (let col = 0; col < obj.w; col++){
                    tempGrid[obj.y + row][obj.x + col] = true
                }
            }
        }

        return tempGrid;
    }

    /**
     * Check for empty positions in the grid and see if the given dimensions can be placed on it.
     * @param {number} startRow
     * @param {number} startColumn
     * @param {{h: number, w: number}} dimensions
     * @returns {boolean}
     */
    checkForPlacement(startRow, startColumn, {h: objectHeight,w: objectWidth}) {
        // check where it would end
        const endRow =  startRow + objectHeight;
        const endColumn =  startColumn + objectWidth;
        
        // check if ends are inbounds
        if (endRow > this.rows || endColumn > this.cols) return false;
        
        for (let row = startRow; row < endRow; row++) {
            for(let col = startColumn; col < endColumn; col++) {
                if (this.grid[row][col] === true) return false;
            }
        }

        return true;
    }

    /**
     * Retrieve coordinates which are compatible with given object dimensions
     * @param {h: number, w: number} dimensions
     * @returns {{x: number, y: number} | null}
     */
    getCoordinates(dimensions) {
        // find empty spot
        for (let row = 0; row < this.rows; row++) {
            for (let col = 0; col < this.cols; col++) {
                // Available slot
                if (this.grid[row][col] === false) {
                    if (this.checkForPlacement(row, col, dimensions)) {
                        // Spot found
                        return {x: col, y: row};
                    }
                }
            }
        }

        return null;
    }
}