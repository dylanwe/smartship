<template>
  <div class="text-4xl font-bold pt-10 pb-5">Notifications</div>
<div class="bg-white rounded-2xl">
  <ol class=" pt-9 pb-9 px-9">
    <li class="space-y-3 hover:bg-sky-50 rounded-md pb-4">

      <div class="h-2 w-2 bg-sky-500 rounded-full flex-row"></div>
      <h3 class="text-lg font-semibold text-gray-900 dark:text-white">System Info</h3>

      <time class="mb-1 text-sm font-normal leading-none text-gray-400 dark:text-gray-500">2023, July 13, 10:30 AM</time>
      <p class="mb-4 text-base font-normal text-gray-500 dark:text-gray-400">A overview of your system.</p>
      <span class="bg-sky-200 text-sky-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full dark:bg-blue-200 dark:text-blue-800">Info</span>
    </li>
    <li class="space-y-3 hover:bg-sky-50 rounded-md">
      <h3 class="text-lg font-semibold text-gray-900 dark:text-white">System Info</h3>
      <time class="mb-1 text-sm font-normal leading-none text-gray-400 dark:text-gray-500">2023, July 13, 10:30 AM</time>
      <p class="mb-4 text-base font-normal text-gray-500 dark:text-gray-400">A overview of your system.</p>
      <span class="bg-sky-200 text-sky-800 text-xs font-inter mr-2 px-2.5 py-0.5 rounded-full dark:bg-blue-200 dark:text-blue-800">Info</span>
    </li>
  </ol>
</div>
</template>

<script>
export default {
  name: "NotificationIndex"
}
</script>

<style scoped>

</style>