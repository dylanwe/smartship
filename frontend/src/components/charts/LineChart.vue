<template>
  <Line :chart-options="chartOptions" :chart-data="chartData" />
</template>
  
<script>
import { Line } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, PointElement, CategoryScale, LinearScale, Filler } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale, Filler)

export default {
  name: 'LineChart',
  components: { Line },
  props: {
    hasIndicators: Boolean,
    hideGrid: Boolean,
    hidePointers: Boolean,
    data: Object
  },
  data() {
    return {
      chartData: {
        datasets: [{
          labels:  this.data?.x || ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
          borderColor: 'rgb(14, 165, 233)',
          pointBackgroundColor: 'white',
          pointRadius: this.hidePointers ? 0 : 4,
          pointBorderWidth: 1,
          data: this.data?.dataSet || [12, 19, 3, 5, 2, 3],
          fill: true,
          borderWidth: 2,
          backgroundColor: (ctx) => {
            const canvas = ctx.chart.ctx;
            const gradient = canvas.createLinearGradient(0, 0, 0, 200); //TODO get height of div dynamically
            gradient.addColorStop(0.2, '#54d6ff');
            gradient.addColorStop(1, 'white');
            return gradient;
          },
        }]
      },
      chartOptions: {
       
        responsive: true,
        maintainAspectRatio: false,

        plugins: {
          legend: {
            display: false
          }
        },
        scales: {
          y: {
            display: !this.hideGrid,
            beginAtZero: true,
            grid: {
              display: false
            }
          },
          x: {
            display: !this.hideGrid,
            grid: {
              display: false
            }
          }
        },
        tension: 0.3

      }
    }
  }
}
</script>