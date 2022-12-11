<template>
  <div class="text-neutral-500 bg-neutral-100 text-center  p-0 rounded-lg"
       :class="{
        'text-red-600 bg-red-100': 80
    }">
    <h3 class="text-sm">{{ sensor.nameShort || sensor.name }}</h3>
    <h2 class="flex-grow text-lg md:text-3xl font-semibold ">{{ randomNumber() }}º</h2>

  </div>

</template>
<script>
export default {
  name: "BatteryTemperature",
  props: {sensor: Object, data: Object},
  methods: {
    randomNumber() {
      return Math.floor(Math.random() * 110)
    }
  }
}

</script>
