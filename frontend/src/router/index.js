import {createRouter, createWebHistory} from "vue-router";
import DashboardComponent from "@/components/dashboard/DashboardComponent";
import DashboardIndex from "@/components/dashboard/DashboardIndex";
import UnknownRoute from "@/components/UnknownRoute";
import LoginComponent from "@/components/login/LoginComponent";
import ResetPasswordComponent from "@/components/passwordReset/ResetPasswordComponent";

const routes = [
    {path: "/", component: UnknownRoute},
    {path: "/dashboard", component: DashboardComponent, children: [
            {path: "", component: DashboardIndex},
            {path: "/dashboard/:pathMatch(.*)", component: UnknownRoute}
        ]
    },
    {path: "/login", component: LoginComponent},
    {path: "/resetPassword", component: ResetPasswordComponent},
    {path: "/:pathMatch(.*)", component: UnknownRoute},
];

export const router = createRouter({
    history: createWebHistory(),
    routes
});