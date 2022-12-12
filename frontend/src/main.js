import { createApp } from 'vue'
import App from './App.vue'
import './assets/tailwind.css'
import { router } from "@/router";
import VueApexCharts from "vue3-apexcharts";

createApp(App).use(router).use(VueApexCharts).mount('#app')
