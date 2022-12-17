<template>
  <div class="mt-6">
    <!-- Header -->
    <div class="flex flex-col justify-between mb-4 sm:flex-row">
      <div class="flex flex-row gap-4 w-full px-2">
        <h1 class="text-4xl text-neutral-800 font-bold">Dashboard</h1>

        <!--  Date picker range-->
        <div class="flex-1">
          <button class="flex items-center space-x-2 bg-white py-2 px-2.5 rounded-md border border-neutral-400" @click="toggleDatePickerMenu">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6 text-neutral-500 transition-colors" :class="{'text-sky-600': openDatePicker}">
              <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 3v2.25M17.25 3v2.25M3 18.75V7.5a2.25 2.25 0 012.25-2.25h13.5A2.25 2.25 0 0121 7.5v11.25m-18 0A2.25 2.25 0 005.25 21h13.5A2.25 2.25 0 0021 18.75m-18 0v-7.5A2.25 2.25 0 015.25 9h13.5A2.25 2.25 0 0121 11.25v7.5m-9-6h.008v.008H12v-.008zM12 15h.008v.008H12V15zm0 2.25h.008v.008H12v-.008zM9.75 15h.008v.008H9.75V15zm0 2.25h.008v.008H9.75v-.008zM7.5 15h.008v.008H7.5V15zm0 2.25h.008v.008H7.5v-.008zm6.75-4.5h.008v.008h-.008v-.008zm0 2.25h.008v.008h-.008V15zm0 2.25h.008v.008h-.008v-.008zm2.25-4.5h.008v.008H16.5v-.008zm0 2.25h.008v.008H16.5V15z" />
            </svg>
            <span class="text-neutral-700 text-sm">
              {{getRangeDistance}}
            </span>
          </button>

          <div class="w-[1px] h-[1px] mt-[-15px] overflow-hidden opacity-0">
          <date-picker
              v-model:value="dateRange"
              v-model:open="openDatePicker"
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
                <div class="ml-auto bg-primary-100 rounded px-2">
                  <button class="mx-btn mx-btn-text " @click="toggleTimeRangePanel">
                    {{ showTimeRangePanel ? 'select date' : 'select time' }}
                  </button>
                </div>
              </div>
            </template>
          </date-picker>
          </div>
        </div>
      <!-- Edit widgets buttons -->

        <!--    Edit mode enabled-->
        <div
            v-if="editMode"
            class="flex flex-row gap-1  "
        >
          <button
              class="text-white bg-primary-500 disabled:bg-neutral-300 hover:bg-primary-600 focus:ring-2 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-2.5 py-2.5 text-center transition-colors"
              @click="toggleWidgetbar"
          >
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
            </svg>

          </button>
          <button
              class="text-primary-600 bg-primary-100 border-2 border-primary-500 disabled:bg-neutral-300 hover:bg-primary-200 focus:ring-2 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
              @click="saveChanges">
            Save & Exit
          </button>
        </div>

        <!--    Edit mode disabled-->
        <button
            class="text-primary-600 bg-primary-100 border-2 border-primary-500 disabled:bg-neutral-300 hover:bg-primary-200 focus:ring-2 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
            v-else
                @click="toggleEditMode"
        >
          Manage widgets
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
                   class="bg-white border border-neutral-400 rounded-lg  p-2"
        >
          <!-- Widget Component -->
          <!--:is="item.shipSensor.sensor.widget.componentName"-->
          <component class="h-full w-full" v-if="item.shipSensor"
                     :is="item.shipSensor.sensor.widget.componentName"
                     :dataSet="item.data"
                     :sensor="item.shipSensor.sensor"/>

          <span v-if="editMode"
                class="z-30 remove absolute right-2 top-2 hover:bg-red-100 bg-red-50 p-2 rounded-lg text-red-500 font-bold items-center cursor-default"
                @click="removeItem(item.i)">
              <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
          </span>
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

import { formatDistance } from 'date-fns'
import Grid from "@/utils/Grid";

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
  computed:{
    getRangeDistance(){
      const [from,to] = this.dateRange;
     return formatDistance(to, from);
    }
  },

  async created() {
    const {id: dashboardId,layout} = await this.dashboardService.getUserDashboard(this.sessionService.getCurrentUser());
    this.dashboardId = dashboardId;

        this.layout = await Promise.all( layout.map(async (obj) =>  ({...obj, i: this.index++, data: (await this.updateWidgetData(obj))})));
  },
  data() {
    return {
      tempComponent: "SmallLineChart",
      dateRange: [new Date(new Date().setDate(new Date().getDate() - 365)).valueOf(), new Date().valueOf()],
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
      showWidgetbar: false,


      openDatePicker:false
    }
  },


  methods: {
    ExtractDataSet,
    toggleDatePickerMenu(){
      this.openDatePicker = !this.openDatePicker
    },
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
    async saveChanges() {
      this.toggleEditMode()
      await this.dashboardService.saveLayout(this.dashboardId, this.layout);
    },

    async addWidget(widget) {
      const grid = new Grid(this.numberOfColumns, this.numberOfColumns, this.layout);

      const {defaultHeight,defaultWidth} = widget.sensor.widget;

      const foundCoords = grid.getCoordinates({
        h: defaultHeight,
        w: defaultWidth
      });

      if (!foundCoords) return console.error("noh man");

      const newWidget = {
        x: foundCoords.x,
        y: foundCoords.y,
        w: defaultWidth,
        h: defaultHeight,
        shipSensor: widget,
        data: [],
        i: this.index++
      };

      this.layout.push(newWidget);

      // Load widget's data
      await this.updateWidgetData(newWidget);
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