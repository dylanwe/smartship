export default class ToDoAdapter {
    RESOURCE_URL;

    constructor(resourceUrl) {
        this.RESOURCE_URL = resourceUrl;
    }

    /**
     * gets all the to-do's of the user
     *
     * @param userId id of the user
     * @returns the to-do's of the user
     */
    async getUserTodos(userId) {
        return await fetch(`${this.RESOURCE_URL}/${userId}/todos`, {
            method: 'GET', headers: {'Content-Type': 'application/json'}, credentials: 'include'
        });
    }

    /**
     * gets a to-do of the user
     * @param userId
     * @param toDoId
     * @returns {Promise<Response|any>}
     */
    async getUserToDoById(userId, toDoId) {
        const resp = await fetch(`${this.RESOURCE_URL}/${userId}/todos/${toDoId}`, {
            method: 'GET', headers: {'Content-Type': 'application/json'}, credentials: 'include'
        });

        if (resp.ok) {
            return await resp.json();
        }

        return resp;
    }

    /**
     * Saves a to-do
     *
     * @param userId id of the user
     * @param name of the to-do
     * @param dueDate of the to-do
     * @returns a to-do
     */
    async saveTodo(userId, name, dueDate) {
        dueDate = new Date(dueDate);
        // format month, concatenate 0 if month is smaller than 10
        const month = ((dueDate.getMonth() + 1 < 10) ? `0${dueDate.getMonth() + 1}` : dueDate.getMonth() + 1);
        // format the whole date
        dueDate = `${dueDate.getDate()}-${month}-${dueDate.getFullYear()}`;

        return await fetch(`${this.RESOURCE_URL}/${userId}/todos`, {
            method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
                name, dueDate,
            }), credentials: 'include',
        });
    }

    /**
     * update an existing to-do
     *
     * @param userId id of the user
     * @param toDo the to-do of the user
     * @returns an updated to-do
     */
    async updateToDo(userId, toDo) {
        // converts dueAt to a js date
        let dueDate = new Date(toDo.dueAt);
        // format month, concatenate 0 if month is smaller than 10
        const month = (dueDate.getMonth() + 1 < 10) ? `0${dueDate.getMonth() + 1}` : dueDate.getMonth() + 1;
        // stringify the whole date
        toDo.dueAt = `${dueDate.getDate()}-${month}-${dueDate.getFullYear()}`;
        return await fetch(`${this.RESOURCE_URL}/${userId}/todos/${toDo.id}`, {
            method: 'PUT', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({
                id: toDo.id, name: toDo.name, dueAt: toDo.dueAt, isCompleted: toDo.completed,
            }), credentials: 'include',
        });
    }

    /**
     * deletes a to-do
     *
     * @param userId id of the user
     * @param toDoId id of the to-do
     * @returns deleted to-do
     */
    async deleteUserToDoById(userId, toDoId) {
        return await fetch(`${this.RESOURCE_URL}/${userId}/todos/${toDoId}`, {
            method: 'DELETE', headers: {'Content-Type': 'application/json'}, credentials: 'include'
        });
    }

}





















