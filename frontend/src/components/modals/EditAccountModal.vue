<template>
  <vue-final-modal
      v-if="this.copyUser != null"
      classes="flex justify-center items-center"
      content-class="relative flex flex-col max-h-full mx-4 p-6 border rounded-md bg-white bg-neutral-50">
    <span class="mr-8 text-2xl font-bold">Edit an account</span>
    <p class="text-xl mb-6">Fill in the necessary information to edit a account.</p>

    <form @submit="closeModal()" @submit.prevent>

      <div class="flex justify-between">
        <div class="mb-6 w-full mr-4">
          <label for="firstName" class="block mb-1 text-sm font-medium text-neutral-500">First name</label>
          <input type="text" id="firstName" name="firstName"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="this.copyUser.firstName">
        </div>
        <div class="mb-6 w-full">
          <label for="lastName" class="block mb-1 text-sm font-medium text-neutral-500">Last name</label>
          <input type="text" id="lastName"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="this.copyUser.lastName">
        </div>
      </div>

      <div class="flex justify-between">
        <div class="mb-6 w-full">
          <label for="email" class="block mb-1 text-sm font-medium text-neutral-500">E-mail</label>
          <input type="email" id="email"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="this.copyUser.email">
        </div>
        <div v-if="this.userRole === 'Manager'" class="mb-6 w-full ml-4">
          <label for="shipSelect" class="block mb-1 text-sm font-medium text-neutral-500">Assign a ship</label>
          <select id="shipSelect"
                  class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                  required v-model="newAssignedShip">
            <option v-for="ship in ships" :key="ship.id" :value="ship.smartShipId">
              {{ ship.name }}
            </option>
          </select>
        </div>
      </div>

      <div class="flex gap-2">
        <button v-if="this.userRole === 'Manager'" type="submit" @click="saveAccount(this.copyUser, this.newAssignedShip)"
                class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors "
        >
          Save
        </button>
        <button v-else type="submit" @click="saveAccount(this.copyUser)"
                class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors "
        >
          Save
        </button>
        <button
            class="text-neutral-600 bg-neutral-300 hover:bg-neutral-400 hover:text-neutral-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
            @click="$emit('close')" formnovalidate>
          Cancel
        </button>
      </div>

    </form>

  </vue-final-modal>
</template>

<script>
import {VueFinalModal} from 'vue-final-modal'
import User from "@/models/User";

export default {
  name: "EditAccountModal",
  components: {VueFinalModal},
  inject: ['sessionService', 'shipService'],
  props: ["toEditUser"],

  async created() {
    this.userRole = this.sessionService.getCurrentUser().role;
  },
  data() {
    return {
      ships: [],
      copyUser: null,
      email: "",
      firstName: "",
      lastName: "",
      assignedShip: "",
      newAssignedShip: "",
      userRole: "",
    }
  },
  watch: {
    async 'toEditUser'() {
      this.ships = await this.shipService.getAllShips();

      this.copyUser = Object.assign(new User(), this.toEditUser);
      this.assignedShip = this.toEditUser.ship?.smartShipId;
      this.newAssignedShip = this.toEditUser.ship?.smartShipId;
    }
  },
  methods: {
    async closeModal() {
      this.$emit('close');
    },
    async saveAccount() {
      if (!this.copyUser.equals(this.toEditUser) || this.newAssignedShip !== this.assignedShip
          && this.userRole === 'Manager') {
        this.$emit('save', this.copyUser, this.newAssignedShip)
      }

      if (!this.copyUser.equals(this.toEditUser) && this.userRole === 'Admin') {
        this.$emit('save', this.copyUser)
      }

    }
  }

}
</script>

<style scoped>

</style>