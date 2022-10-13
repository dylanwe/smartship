<template>
  <router-view></router-view>
</template>

<script>
import UserAdapter from "@/services/userAdapter";
import SessionSbService from "@/services/SessionSbService";
import {shallowReactive} from "vue";
import FetchInterceptor from "@/utils/FetchInterceptor";

const API_URL = 'http://localhost:8087';
let JWT_STORAGE_ITEM;

export default {
  name: 'App',
  provide() {
    this.theSessionService = shallowReactive(
        new SessionSbService(API_URL + '/authentication', JWT_STORAGE_ITEM)
    )
    this.FetchInterceptor = new FetchInterceptor(this.theSessionService, this.$router);
    return {
      sessionService: this.theSessionService,
      userService: new UserAdapter(),
    }
  },

  unmounted() {
    console.log('App.unmounted() has been called.');
    this.FetchInterceptor.unregister();
  }
}
</script>