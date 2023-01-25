import UserAdapterInMemory from "@/services/UserAdapter-InMemory";
import SessionSbServiceInMemory from "@/services/SessionSbService-InMemory";
import {reactive} from "vue";
import {mount} from '@vue/test-utils'
import ToDoAdapterInMemory from "@/services/toDoAdapter-InMemory";
import EditToDoModal from "@/components/modals/EditToDoModal.vue";

/**
 * @Author Lisanne Lin
 */

let wrapper;
let currentUser;
const firstToDoId = 30_000;

const date = new Date();
const year = date.getFullYear();
const month = ((date.getMonth() + 1 < 10) ? `0${date.getMonth() + 1}` : date.getMonth() + 1);
const day = date.getDate();
const today = `${year}-${month}-${day}`;

let firstToDo;

//create new user and to-dos before every test
beforeEach(async () => {
    const userAdapterInMemory = new UserAdapterInMemory(10_000);
    currentUser = userAdapterInMemory.users[0];
    let sessionSbServiceInMemory = new SessionSbServiceInMemory(currentUser);
    let toDoServiceInMemory = new ToDoAdapterInMemory(firstToDoId, userAdapterInMemory);

    //get the first to-do of the user
    firstToDo = await toDoServiceInMemory.getUserToDoById(currentUser.id, firstToDoId);

    // add to-do for testing
    toDoServiceInMemory.saveTodo(currentUser.id, "Check engine", today)
    wrapper = await mount(EditToDoModal, {
        global: {
            provide: {
                "sessionService": reactive(sessionSbServiceInMemory), "toDoService": reactive(toDoServiceInMemory),
            }
        }, propsData: {
            selectedToDoId: firstToDoId, showToDoModal: true
        }
    });
});

it('should load the given to-do', async () => {
    // check if selected to-do is loaded
    expect(wrapper.props().selectedToDoId).toBe(firstToDoId);

    // force watch to happen to load in the selected to-do
    await wrapper.vm.$options.watch.selectedToDoId.call(wrapper.vm);

    // check if input has correct name
    const toDoNameInput = await wrapper.find("input[name=toDoName]").element.value;
    expect(toDoNameInput).toBe("Check engine")

    // check if input has correct the correct date
    const dueDateInput = await wrapper.find("input[name=dueDate]").element.value;
    expect(dueDateInput).toBe(today)
});


