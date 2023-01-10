<template >
  <div v-if="showWidgetbar" id="widget-modal" tabindex="-1" aria-hidden="true" class="bg-slate-800 bg-opacity-40 fixed top-0 left-0 right-0 z-50 w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-full">
    <div class="relative w-full h-full max-w-sm md:h-auto ml-auto mt-16 mr-6">
      <!-- Modal content -->
      <div class="relative bg-white rounded-lg shadow-2xl max-h-[80vh]">
        <button @click="closeMenu" type="button" class="absolute top-3 right-2.5 text-neutral-400 bg-transparent hover:bg-neutral-200 hover:text-neutral-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center" data-modal-toggle="widget-modal">
          <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
          <span class="sr-only">Close modal</span>
        </button>
        <!-- Modal header -->
        <div class="px-6 py-4 border-b rounded-t">
          <h3 class="text-base font-bold text-neutral-900 lg:text-xl">
            Pick a new widget
          </h3>
        </div>
        <!-- Modal body -->
        <div class="p-6 pt-2">
          <p class="text-sm font-normal text-neutral-500 mb-4">Select widgets to view various statistics of your ship You can also view the ship's history of performance and maintenance records through these widgets. </p>
          <label for="simple-search" class="sr-only">Search</label>
          <div class="relative w-full">
            <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
              <svg aria-hidden="true" class="w-5 h-5 text-neutral-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
            </div>
            <input v-model="search" type="text" id="simple-search" class="bg-neutral-50 border border-neutral-300 text-neutral-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5" placeholder="Search" required>
          </div>

          <ul class="my-4 space-y-3 w-full max-h-[35vh] overflow-y-scroll" v-if="filteredSearch">
            <li class="w-full" v-for="(widget, index) in filteredSearch" :key="index">
              <button @click="addWidget(widget)" class="flex w-full items-center p-3 text-base font-bold text-neutral-900 rounded-lg bg-neutral-50 hover:bg-neutral-100 group hover:shadow ">
                <!-- FIXME add icons-->
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                  <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 18L9 11.25l4.306 4.307a11.95 11.95 0 015.814-5.519l2.74-1.22m0 0l-5.94-2.28m5.94 2.28l-2.28 5.941" />
                </svg>
                <span class="flex-1 ml-3 text-left whitespace-nowrap">{{ widget.sensor.name }}</span>
                <!--      Determine if widget already exists in layout          -->
                <span class=" inline-flex items-center justify-center px-2 py-0.5 ml-3 text-xs font-medium text-neutral-500 bg-neutral-200 rounded "
                      :class="{'hidden': !layout.find(x=> x.shipSensor.sensor.name === widget.sensor.name)}">Active</span>
              </button>
            </li>
          </ul>
          <div>
            <span class="inline-flex items-center text-xs font-normal text-neutral-500">
              <svg class="w-3 h-3 mr-2" focusable="false" data-prefix="far" data-icon="question-circle" role="img" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path fill="currentColor" d="M256 8C119.043 8 8 119.083 8 256c0 136.997 111.043 248 248 248s248-111.003 248-248C504 119.083 392.957 8 256 8zm0 448c-110.532 0-200-89.431-200-200 0-110.495 89.472-200 200-200 110.491 0 200 89.471 200 200 0 110.53-89.431 200-200 200zm107.244-255.2c0 67.052-72.421 68.084-72.421 92.863V300c0 6.627-5.373 12-12 12h-45.647c-6.627 0-12-5.373-12-12v-8.659c0-35.745 27.1-50.034 47.579-61.516 17.561-9.845 28.324-16.541 28.324-29.579 0-17.246-21.999-28.693-39.784-28.693-23.189 0-33.894 10.977-48.942 29.969-4.057 5.12-11.46 6.071-16.666 2.124l-27.824-21.098c-5.107-3.872-6.251-11.066-2.644-16.363C184.846 131.491 214.94 112 261.794 112c49.071 0 101.45 38.304 101.45 88.8zM298 368c0 23.159-18.841 42-42 42s-42-18.841-42-42 18.841-42 42-42 42 18.841 42 42z"></path></svg>
              A widget may have multiple chart types</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

export default {
    name: "WidgetLibrary",
    inject: ["shipService", "sessionService"],
    props: {
        showWidgetbar: Boolean,
        layout: Array
    },
    emits: ['addWidget', 'closeWidgetMenu'],

    async created() {
      const {shipId} = this.sessionService.getCurrentUser();
      this.widgets = await this.shipService.getSensors(shipId);
    },

    data() {
        return {
            search: null,
            widgets: []
        }
    },
    methods: {
        addWidget(widget) {
            this.$emit('addWidget', widget)
        },
        closeMenu() {
            this.$emit('closeWidgetMenu')
        }
    },
    computed: {
        // Search widget filter
        filteredSearch() {
            if (this.search) {
                let filtered = this.widgets.filter(({ sensor: {name} }) =>
                    name.toLowerCase().includes(this.search)
                );
                return filtered.length ? filtered : null
            } else {
                return this.widgets;
            }
        }
    }
}
</script>
<style scoped>

</style>