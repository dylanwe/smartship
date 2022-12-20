<template>

  <apexchart type="area" height="50%" width="100%" :options="chartOptions"
             :series="series"/>
</template>

<script>

export default {
  name: 'SparkLineChart',
  props: {
    sensor: Object,
    dataSet: Array
  },
  watch: {
    dataSet(newVal) { // update
      this.series = [{data: newVal}];
    }
  },
  data() {
    return {
      series: [{
        name: this.sensor.unit,
        data: this.dataSet
      }],
      chartOptions: {
        chart: {
          type: 'area',
          sparkline: {
            enabled: true
          },
          stroke: {
            curve: 'straight'
          },
          fill: {
            opacity: 0.3
          },
          xaxis: {
            crosshairs: {
              width: 1
            },
          },
          yaxis: {
            min: 0
          },
        }
      },


    }
  }
}
</script>