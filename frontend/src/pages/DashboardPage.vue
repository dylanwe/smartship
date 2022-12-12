<template>
  <div class="mt-6">
    <!-- Header -->
    <div class="flex flex-col justify-between mb-4 sm:flex-row">
      <div class="flex flex-row gap-4">
        <h1 class="text-4xl text-neutral-800 font-bold">Dashboard</h1>

        <div class="mt-2">
          <date-picker
              v-model:value="dateRange"
              value-type="timestamp"
              type="datetime"
              range
              placeholder="Select datetime range"
              :show-time-panel="showTimeRangePanel"
              @change="onTimeRangeChange"
              @close="handleRangeClose">
            <template #footer="{ emit }">
              <div class="flex gap-1">
                <div>
                  <button v-for="(date, index) in presetRanges" :key="index"
                          class="mx-btn mx-btn-text" @click="emit(date.range)">
                    {{ date.label }}
                  </button>
                </div>
                <div class="ml-auto bg-sky-200 rounded-lg px-2">
                  <button class="mx-btn mx-btn-text" @click="toggleTimeRangePanel">
                    {{ showTimeRangePanel ? 'select date' : 'select time' }}
                  </button>
                </div>
              </div>
            </template>
          </date-picker>
        </div>


      </div>


      <!-- Edit widgets buttons -->
      <div>
        <div
            v-if="editMode"
            class="flex flex-row gap-1  "
        >
          <button
              class="text-white bg-primary-500 disabled:bg-neutral-300 hover:bg-primary-600 focus:ring-2 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
              @click="toggleWidgetbar"
          >
            Open Widget Library
          </button>
          <button
              class="text-white bg-primary-500 disabled:bg-neutral-300 hover:bg-primary-600 focus:ring-2 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
              @click="saveChanges">
            Save & Exit
          </button>

          <button
              class="text-white bg-gray-400 hover:bg-gray-600 focus:ring-2 focus:outline-none focus:ring-gray-300  rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center font-bold transition-colors"
              @click="cancelChanges">
            Cancel
          </button>
        </div>
        <button v-else
                class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-2 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
                @click="toggleEditMode"
        >
          Edit Panels
        </button>
      </div>
    </div>
    <!-- Widget Library -->
    <WidgetLibraryComponent @addWidget="addWidget" :showWidgetbar="showWidgetbar"
                            @closeWidgetMenu="toggleWidgetbar"/>
    <div class="container select-none">
      <grid-layout v-model:layout="layout"
                   :col-num="numberOfColumns"
                   :row-height="100"
                   :is-draggable="draggable"
                   :is-resizable="resizable"
                   :vertical-compact="true"
                   :use-css-transforms="true"
                   :responsive="false"
                   :maxRows="5"
                   :autoSize="false"

      >
        <!-- Grid Item -->
        <grid-item v-for="(item, index) in layout"
                   :x="item.x"
                   :y="item.y"
                   :w="item.w"
                   :h="item.h"
                   :i="item.i"
                   :key="index"
                   class="bg-white border border-gray-600 rounded-md"
        >
          <!-- Widget Component -->
          <!--:is="item.shipSensor.sensor.widget.componentName"-->
          <component class="h-full w-full" v-if="item.shipSensor"
                     :is="this.tempComponent"
                     :dataSet="item.data"
                     :sensor="item.shipSensor.sensor"/>

          <span v-if="editMode"
                class="z-30 remove absolute right-1 top-1 hover:bg-red-400 bg-red-300 px-4 rounded-lg text-red-700 font-semibold items-center cursor-default"
                @click="removeItem(item.i)">X</span>
        </grid-item>

      </grid-layout>
    </div>
  </div>
</template>

<script>

import {GridLayout, GridItem} from 'vue3-grid-layout';
import LineChart from '../components/charts/LineChart';
import SparkBarChart from '../components/charts/SparkBarChart'
import SparkLineChart from '../components/charts/SparkLineChart'


import WidgetLibraryComponent from '../components/dashboard/widgets/WidgetLibraryComponent.vue';

import BigLineChart from '../components/dashboard/widgets/base/BigLineChart.vue';
import SmallLineChart from '../components/dashboard/widgets/base/SmallLineChart.vue';

import DatePicker from 'vue-datepicker-next';
import 'vue-datepicker-next/index.css';
import ExtractDataSet from "@/utils/ExtractDataSet";

// import { formatDistanceStrict } from 'date-fns'

export default {
  name: "DashboardIndex",
  inject: ["dashboardService", "sessionService"],
  components: {
    DatePicker,
    GridLayout: GridLayout,
    GridItem: GridItem,
    WidgetLibraryComponent
    ,BigLineChart, SmallLineChart,LineChart, SparkBarChart, SparkLineChart
  },


  async created() {
    const {id: dashboardId,layout} = await this.dashboardService.getUserDashboard(this.sessionService.getCurrentUser());
    this.dashboardId = dashboardId;

        this.layout = await Promise.all( layout.map(async (obj) =>  ({...obj, i: this.index++, data: (await this.updateWidgetData(obj))})));
        console.log(this.layout)
  },
  data() {
    return {
      tempComponent: "SmallLineChart",
      dateRange: [new Date(new Date().setDate(new Date().getDate() - 1)).valueOf(), new Date().valueOf()],
      showTimeRangePanel: false,
      presetRanges: [
        {
          label: 'Last 24 hours',
          range: [new Date(new Date().setDate(new Date().getDate() - 1)), new Date()]
        },
        {
          label: 'Last 7 days',
          range: [new Date(new Date().setDate(new Date().getDate() - 7)), new Date()]
        },
        {
          label: 'Last 30 days',
          range: [new Date(new Date().setDate(new Date().getDate() - 30)), new Date()]
        },
        {
          label: 'Last 365 days',
          range: [new Date(new Date().setDate(new Date().getDate() - 365)), new Date()]
        }
      ],
      // Temp stored widgets
      layout: [],
      // Grid options
      numberOfColumns: 5,
      draggable: false,
      resizable: false,

      index: 0,

      // Widget edit
      editMode: false,
      showWidgetbar: false

    }
  },

  methods: {
    ExtractDataSet,
    toggleTimeRangePanel() {
      this.showTimeRangePanel = !this.showTimeRangePanel;
    },
    handleRangeClose() {
      this.showTimeRangePanel = false;
    },

    async updateWidgetData(item){
      const [from,to] =this.dateRange;
        return ExtractDataSet(await this.dashboardService.getWidgetData(item.shipSensor.id, from,to));
    },

    async updateWidgetsData(){
      const [from,to] =this.dateRange;
      for (const item of this.layout) {
        item.data = ExtractDataSet(await this.dashboardService.getWidgetData(item.shipSensor.id, from,to));
      }
      console.log(this.layout)
    },

    async onTimeRangeChange() {
     await this.updateWidgetsData()
    },

    async toggleWidgetbar() {
      this.showWidgetbar = !this.showWidgetbar
    },
    toggleEditMode() {
      this.editMode = !this.editMode;
      this.draggable = !this.draggable;
      this.resizable = !this.resizable;
    },
    cancelChanges() {
      this.toggleEditMode()
    },
    async saveChanges() {
      this.toggleEditMode()
      await this.dashboardService.saveLayout(this.dashboardId, this.layout);
    },

    addWidget(widget) {
      const obj = {
        x: (this.layout.length * 2) % (this.numberOfColumns),
        y: this.layout.length + (this.numberOfColumns), // puts it at the bottom
        w: widget.sensor.widget.defaultWidth,
        h: widget.sensor.widget.defaultHeight,
        shipSensor: widget,
        data: [],
        i: this.index++
      }
      this.layout.push(obj)

    },

    removeItem(val) {
      const index = this.layout.map(item => item.i).indexOf(val);
      if (confirm(`Are you sure you want to delete this widget?`)) {
        this.layout.splice(index, 1);
      }

    },
  },


}
</script>

<style scoped>

.vue-grid-item .no-drag {
  height: 100%;
  width: 100%;
}

.vue-grid-item .minMax {
  font-size: 12px;
}

.vue-draggable-handle {
  position: absolute;
  width: 20px;
  height: 20px;
  top: 0;
  left: 0;
  background: url("data:image/svg+xml;utf8,<svg xmlns='http://www.w3.org/2000/svg' width='10' height='10'><circle cx='5' cy='5' r='5' fill='#999999'/></svg>") no-repeat;
  background-position: bottom right;
  padding: 0 8px 8px 0;
  background-repeat: no-repeat;
  background-origin: content-box;
  box-sizing: border-box;
  cursor: pointer;
}
</style>