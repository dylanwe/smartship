<template ><div class="flex flex-col justify-center">
    <h3 class="text-neutral-400">{{sensor.shortName || sensor.name}}</h3>
    <div class="flex flex-row gap-3">
      <h1 class="text-4xl">5</h1>
      <LineChart :hideGrid="true" :data="data" :hidePointers="true" class="w-[70%] h-[90%] hidden md:block "/>
    </div>

  </div>
</template>

<script>
import LineChart from '@/components/charts/LineChart';
export default {
    name: "BatteryTemperatureChart",
    components: { LineChart },
    props: {sensor:Object, data: Object},

}
</script>
