<template>
  <!-- SELECTED NOTIFICATION -->
  <div v-if="selectedNotification" class="bg-white rounded-2xl p-4">
    <div class="flex">
    <h2 class="text-2xl font-semibold text-slate-900">{{ selectedNotification.title }}</h2>
      <time class="mb-1 text-sm font-normal leading-none text-slate-400 dark:text-slate-500">
      {{selectedNotification.date}}
      </time>
    </div>
    <div class="pt-1 pb-7">
      <span v-if="selectedNotification.notifiicationType === 'INFO'"
            class="bg-sky-200 text-sky-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full dark:bg-blue-200 dark:text-blue-800">Info</span>
      <span v-else-if="selectedNotification.notifiicationType === 'ERROR'"
            class="bg-red-200 text-red-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full dark:bg-blue-200 dark:text-blue-800">Error</span>
      <span v-else
            class="bg-slate-200 text-slate-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full dark:bg-blue-200 dark:text-blue-800">Message</span>
    </div>
    <p>
      {{ selectedNotification.text }}
    </p>
  </div>
</template>

<script>
import Notification from "@/models/notifications/Notification";

export default {

  name: "NotificationsDetail",
  props: {
    selectedNotification: Notification,
  },

  data() {
    return {}
  },
}


</script>

<style scoped>

</style>