<template>
  <div class="bg-white p-4 rounded-2xl mb-4">
    <h2 class="text-xl font-bold text-neutral-800">Notifications</h2>
    <p class="text-neutral-600">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
      mollit anim id est
      laborum.</p>
    <form v-if="notificationSettings" @submit.prevent="updateNotificationPreferences" class="mt-4" autocomplete="off">
      <div v-for="setting in notificationSettings" :key="setting.id">
        <h3 class="text-lg text-neutral-800 font-bold">{{ setting.title }}</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
          <div>
            <p class="text-neutral-600">{{ setting.description }}</p>
          </div>
          <div>
            <div>
              <label :for="`${setting.id}-push`" class="inline-flex relative items-center cursor-pointer">
                <input type="checkbox" value="" :id="`${setting.id}-push`" class="sr-only peer"
                       v-model="setting.isPushActive">
                <div
                    class="w-11 h-6 bg-neutral-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-primary-200 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-neutral-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary-500"></div>
                <span class="ml-3 text-sm font-medium text-neutral-900">Push notification</span>
              </label>
            </div>
            <div>
              <label :for="`${setting.id}-email`" class="inline-flex relative items-center cursor-pointer">
                <input type="checkbox" value="" :id="`${setting.id}-email`" class="sr-only peer"
                       v-model="setting.isEmailActive">
                <div
                    class="w-11 h-6 bg-neutral-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-primary-200 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-neutral-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary-500"></div>
                <span class="ml-3 text-sm font-medium text-neutral-900">E-mail</span>
              </label>
            </div>
          </div>
        </div>
      </div>

      <button type="submit"
              class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors">
        Save
      </button>
    </form>
    <div v-else class="h-64"></div>
  </div>
</template>

<script>
export default {
  name: "NotificationsForm",
  inject: ["userService"],
  emits: ['showToast'],

  data() {
    return {
      notificationSettings: null,
    }
  },

  async created() {
    this.notificationSettings = await this.userService.findNotificationSettings();
  },

  methods: {
    async updateNotificationPreferences() {
      const settings = [];

      for (const notificationSettingsKey in this.notificationSettings) {
        const notification = {};

        notification.notificationSettingId = this.notificationSettings[notificationSettingsKey].id;
        notification.isEmailActive = this.notificationSettings[notificationSettingsKey].isEmailActive;
        notification.isPushActive = this.notificationSettings[notificationSettingsKey].isPushActive;
        notification.notificationPreferenceId = this.notificationSettings[notificationSettingsKey].notificationPreferenceId;

        settings.push(notification);
      }

      const response = await this.userService.updateNotificationSettings(settings);

      // handle response
      if (response.ok) {
        this.$emit('showToast', 'success', 'Notification preferences saved');
      } else {
        const data = await response.json();
        this.$emit('showToast', 'error', data.message);
      }
    }
  }
}
</script>