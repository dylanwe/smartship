<template>
  <SettingToast :show="isToastShown" :toastType="toastType" :text="toastText"/>

  <div class="mt-6 max-w-4xl mx-auto">
    <h1 class="text-4xl text-neutral-800 font-bold mb-4">Settings</h1>

    <!-- Profile -->
    <UserInfoForm @showToast="showToast"/>

    <!-- Privacy -->
    <PasswordForm @showToast="showToast"/>

    <!-- Notifications -->
    <NotificationsForm @showToast="showToast"/>
  </div>
</template>

<script>
import SettingToast from "@/components/dashboard/settings/SettingToast";
import UserInfoForm from "@/components/dashboard/settings/UserInfoForm";
import PasswordForm from "@/components/dashboard/settings/PasswordForm";
import NotificationsForm from "@/components/dashboard/settings/NotificationsForm";

export default {
  name: "SettingsIndex",
  components: {
    NotificationsForm,
    SettingToast,
    UserInfoForm,
    PasswordForm,
  },

  data() {
    return {
      isToastShown: false,
      toastText: '',
      toastType: 'info',
    }
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