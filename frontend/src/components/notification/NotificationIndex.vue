<template>
  <div class="text-4xl font-bold pt-10 pb-5">Notifications</div>
  <div class="flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-4">
    <div class="bg-white rounded-2xl flex-1 md:flex-none md:w-[400px]">
      <ol class="p-2 space-y-1">
        <li v-for="notification in notifications" :key="notification.id" @click="selectNotification(notification)"
            class="hover:bg-sky-50 rounded-md flex p-2 rounded-2xl">
          <div :class="(notification.status === 'READ') ? 'bg-slate-200' : 'bg-sky-500'"
               class="h-2 w-2 rounded-full mt-2.5"></div>
          <div class="flex-1 pl-4">
            <h3 class="text-lg font-semibold text-slate-900">{{ notification.title }}</h3>
            <time class="mb-1 text-sm font-normal leading-none text-slate-400 dark:text-slate-500">2023, July 13, 10:30 AM
            </time>
            <p class="mb-1 text-base font-normal text-slate-500 dark:text-slate-400">{{
                notification.text.substring(0, 80)
              }}...</p>

            <span v-if="notification.notifiicationType === 'INFO'"
                  class="bg-sky-200 text-sky-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full dark:bg-blue-200 dark:text-blue-800">Info</span>
            <span v-else-if="notification.notifiicationType === 'ERROR'"
                  class="bg-red-200 text-red-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full dark:bg-blue-200 dark:text-blue-800">Error</span>
            <span v-else
                  class="bg-slate-200 text-slate-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full dark:bg-blue-200 dark:text-blue-800">Message</span>
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
import NotificationsDetail from "@/components/notification/NotificationsDetail";

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
    this.notifications = await this.notificationService.findAllNotification();
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