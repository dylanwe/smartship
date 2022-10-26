<template>
  <div class="h-screen p-4 lg:p-8 flex justify-evenly">
    <div class="bg-sky-500 hidden sm:block sm:w-1/2 lg:w-1/2 xl:w-1/3 p-8 rounded-2xl h-fit">
      <div class="mb-4 md:mb-12 2xl:mb-16">
        <h3 class="text-lg xl:text-xl 2xl:text-2xl text-white font-bold mb-8 xl:mb-16">SmartShip</h3>
        <h2 class="text-xl xl:text-2xl 2xl:text-4xl text-white font-bold mb-4">Start your journey and enable a
          sustainable maritime future</h2>
        <p class="text-slate-100 text-base">
          At Smart-Ship, we make force-feedback control levers for the maritime industry.
          By bridging the gap in information transfer we aim to enable a sustainable maritime future
          with regard to people, equipment and the environment.
        </p>
      </div>

      <div>
        <div class="block p-5 max-w bg-sky-700 rounded-xl mb-4 md:mb-8 2xl:mb-12">
          <p class="text-sm md:text-base font-normal text-slate-200 mb-2">
            Our maritime control levers enable operators to sail environmentally friendly,
            by transferring the vessel’s optimal speed, acceleration rate and rate of turn,
            without requiring visual feedback.
          </p>
        </div>
      </div>

    </div>
    <div class="w-full sm:w-1/2 lg:w-1/3 2xl:w-1/4 p-8">
      <h3 class="text-2xl text-slate-800 font-bold">Sign in</h3>
      <p class="text-md text-slate-500 mb-4">Login to get started</p>
      <form @submit="login()" @submit.prevent>
        <div class="mb-6">
          <label for="email" class="block mb-1 text-sm font-medium text-slate-500">E-mail</label>
          <input type="email" id="email"
                 class="peer/email bg-white border border-2 border-slate-200 text-slate-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="email">
        </div>
        <div class="mb-1">
          <label for="password" class="block mb-1 text-sm font-medium text-slate-500">Password</label>
          <input type="password" id="password"
                 class="bg-white border border-2 border-slate-200 text-slate-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="password">
        </div>
        <div class="mb-8">
          <router-link to="/resetPassword" class="text-sm font-medium text-sky-500 cursor-pointer">Reset Password
          </router-link>
        </div>
        <button type="submit"
                class="text-white bg-sky-500 hover:bg-sky-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors">
          Login
        </button>
      </form>
      <p class="hidden mt-5 text-sm font-medium text-rose-500" id="error">Incorrect email or password
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "LoginComponent",
  inject: ["sessionService"],

  data() {
    return {
      email: "",
      password: "",
    }
  },

  methods: {
    async login() {
      await this.sessionService.signIn(this.email, this.password);

      // redirect to dashboard if user is authenticated
      if (this.sessionService.getLoggedIn()) {
        this.$router.push('/dashboard');
      } else {
        document.getElementById('error').classList.remove('hidden');
      }
    },
  }
}
</script>