import { createApp } from 'vue'
import App from './App.vue'
import './assets/tailwind.css'
import { router } from "@/router";

createApp(App).use(router).mount('#app')


// widget: {
//     widgetID: 0
//     componnentName: ""
//     icon: "📈"
//     title: "Line Chart"
//     data: {} //for chart
//     config: {
//         minHeight: 1
//         maxHeight: 1
//         minWidth: 1
//         maxWidth: 1
//         h:3 
//         w:2
//     }
//     requiredRoles: [ROLES.ADMIN]
// }

// data: [
//     {
//         "x": 0, "y": 0, // position
//         "w": widget.config.w,  // size 
//         "h": widget.config.h,

//         data: widget.data,  // stats data
//         widgetID: widget.id 
//     },
// ]