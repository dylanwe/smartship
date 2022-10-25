<template >
    <div class="flex flex-col p-2 gap-5">
        <div class="flex gap-4">
            <h1 class="font-semibold">{{ data.title }}</h1>
            <span class="text-green-600">+6%</span>
        </div>

        <LineChart class="flex-grow h-[10%]" :data="data" />

    </div>


</template>

<script>
import LineChart from '@/components/charts/LineChart';

export default {
    name: "BigLineChart",
    props: {
        data: Object,

    },
    components: { LineChart },  
};

</script>