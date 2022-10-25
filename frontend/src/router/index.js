import {createRouter, createWebHistory} from "vue-router";
import DashboardComponent from "@/components/dashboard/DashboardComponent";
import DashboardIndex from "@/components/dashboard/DashboardIndex";
import ProfileIndex from "@/components/dashboard/profile/ProfileIndex";
import UnknownRoute from "@/components/UnknownRoute";
import LoginComponent from "@/components/login/LoginComponent";
import ResetPasswordComponent from "@/components/passwordReset/ResetPasswordComponent";
import SettingsIndex from "@/components/dashboard/settings/SettingsIndex";
import SessionSbService from "@/services/SessionSbService";

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