<template>
  <vue-final-modal
      classes="flex justify-center items-center"
      content-class="relative flex flex-col max-h-full mx-4 p-6 border rounded-md bg-white bg-neutral-50">

    <div v-if="this.operators.length !== 0" class="overflow-x-auto relative mb-6">
      <table class="w-full text-sm text-left text-neutral-700">
        <caption class="p-2 text-neutral-700 bg-neutral-50 text-left">
          <span class="mr-8 text-2xl font-bold">All operators</span>
          <p class="text-xl mb-4">These are all the operators for this ship.</p>
        </caption>
        <thead class="text-sm text-neutral-900 uppercase bg-neutral-50 border-t-2 border-neutral-200">
        <tr>
          <th scope="col" class="py-3 px-6">
            NAME
          </th>
          <th scope="col" class="py-3 px-6">
            EMAIL ADDRESS
          </th>
          <th scope="col" class="py-3 px-6">
            SHIP
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="operator in operators" :key="operator.id" class="bg-neutral-50 text-base hover:bg-neutral-100">
          <th scope="row" class="flex items-center py-4 px-6 text-neutral-700 whitespace-nowrap">
            <img class="w-10 h-10 rounded-full" src="@/assets/img/profile_picture.jpeg" alt="Profile image">
            <div class="pl-3">
              <div class="text-base font-semibold">{{ operator.firstName }} {{ operator.lastName }}</div>
            </div>
          </th>
          <td class="py-4 px-6">
            {{ operator.email }}
          </td>
          <td class="py-4 px-6">
            {{ operator.ship?.name }}
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else>
      <span class="mr-8 text-2xl font-bold">All operators</span>
      <p class="text-xl mb-6">There are no operators assigned to this ship.</p>
    </div>

    <button
        class="text-neutral-600 bg-neutral-300 hover:bg-neutral-400 hover:text-neutral-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
        @click="$emit('close')">
      Close
    </button>

  </vue-final-modal>
</template>

<script>
import { VueFinalModal } from 'vue-final-modal'

export default {
  name: "showOperatorsForShipModal",
  inject: ['userManagementService'],
  components: { VueFinalModal },
  props: ["operatorsForShip"],

  data() {
    return {
      operators: [],
    }
  },
  watch: {
    async 'operatorsForShip'() {
      this.operators = await this.userManagementService.getOperatorsForShip(this.operatorsForShip);
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