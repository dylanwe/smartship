<template>
  <router-view></router-view>
</template>

<script>
import UserAdapter from "@/services/userAdapter";
import SessionSbService from "@/services/SessionSbService";
import {shallowReactive} from "vue";

const API_URL = 'http://localhost:8087';
let JWT_STORAGE_ITEM;

export default {
  name: 'App',
  provide() {
    this.theSessionService = shallowReactive(
        new SessionSbService(API_URL + '/authentication', JWT_STORAGE_ITEM)
    )
    return {
      userService: new UserAdapter(),
      sessionService: this.theSessionService,
    }
  }
}
</script>