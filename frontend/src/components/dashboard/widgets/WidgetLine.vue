<template >
    <LineChart/>
</template>

<script>
import LineChart from '@/components/charts/LineChart';

export default {
    name: "WidgetLine",
    props: {
        title: String,
    },
    components: { LineChart },
  
};

</script>