<template>
  <vue-final-modal
      classes="flex justify-center items-center"
      content-class="relative flex flex-col max-h-full mx-4 p-6 border rounded-md bg-white bg-neutral-50">
    <span class="mr-8 text-2xl font-bold">Add an account</span>
    <p class="text-xl mb-6">Fill in the necessary information to add a new account.</p>

    <form @submit="closeModal()" @submit.prevent>

      <div class="flex justify-between">
        <div class="mb-6 w-full mr-4">
          <label for="firstName" class="block mb-1 text-sm font-medium text-neutral-500">First name</label>
          <input type="text" id="firstName"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="firstName">
        </div>
        <div class="mb-6 w-full">
          <label for="lastName" class="block mb-1 text-sm font-medium text-neutral-500">Last name</label>
          <input type="text" id="lastName"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="lastName">
        </div>
      </div>

      <div class="flex justify-between">
        <div class="mb-6 w-full mr-4">
          <label for="email" class="block mb-1 text-sm font-medium text-neutral-500">E-mail</label>
          <input type="email" id="email"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="email">
        </div>
        <div class="mb-6 w-full">
          <label for="password" class="block mb-1 text-sm font-medium text-neutral-500">Password</label>
          <input type="password" id="password"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="password">
        </div>
      </div>

      <div class="flex">
        <div class="mb-6 w-full">
          <label for="shipSelect" class="block mb-1 text-sm font-medium text-neutral-500">Assign a ship</label>
          <select id="shipSelect"
                  class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                  required v-model="assignedShip">
            <option value="" disabled selected>Choose a ship</option>
            <option v-for="ship in ships" :key="ship.id" :value="ship.smartShipId">
              {{ ship.name }}
            </option>
          </select>
        </div>
      </div>

      <div class="flex">
        <button type="submit" @click="$emit('add', this.email, this.firstName, this.lastName, this.password, this.assignedShip)"
                class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors mr-6"
                >
          Add account
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
import { VueFinalModal } from 'vue-final-modal'

export default {
  name: "AddOperatorModal",
  inject: ['shipService'],
  components: { VueFinalModal },

  async created() {
    this.ships = await this.shipService.getAllShips();
  },
  data() {
    return {
      ships: [],
      email: "",
      firstName: "",
      lastName: "",
      password: "",
      assignedShip: "",
    }
  },

  methods: {
    async closeModal() {
      this.$emit('close');
    }
  }

}
</script>

<style scoped>

</style>