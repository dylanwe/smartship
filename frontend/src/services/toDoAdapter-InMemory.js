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

    getUserTodos(userId) {
        return this.userToDos
            .find(user => user.userId === userId) // find the user
            ?.toDos; // get that users to-do if any
    }

    getUserToDoById(userId, toDoId) {
        return this.userToDos
            .find(user => user.userId === userId) // find the user
            ?.toDos.find(toDo => toDo.id === toDoId); //find the to-do with id
    }

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
            .find(user => user.userId === userId) // find the user
            ?.toDos.push(newToDo); // add new to-do

        return newToDo;
    }


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