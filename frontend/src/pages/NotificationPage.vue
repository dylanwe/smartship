<template>
  <div class="text-4xl font-bold pt-10 pb-5">Notifications</div>
  <div class="flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-4">
    <div class="bg-white rounded-2xl flex-1 md:flex-none md:w-[400px]">
      <ol class="p-2 space-y-1">
        <li v-for="notification in notifications" :key="notification.id" @click="selectNotification(notification)"
            class="hover:bg-primary-50 rounded-md flex p-2 rounded-2xl cursor-pointer transition-colors">
          <div :class="(notification.status === 'READ') ? 'bg-neutral-200' : 'bg-primary-500'"
               class="h-2 w-2 rounded-full mt-2.5"></div>
          <div class="flex-1 pl-4">
            <h3 class="text-lg font-semibold text-neutral-900">{{ notification.title }}</h3>
            <time class="mb-1 text-sm font-normal leading-none text-neutral-400">
              {{ notification.date }}
            </time>
            <p class="mb-1 text-base font-normal text-neutral-500">{{
                notification.text.substring(0, 80)
              }}...</p>

            <span v-if="notification.notifiicationType === 'INFO'"
                  class="bg-primary-200 text-primary-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full">Info</span>
            <span v-else-if="notification.notifiicationType === 'ERROR'"
                  class="bg-red-200 text-red-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full">Error</span>
            <span v-else
                  class="bg-neutral-200 text-neutral-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full">Message</span>
          </div>
        </li>
      </ol>
    </div>

    <!-- DETAILS -->
    <div class="flex-1">
      <NotificationsDetail :selected-notification="selectedNotification"/>
    </div>
  </div>
</template>

<script>
import Notification from "@/models/notifications/Notification";
import NotificationsDetail from "@/components/dashboard/notification/NotificationsDetail";

export default {
  inject: ["notificationService", "userService"],
  name: "NotificationIndex",
  components: {NotificationsDetail},

  data() {
    return {
      notifications: null,
      selectedNotification: null
    };
  },

  async created() {
    this.notifications = await this.notificationService.findAllInOrderFromNewestToOldest();
  },

  methods: {
    selectNotification(notification) {
      this.selectedNotification = notification;
      this.selectedNotification.status = Notification.Status.READ;
    },
  }
}

</script>

<style scoped>

</style>