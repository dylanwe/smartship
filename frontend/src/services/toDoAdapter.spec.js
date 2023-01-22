import ToDoAdapterInMemory from "@/services/toDoAdapter-InMemory";
import UserAdapterInMemory from "@/services/UserAdapter-InMemory";

/**
 * @Author Lisanne Lin
 */

const FIRST_ID_USERS = 10_000;
const FIRST_ID_TODOS = 30_000;
let userService;
let toDoService;
let dateOfToday;

beforeEach(function () {
    userService = new UserAdapterInMemory(FIRST_ID_USERS);
    toDoService = new ToDoAdapterInMemory(FIRST_ID_TODOS, userService);

    const date = new Date();
    const year = date.getFullYear();
    const month = ((date.getMonth() + 1 < 10) ? `0${date.getMonth() + 1}` : date.getMonth() + 1);
    const day = date.getDate();
    dateOfToday = `${year}-${month}-${day}`;
});

it('should find the first to-do of the first user', () => {
    const user = userService.getUsers()[0];
    const todos = toDoService.getUserTodos(user.id);

    expect(todos.length,
        "Can't find todo")
        .toBe(0)
});

it('should add to-do to the first user', () => {
    const user = userService.getUsers()[0];
    const name = "clean deck";
    const todos = toDoService.getUserTodos(user.id);
    toDoService.saveTodo(user.id, name, dateOfToday);

    // get the last to-do of the first user
    const newestUserToDo = toDoService.getUserTodos(user.id)[toDoService.getUserTodos(user.id).length - 1];

    // check if name is correct
    expect(newestUserToDo.name,
        'The todo is not saved, name is not correct')
        .toBe(name);

    // check stored date of new to do
    expect(newestUserToDo.dueAt,
        'The todo is not saved, dueAt date is not correct')
        .toBe(dateOfToday);

    // check new length of to dos
    expect(todos.length,
        "Can't find todo")
        .toBe(1)
});

it('should get user todo by todo id', () => {
    const user = userService.getUsers()[0];
    const name = "check engine";
    // save a to-do
    toDoService.saveTodo(user.id, name, dateOfToday);

    // get the to do by it's id
    const foundToDo = toDoService.getUserToDoById(user.id, FIRST_ID_TODOS);

    // check if gotten to-do is the same as the saved one
    expect(foundToDo.name,
        "name did not match with the saved to do")
        .toBe(name);

    // check found due at
    expect(foundToDo.dueAt,
        "date did not match with the saved to do")
        .toBe(dateOfToday);

    // check found id
    expect(foundToDo.id,
        "can't find todo by id")
        .toBe(FIRST_ID_TODOS);


});
it('should update todo', function () {
    const user = userService.getUsers()[0];
    const name = "check engine";
    const newName = "check battery"
    const newToDo = toDoService.saveTodo(user.id, newName, dateOfToday)

    // save a to-do
    toDoService.saveTodo(user.id, name, dateOfToday);

    toDoService.updateToDo(user.id, newToDo);
    expect(newToDo.name,
        "can't find updated todo, name does not match")
        .toBe(newName)
});

