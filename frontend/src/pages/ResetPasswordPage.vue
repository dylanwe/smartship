<template>
  <div class="h-screen p-4 lg:p-8 flex justify-evenly">
    <div class="w-full sm:w-1/2 lg:w-1/3 p-8">
      <h3 class="text-2xl text-neutral-800 font-bold">Forgot password</h3>
      <p class="text-md text-neutral-500 mb-4">We will send you reset instructions</p>
      <form @submit.prevent>
        <div class="mb-6">
          <label for="email" class="block mb-1 text-sm font-medium text-neutral-500">E-mail</label>
          <input type="email" id="email"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="email">
        </div>
        <button type="button" @click="resetPassword(this.email)"
                class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors">
          Reset password
        </button>
      </form>
      <p class="hidden mt-5 text-sm font-medium text-rose-500" id="error">No user exist with the given email</p>
      <div class="mb-8 mt-6">
        <router-link to="/" class="text-sm font-medium text-primary-500 cursor-pointer flex">
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
import emailjs from "emailjs-com";

export default {
  name: "ResetPasswordComponent",
  inject: ['userManagementService'],
  data() {
    return {
      email: "",
    }
  },
  methods: {
    async resetPassword(email) {

      //Make a regex object that is used to check if the given email is valid.
      const emailRegex =
          new RegExp(/^[A-Za-z0-9_!#$%&'*+/=?`{|}~^.-]+@[A-Za-z0-9.-]+$/, "gm");

      //Check if the email is valid.
      if (emailRegex.test(email)) {
        let error = document.getElementById('error');

        //Find the user with the given email
        const foundUser = await this.userManagementService.findUserByEmail(email)

        //Show an error message if no user was found
        if (foundUser == null) {
          error.classList.remove('hidden');
        } else {
          error.classList.add('hidden');

          //Create a random password
          const randomPassword = Math.random().toString().substring(2, 8);

          //Update the user with the random password in the backend
          const updatedUser = await this.userManagementService.updateUserPassword(foundUser.id, randomPassword)

          let emailParams = {
            name: updatedUser.firstName,
            email: updatedUser.email,
            password: randomPassword
          };

          // Send an email to the found user.
          await emailjs.send("service_i66vivu", "template_21wt47x", emailParams, "LB6axeycasCvaughh")
              .then(function(response) {
                console.log('SUCCESS!', response.status, response.text);
              }, function(error) {
                console.log('FAILED...', error);
              });
        }

      }
    },
  }
}

</script>