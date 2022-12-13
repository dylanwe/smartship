<template>
  <div class="text-4xl font-bold pt-10 pb-5">Notifications</div>

  <form class="w-full">
    <div class="flex w-full space-x-3">
      <div class="flex mb-3">
        <button id="dropdownActionButton" v-on:click="showDropdown = !showDropdown" data-dropdown-toggle="dropdownAction"
                class="inline-flex items-center text-neutral-500 bg-white border border-neutral-300 focus:outline-none hover:bg-neutral-100 focus:ring-4 focus:ring-neutral-200 font-medium rounded-lg text-sm px-3 py-1.5 "
                type="button">
          <span class="sr-only">Action button</span>
          Filter
          <svg class="ml-2 w-3 h-3" aria-hidden="true" fill="none" stroke="currentColor" viewBox="0 0 24 24"
               xmlns="http://www.w3.org/2000/svg">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
          </svg>
        </button>
        <!-- Dropdown menu -->
        <div v-if="showDropdown" id="dropdownAction"
             class="hidden z-10 w-44 bg-white rounded divide-y divide-neutral-100 shadow">
          <ul class="py-1 text-sm text-neutral-700" aria-labelledby="dropdownActionButton">
            <li>
              <a href="#"
                 class="block py-2 px-4 hover:bg-neutral-100 ">Date Ascending</a>
            </li>
            <li>
              <a href="#"
                 class="block py-2 px-4 hover:bg-neutral-100">Date Descending</a>
            </li>
          </ul>
        </div>
      </div>
      <div class="flex flex-1 mb-3">
        <button id="dropdown-button" data-dropdown-toggle="dropdown"
                class="flex-shrink-0 z-10 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-neutral-900 bg-neutral-100 border border-neutral-300 rounded-l-lg hover:bg-neutral-200 focus:ring-4 focus:outline-none focus:ring-neutral-100 "
                type="button">Notification Types
          <svg aria-hidden="true" class="w-4 h-4 ml-1" fill="currentColor" viewBox="0 0 20 20"
               xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd"
                  d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                  clip-rule="evenodd"></path>
          </svg>
        </button>
        <div id="dropdown"
             class="z-10 hidden bg-white divide-y divide-neutral-100 rounded shadow w-44"
             data-popper-reference-hidden="" data-popper-escaped="" data-popper-placement="top"
             style="position: absolute; transform: translate3d(897px, 5637px, 0px);">
          <ul class="py-1 text-sm text-neutral-700" aria-labelledby="dropdown-button">
            <li>
              <button type="button"
                      class="inline-flex w-full px-4 py-2 hover:bg-neutral-100">
                Error
              </button>
            </li>
            <li>
              <button type="button"
                      class="inline-flex w-full px-4 py-2 hover:bg-neutral-100">
                Info
              </button>
            </li>
            <li>
              <button type="button"
                      class="inline-flex w-full px-4 py-2 hover:bg-neutral-100">
                Message
              </button>
            </li>
          </ul>
        </div>
        <div class="relative w-full">
          <input type="search" id="search-dropdown"
                 class="block p-2.5 w-full z-20 text-sm text-neutral-900 bg-neutral-50 rounded-r-lg border-l-neutral-50 border-l-2 border border-neutral-300 "
                 placeholder="Search Notifications" required>
        </div>
      </div>
    </div>
  </form>


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
      selectedNotification: null,
      showDropdown: false
    };
  },

  async created() {
    this.notifications = await this.notificationService.getUserNotifications();
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