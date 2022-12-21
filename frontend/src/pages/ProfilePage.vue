<template>
  <AddToDoModal
      :showToDoModal="isToDoModalShown"
      @toggleToDoModal="toggleToDoModal"
      @refreshToDoList="refreshToDoList"/>

  <EditToDoModal
      :showToDoModal="isEditTodoModalShown"
      :selectedToDoId="selectedToDoId"
      @toggleEditToDoModal="toggleEditToDoModal"
      @refreshToDoList="refreshToDoList"/>
  <div class="mt-6">
    <div class="flex flex-col lg:flex-row lg:space-y-0 space-y-4 lg:space-x-4 mb-4">
      <div class="bg-white flex-1 h-full rounded-2xl p-4">
        <div class="profile-picture flex inline-block items-center">
          <div>
            <h2 class="text-4xl font-bold text-neutral-900">{{
                (user) ? `${user.firstName} ${user.lastName}` : ''
              }}</h2>
            <p class="text-neutral-500">{{ (user) ? `${user.bio}` : '' }}</p>
          </div>
        </div>
        <!--User info, email-->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4 pt-6 pb-6">
          <div class="flex space-x-4">
            <div class="w-12 h-12 bg-primary-100 rounded-lg text-primary-700 flex items-center justify-center">
              <svg class="w-8 h-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                   stroke="#2D689D" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round"
                      d="M21.75 6.75v10.5a2.25 2.25 0 01-2.25 2.25h-15a2.25 2.25 0 01-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0019.5 4.5h-15a2.25 2.25 0 00-2.25 2.25m19.5 0v.243a2.25 2.25 0 01-1.07 1.916l-7.5 4.615a2.25 2.25 0 01-2.36 0L3.32 8.91a2.25 2.25 0 01-1.07-1.916V6.75"/>
              </svg>
            </div>
            <div>
              <p class="text-neutral-700">{{ (user) ? `${user.email}` : '' }}</p>
              <p class="text-neutral-400">E-mail</p>
            </div>
          </div>
          <div>
            <!--User info, Birthday-->
            <div class="flex space-x-4">
              <div class="w-12 h-12 bg-primary-100 rounded-lg text-primary-700 flex items-center justify-center">
                <svg class="w-8 w-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                     strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                  <path strokeLinecap="round" strokeLinejoin="round"
                        d="M12 8.25v-1.5m0 1.5c-1.355 0-2.697.056-4.024.166C6.845 8.51 6 9.473 6 10.608v2.513m6-4.87c1.355 0 2.697.055 4.024.165C17.155 8.51 18 9.473 18 10.608v2.513m-3-4.87v-1.5m-6 1.5v-1.5m12 9.75l-1.5.75a3.354 3.354 0 01-3 0 3.354 3.354 0 00-3 0 3.354 3.354 0 01-3 0 3.354 3.354 0 00-3 0 3.354 3.354 0 01-3 0L3 16.5m15-3.38a48.474 48.474 0 00-6-.37c-2.032 0-4.034.125-6 .37m12 0c.39.049.777.102 1.163.16 1.07.16 1.837 1.094 1.837 2.175v5.17c0 .62-.504 1.124-1.125 1.124H4.125A1.125 1.125 0 013 20.625v-5.17c0-1.08.768-2.014 1.837-2.174A47.78 47.78 0 016 13.12M12.265 3.11a.375.375 0 11-.53 0L12 2.845l.265.265zm-3 0a.375.375 0 11-.53 0L9 2.845l.265.265zm6 0a.375.375 0 11-.53 0L15 2.845l.265.265z"/>
                </svg>
              </div>
              <div>
                <p class="text-neutral-700">{{ (user) ? `${user.birthday}` : '' }}</p>
                <p class="text-neutral-400">Birthday</p>
              </div>
            </div>
          </div>
          <!--User info, function-->
          <div>
            <div class="flex space-x-4">
              <div class="w-12 h-12 bg-primary-100 rounded-lg text-primary-700 flex items-center justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="-2 -2 24 24" width="24" fill="currentColor">
                  <path
                      d="M16.93 11.998A1 1 0 0 1 17 10h2a1 1 0 0 1 0 2h-.055a9.001 9.001 0 0 1-17.89 0H1a1 1 0 0 1 0-2h2a1 1 0 0 1 .07 1.998A7.005 7.005 0 0 0 9 17.929V7.874A4.002 4.002 0 0 1 10 0a4 4 0 0 1 1 7.874v10.055a7.005 7.005 0 0 0 5.93-5.931zM10 6a2 2 0 1 0 0-4 2 2 0 0 0 0 4z"></path>
                </svg>
              </div>
              <div>
                <p class="text-neutral-700">{{ (user) ? `${user.role}` : '' }}</p>
                <p class="text-neutral-400">Function</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Notifications -->
      <div class="bg-white flex-1 lg:flex-none lg:w-96 rounded-2xl p-4 text-neutral-900">
        <div class="flex space-x-2">
          <router-link to="/dashboard/notifications">
            <h2 class="font-bold text-2xl hover:underline">Notifications</h2>
          </router-link>

          <span class="bg-primary-100 text-primary-700 py-1 px-2 rounded-md font-bold">{{
              notifications.length
            }}</span>

        </div>
        <ul>
          <li class="flex items-center border-b border-neutral-200 py-2" v-for="notification in notifications"
              :key="notification.id">
            <svg class="text-primary-500 w-6 h-6 stroke-1.5 mr-2" xmlns="http://www.w3.org/2000/svg" fill="none"
                 viewBox="0 0 24 24"
                 strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
              <path strokeLinecap="round" strokeLinejoin="round"
                    d="M11.25 11.25l.041-.02a.75.75 0 011.063.852l-.708 2.836a.75.75 0 001.063.853l.041-.021M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-9-3.75h.008v.008H12V8.25z"/>
            </svg>
            <div>
              <h4 class="text-lg font-bold">{{ notification.title }}</h4>
              <p class="text-neutral-700">{{ notification.desc }}</p>
            </div>
          </li>
        </ul>
      </div>
    </div>

    <!--Block 2-->
    <div class="flex flex-col lg:flex-row lg:space-y-0 space-y-4 lg:space-x-4 mb-4">
      <div class="flex-1">
        <div class="bg-white w-full rounded-2xl p-4">
          <h2 class="text-2xl font-bold text-neutral-900">Tasks done</h2>
          <div class="w-[98%]">
            <Bar
                :chart-options="chartOptions"
                :chart-data="toDosDoneChart"
                :chart-id="chartId"
                :dataset-id-key="datasetIdKey"
                :height="250"
            />
          </div>
        </div>
      </div>

      <!-- To-do List -->
      <div class="bg-white flex-1 lg:flex-none lg:w-96 rounded-2xl p-4">
        <div class="flex space-x-2 justify-between">
          <div class="flex space-x-2">
            <h2 class="font-bold text-2xl text-neutral-900">Tasks</h2>
            <span class="bg-primary-100 text-primary-700 p-2 self-center rounded-md font-bold">{{
                getSelectedAmount
              }}</span>
          </div>
          <button @click="toggleToDoModal"
                  class="h-8 w-8 flex justify-center items-center text-center add-btn bg-sky-400 text-white rounded-md hover:bg-sky-600 transition-colors font-bold self-center">
            +
          </button>
        </div>
        <div class="mt-3" v-if="todos">
          <li class="list-none flex justify-between space-x-2 hover:bg-slate-100 p-2 rounded-md" v-for="todo in todos"
              :key="todo.id">
            <div>
              <input type="checkbox" v-model="todo.completed" @change="toDoCompleted(todo)" />
              <label id="checkbox" for="checkbox"
                     :class="(todo.completed) ? 'text-neutral-300 line-through' : 'text-neutral-900'"
                     class="ml-3 hover:underline cursor-pointer" @click="toggleEditToDoModal(todo.id)">{{ todo.name }}
              </label>
              <br>
              <small class="text-slate-500" v-if="!todo.completed">{{todo.dueAt}}</small>
            </div>
          </li>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Bar} from 'vue-chartjs';
import AddToDoModal from "@/components/modals/AddToDoModal";
import EditToDoModal from "@/components/modals/EditToDoModal";
import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);
export default {
  name: "ProfileIndex",
  inject: ["sessionService", "userService", "toDoService"],
  chartName: 'BarChart',
  components: {Bar, AddToDoModal, EditToDoModal},
  props: {
    chartId: {
      type: String,
      default: 'bar-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
  },
  data() {
    return {
      isToDoModalShown: false,
      isEditTodoModalShown: false,
      selectedToDoId: null,
      user: null,
      todos: null,
      notifications: [
        {id: 1, title: "System info", desc: "You have a new task"},
        {id: 2, title: "Parameter alert!", desc: "Alert alert alert"},
      ],
      hoursWorkedChart: {
        labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
        datasets: [
          {
            label: 'Hours',
            backgroundColor: '#92D1F880',
            data: [8, 12, 14, 10, 10, 14, 5],
            borderRadius: 10,
            borderColor: '#0EA5E9',
            borderWidth: 2,
          },
        ]
      },

      toDosDoneChart: {
        labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
        datasets: [
          {
            label: 'Amount',
            backgroundColor: '#92D1F880',
            data: [10, 3, 5, 12, 1, 0, 15],
            borderRadius: 10,
            borderColor: '#0EA5E9',
            borderWidth: 2,
          }
        ]
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      },
      chartOptions2: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            max: 24
          }
        }
      }
    }
  },
  computed: {
    getSelectedAmount() {
      if (this.todos) {
        return this.todos.filter(todo => todo.completed === false).length;
      } else {
        return 0;
      }
    }
  },
  async created() {
    this.user = this.sessionService.getCurrentUser();
    const userToDos = await this.toDoService.getUserTodos(this.user.id);
    this.todos = await userToDos.json();
  },

  methods: {
    toggleToDoModal() {
      this.isToDoModalShown = !this.isToDoModalShown;
    },
    toggleEditToDoModal(toDoId) {
      this.selectedToDoId = toDoId;
      this.isEditTodoModalShown = !this.isEditTodoModalShown;
    },

    async toDoCompleted(todo) {
      await this.toDoService.updateToDo(this.user.id, todo);
      await this.refreshToDoList();
    },

    async refreshToDoList() {
      const userToDos = await this.toDoService.getUserTodos(this.user.id);
      this.todos = await userToDos.json();
    }
  }
}

</script>
