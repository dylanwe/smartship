import {createRouter, createWebHistory} from "vue-router";
import DashboardComponent from "@/components/dashboard/DashboardComponent";
import DashboardIndex from "@/components/dashboard/DashboardIndex";
import ProfileIndex from "@/components/dashboard/profile/ProfileIndex";
import UnknownRoute from "@/components/UnknownRoute";
import SettingsIndex from "@/components/dashboard/settings/SettingsIndex";
import LoginForm from "@/components/LoginForm";

const routes = [
    {path: "/", component: LoginForm},
    {path: "/dashboard", component: DashboardComponent, children: [
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