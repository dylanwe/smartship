
export default class ToDoAdapter {
    RESOURCE_URL;

    constructor(resourceUrl) {
        this.RESOURCE_URL = resourceUrl;
    }

    async getUserTodos(userId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/todos`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        );
    }

    async getUserToDoById(userId, toDoId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/todos/${toDoId}`,
            {
                method: 'GET',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        );
    }

    async saveTodo(userId, name, dueDate) {
        dueDate = new Date(dueDate);
        const month = (dueDate.getMonth() + 1 < 10) ? `0${dueDate.getMonth() + 1}` : dueDate.getMonth() + 1
        // format date
        dueDate = `${dueDate.getDate()}-${month}-${dueDate.getFullYear()}`;

        return await fetch(
            `${this.RESOURCE_URL}/${userId}/todos`,
            {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    name,
                    dueDate,
                }),
                credentials: 'include',
            }
        );
    }

    async updateToDo(userId, toDo) {
        let dueDate = new Date(toDo.dueAt);
        const month = (dueDate.getMonth() + 1 < 10) ? `0${dueDate.getMonth() + 1}` : dueDate.getMonth() + 1;
        // format date
        toDo.dueAt = `${dueDate.getDate()}-${month}-${dueDate.getFullYear()}`;
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/todos/${toDo.id}`,
            {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    id: toDo.id,
                    name: toDo.name,
                    dueAt: toDo.dueAt,
                    isCompleted: toDo.completed,
                }),
                credentials: 'include',
            }
        );
    }

    async deleteUserToDoById(userId, toDoId) {
        return await fetch(
            `${this.RESOURCE_URL}/${userId}/todos/${toDoId}`,
            {
                method: 'DELETE',
                headers: {'Content-Type': 'application/json'},
                credentials: 'include'
            }
        );
    }

}