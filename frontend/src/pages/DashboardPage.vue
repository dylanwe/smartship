<template>
  <div class="mt-6">
    <!-- Header -->
    <div class="flex flex-col justify-between mb-4 sm:flex-row">
        <div class="flex flex-row gap-4">
          <h1 class="text-4xl text-neutral-800 font-bold">Dashboard</h1>

          <span class="text-neutral-500 font-semibold text-sm self-center flex gap-1">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v6h4.5m4.5 0a9 9 0 11-18 0 9 9 0 0118 0z" />
</svg>

            Past 6 hours
          </span>
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
            <button  v-else
                class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-2 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors"
                @click="toggleEditMode"
            >
            Edit Panels
            </button>
        </div>
    </div>
    <!-- Widget Library -->
    <WidgetLibraryComponent @addWidget="addWidget" :showWidgetbar="showWidgetbar" @closeWidgetMenu="toggleWidgetbar"/>
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
                     @layout-updated="layoutUpdatedEvent"
                
        >
        <!-- Grid Item -->
            <grid-item v-for="(item,index) in layout"
                   :static="item.static"
                   :x="item.x"
                   :y="item.y"
                   :w="item.w"
                   :h="item.h"
                   :i="item.i"
                   :key="index"

                class="bg-white border border-gray-600 rounded-md"
            >
          <!-- Widget Component -->
            <component class="h-full w-full" v-if="item.widget" :is="item.widget.component" :data="item.data"/>
            <!-- TODO remove this -->
            <span v-else>{{item.i}}</span>
                
                <span v-if="editMode" class="remove absolute right-1 top-1 hover:bg-red-400 bg-red-300 px-4 rounded-lg text-red-700 font-semibold items-center cursor-default" @click="removeItem(item.i)">X</span>
             </grid-item>
   
        </grid-layout>
    </div>
</div>
</template>

<script>

import {GridLayout,GridItem} from 'vue3-grid-layout';
import WidgetLibraryComponent from '../components/dashboard/widgets/WidgetLibraryComponent.vue';
import WidgetTemperature from '../components/dashboard/widgets/temperature/WidgetTemperature.vue';
import BatteryLevel from '../components/dashboard/widgets/battery/BatteryLevel.vue';
import BigLineChart from '../components/dashboard/widgets/base/BigLineChart.vue';
import SmallLineChart from '../components/dashboard/widgets/base/SmallLineChart.vue';

export default {
  name: "DashboardIndex",
  inject: ["dashboardService"],
  components: {
    GridLayout: GridLayout,
    GridItem: GridItem,
    WidgetLibraryComponent,
   WidgetTemperature, BatteryLevel,BigLineChart,SmallLineChart
},
  data(){
    return{
        // Temp stored widgets
      layout: [
                // {"x":0,"y":0,"w":3,"h":2,"i":"0"},
                // {"x":1,"y":0,"w":1,"h":1,"i":"1"},
                // {"x":2,"y":2,"w":1,"h":1,"i":"2"},
                // {"x":2,"y":5,"w":2,"h":1,"i":"3"},
             
            ],
            // Grid options
            numberOfColumns:5,
            draggable: false,
            resizable: false,

            index: 0,

            // Widget edit
            editMode: false,
            showWidgetbar:false
  
    }
  },
  methods: {
        async toggleWidgetbar(){
            this.showWidgetbar = !this.showWidgetbar
          console.log(await this.dashboardService.getAll())
        },
        toggleEditMode() {
            this.editMode = !this.editMode;
            this.draggable = !this.draggable;
            this.resizable = !this.resizable;
        },
        cancelChanges(){
            this.toggleEditMode()
        },
        saveChanges(){
            this.toggleEditMode()
        },

        addWidget(widget){
              // Add a new item. It must have a unique key!
              this.layout.push({
                x: (this.layout.length * 2) % (this.numberOfColumns),
                y: this.layout.length + (this.numberOfColumns), // puts it at the bottom
                w: widget.config.width || 1,
                h: widget.config.height || 1,
                widget:widget,
                data:widget.data,

                i: this.index,

              });
            // Increment the counter to ensure key is always unique.
            this.index++;
        },
    
        removeItem(val) {
            const index = this.layout.map(item => item.i).indexOf(val);
            if (confirm(`Are you sure you want to delete this widget?`)) this.layout.splice(index, 1);
           
        },
    },

  layoutUpdatedEvent: function(newLayout){
    console.log("Updated layout: ", newLayout)
  },
  //
  // layoutCreatedEvent: function(newLayout){
  //   console.log("Created layout: ", newLayout)
  // },
  // moveEvent: function(i, newX, newY){
  //   console.log("MOVE i=" + i + ", X=" + newX + ", Y=" + newY);
  // },
  // resizeEvent: function(i, newH, newW, newHPx, newWPx){
  //   console.log("RESIZE i=" + i + ", H=" + newH + ", W=" + newW + ", H(px)=" + newHPx + ", W(px)=" + newWPx);
  // },
  // movedEvent: function(i, newX, newY){
  //   console.log("MOVED i=" + i + ", X=" + newX + ", Y=" + newY);
  // },
  // resizedEvent: function(i, newH, newW, newHPx, newWPx){
  //   console.log("RESIZED i=" + i + ", H=" + newH + ", W=" + newW + ", H(px)=" + newHPx + ", W(px)=" + newWPx);
  // },
  // containerResizedEvent: function(i, newH, newW, newHPx, newWPx){
  //   console.log("CONTAINER RESIZED i=" + i + ", H=" + newH + ", W=" + newW + ", H(px)=" + newHPx + ", W(px)=" + newWPx);
  // },
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