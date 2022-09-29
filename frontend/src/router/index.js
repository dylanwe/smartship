import {createRouter, createWebHistory} from "vue-router";
import DashboardComponent from "@/components/dashboard/DashboardComponent";
import DashboardIndex from "@/components/dashboard/DashboardIndex";
import UnknownRoute from "@/components/UnknownRoute";

const routes = [
    {path: "/", component: UnknownRoute},
    {path: "/dashboard", component: DashboardComponent, children: [
            {path: "", component: DashboardIndex},
            {path: "/dashboard/:pathMatch(.*)", component: UnknownRoute}
        ]
    },
    {path: "/:pathMatch(.*)", component: UnknownRoute},
];

export const router = createRouter({
    history: createWebHistory(),
    routes
});