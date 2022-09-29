<template>
  <SidebarComponent @isMenuOpen="toggled" />
  <div :class="(isMenuOpen) ? 'ml-[240px]' : 'ml-[56px]'" class="transition-all duration-500">
    <div class="max-w-7xl mx-auto px-8">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import SidebarComponent from "@/components/SidebarComponent";

export default {
  name: 'App',
  components: {
    SidebarComponent
  },
  data() {
    return {
      isMenuOpen: true,
    }
  },
  methods: {
    toggled(isMenuOpen) {
      this.isMenuOpen = isMenuOpen;
    }
  }
}
</script>