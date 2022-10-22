<template >
    <div>
        <h2>Battery Pack 1 Temperature</h2>
        <LineChart :data=data />
    </div>
</template>

<script>
import LineChart from '@/components/charts/LineChart';
export default {
    name: "BatteryTemperatureChart",
    components: { LineChart },
    props: { data }
}
</script>
