<template>
  <div class="bg-white p-4 rounded-2xl mb-4">
    <h2 class="text-xl font-bold text-neutral-800">Profile</h2>
    <p class="text-neutral-600">Change your personal information to make clear to us and your coworkers who you
      are and how you may be contacted.</p>
    <form class="mt-4" @submit.prevent="updateUserInfo" autocomplete="off">
      <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div class="mb-4">
          <label class="block mb-2 text-sm font-medium text-neutral-900">First name</label>
          <input type="text" v-model.trim="userCopy.firstName"
                 name="firstName"
                 class="border text-sm rounded-lg block w-full p-2.5"
                 :class="(v$.userCopy.firstName.$error) ? errorInputStyle : goodInputStyle"
                 placeholder="Jane" required="">
          <div v-if="v$.userCopy.firstName.$error" class="text-sm text-red-500 pt-2">{{
              v$.userCopy.firstName.$errors[0]?.$message
            }}
          </div>
        </div>
        <div class="mb-4">
          <label class="block mb-2 text-sm font-medium text-neutral-900">Last Name</label>
          <input type="text" v-model.trim="userCopy.lastName"
                 name="lastName"
                 class="border text-sm rounded-lg block w-full p-2.5"
                 :class="(v$.userCopy.lastName.$error) ? errorInputStyle : goodInputStyle"
                 placeholder="Smith" required="">
          <div v-if="v$.userCopy.lastName.$error" class="text-sm text-red-500 pt-2">{{
              v$.userCopy.lastName.$errors[0]?.$message
            }}
          </div>
        </div>
        <div class="mb-4">
          <label class="block mb-2 text-sm font-medium text-neutral-900">Email</label>
          <input type="email" v-model.trim="userCopy.email"
                 name="email"
                 class="border text-sm rounded-lg block w-full p-2.5"
                 :class="(v$.userCopy.email.$error) ? errorInputStyle : goodInputStyle"
                 placeholder="janesmith@mail.com" required="">
          <div v-if="v$.userCopy.email.$error" class="text-sm text-red-500 pt-2">{{
              v$.userCopy.email.$errors[0]?.$message
            }}
          </div>
        </div>
        <div class="mb-4">
          <label class="block mb-2 text-sm font-medium text-neutral-900">Birthday</label>
          <input type="date" v-model.trim="userCopy.birthday"
                 name="birthday"
                 class="border text-sm rounded-lg block w-full p-2.5"
                 :class="(v$.userCopy.birthday.$silentErrors.length !== 0) ? errorInputStyle : goodInputStyle"
                 required="">
          <div v-if="v$.userCopy.birthday.$silentErrors" class="text-sm text-red-500 pt-2">{{
              v$.userCopy.birthday.$silentErrors[0]?.$message
            }}
          </div>
        </div>
      </div>

      <label for="Bio" class="block mb-2 text-sm font-medium text-neutral-900">Bio</label>
      <textarea id="Bio" rows="2" v-model.trim="userCopy.bio"
                name="bio"
                class="block p-2.5 w-full text-sm resize-none rounded-lg border"
                :class="(v$.userCopy.bio.$error) ? errorInputStyle : goodInputStyle"
                placeholder="Tell us about yourself"></textarea>
      <div v-if="v$.userCopy.bio.$error" class="text-sm text-red-500 pt-2">{{
          v$.userCopy.bio.$errors[0]?.$message
        }}
      </div>

      <button type="submit" :disabled="v$.userCopy.$invalid || !userInfoHasChanged"
              class="text-white bg-primary-500 disabled:bg-neutral-300 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center mt-4 transition-colors">
        Save
      </button>
    </form>
  </div>
</template>

<script>
import User from "@/models/User";
import {useVuelidate} from '@vuelidate/core'
import {email, helpers, maxLength, minLength, required} from "@vuelidate/validators";
import {toRaw} from "vue";

export default {
  name: "UserInfoForm",
  inject: ["sessionService", "userService"],
  emits: ['showToast'],
  setup() {
    return {v$: useVuelidate()}
  },

  data() {
    return {
      user: null,
      userCopy: null,
      errorInputStyle: 'bg-red-50 text-red-900 border-red-500 focus:ring-red-500 focus:border-red-500',
      goodInputStyle: 'bg-neutral-50 border-neutral-300 text-neutral-900 focus:ring-primary-500 focus:border-primary-500'
    }
  },

  async created() {
    this.user = this.sessionService.getCurrentUser();
    this.userCopy = User.copyUser(this.user);
  },

  computed: {
    userInfoHasChanged() {
      return !this.user.equals(this.userCopy);
    }
  },

  validations: {
    userCopy: {
      firstName: {
        required,
        minLength: minLength(2),
        maxLength: maxLength(24),
        $autoDirty: true,
      },
      lastName: {
        required,
        minLength: minLength(2),
        $autoDirty: true,
      },
      email: {
        required,
        email,
        $autoDirty: true,
        maxLength: maxLength(32),
      },
      birthday: {
        maxValue: helpers.withMessage('Birthday can\'t be in the future', value => new Date(value) < new Date()),
      },
      bio: {
        $autoDirty: true,
        maxLength: maxLength(256),
      },
    },
  },

  methods: {
    async updateUserInfo() {
      const updatedUser = await this.userService.updateUserInfo(toRaw(this.userCopy));

      if (updatedUser !== null) {
        this.user = updatedUser;
        this.$emit('showToast', 'success', 'User information saved');
      } else {
        this.$emit('showToast', 'error', 'Couldn\'t save information');
      }
    },
  },
}
</script>