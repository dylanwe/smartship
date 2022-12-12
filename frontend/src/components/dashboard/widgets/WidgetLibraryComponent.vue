<template >
    <div v-if="showWidgetbar"
        class="bg-white px-2 h-[55%] transition-all duration-500 flex flex-col fixed top-0 w-[400px] right-0 z-10 border-l-2 select-none rounded-xl">
        <!-- Header -->
        <div class="flex justify-between py-5">
            <h1 class="text-xl font-semibold">Widget Library</h1>
            <span class=" text-red-700 text font-semibold bg-red-300 px-4 rounded-lg hover:bg-red-400" @click="closeMenu">X</span>
        </div>

        <!-- Search bar -->
        <div>
            <div class="relative w-full">
                <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                    <svg aria-hidden="true" class="w-5 h-5 text-gray-500 " fill="currentColor" viewBox="0 0 20 20"
                        xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd"
                            d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                            clip-rule="evenodd"></path>
                    </svg>
                </div>
                <input v-model="search" type="text"
                    class="bg-gray-50 border border-gray-300 text-neutral-500 text-sm rounded-lg block w-full pl-10 p-2.5 "
                    placeholder="Search widget" required>
            </div>
        </div>

        <table class="mt-3 border-gray-600  border-separate border-spacing-2 px-4">
            <tbody v-if="filteredSearch">
                <tr v-for="(widget, index) in filteredSearch" :key="index">
                    <td class="bg-gray-300 hover:bg-gray-400 rounded-lg p-3 cursor-pointer"
                        @click="addWidget(widget)">{{ widget.sensor.name }}</td>
                </tr>
            </tbody>
            <span v-else>Sorry, we couldn't find any results...</span>
        </table>

    </div>
</template>
<script>

export default {
    name: "WidgetLibrary",
    inject: ["shipService" ],
    props: {
        showWidgetbar: Boolean
    },
    emits: ['addWidget', 'closeWidgetMenu'],

    async created() {
      this.widgets = await this.shipService.getSensors(5);
    },

    data() {
      // Temp roles
      // const ROLES = { "ADMIN": "Admin" }
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