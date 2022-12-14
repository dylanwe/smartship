<template>
  <div class="flex flex-col p-2 gap-5">
    <div class="flex gap-4">
      <h1 class="font-semibold">{{ title }}</h1>
    </div>
    <LineChart class="flex-grow h-[200px]" :data="dataSet" :title="title"/>
  </div>


</template>

<script>
import LineChart from '@/components/charts/LineChart';

export default {
  name: "BigLineChart",
  props: {
    title: String,
    dataSet: Array
  },
  components: {LineChart},
  data() {
    return {
      chart: {
        height: 380,
        width: "100%",
        type: "area",
        animations: {
          initialAnimation: {
            enabled: false
          }
        }
      },
      series: [
        {
          name: "Series 1",
          data: [
            [1486684800000, 34],
            [1486771200000, 43],
            [1486857600000, 31],
            [1486944000000, 43],
            [1487030400000, 33],
            [1487116800000, 52]
          ]
        }
      ],
      xaxis: {
        type: 'datetime'
      }
    }
  }
};

</script>