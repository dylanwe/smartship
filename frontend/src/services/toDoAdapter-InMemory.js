/**
 * @Author Lisanne Lin
 */

export default class ToDoAdapterInMemory {
    userToDos;
    users;
    lastId;
    dateOfToday;

    /**
     *
     * @param {number} startId
     * @param {UserAdapterInMemory} userAdapterInMemory
     */
    constructor(startId = 3000, userAdapterInMemory) {
        this.users = userAdapterInMemory.getUsers();
        this.lastId = startId;

        const date = new Date();
        const year = date.getFullYear();
        const month = ((date.getMonth() + 1 < 10) ? `0${date.getMonth() + 1}` : date.getMonth() + 1);
        const day = date.getDate();

        this.dateOfToday = `${year}-${month}-${day}`;

        // give the first user todos
        this.userToDos = [
            {
                userId: this.users[0].id,
                toDos: []
            }
        ];
    }

    /**
     * gets the to-do's of a user by userId
     *
     * @param userId id of a user
     * @returns {[]} a list of to-do's
     */
    getUserTodos(userId) {
        return this.userToDos
            .find(user => user.userId === userId) // find the user
            ?.toDos; // get that users to-do if any
    }

    /**
     * gets a to-do from a user
     *
     * @param userId id of a user
     * @param toDoId id of a to-do
     * @returns the found to-do
     */
    getUserToDoById(userId, toDoId) {
        return this.userToDos
            .find(user => user.userId === userId) // find the user
            ?.toDos.find(toDo => toDo.id === toDoId); // find the to-do with the correct id
    }

    /**
     * saves a to-do for a user
     *
     * @param userId id of a user
     * @param name of the new to-do
     * @param dueDate of the new to-do
     * @returns the newly added to-do
     */
    saveTodo(userId, name, dueDate) {
        // make a new to-do with today's date
        const newToDo = {
            id: this.lastId++,
            completed: false,
            completedAt: null,
            createdAt: this.dateOfToday,
            dueAt: dueDate,
            name: name
        };

        // find user, get his todos and add this new to do to that user's todos
        this.userToDos
            .find(user => user.userId === userId) // find the correct user
            ?.toDos.push(newToDo); // adds new to-do to the user's to-do list

        return newToDo;
    }

    /**
     * updates an existing to-do
     *
     * @param userId id of the user
     * @param toDo the to-do for the user
     * @returns the updated to-do
     */
    updateToDo(userId, toDo) {
        // find the users todos
        const userToDos = this.userToDos
            .find(user => user.userId === userId)?.toDos;

        if (userToDos) {
            // find index of stored to-do
            const indexOfToDo = userToDos.findIndex(oldToDo => oldToDo.id === toDo.id);

            // checks if to-do exist in the array
            if (indexOfToDo !== -1) {
                // replace to-do of the user found at the index with the new to-do
                userToDos[indexOfToDo] = toDo;
                return toDo;
            }
        }
        return null;
    }

    deleteUserToDoById(userId, toDoId) {


    }
}