<template>
  <div class="mt-6 max-w-4xl mx-auto">
    <h1 class="text-4xl text-slate-800 font-bold mb-4">Settings</h1>

    <!-- Profile -->
    <div class="bg-white p-4 rounded-2xl mb-4">
      <h2 class="text-xl font-bold text-slate-800">Profile</h2>
      <p class="text-slate-600">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est
        laborum.</p>
      <form class="mt-4">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-slate-900">First name</label>
            <input type="text" v-if="user" v-model="user.firstName"
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   placeholder="Jane" required="">
            <input type="text" v-else
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   placeholder="Jane" required="">
          </div>
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-slate-900">Last Name</label>
            <input type="text" v-if="user" v-model="user.lastName"
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   placeholder="Smith" required="">
            <input type="text" v-else
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   placeholder="Jane" required="">
          </div>
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-slate-900">Email</label>
            <input type="email" v-if="user" v-model="user.email"
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   placeholder="janesmith@mail.com" required="">
            <input type="text" v-else
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   placeholder="Jane" required="">
          </div>
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-slate-900">Birthday</label>
            <input type="date" v-if="user" v-model="user.birthday"
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   required="">
            <input type="text" v-else
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   placeholder="Jane" required="">
          </div>
        </div>

        <label for="Bio" class="block mb-2 text-sm font-medium text-slate-900">Bio</label>
        <textarea id="Bio" rows="2" v-if="user" v-model="user.bio"
                  class="block p-2.5 w-full text-sm resize-none text-slate-900 bg-slate-50 rounded-lg border border-slate-300 focus:ring-sky-500 focus:border-sky-500"
                  placeholder="Tell us about yourself"></textarea>
        <textarea id="Bio" rows="2" v-else
                  class="block p-2.5 w-full text-sm resize-none text-slate-900 bg-slate-50 rounded-lg border border-slate-300 focus:ring-sky-500 focus:border-sky-500"
                  placeholder="Tell us about yourself"></textarea>


        <button type="submit"
                class="text-white bg-sky-500 disabled:bg-slate-300 hover:bg-sky-600 focus:ring-4 focus:outline-none focus:ring-sky-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center mt-4 transition-colors">Save</button>
      </form>
    </div>

    <!-- Privacy -->
    <div class="bg-white p-4 rounded-2xl mb-4">
      <h2 class="text-xl font-bold text-slate-800">Privacy</h2>
      <p class="text-slate-600">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est
        laborum.</p>
      <form class="mt-4">
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-slate-900">Current Password*</label>
            <input type="password"
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   placeholder="••••••••" required="" v-model="password">
          </div>
          <div class="mb-4">
            <label class="block mb-2 text-sm font-medium text-slate-900">New Password</label>
            <input type="password"
                   class="bg-slate-50 border border-slate-300 text-slate-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5"
                   placeholder="" required="">
          </div>
        </div>

        <button type="submit" :disabled="password.length <= 0"
                class="text-white bg-sky-500 disabled:bg-slate-300 hover:bg-sky-600 focus:ring-4 focus:outline-none focus:ring-sky-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center mt-4 transition-colors">Save</button>
      </form>
    </div>

    <!-- Notifications -->
    <div class="bg-white p-4 rounded-2xl mb-4">
      <h2 class="text-xl font-bold text-slate-800">Notifications</h2>
      <p class="text-slate-600">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
        mollit anim id est
        laborum.</p>
      <form v-if="notificationSettings" class="mt-4">
        <div v-for="setting in notificationSettings.notifications" :key="setting.id">
          <h3 class="text-lg text-slate-800 font-bold">{{ setting.name }}</h3>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
            <div>
              <p class="text-slate-600">{{ setting.description }}</p>
            </div>
            <div>
              <div v-for="(notification, index) in setting.options" :key="`${setting.id}-${index}`">
                <label :for="setting.id + '-' + index"
                       class="inline-flex relative items-center mb-4 cursor-pointer">
                  <input type="checkbox" value="" :id="setting.id + '-' + index" class="sr-only peer"
                         :checked="notification.on">
                  <div
                      class="w-11 h-6 bg-slate-200 rounded-full peer peer-focus:ring-4 peer-focus:ring-sky-200 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-0.5 after:left-[2px] after:bg-white after:border-slate-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-sky-500"></div>
                  <span class="ml-3 text-sm font-medium text-slate-500">{{ notification.name }}</span>
                </label>
              </div>
            </div>
          </div>
        </div>

        <button type="submit"
                class="text-white bg-sky-500 hover:bg-sky-600 focus:ring-4 focus:outline-none focus:ring-sky-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">
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