<template>
  <VueFinalModal
      v-if="ship"
      classes="flex justify-center items-center"
      content-class="relative w-full h-full max-w-sm md:h-auto">

    <div class="relative bg-white rounded-lg shadow-2xl max-h-[80vh] px-6">
      <button @click="closeModal" type="button"
              class="absolute top-3 right-2.5 text-neutral-400 bg-transparent hover:bg-neutral-200 hover:text-neutral-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center"
              data-modal-toggle="widget-modal">
        <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
             xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                clip-rule="evenodd"></path>
        </svg>
        <span class="sr-only">Close modal</span>
      </button>
      <!-- Modal header -->
      <div class="py-4 border-b rounded-t">
        <h3 class="text-base font-bold text-neutral-900 lg:text-xl">
          {{ ship.name }}'{{ ['c', 'x', 's'].includes(ship.name.slice(-1).toLowerCase()) ? '' : 's' }} Sensors
          Thresholds
        </h3>
      </div>

      <div class="py-2 flex flex-col gap-3">
        <div>
          <label for="shipSelect" class="block mb-1 font-medium text-neutral-500">Select a sensor</label>
          <select id="shipSelect"
                  class="bg-white border border-2 border-neutral-200 text-neutral-700 rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                  required v-model="selectedSensor">
            <option v-for="sensor in sensors" :key="sensor.id" :value="sensor">
              {{ sensor.sensor.name }}
            </option>
          </select>
        </div>

        <div class="flex flex-row gap-3">
          <div>
            <label for="min" class="block mb-1 text-sm font-medium text-neutral-500">Minimum</label>
            <input type="number" id="min"
                   class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                   required v-model="this.min">
          </div>

          <div>
            <label for="max" class="block mb-1 text-sm font-medium text-neutral-500">Maximum</label>
            <input type="number" id="max"
                   class="bg-white border border-2 border-neutral-200 text-neutral-700 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                   required v-model="this.max">
          </div>
        </div>

      </div>


      <div class="flex justify-start gap-2 py-3">
        <button
            class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
            @click="updateSensor">
          Save
        </button>

        <button @click="$emit('close')"
                class="text-slate-600 bg-neutral-300 hover:bg-neutral-400 focus:ring-gray-300 focus:ring-4 focus:outline-none font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
        >
          Cancel
        </button>


      </div>
    </div>
  </VueFinalModal>

</template>

<script>
import {VueFinalModal} from 'vue-final-modal'

export default {
  name: "EditShipSensorsModal",
  components: {VueFinalModal},
  inject: ["sessionService", 'shipService'],
  props: {
    ship: Object

  },

  data() {
    return {
      selectedSensor: null,
      sensors: [],
      min: null,
      max: null
    }
  },

  emits: ["close"],
  async created() {
    this.user = this.sessionService.getCurrentUser();
  },

  methods: {
     closeModal() {
      this.$emit('close');
    },

    /**
     * Update sensor's min and max threshold values
     */
    async updateSensor() {
      // check if min threshold is string or number
      this.selectedSensor.minThreshold = typeof this.min === 'string' ? null : this.min;
      // check if max threshold is string or number
      this.selectedSensor.maxThreshold = typeof this.max === 'string' ? null : this.max;

      // update sensor in the ship service
      await this.shipService.updateSensor(this.selectedSensor?.id, this.selectedSensor)

      // close the modal
      this.closeModal();
    }

  },
  watch: {
    async 'ship'() {
      this.sensors = await this.shipService.getSensors(this.ship?.id)
    },
    'selectedSensor'(selected) {
      const {minThreshold, maxThreshold} = selected;
      this.min = minThreshold;
      this.max = maxThreshold;
    },
  }

}
</script>

