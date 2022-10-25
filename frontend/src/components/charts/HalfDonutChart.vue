<template>
  <Doughnut :chart-options="chartOptions" :chart-data="chartData" class="bg-green-200" />
</template>

<script>
import { Doughnut } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: 'HalfDonutChart',
  components: { Doughnut },
  props: {
  },
  data() {
    return {
      chartData: {
        labels: ['January', 'Februar'],
        datasets: [{
          data: [40, 20,], backgroundColor: ["#Cf1", '#FF331']
        }]
      },
      chartOptions: {
     
        responsive: true,
        maintainAspectRatio: false
      }
    }
  }
}
</script>