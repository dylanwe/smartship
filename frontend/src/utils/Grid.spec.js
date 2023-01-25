import Grid from "./Grid";

/**
 * @Author: Jesaja Pavlovic
 */

// test data
const ROWS = 3
const COLUMNS = 3
const LAYOUT = [
    {x: 0, y: 0, h: 1, w: 1}, {x: 2, y: 0, h: 1, w: 1},
    {x: 0, y: 1, h: 1, w: 2},
    {x: 2, y: 2, h: 1, w: 1}
]

/**
 *  | FALSE | TRUE  | FALSE
 *  | FALSE | FALSE | TRUE
 *  | TRUE  | TRUE  | FALSE
 *
 */

// instance for testing
let grid;

beforeEach(() => {
    grid = new Grid(
        ROWS, COLUMNS, LAYOUT
    );
});

it('should initialize with empty layout', () => {
    const grid = new Grid(ROWS, COLUMNS, []);
    expect(grid.grid).toEqual([
        [false, false, false],
        [false, false, false],
        [false, false, false]
    ]);
});
it('should find available coords for item dimensions', () => {
    expect(grid.getCoordinates({h: 1, w: 1})).toEqual({x: 1, y: 0});
    expect(grid.getCoordinates({h: 2, w: 1})).toEqual(null);
})

it('should not find available coords', () => {
    expect(grid.getCoordinates({h: 2, w: 2})).toBe(null);
});

it('should check if the placement is valid and in bounds', () => {
    expect(grid.checkForPlacement(3, 3, {h: 1, w: 1})).toEqual(false);
    expect(grid.checkForPlacement(2, 2, {h: 2, w: 2})).toEqual(false);
});

it('should check if the placement of an object is valid', () => {
    expect(grid.checkForPlacement(0, 0, {h: 1, w: 1})).toEqual(false);
    expect(grid.checkForPlacement(2, 0, {h: 1, w: 2})).toEqual(true);
    expect(grid.checkForPlacement(0, 2, {h: 2, w: 1})).toEqual(false);
    expect(grid.checkForPlacement(2, 2, {h: 2, w: 2})).toEqual(false);
});
