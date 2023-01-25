<template>
  <apexchart type="area" height="90%" width="100%" :options="chartOptions"
             :series="series"></apexchart>

</template>

<script>

export default {
  name: 'LineChart',
  props: {
    sensor: Object,
    dataSet: Array
  },
  watch: {
    dataSet(newVal) { // update dataset on change
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
          stacked: false,
          zoom: {
            type: 'x',
            enabled: true,
            autoScaleYaxis: true
          },
          toolbar: {
            autoSelected: 'zoom'
          }
        },
        dataLabels: {
          enabled: false
        },
        markers: {
          size: 0,
        },
        title: {
          text: this.sensor.name,
          align: 'left'
        },
        fill: {
          type: 'gradient',
          gradient: {
            shadeIntensity: 1,
            inverseColors: false,
            opacityFrom: 0.5,
            opacityTo: 0,
            stops: [0, 90, 100]
          },
        },
        yaxis: {
          title: {
            text: this.sensor.unit
          },
        },
        xaxis: {
          type: 'datetime',
        },
        tooltip: {
          shared: false,
          x: {
            formatter(val) {
              return new Date(val).toLocaleString()
            }
          },
          y: {
            formatter(val) {
              return val
            }
          }
        }
      },


    }
  }
}
</script>