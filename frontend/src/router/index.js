import {createRouter, createWebHistory} from "vue-router";
import DashboardComponent from "@/components/dashboard/DashboardComponent";
import DashboardIndex from "@/components/dashboard/DashboardIndex";
import ProfileIndex from "@/components/dashboard/profile/ProfileIndex";
import UnknownRoute from "@/components/UnknownRoute";
import SettingsIndex from "@/components/dashboard/settings/SettingsIndex";
import LoginForm from "@/components/LoginForm";
import SessionSbService from "@/services/SessionSbService";
import NotificationIndex from "@/components/notification/NotificationIndex";

const routes = [
    {path: "/", name: 'login', component: LoginForm, beforeEnter: () => {
            if (SessionSbService.isLoggedIn) {
                return '/dashboard'
            }
        }
    },
    {path: "/dashboard", component: DashboardComponent, children: [
            {path: "", component: DashboardIndex},
            {path: "profile", component: ProfileIndex},
            {path: "settings", component: SettingsIndex},
            {path: "notifications", component: NotificationIndex},
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
    if (to.name !== 'login' && !SessionSbService.isLoggedIn) next({name: 'login'})
    else next();
})