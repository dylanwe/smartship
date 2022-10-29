<template>
  <div class="mt-6 max-w-4xl mx-auto">
    <h1 class="text-4xl text-neutral-800 font-bold mb-4">Settings</h1>

    <!-- Profile -->
    <div class="bg-white p-4 rounded-2xl mb-4">
      <h2 class="text-xl font-bold text-neutral-800">Profile</h2>
      <p class="text-neutral-600">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est
        laborum.</p>
      <form class="mt-4">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-neutral-900">First name</label>
            <input type="text" v-if="user" v-model="user.firstName"
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   placeholder="Jane" required="">
            <input type="text" v-else
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   placeholder="Jane" required="">
          </div>
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-neutral-900">Last Name</label>
            <input type="text" v-if="user" v-model="user.lastName"
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   placeholder="Smith" required="">
            <input type="text" v-else
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   placeholder="Jane" required="">
          </div>
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-neutral-900">Email</label>
            <input type="email" v-if="user" v-model="user.email"
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   placeholder="janesmith@mail.com" required="">
            <input type="text" v-else
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   placeholder="Jane" required="">
          </div>
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-neutral-900">Birthday</label>
            <input type="date" v-if="user" v-model="user.birthday"
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   required="">
            <input type="text" v-else
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   placeholder="Jane" required="">
          </div>
        </div>

        <label for="Bio" class="block mb-2 text-sm font-medium text-neutral-900">Bio</label>
        <textarea id="Bio" rows="2" v-if="user" v-model="user.bio"
                  class="block p-2.5 w-full text-sm resize-none text-neutral-900 bg-neutral-50 rounded-lg border border-neutral-300 focus:ring-primary-500 focus:border-primary-500"
                  placeholder="Tell us about yourself"></textarea>
        <textarea id="Bio" rows="2" v-else
                  class="block p-2.5 w-full text-sm resize-none text-neutral-900 bg-neutral-50 rounded-lg border border-neutral-300 focus:ring-primary-500 focus:border-primary-500"
                  placeholder="Tell us about yourself"></textarea>


        <button type="submit"
                class="text-white bg-primary-500 disabled:bg-neutral-300 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center mt-4 transition-colors">Save</button>
      </form>
    </div>

    <!-- Privacy -->
    <div class="bg-white p-4 rounded-2xl mb-4">
      <h2 class="text-xl font-bold text-neutral-800">Privacy</h2>
      <p class="text-neutral-600">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est
        laborum.</p>
      <form class="mt-4">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-neutral-900">Current Password*</label>
            <input type="password"
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   placeholder="••••••••" required="" v-model="password">
          </div>
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-neutral-900">New Password</label>
            <input type="password"
                   class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-primary-500 focus:border-primary-500 block w-full p-2.5"
                   placeholder="" required="">
          </div>
        </div>

        <button type="submit" :disabled="password.length <= 0"
                class="text-white bg-primary-500 disabled:bg-neutral-300 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center mt-4 transition-colors">Save</button>
      </form>
    </div>

    <!-- Notifications -->
    <div class="bg-white p-4 rounded-2xl mb-4">
      <h2 class="text-xl font-bold text-neutral-800">Notifications</h2>
      <p class="text-neutral-600">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est
        laborum.</p>
      <form v-if="notificationSettings" class="mt-4">
        <div v-for="setting in notificationSettings.notifications" :key="setting.id">
          <h3 class="text-lg text-neutral-800 font-bold">{{ setting.name }}</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
            <div>
              <p class="text-neutral-600">{{ setting.description }}</p>
            </div>
            <div>
              <div v-for="(notification, index) in setting.options" :key="`${setting.id}-${index}`">
                <label :for="setting.id + '-' + index"
                       class="inline-flex relative items-center mb-4 cursor-pointer">
                  <input type="checkbox" value="" :id="setting.id + '-' + index" class="sr-only peer"
                         :checked="notification.on">
                  <div
                      class="w-11 h-6 bg-neutral-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-primary-200 peer-checked:after:tranneutral-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-0.5 after:left-[2px] after:bg-white after:border-neutral-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-primary-500"></div>
                  <span class="ml-3 text-sm font-medium text-neutral-500">{{ notification.name }}</span>
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
export default {
  name: "SettingsIndex",
  inject: ["sessionService", "userService"],

  data() {
    return {
      user: null,
      notificationSettings: null,
      password: ''
    }
  },

  async created() {
    this.user = this.sessionService.getCurrentUser();
    this.notificationSettings = await this.userService.findNotificationSettings(this.user.id);
  },
}
</script>