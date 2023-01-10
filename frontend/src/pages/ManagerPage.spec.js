import {mount} from '@vue/test-utils'
import {ShipAdapterInMemory} from "@/services/ShipAdapter-InMemory";
import {reactive} from "vue";
import User from "@/models/User";
import SessionSbServiceInMemory from "@/services/SessionSbService-InMemory";
import managerPage from "@/pages/ManagerPage";
import {userManagementAdapterInMemory} from "@/services/userManagementAdapter-InMemory";

/**
 * @Author: Jan van der Henst
 */

let wrapper;
let currentUser;

beforeEach(async function () {
    let shipAdapterInMemory = new ShipAdapterInMemory(10000);
    currentUser = new User(
        1000,
        "John",
        "Smith",
        "JohnSmith@mail.com",
        "Manager",
        "12-07-2000",
        "My name is John"
    );
    let sessionSbServiceInMemory = new SessionSbServiceInMemory(currentUser);
    let userManagementServiceInMemory = new userManagementAdapterInMemory(10000);

    wrapper = await mount(managerPage, {
        shallow: true,
        global: {
            provide: {
                "sessionService": reactive(sessionSbServiceInMemory),
                "userManagementService": reactive(userManagementServiceInMemory),
                "shipService": reactive(shipAdapterInMemory),
            }
        }
    })
});

it('should show operators', async () => {
    expect(wrapper.vm.operators.length).toBe(2);
});

it('should show ships', async () => {
    expect(wrapper.vm.ships.length).toBe(7);
});

it('should delete an operator', async () => {
    global.confirm = jest.fn(() => true);

    expect(wrapper.vm.operators.length).toBe(2);
    await wrapper.find("button[name=deleteUserButton]").trigger('click');
    expect(wrapper.vm.operators.length).toBe(1);
});

it('should not delete an operator', async () => {
    global.confirm = jest.fn(() => false);

    expect(wrapper.vm.operators.length).toBe(2);
    await wrapper.find("button[name=deleteUserButton]").trigger('click');
    expect(wrapper.vm.operators.length).toBe(2);
});

it('should delete a ship', async () => {
    global.confirm = jest.fn(() => true);

    expect(wrapper.vm.ships.length).toBe(7);
    await wrapper.find("button[name=deleteShipButton]").trigger('click');
    expect(wrapper.vm.ships.length).toBe(6);
});

it('should not delete a ship', async () => {
    global.confirm = jest.fn(() => false);

    expect(wrapper.vm.ships.length).toBe(7);
    await wrapper.find("button[name=deleteShipButton]").trigger('click');
    expect(wrapper.vm.ships.length).toBe(7);
});
