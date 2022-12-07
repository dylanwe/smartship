import {createRouter, createWebHistory} from "vue-router";
import DashboardComponent from "@/components/dashboard/DashboardComponent";
import DashboardIndex from "@/pages/DashboardPage";
import ProfileIndex from "@/pages/ProfilePage";
import UnknownRoute from "@/pages/UnknownPage";
import LoginComponent from "@/pages/LoginPage";
import ResetPasswordComponent from "@/pages/ResetPasswordPage";
import SettingsIndex from "@/pages/SettingsPage";
import SessionSbService from "@/services/SessionSbService";
import NotificationIndex from "@/pages/NotificationPage";
import ManagerIndex from "@/pages/ManagerPage";
import AdminIndex from "@/pages/AdminPage";

const savePages = ["login", "resetPassword"];

const routes = [
    {
        path: "/", name: 'login', component: LoginComponent, beforeEnter: () => {
            if (SessionSbService.isLoggedIn) {
                return '/dashboard'
            }
        }
    },
    {
        path: "/resetPassword", name: 'resetPassword', component: ResetPasswordComponent, beforeEnter: () => {
            if (SessionSbService.isLoggedIn) {
                return '/dashboard'
            }
        }
    },
    {
        path: "/dashboard", component: DashboardComponent, children: [
            {path: "", component: DashboardIndex},
            {path: "profile", component: ProfileIndex},
            {path: "settings", component: SettingsIndex},
            {path: "notifications", component: NotificationIndex},
            {path: "managerPanel", component: ManagerIndex},
            {path: "adminPanel", component: AdminIndex},
            {path: ":pathMatch(.*)", component: UnknownRoute}
        ]
    },
    {path: "/:pathMatch(.*)", component: UnknownRoute},
];

export const router = createRouter({
    history: createWebHistory(),
    routes
});

// Protect all pages except for login
router.beforeEach((to, from, next) => {
    if (!savePages.includes(to.name) && !SessionSbService.isLoggedIn) {
        next({name: 'login'})
        return;
    }
    next();
})