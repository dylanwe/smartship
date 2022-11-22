<template>
  <!-- Privacy -->
  <div class="bg-white p-4 rounded-2xl mb-4">
    <h2 class="text-xl font-bold text-neutral-800">Privacy</h2>
    <p class="text-neutral-600">Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
      mollit anim id est
      laborum.</p>
    <form class="mt-4" autocomplete="off" @submit.prevent="updateUserPassword">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div class="mb-4">
          <label class="block mb-2 text-sm font-medium text-neutral-900">Current Password</label>
          <input type="password"
                 class="block p-2.5 w-full text-sm resize-none rounded-lg border"
                 :class="(v$.password.$error) ? errorInputStyle : goodInputStyle"
                 placeholder="••••••••" required="" v-model.trim="password">
          <div v-if="v$.password.$error" class="text-sm text-red-500 pt-2">{{
              v$.password.$errors[0]?.$message
            }}</div>
        </div>
        <div class="mb-4">
          <label class="block mb-2 text-sm font-medium text-neutral-900">New Password</label>
          <input type="password"
                 class="block p-2.5 w-full text-sm resize-none rounded-lg border"
                 :class="(v$.newPassword.$error) ? errorInputStyle : goodInputStyle"
                 placeholder="" required="" v-model.trim="newPassword">
          <div v-if="v$.newPassword.$error" class="text-sm text-red-500 pt-2">{{
              v$.newPassword.$errors[0]?.$message }} </div>
        </div>
      </div>
      <button type="submit" :disabled="v$.$validationGroups.passwordGroup.$invalid"
              class="text-white bg-primary-500 disabled:bg-neutral-300 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center mt-2 transition-colors">
        Save
      </button>
    </form>
  </div>
</template>

<script>
import { useVuelidate } from '@vuelidate/core'
import { required, minLength } from '@vuelidate/validators'

export default {
  name: "PasswordForm",
  inject: ["userService"],
  emits: ['showToast'],
  setup() {
    return {v$: useVuelidate()}
  },

  data() {
    return {
      password: '',
      newPassword: '',
      errorInputStyle: 'bg-red-50 text-red-900 border-red-500 focus:ring-red-500 focus:border-red-500',
      goodInputStyle: 'bg-neutral-50 border-neutral-300 text-neutral-900 focus:ring-primary-500 focus:border-primary-500'
    }
  },

  validations: {
      password: {
        required,
        minlength: minLength(6),
        $autoDirty: true,
      },
      newPassword: {
        required,
        minLength: minLength(6),
        $autoDirty: true,
      },
      $validationGroups: {
        passwordGroup: ['password', 'newPassword']
      }
  },

  methods: {
    async updateUserPassword() {
      const response = await this.userService.updateUserPassword(
          this.password,
          this.newPassword
      );

      // empty inputs
      this.password = "";
      this.newPassword = "";

      // handle response
      if (response.ok) {
        this.$emit('showToast', 'succes', 'New password saved');
      } else {
        const data = await response.json();
        this.$emit('showToast', 'error', data.message);
      }
    },
  },
}
</script>