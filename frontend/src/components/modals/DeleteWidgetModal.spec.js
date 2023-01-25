import {mount} from '@vue/test-utils'
import {reactive} from "vue";
import SessionSbServiceInMemory from "@/services/SessionSbService-InMemory";
import deleteWidgetModal from "@/components/modals/DeleteWidgetModal";
import DashboardAdapterInMemory from "@/services/dashboardAdapter-InMemory";
import UserAdapterInMemory from "@/services/UserAdapter-InMemory";

/**
 * @Author: Jesaja Pavlovic
 */

let wrapper;
let currentUser;
let dashboard;
beforeEach(async () => {
    const userAdapterInMemory = new UserAdapterInMemory(20_000);
    currentUser = userAdapterInMemory.users[0];
    let sessionSbServiceInMemory = new SessionSbServiceInMemory(currentUser);

    let dashboardAdapterInMemory = new DashboardAdapterInMemory(10_000,userAdapterInMemory);

   dashboard = dashboardAdapterInMemory.getUserDashboard(currentUser);



    wrapper = await mount(deleteWidgetModal, {
        global: {
            provide: {
                "sessionService": reactive(sessionSbServiceInMemory),
                "dashboardService": reactive(dashboardAdapterInMemory),
            }
        }, props:{widget:{id:1, shipSensor: {sensor: {name: "SENSOR_NAME"}}}, showDeleteWidgetModal:true}
    })
});

it('should emit remove widget event on click', async () => {
    await wrapper.find("button[name=confirmDelete]").trigger('click');
    expect(wrapper.emitted('delete')[0][0].id,
        "Widget did not get removed")
        .toBe(dashboard.layout[0].id);

});


