<template>
  <SettingToast :show="isToastShown" :toastType="toastType" :text="toastText" />

  <div class="mt-6 max-w-4xl mx-auto">
    <h1 class="text-4xl text-neutral-800 font-bold mb-4">Settings</h1>

    <!-- Profile -->
    <UserInfoForm @showToast="showToast"/>

    <!-- Privacy -->
    <PasswordForm @showToast="showToast"/>

    <!-- Notifications -->
    <div class="bg-white p-4 rounded-2xl mb-4">
      <h2 class="text-xl font-bold text-neutral-800">Notifications</h2>
      <p class="text-neutral-600">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est
        laborum.</p>
      <form v-if="notificationSettings" class="mt-4" autocomplete="off">
        <div v-for="setting in notificationSettings.notifications" :key="setting.id">
          <h3 class="text-lg text-neutral-800 font-bold">{{ setting.name }}</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
            <div>
              <p class="text-neutral-600">{{ setting.description }}</p>
            </div>
            <div>
              <div v-for="(notification, index) in setting.options" :key="`${setting.id}-${index}`">
                <label :for="`${setting.id}-${index}`" class="inline-flex relative items-center cursor-pointer">
                  <input type="checkbox" value="" :id="`${setting.id}-${index}`" class="sr-only peer"
                         :checked="notification.on">
                  <div
                      class="w-11 h-6 bg-neutral-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-primary-200 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-neutral-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary-500"></div>
                  <span class="ml-3 text-sm font-medium text-neutral-900">{{ notification.name }}</span>
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
  </div>
</template>

<script>
import SettingToast from "@/components/dashboard/settings/SettingToast";
import UserInfoForm from "@/components/dashboard/settings/UserInfoForm";
import PasswordForm from "@/components/dashboard/settings/PasswordForm";

export default {
  name: "SettingsIndex",
  inject: ["sessionService", "userService"],
  components: {
    SettingToast,
    UserInfoForm,
    PasswordForm,
  },

  data() {
    return {
      notificationSettings: null,
      isToastShown: false,
      toastText: '',
      toastType: 'info',
    }
  },

  async created() {
    this.notificationSettings = await this.userService.findNotificationSettings();
  },

  methods: {
    showToast(type, text) {
      this.toastType = type;
      this.toastText = text;
      this.isToastShown = true;
      setTimeout(() => this.isToastShown = false, 2000);
    },
  },
}
</script>