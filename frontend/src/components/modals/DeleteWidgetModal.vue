<template>
  <VueFinalModal
      classes="flex justify-center items-center"
      content-class="relative w-full h-full max-w-sm md:h-auto">

    <div class="relative bg-white rounded-lg shadow-2xl max-h-[80vh]">
      <button @click="closeModal" type="button" class="absolute top-3 right-2.5 text-neutral-400 bg-transparent hover:bg-neutral-200 hover:text-neutral-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center" data-modal-toggle="widget-modal">
        <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
        <span class="sr-only">Close modal</span>
      </button>
      <!-- Modal header -->
      <div class="px-6 py-4 border-b rounded-t">
        <h3 class="text-base font-bold text-neutral-900 lg:text-xl">
         Confirm Delete
        </h3>
      </div>
    <p class="px-5 py-2.5 text-center">Are you sure you want to delete widget <br/> <span class="font-semibold">{{widget?.shipSensor.sensor.name}}</span>?</p>

    <div class="flex justify-center gap-2 p-3">

      <button @click="$emit('close')"
              name="cancelDelete"
              class="text-slate-600 bg-neutral-300 hover:bg-neutral-400 focus:ring-gray-300 focus:ring-4 focus:outline-none font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
      >
        Cancel
      </button>
      <button
          name="confirmDelete"
          class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
          @click="$emit('delete', widget)">
        Yes, delete
      </button>

    </div>
    </div>
    </VueFinalModal>
</template>
<script>

import { VueFinalModal } from 'vue-final-modal'
export default {
  name: "DeleteWidgetModal",
  props:{widget: Object, showDeleteWidgetModal: Boolean},
  components: {VueFinalModal},
  data: () => ({
    showModal: false
  }),
  methods: {
    async closeModal() {
      this.$emit('close');
    }
  }
}
</script>

<style scoped>

</style>
