<template>
  <div class="h-screen p-4 lg:p-8 flex justify-evenly">
    <div class="w-full sm:w-1/2 lg:w-1/3 p-8">
      <h3 class="text-2xl text-slate-800 font-bold">Forgot password</h3>
      <p class="text-md text-slate-500 mb-4">We will send you reset instructions</p>
      <form>
        <div class="mb-6">
          <label for="email" class="block mb-1 text-sm font-medium text-slate-500">E-mail</label>
          <input type="email" id="email"
                 class="bg-white border border-2 border-slate-200 text-slate-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required>
        </div>
        <button type="submit"
                class="text-white bg-sky-500 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">
          Reset password
        </button>
      </form>
      <div class="mb-8 mt-6">
        <router-link to="/" class="text-sm font-medium text-sky-500 cursor-pointer flex">
          <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24"
               xmlns="http://www.w3.org/2000/svg">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M10 19l-7-7m0 0l7-7m-7 7h18"></path>
          </svg>
          <p>Back to log in</p>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ResetPasswordComponent"
}
</script>