import {createRouter, createWebHistory} from "vue-router";
import DashboardComponent from "@/components/DashboardComponent";
import UnknownRoute from "@/components/UnknownRoute";

const routes = [
    {path: "/", redirect: "/dashboard"},
    {path: "/dashboard", component: DashboardComponent},
    {path: "/:pathMatch(.*)", component: UnknownRoute},
];

export const router = createRouter({
    history: createWebHistory(),
    routes
});