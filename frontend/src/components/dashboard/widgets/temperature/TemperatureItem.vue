<template >
 <div class="flex flex-col text-neutral-500 bg-neutral-100 text-center p-2 lg:mx-3 rounded-lg" :class="{
        'text-red-600 bg-red-100': temperatureIsCritical()
    }">
        <h3 class="text-sm">{{itemName}}</h3>
        <h2 class="flex-grow text-lg md:text-3xl font-semibold ">{{temperature}}º</h2>

    </div>
</template>
<script>
export default {
    name: "TemperaturePack",
    props:{
      itemName: [String,Number],
        temperature: [Number,String],
      maxTemperature: Number
    },

    methods:{
        temperatureIsCritical() {
            return this.temperature >= this.maxTemperature;
        }
    }
}
</script>
<style >
    
</style>