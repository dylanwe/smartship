<template>
  <!-- SELECTED NOTIFICATION -->
  <div v-if="selectedNotification" class="bg-white rounded-2xl p-4">
    <div class="flex justify-between">
      <h2 class="text-2xl font-semibold text-neutral-900">
        {{ selectedNotification.title }}
      </h2>
      <time class="mb-1 text-sm font-normal leading-none text-neutral-400 dark:text-neutral-500 mt-2.5">
        {{ selectedNotification.date }}
      </time>
    </div>
    <div class="pt-1 pb-7">
      <span v-if="selectedNotification.notifiicationType === 'INFO'"
            class="bg-primary-200 text-primary-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full">Info</span>
      <span v-else-if="selectedNotification.notifiicationType === 'ERROR'"
            class="bg-red-200 text-red-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full">Error</span>
      <span v-else
            class="bg-neutral-200 text-neutral-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full ">Message</span>
    </div>
    <p class="mb-1 text-base font-normal text-neutral-500">
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