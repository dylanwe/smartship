<template>
  <Transition name="slide-fade">
    <div id="toast-success" v-if="show"
         class="fixed top-6 right-6 flex items-center p-4 mb-4 w-full max-w-xs text-neutral-300 bg-neutral-800 rounded-lg shadow-2xl"
         role="alert">
      <div
          class="inline-flex flex-shrink-0 justify-center items-center w-8 h-8 rounded-lg"
          :class="toastStyling">
        <svg v-if="toastType === 'succes'" aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
             xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd"
                d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
                clip-rule="evenodd"></path>
        </svg>
        <svg v-else-if="toastType === 'error'" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
             stroke-width="1.5"
              stroke="currentColor" class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v3.75m9-.75a9 9 0 11-18 0 9 9 0 0118 0zm-9 3.75h.008v.008H12v-.008z" />
        </svg>
        <span class="sr-only">Check icon</span>
      </div>
      <div class="ml-3 text-sm font-normal">{{ text }}</div>
    </div>
  </Transition>
</template>

<script>
export default {
  name: "SettingToast",
  props: {
    show: Boolean,
    text: String,
    toastType: {
      type: String,
      validator(value) {
        // The value must match one of these strings
        return ['succes', 'error', 'info'].includes(value)
      },
      default: 'info',
    },
  },
  computed: {
    toastStyling() {
      switch (this.toastType) {
        case 'succes':
          return 'text-green-500 bg-green-800';
        case 'error':
          return 'text-red-400 bg-red-900';
        default:
          return 'text-neutral-200 bg-neutral-600'
      }
    }
  }
}
</script>