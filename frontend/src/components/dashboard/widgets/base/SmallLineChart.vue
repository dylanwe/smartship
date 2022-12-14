<template>
  <div class="flex flex-col justify-center pl-2">
    <h3 class="text-neutral-400">{{ sensor.name }}</h3>
      <h1 class="text-xl font-semibold">
        {{ this.dataSet.length ? `${this.dataSet.at(-1)[1]} ${this.sensor.unit}` : 'NaN' }}
      </h1>

      <SparkLineChart :sensor="sensor" :dataSet="dataSet"/>
    </div>


</template>

<script>

import SparkLineChart from "@/components/charts/SparkLineChart";

export default {
  name: "SmallLineChart",
  props: {
    sensor: Object,
    dataSet: Array
  },
  components: {SparkLineChart, },
  created() {
  }

};

</script>