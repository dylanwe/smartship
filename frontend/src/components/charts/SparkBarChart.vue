<template>
  <apexchart type="bar" height="40%" width="100%" :options="chartOptions"
             :series="series"></apexchart>

</template>

<script>

export default {
  name: 'SparkBarChart',
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
          type: 'bar',
          sparkline: {
            enabled: true
          }
        },
        plotOptions: {
          bar: {
            columnWidth: '80%'
          }
        },
        tooltip: {
          fixed: {
            enabled: false
          },
          x: {
            show: false
          },
          y: {
            title: {
              formatter: function () {
                return ''
              }
            }
          },
          marker: {
            show: false
          }
        }
      },


    }
  }
}
</script>