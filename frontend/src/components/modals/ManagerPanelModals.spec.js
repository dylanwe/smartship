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

it('should show addAccountModal', async () => {
    expect(wrapper.vm.showNewUserModal).toBe(false);
    await wrapper.find("button[name=newUserButton]").trigger('click');
    expect(wrapper.vm.showNewUserModal).toBe(true);
});

it('should show editAccountModal', async () => {
    expect(wrapper.vm.showEditUserModal).toBe(false);
    await wrapper.find("button[name=editUserButton]").trigger('click');
    expect(wrapper.vm.showEditUserModal).toBe(true);
});

it('should show addShipModal', async () => {
    expect(wrapper.vm.showNewShipModal).toBe(false);
    await wrapper.find("button[name=newShipButton]").trigger('click');
    expect(wrapper.vm.showNewShipModal).toBe(true);
});

it('should show showOperatorsForShipModal', async () => {
    expect(wrapper.vm.showOperatorsShipModal).toBe(false);
    await wrapper.find("button[name=showShipOperatorsButton]").trigger('click');
    expect(wrapper.vm.showOperatorsShipModal).toBe(true);
});
