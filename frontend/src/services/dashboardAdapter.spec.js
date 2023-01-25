import DashboardAdapterInMemory from "@/services/DashboardAdapter-InMemory";
import User from "@/models/User";
import UserAdapterInMemory from "@/services/UserAdapter-InMemory";

/**
 * @Author: Jesaja Pavlovic
 */

const FIRST_ID_USERS = 10_000;
const FIRST_ID_DASHBOARDS = 30_000;
let userService;
let dashboardService;

beforeEach(function () {
    userService = new UserAdapterInMemory(FIRST_ID_USERS);
    dashboardService = new DashboardAdapterInMemory(FIRST_ID_DASHBOARDS, userService);
});

it('should create a new user dashboard', () => {
    expect(dashboardService.dashboards).toHaveLength(7);

    const newUser = User.createSampleUser(3000);
    dashboardService.getUserDashboard(newUser);

    expect(dashboardService.dashboards).toHaveLength(8);
    expect(dashboardService.dashboards.at(-1).user).toBe(newUser);
});

it('should add an item to layout', () => {
    const dashboard = dashboardService.dashboards[0]
    expect(dashboard.layout).toHaveLength(1)

    const newLayout = [{id: 66, x: 3, y: 3, h: 1, w: 2},{id: 22, x: 3, y: 3, h: 1, w: 2}]
    dashboardService.saveLayout(dashboard.id, newLayout);


    expect(dashboard.layout).toHaveLength(2)
    expect(dashboard.layout).toBe(newLayout);


});