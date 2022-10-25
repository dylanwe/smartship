<template>

  <div class="flex flex-col gap-2 p-2">
    <h3 class="font-semibold">{{ data.title }}</h3>
    <TemperatureItem :itemName="data.items[0].name" :temperature=randomNumber() :maxTemperature="data.items[0].maxTemperature" />
    <TemperatureItem :itemName="data.items[1].name" :temperature=randomNumber() :maxTemperature="data.items[1].maxTemperature" />
  </div>

</template>
<script>
import TemperatureItem from '@/components/dashboard/widgets/temperature/TemperatureItem.vue';

export default {
  name: "BatteryTemperature",
  components: { TemperatureItem },
  props: { data: Object },
  methods: {
    randomNumber() {
      return Math.floor(Math.random() * 110)
    }
  }
}

</script>
