<template >
    <div class="flex flex-col justify-center p-2">

        <h2 class="font-semibold">Battery Levels</h2>

        <SmallLineChart :data="data" title="Pack 1" value="50%" class="h-[40%]" />

        <SmallLineChart :data="data" title="Pack 2" value="39%" class="h-[40%]" />

    </div>
</template>

<script>
// import LineChart from '@/components/charts/LineChart';
import SmallLineChart from '../base/SmallLineChart.vue';
export default {
    name: "BatteryTemperatureChart",
    components: { SmallLineChart },
    props: {data: Object},

}
</script>
