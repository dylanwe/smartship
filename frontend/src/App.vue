<template>
  <router-view></router-view>
</template>

<script>
import UserAdapter from "@/services/userAdapter";
import SessionSbService from "@/services/SessionSbService";
import {shallowReactive} from "vue";
import FetchInterceptor from "@/utils/FetchInterceptor";
import NotificationService from "@/services/NotificationService";
import ManagerAdapter from "@/services/managerAdapter";

const API_URL = "http://localhost:8087/api/v1";
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
      notificationService: new NotificationService(),
      managerService: new ManagerAdapter(`${API_URL}/manager`),
    }
  },

  unmounted() {
    console.log('App.unmounted() has been called.');
    this.FetchInterceptor.unregister();
  }
}
</script>