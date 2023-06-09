<template>
  <section class="w-full h-full top-0 left-0 fixed bg-slate-800 bg-opacity-30" v-show="showToDoModal">
    <div id="authentication-modal" tabindex="-1" aria-hidden="true"
         class="fixed top-0 left-0 right-0 z-50 w-full p-4 overflow-x-hidden overflow-y-auto md:inset-0 h-modal md:h-full">
      <div class="flex w-full h-full items-center justify-center mx-auto">
        <!-- Modal content -->
        <div class="relative bg-white max-w-lg w-full rounded-lg shadow">
          <button @click="closeToDoModal" type="button"
                  class="absolute top-3 right-2.5 text-slate-400 bg-transparent hover:bg-slate-200 hover:text-slate-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center :hover:bg-slate-800"
                  data-modal-toggle="authentication-modal">
            <svg aria-hidden="true" class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
                 xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd"
                    d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                    clip-rule="evenodd"></path>
            </svg>
            <span class="sr-only">Close modal</span>
          </button>
          <div class="px-6 py-6 lg:px-8">
            <h3 class="mb-4 text-xl font-medium text-slate-900">Add your task</h3>
            <form class="space-y-6" @submit.prevent="saveToDo">
              <div>

                <label for="todo" class="block mb-2 text-sm font-medium text-slate-900">Task name</label>
                <input type="text" v-model.trim="taskName"
                       class="border text-sm rounded-lg block w-full p-2.5"
                       :class="(v$.taskName.$error) ? errorInputStyle : goodInputStyle"
                       placeholder="Task" required="">
                <div v-if="v$.taskName.$error" class="text-sm text-red-500 pt-2">{{
                    v$.taskName.$errors[0]?.$message
                  }}
                </div>
              </div>
              <label for="dueDate" class="block mb-2 text-sm font-medium text-slate-900">Due date</label>
              <input type="date" v-model.trim="dueDate"
                     class="border text-sm rounded-lg block w-full p-2.5"
                     :class="(v$.dueDate.$silentErrors.length !== 0) ? errorInputStyle : goodInputStyle"
                     required="">
              <div v-if="v$.dueDate.$silentErrors" class="text-sm text-red-500 pt-2">{{
                  v$.dueDate.$silentErrors[0]?.$message
                }}
              </div>
              <button :disabled="taskName.length < 2 || v$.taskName.$error || v$.dueDate.$silentErrors.length !== 0"
                      type="submit"
                      class="disabled:bg-slate-400 w-full text-white bg-sky-500 hover:bg-sky-600 hover: transition-colors focus:ring-4 focus:outline-none focus:ring-sky-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center">
                Save
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>


  </section>

</template>

<script>
import {useVuelidate} from "@vuelidate/core";
import {helpers, minLength, required} from "@vuelidate/validators";

export default {
  name: "AddToDoModal",
  inject: ["sessionService", "toDoService"],
  props: {
    showToDoModal: Boolean,
  },
  setup() {
    return {v$: useVuelidate()}
  },
  data() {
    return {
      taskName: '',
      dueDate: new Date().toISOString().slice(0, 10),
      errorInputStyle: 'bg-red-50 text-red-900 border-red-500 focus:ring-red-500 focus:border-red-500',
      goodInputStyle: 'bg-neutral-50 border-neutral-300 text-neutral-900 focus:ring-primary-500 focus:border-primary-500'
    }
  },
  validations: {
    taskName: {
      required,
      minLength: minLength(2),
      $autoDirty: true,
    },
    dueDate: {
      maxValue: helpers.withMessage('Due date can\'t be in the past', value => {
        return new Date(value) >= new Date(new Date().toISOString().slice(0, 10));
      }),
    }
  },
  emits: ["toggleToDoModal", "refreshToDoList"],
  async created() {
    this.user = this.sessionService.getCurrentUser();
  },
  methods: {
    closeToDoModal() {
      this.$emit("toggleToDoModal");
    },
    async saveToDo() {
      await this.toDoService.saveTodo(this.user.id, this.taskName, this.dueDate);
      this.$emit("refreshToDoList");
      this.taskName = '';
      this.closeToDoModal();
    }
  }
}
</script>