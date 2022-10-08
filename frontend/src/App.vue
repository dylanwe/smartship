<template>
  <router-view></router-view>
</template>

<script>
import UserAdapter from "@/services/userAdapter";

export default {
  name: 'App',
  provide() {
    return {
      userService: new UserAdapter(),
    }
  }
}
</script>