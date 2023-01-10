import {UserAdapterInMemory} from "@/services/UserAdapter-InMemory";
export default class ToDoAdapterInMemory {
    lastId;
    userToDos;
    userAdapter;
    users;

    constructor(startId = 3000) {
        this.userAdapter = new UserAdapterInMemory(1000);
        this.users = this.userAdapter.getUsers();
        this.lastId = startId;
        this.userToDos = [
            {
            }
        ];
    }

    getUserTodos(userId) {
        // make to do for users;
        // return this.userToDos.f;
    }

    getUserToDoById(userId, toDoId) {

    }

    saveTodo(userId, name, dueDate) {

    }

    updateToDo(userId, toDo) {

    }

    deleteUserToDoById(userId, toDoId) {

    }

}