<template >
    <BarChart/>
</template>

<script>
import BarChart from '@/components/charts/BarChart';

export default {
    name: "WidgetBar",
    props: {
        title: String,
    },
    components: { BarChart },
  
};

</script>