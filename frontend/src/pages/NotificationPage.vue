<template>
  <div class="text-4xl font-bold pt-10 pb-5">Notifications</div>

  <form class="w-full">
    <div class="flex w-full space-x-3">
      <div class="flex mb-3">
        <button @click="handleDateClick" id="dropdownActionButton"
                class="inline-block relative w-full flex-shrink-0 z-10 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-neutral-900 bg-neutral-100 border border-neutral-300 rounded-full hover:bg-neutral-200 focus:ring-4 focus:outline-none focus:ring-neutral-100"
                type="button">
          <span class="sr-only">Action button</span>
          {{ textContentFilter }}
          <svg class="ml-2 w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24"
               xmlns="http://www.w3.org/2000/svg">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path>
          </svg>
        </button>
        <!-- Dropdown menu -->
        <div v-if="showDateDropdown" id="dropdownAction"
             class="absolute w-full mt-11 z-10 w-44 bg-white rounded divide-y divide-neutral-100 shadow">
          <ul class="py-1 text-sm text-neutral-700" aria-labelledby="dropdownActionButton">
            <li @click="sortNotificationsAscending">
              <a href="#" @click="selectOptionFilter('Date Ascending')"
                 class="block py-2 px-4 hover:bg-neutral-100 ">Date Ascending</a>
            </li>
            <li @click="sortNotificationsByDateDescending">
              <a href="#" @click="selectOptionFilter('Date Descending')"
                 class="block py-2 px-4 hover:bg-neutral-100">Date Descending</a>
            </li>
          </ul>
        </div>
      </div>
      <div class="flex flex-1 mb-3">
        <button @click="handleTypeClick" id="dropdown-button"
                class="flex-shrink-0 z-10 inline-flex items-center py-2.5 px-4 text-sm font-medium text-center text-neutral-900 bg-neutral-100 border border-neutral-300 rounded-l-lg hover:bg-neutral-200 focus:ring-4 focus:outline-none focus:ring-neutral-100 "
                type="button"> {{ textContentType }}
          <svg class="w-4 h-4 ml-1" fill="currentColor" viewBox="0 0 20 20"
               xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd"
                  d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                  clip-rule="evenodd"></path>
          </svg>
        </button>
        <div v-if="showTypeDropdown" id="dropdown"
             class="absolute w-full mt-11 z-10 bg-white divide-y divide-neutral-100 rounded shadow w-44">
          <ul
              class="py-1 text-sm text-neutral-700" aria-labelledby="dropdown-button">
            <li @click="selectOptionType('Notification Types')">
              <button @click="filterNotificationsByType('Notification Types')" type="button"
                      class="inline-flex w-full px-4 py-2 hover:bg-neutral-100">
                None
              </button>
            </li>
            <li @click="selectOptionType('Error')">
              <button @click="filterNotificationsByType('Error')"
                      type="button"
                      class="inline-flex w-full px-4 py-2 hover:bg-neutral-100">
                Error
              </button>
            </li>
            <li @click="selectOptionType('Info')">
              <button @click="filterNotificationsByType('Info')"
                      type="button"
                      class="inline-flex w-full px-4 py-2 hover:bg-neutral-100">
                Info
              </button>
            </li>
            <li @click="selectOptionType('Message')">
              <button @click="filterNotificationsByType('Message')"
                      type="button"
                      class="inline-flex w-full px-4 py-2 hover:bg-neutral-100">
                Message
              </button>
            </li>
          </ul>
        </div>
        <div class="relative w-full">
          <input v-model="search" type="text" id="search-dropdown"
                 class="block p-2.5 w-full z-20 text-sm text-neutral-900 bg-neutral-50 rounded-r-lg border-l-neutral-50 border-l-2 border border-neutral-300 "
                 placeholder="Search Notifications" required @input="searchNotifications">
        </div>
      </div>
    </div>
  </form>


  <div class="flex flex-col md:flex-row space-y-4 md:space-y-0 md:space-x-4">
    <div class="bg-white rounded-2xl flex-1 md:flex-none md:w-[400px]">
      <ol class="p-2 space-y-1">
        <ul v-if="filteredSearch">
          <li v-for="(notification, index) in filteredSearch" :key="index"
              @click="markNotificationAsRead(user.id, notification.id, filteredNotifications)"
              class="hover:bg-primary-50 rounded-md flex p-2 rounded-2xl cursor-pointer transition-colors">
            <div :class="(notification.status === 'READ') ? 'bg-neutral-200' : 'bg-primary-500'"
                 class="h-2 w-2 rounded-full mt-2.5"></div>
            <div class="flex-1 pl-4">
              <h3 class="text-lg font-semibold text-neutral-900">{{ notification.title }}</h3>
              <time class="mb-1 text-sm font-normal leading-none text-neutral-400">
                {{
                  formatDate(notification.date)
                }}
              </time>
              <p class="mb-1 text-base font-normal text-neutral-500">{{
                  notification.body.substring(0, 80)
                }}...</p>

              <span v-if="notification.notificationType.toUpperCase() === 'INFO'"
                    class="bg-primary-200 text-primary-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full">Info</span>
              <span v-else-if="notification.notificationType.toUpperCase() === 'ERROR'"
                    class="bg-red-200 text-red-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full">Error</span>
              <span v-else
                    class="bg-neutral-200 text-neutral-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full">Message</span>
            </div>
          </li>
        </ul>
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
  inject: ["notificationService", "userService", "sessionService"],
  name: "NotificationIndex",
  components: {NotificationsDetail},
  emits: [
    'showDropdown'
  ],

  data() {
    return {
      originalNotifications: [],
      notifications: [],
      filteredNotifications: [],
      selectedNotification: null,
      showDateDropdown: false,
      showTypeDropdown: false,
      textContentFilter: 'Filter',
      textContentType: 'Notification Types',
      search: null,
    };
  },

  async created() {
    this.user = this.sessionService.getCurrentUser();
    const userNotifications = await this.notificationService.getUserNotifications(this.user.id);
    this.notifications = await userNotifications.json();
    this.notifications.forEach(notification => {
      this.setNotificationStatus(notification);
    });
    this.originalNotifications = this.notifications;
  },

  computed: {
    filteredSearch() {
      if (this.search) {
        let filtered = this.notifications.filter(notification => {
          const title = notification.title.toLowerCase();
          const body = notification.body.toLowerCase();
          return title.includes(this.search) || body.includes(this.search);
        });
        return filtered.length ? filtered : null;
      } else {
        return this.notifications;
      }
    },
  },

  methods: {
    handleDateClick() {
      this.showDateDropdown = !this.showDateDropdown
      if (this.showDateDropdown) {
        this.showTypeDropdown = false;
      }
    },
    selectOptionFilter(event) {
      this.textContentFilter = event;
      this.showDateDropdown = false;
    },
    handleTypeClick() {
      this.showTypeDropdown = !this.showTypeDropdown;
      if (this.showTypeDropdown) {
        this.showDateDropdown = false;
      }
    },
    selectOptionType(event) {
      this.textContentType = event;
      this.showTypeDropdown = false;
    },
    isNotificationRead(notification) {
      return notification.readNotification;
    },
    setNotificationStatus(notification) {
      if (this.isNotificationRead(notification)) {
        notification.status = Notification.Status.READ;
      } else {
        notification.status = Notification.Status.UNREAD;
      }
    },
    async markNotificationAsRead(userId, notificationId) {
      try {
        // send PUT request to update the notification as read
        const response = await this.notificationService.markNotificationAsRead(userId, notificationId);
        if (response.ok) {
          // update the notification in the frontend
          const updatedNotification = this.notifications.find(notification => notification.id === notificationId);
          updatedNotification.status = Notification.Status.READ;
          this.selectedNotification = updatedNotification;
          console.log(response)
        }
      } catch (error) {
        console.error(error);
      }
    },
    sortNotificationsByDateDescending() {
      this.notifications.sort((a, b) => {
        // Convert the date strings to Date objects for comparison
        const dateA = new Date(a.date);
        const dateB = new Date(b.date);
        // If dateA is less than dateB, sort dateA first
        if (dateA < dateB) {
          return -1;
        }
        // If dateA is greater than dateB, sort dateB first
        if (dateA > dateB) {
          return 1;
        }
        // If the dates are equal, leave them in the same order
        return 0;
      });
    },
    sortNotificationsAscending() {
      this.notifications.sort((a, b) => {
        // Convert the dates to timestamps for comparison
        const aTimestamp = new Date(a.date).getTime();
        const bTimestamp = new Date(b.date).getTime();
        // Sort in ascending order
        return bTimestamp - aTimestamp;
      });
    },
    filterNotificationsByType(notificationType) {
      if (notificationType !== 'Notification Types') {
        this.filteredNotifications = this.originalNotifications.filter(
            notification => notification.notificationType.toUpperCase() === notificationType.toUpperCase()
      );
        this.notifications = this.filteredNotifications
        return this.notifications
      } else {
        this.notifications = this.originalNotifications;
        return this.originalNotifications;
      }
    },
    formatDate(date){
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(date).toLocaleDateString("en-US", options);
    }
  }
}

</script>

<style scoped>

</style>