<template>
  <div :class="(isMenuOpen) ? 'w-[240px]' : 'w-[56px]'"
       class="bg-white h-screen p-4 transition-all duration-500 flex flex-col fixed top-0">

    <div class="flex justify-between my-4">
      <h3 :class="(isMenuOpen) ? '' : ''" class="overflow-x-hidden text-xl text-slate-800 font-bold ">SmartShip</h3>

      <!--Hamburger-->
      <button @click="toggleMenu"
              class="-m-2 p-2 bg-white hover:bg-slate-100 rounded-lg transition-colors text-slate-500">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"/>
        </svg>
      </button>
    </div>

    <!--Menu-->
    <div class="flex flex-col justify-between h-full text-slate-500">
      <ul>
        <li>
          <router-link to="/dashboard"
                       class="flex items-center hover:bg-slate-100 -mx-2 p rounded-lg transition-colors">
            <div class="mr-2">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M3.75 6A2.25 2.25 0 016 3.75h2.25A2.25 2.25 0 0110.5 6v2.25a2.25 2.25 0 01-2.25 2.25H6a2.25 2.25 0 01-2.25-2.25V6zM3.75 15.75A2.25 2.25 0 016 13.5h2.25a2.25 2.25 0 012.25 2.25V18a2.25 2.25 0 01-2.25 2.25H6A2.25 2.25 0 013.75 18v-2.25zM13.5 6a2.25 2.25 0 012.25-2.25H18A2.25 2.25 0 0120.25 6v2.25A2.25 2.25 0 0118 10.5h-2.25a2.25 2.25 0 01-2.25-2.25V6zM13.5 15.75a2.25 2.25 0 012.25-2.25H18a2.25 2.25 0 012.25 2.25V18A2.25 2.25 0 0118 20.25h-2.25A2.25 2.25 0 0113.5 18v-2.25z"/>
              </svg>
            </div>
            <span class="overflow-x-hidden whitespace-nowrap">Dashboard</span>
          </router-link>
        </li>
        <li>
          <router-link to="/dashboard/notifications"
                       class="flex items-center hover:bg-slate-100 -mx-2 p-2 rounded-lg transition-colors">
            <div class="mr-2">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M14.857 17.082a23.848 23.848 0 005.454-1.31A8.967 8.967 0 0118 9.75v-.7V9A6 6 0 006 9v.75a8.967 8.967 0 01-2.312 6.022c1.733.64 3.56 1.085 5.455 1.31m5.714 0a24.255 24.255 0 01-5.714 0m5.714 0a3 3 0 11-5.714 0"/>
              </svg>
            </div>
            <span class="overflow-x-hidden whitespace-nowrap">Notifications</span>
          </router-link>
        </li>
        <li>
          <router-link to="/dashboard/power-usage"
                       class="flex items-center hover:bg-slate-100 -mx-2 p-2 rounded-lg transition-colors">
            <div class="mr-2">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M3.75 13.5l10.5-11.25L12 10.5h8.25L9.75 21.75 12 13.5H3.75z"/>
              </svg>
            </div>
            <span class="overflow-x-hidden whitespace-nowrap">Power usage</span>
          </router-link>
        </li>
        <li>
          <router-link to="/dashboard/information"
                       class="flex items-center hover:bg-slate-100 -mx-2 p-2 rounded-lg transition-colors">
            <div class="mr-2">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M11.25 11.25l.041-.02a.75.75 0 011.063.852l-.708 2.836a.75.75 0 001.063.853l.041-.021M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-9-3.75h.008v.008H12V8.25z"/>
              </svg>
            </div>
            <span class="overflow-x-hidden whitespace-nowrap">Ship information</span>
          </router-link>
        </li>
        <li>
          <router-link to="/dashboard/rating"
                       class="flex items-center hover:bg-slate-100 -mx-2 p-2 rounded-lg transition-colors">
            <div class="mr-2">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M7.5 14.25v2.25m3-4.5v4.5m3-6.75v6.75m3-9v9M6 20.25h12A2.25 2.25 0 0020.25 18V6A2.25 2.25 0 0018 3.75H6A2.25 2.25 0 003.75 6v12A2.25 2.25 0 006 20.25z"/>
              </svg>
            </div>
            <span class="overflow-x-hidden whitespace-nowrap">Rating</span>
          </router-link>
        </li>
      </ul>

      <ul>
        <li>
          <router-link to="/dashboard/profile"
                       class="flex items-center hover:bg-slate-100 -mx-2 p-2 rounded-lg transition-colors">
            <div class="mr-2">
              <div class="h-[24px] w-[24px] bg-slate-200 rounded-full animate-pulse"></div>
            </div>
            <span class="overflow-x-hidden whitespace-nowrap">Profile</span>
          </router-link>
        </li>

        <li>
          <router-link to="/dashboard/settings"
                       class="flex items-center hover:bg-slate-100 -mx-2 p-2 rounded-lg transition-colors">
            <div class="mr-2">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M10.343 3.94c.09-.542.56-.94 1.11-.94h1.093c.55 0 1.02.398 1.11.94l.149.894c.07.424.384.764.78.93.398.164.855.142 1.205-.108l.737-.527a1.125 1.125 0 011.45.12l.773.774c.39.389.44 1.002.12 1.45l-.527.737c-.25.35-.272.806-.107 1.204.165.397.505.71.93.78l.893.15c.543.09.94.56.94 1.109v1.094c0 .55-.397 1.02-.94 1.11l-.893.149c-.425.07-.765.383-.93.78-.165.398-.143.854.107 1.204l.527.738c.32.447.269 1.06-.12 1.45l-.774.773a1.125 1.125 0 01-1.449.12l-.738-.527c-.35-.25-.806-.272-1.203-.107-.397.165-.71.505-.781.929l-.149.894c-.09.542-.56.94-1.11.94h-1.094c-.55 0-1.019-.398-1.11-.94l-.148-.894c-.071-.424-.384-.764-.781-.93-.398-.164-.854-.142-1.204.108l-.738.527c-.447.32-1.06.269-1.45-.12l-.773-.774a1.125 1.125 0 01-.12-1.45l.527-.737c.25-.35.273-.806.108-1.204-.165-.397-.505-.71-.93-.78l-.894-.15c-.542-.09-.94-.56-.94-1.109v-1.094c0-.55.398-1.02.94-1.11l.894-.149c.424-.07.765-.383.93-.78.165-.398.143-.854-.107-1.204l-.527-.738a1.125 1.125 0 01.12-1.45l.773-.773a1.125 1.125 0 011.45-.12l.737.527c.35.25.807.272 1.204.107.397-.165.71-.505.78-.929l.15-.894z"/>
                <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
              </svg>
            </div>
            <span class="overflow-x-hidden whitespace-nowrap">Settings</span>
          </router-link>
        </li>
        <li class="-mr-4">
          <button @click="signOut"
              class="flex items-center hover:bg-slate-100 -mx-2 -mr-4 p-2 rounded-lg transition-colors w-full">
            <div class="mr-2">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M15.75 9V5.25A2.25 2.25 0 0013.5 3h-6a2.25 2.25 0 00-2.25 2.25v13.5A2.25 2.25 0 007.5 21h6a2.25 2.25 0 002.25-2.25V15m3 0l3-3m0 0l-3-3m3 3H9"/>
              </svg>
            </div>
            <span class="overflow-x-hidden whitespace-nowrap">Check out</span>
          </button>
        </li>
       
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "SidebarComponent",
  inject: ["sessionService"],
  emits: [
    'isMenuOpen'
  ],
  data() {
    return {
      isMenuOpen: true,
    }
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
      this.$emit('isMenuOpen', this.isMenuOpen);
    },
    signOut() {
      this.sessionService.signOut();
      this.$router.push("/");
    }
  }
}
</script>