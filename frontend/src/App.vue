<template>
  <router-view></router-view>
</template>

<script>
import UserAdapter from "@/services/UserAdapter";
import SessionSbService from "@/services/SessionSbService";
import {shallowReactive} from "vue";
import FetchInterceptor from "@/utils/FetchInterceptor";
import NotificationService from "@/services/NotificationService";
import UserManagementAdaptor from "@/services/userManagementAdaptor";
import ToDoAdapter from "@/services/toDoAdapter";
import DashboardAdapter from "@/services/dashboardAdapter";
import ShipAdapter from "@/services/shipAdapter";

const API_URL = process.env.VUE_APP_API_URL;
let JWT_STORAGE_ITEM;

export default {
  name: 'App',
  provide() {
    this.theSessionService = shallowReactive(
        new SessionSbService(`${API_URL}/auth`, JWT_STORAGE_ITEM, this.$router)
    )
    this.FetchInterceptor = new FetchInterceptor(this.theSessionService, this.$router);
    return {
      sessionService: this.theSessionService,
      userService: new UserAdapter(`${API_URL}/users`),
      dashboardService: new DashboardAdapter(`${API_URL}/dashboards`),
      shipService: new ShipAdapter(`${API_URL}/ships`),
      notificationService: new NotificationService(),
      userManagementService: new UserManagementAdaptor(`${API_URL}/userManagement`),
      toDoService: new ToDoAdapter(`${API_URL}/users`),
    }
  },

  unmounted() {
    console.log('App.unmounted() has been called.');
    this.FetchInterceptor.unregister();
  }
}
</script>