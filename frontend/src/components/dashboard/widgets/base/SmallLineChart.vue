<template >
    <div class="flex flex-col justify-center">
        <h3 class="text-slate-400">{{title}}</h3>
        <div class="flex flex-row gap-3">
            <h1 class="text-4xl">{{value}}</h1>
            <LineChart :hideGrid="true" :data="data" :hidePointers="true" class="w-[70%] h-[90%] hidden md:block "/>
        </div>
       
    </div>
   
</template>

<script>
import LineChart from '@/components/charts/LineChart';

export default {
    name: "SmallLineChart",
    props: {
        title: String,
        value: [String, Number],
        data: Object
    },
    components: { LineChart },
   created(){
  
   }
  
};

</script>