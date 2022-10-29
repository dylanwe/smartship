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
                        @click="addWidget(widget)">{{ widget.icon }} {{ widget.title }}</td>
                </tr>
            </tbody>
            <span v-else>Sorry, we couldn't find any results...</span>
        </table>

    </div>
</template>
<script>

export default {
    name: "WidgetLibrary",
    props: {
        showWidgetbar: Boolean
    },
    emits: ['addWidget', 'closeWidgetMenu'],
    data() {
        // Temp roles
        const ROLES = { "ADMIN": "Admin" }

        return {
            search: null,
            data: [
                // { icon: "📈", title: "Line Chart", component: "WidgetLine", config: {}, restrictTo: [ROLES.ADMIN] },
                {
                    icon: "🔢", title: "Battery Temperature", component: "WidgetTemperature", config: {
                        minHeight: 2,
                        height: 2
                    }, data: {
                        title: "Batteries",
                        items: [{ name: "Pack 1", maxTemperature: 80 }, { name: "Pack 2", maxTemperature: 80 }],
            
                    },
                    restrictTo: [ROLES.ADMIN]
                },
                {
                    icon: "📈", title: "Battery Levels", component: "BatteryLevel", config: {
                        minHeight: 2,
                        height: 2,
                        minWidth: 2,
                        width: 2
                    }, data:{
                        dataSet: [{ x: '8:00', y: 9 }, { x: '9:00', y: 3 }, { x: '10:00', y: 18 }, { x: '11:00', y: 5 }, { x: '12:00', y: 8 }]
                    }
                },


                {
                    icon: "🔢", title: "Engine Temperature", component: "WidgetTemperature", config: {
                        minHeight: 2,
                        height: 2
                    }, data: {
                        title: "Engines",
                        items: [{ name: "Engine 1", maxTemperature: 80 }, { name: "Engine 2", maxTemperature: 80 }],

                    }
                },
                {
                    icon: "📈", title: "Engine 1 Usage", component: "BigLineChart", config: {
                        width: 3,
                        height: 4
                    }, data: {
                        title: "Engine 1 Usage", dataSet: [{ x: '8:00', y: 9 }, { x: '9:00', y: 3 }, { x: '10:00', y: 18 }, { x: '11:00', y: 5 }, { x: '12:00', y: 8 }]
                    }
                },
                {
                    icon: "📈", title: "Engine 2 Usage", component: "BigLineChart", config: {
                        width: 3,
                        height: 4
                    }, data: { title: "Engine 2 Usage", dataSet: [{ x: '8:00', y: 9 }, { x: '9:00', y: 12 }, { x: '10:00', y: 8 }, { x: '11:00', y: 5 }, { x: '12:00', y: 8 }] }
                },

            ],
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
                let filtered = this.data.filter(({ title }) =>
                    title.toLowerCase().includes(this.search)
                );
                return filtered.length ? filtered : null
            } else {
                return this.data;
            }
        }
    }
}
</script>
<style scoped>

</style>