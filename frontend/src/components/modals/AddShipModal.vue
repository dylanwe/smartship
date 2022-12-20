<template>
  <vue-final-modal
      classes="flex justify-center items-center"
      content-class="relative flex flex-col max-h-full mx-4 p-6 border rounded-md bg-white bg-neutral-50">
    <span class="mr-8 text-2xl font-bold">Add a ship</span>
    <p class="text-xl mb-6">Fill in the necessary information to add a new ship.</p>

    <form @submit="closeModal()" @submit.prevent>

      <div class="flex justify-between flex-col">
        <div class="mb-6 w-full mr-4">
          <label for="name" class="block mb-1 text-sm font-medium text-neutral-500">Name</label>
          <input type="text" id="name"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="name">
        </div>
        <div class="mb-6 w-full">
          <label for="shipId" class="block mb-1 text-sm font-medium text-neutral-500">ship id</label>
          <input type="text" id="shipId"
                 class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                 required v-model="shipId">
        </div>
      </div>

      <div class="flex">
        <button type="submit" @click="$emit('add', this.name, this.shipId)"
                class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors mr-6"
                >
          Add Ship
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
  name: "AddShipModal",
  components: { VueFinalModal },

  data() {
    return {
      name: "",
      shipId: "",
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