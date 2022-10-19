<template>
  <div class="mt-6">
    <div class="flex flex-col lg:flex-row lg:space-y-0 space-y-4 lg:space-x-4 mb-4">
      <div class="bg-white flex-1 h-full rounded-2xl p-4">
        <div class="profile-picture flex inline-block items-center">
          <img class="w-20 h-20 object-none object-top rounded-2xl mr-6" src="@/assets/img/profile_picture.jpeg" alt=""
               style="object-fit: cover">
          <div>
            <h2 class="text-4xl font-bold text-slate-900">{{ (user) ? `${user.firstName} ${user.lastName}` : '' }}</h2>
            <p class="text-slate-500">{{ (user) ? `${user.bio}` : '' }}</p>
          </div>
        </div>
        <!--User info, email-->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4 pt-6 pb-6">
          <div class="flex space-x-4">
            <div class="w-12 h-12 bg-sky-100 rounded-lg text-sky-700 flex items-center justify-center">
              <svg class="w-8 h-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                   stroke="#2D689D" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round"
                      d="M21.75 6.75v10.5a2.25 2.25 0 01-2.25 2.25h-15a2.25 2.25 0 01-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0019.5 4.5h-15a2.25 2.25 0 00-2.25 2.25m19.5 0v.243a2.25 2.25 0 01-1.07 1.916l-7.5 4.615a2.25 2.25 0 01-2.36 0L3.32 8.91a2.25 2.25 0 01-1.07-1.916V6.75"/>
              </svg>
            </div>
            <div>
              <p class="text-slate-700">{{ (user) ? `${user.email}` : '' }}</p>
              <p class="text-slate-400">E-mail</p>
            </div>
          </div>
          <div>
            <!--User info, Birthday-->
            <div class="flex space-x-4">
              <div class="w-12 h-12 bg-sky-100 rounded-lg text-sky-700 flex items-center justify-center">
                <svg class="w-8 w-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                     strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
                  <path strokeLinecap="round" strokeLinejoin="round"
                        d="M12 8.25v-1.5m0 1.5c-1.355 0-2.697.056-4.024.166C6.845 8.51 6 9.473 6 10.608v2.513m6-4.87c1.355 0 2.697.055 4.024.165C17.155 8.51 18 9.473 18 10.608v2.513m-3-4.87v-1.5m-6 1.5v-1.5m12 9.75l-1.5.75a3.354 3.354 0 01-3 0 3.354 3.354 0 00-3 0 3.354 3.354 0 01-3 0 3.354 3.354 0 00-3 0 3.354 3.354 0 01-3 0L3 16.5m15-3.38a48.474 48.474 0 00-6-.37c-2.032 0-4.034.125-6 .37m12 0c.39.049.777.102 1.163.16 1.07.16 1.837 1.094 1.837 2.175v5.17c0 .62-.504 1.124-1.125 1.124H4.125A1.125 1.125 0 013 20.625v-5.17c0-1.08.768-2.014 1.837-2.174A47.78 47.78 0 016 13.12M12.265 3.11a.375.375 0 11-.53 0L12 2.845l.265.265zm-3 0a.375.375 0 11-.53 0L9 2.845l.265.265zm6 0a.375.375 0 11-.53 0L15 2.845l.265.265z"/>
                </svg>
              </div>
              <div>
                <p class="text-slate-700">{{ (user) ? `${user.birthday}` : '' }}</p>
                <p class="text-slate-400">Birthday</p>
              </div>
            </div>
          </div>
          <!--User info, function-->
          <div>
            <div class="flex space-x-4">
              <div class="w-12 h-12 bg-sky-100 rounded-lg text-sky-700 flex items-center justify-center">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="-2 -2 24 24" width="24" fill="currentColor">
                  <path
                      d="M16.93 11.998A1 1 0 0 1 17 10h2a1 1 0 0 1 0 2h-.055a9.001 9.001 0 0 1-17.89 0H1a1 1 0 0 1 0-2h2a1 1 0 0 1 .07 1.998A7.005 7.005 0 0 0 9 17.929V7.874A4.002 4.002 0 0 1 10 0a4 4 0 0 1 1 7.874v10.055a7.005 7.005 0 0 0 5.93-5.931zM10 6a2 2 0 1 0 0-4 2 2 0 0 0 0 4z"></path>
                </svg>
              </div>
              <div>
                <p class="text-slate-700">{{ (user) ? `${user.role}` : '' }}</p>
                <p class="text-slate-400">Function</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Notifications -->
      <div class="bg-white flex-1 lg:flex-none lg:w-96 rounded-2xl p-4 text-slate-900">
        <div class="flex space-x-2">
          <h2 class="font-bold text-2xl">Notifications</h2>
          <span class="bg-sky-100 text-sky-700 py-1 px-2 rounded-md font-bold">{{
              notifications.length
            }}</span>

        </div>
        <ul>
          <li class="flex items-center border-b border-slate-200 py-2" v-for="notification in notifications"
              :key="notification.id">
            <svg class="text-sky-500 w-6 h-6 stroke-1.5 mr-2" xmlns="http://www.w3.org/2000/svg" fill="none"
                 viewBox="0 0 24 24"
                 strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
              <path strokeLinecap="round" strokeLinejoin="round"
                    d="M11.25 11.25l.041-.02a.75.75 0 011.063.852l-.708 2.836a.75.75 0 001.063.853l.041-.021M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-9-3.75h.008v.008H12V8.25z"/>
            </svg>
            <div>
              <h4 class="text-lg font-bold">{{ notification.title }}</h4>
              <p class="text-slate-700">{{ notification.desc }}</p>
            </div>
          </li>
        </ul>
      </div>
    </div>

    <!--Block 2-->
    <div class="flex flex-col lg:flex-row lg:space-y-0 space-y-4 lg:space-x-4 mb-4">
      <div class="flex-1">
        <div class="bg-white w-full rounded-2xl p-4 mb-4">
          <h2 class="text-2xl font-bold text-slate-900">Graph 1</h2>
          <div class="w-full">
            <Bar
                :chart-options="chartOptions"
                :chart-data="chartData"
                :chart-id="chartId"
                :dataset-id-key="datasetIdKey"
                :height="250"
                class="w-full"
            />
          </div>
        </div>
        <div class="bg-white w-full rounded-2xl p-4">
          <h2 class="text-2xl font-bold text-slate-900">Graph 2</h2>
          <div class="w-full">
            <Bar
                :chart-options="chartOptions"
                :chart-data="chartData"
                :chart-id="chartId"
                :dataset-id-key="datasetIdKey"
                :height="250"
                class="w-full"
            />
          </div>
        </div>
      </div>

      <!-- To-do List -->
<!--      <div class="bg-white flex-1 lg:flex-none lg:w-96 rounded-2xl p-4">-->
<!--        <div class="flex space-x-2">-->
<!--          <h2 class="font-bold text-2xl text-slate-900">Tasks</h2>-->
<!--          <span class="bg-sky-100 text-sky-700 pt-1 pb-1 pl-2 pr-2 rounded-md font-bold">{{ getSelectedAmount }}</span>-->
<!--          <div>-->
<!--          </div>-->
<!--        </div>-->
<!--        <div class="mt-3">-->
<!--          <li class="list-none mb-4" v-for="todo in todos" :key="todo.id">-->
<!--            <input type="checkbox" v-model="todo.done"/>-->
<!--            <label id="checkbox" for="checkbox" :class="(todo.done) ? 'text-slate-300 line-through' : 'text-slate-900'"-->
<!--                   class="ml-3">{{ todo.title }}</label>-->
<!--          </li>-->
<!--        </div>-->
<!--      </div>-->
    </div>
  </div>
</template>

<script>
import {Bar} from 'vue-chartjs';
import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);
export default {
  name: "ProfileIndex",
  inject: ["sessionService", "userService"],
  chartName: 'BarChart',
  components: {Bar},
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
      user: null,
      todos: [
        {id: 1, title: "Clean the floor", done: false},
        {id: 2, title: "Mop the floor", done: false},
        {id: 3, title: "Organize the floor", done: false},
        {id: 4, title: "Cook the floor", done: false},
        {id: 5, title: "Smell the floor", done: false},
        {id: 6, title: "Eat the floor", done: false},
        {id: 7, title: "Sniff the floor", done: true},
        {id: 8, title: "Feel the floor", done: true},
        {id: 9, title: "Laugh at the floor", done: true},
        {id: 10, title: "Sing to the floor", done: true},
        {id: 11, title: "floor", done: true},
      ],
      notifications: [
        {id: 1, title: "System info", desc: "You have a new task"},
        {id: 2, title: "Parameter alert!", desc: "Alert alert alert"},
      ],
      chartData: {
        labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'],
        datasets: [
          {
            backgroundColor: '#92D1F880',
            data: [40, 20, 12, 90, 51, 32, 9],
            borderRadius: 10,
            borderColor: '#0EA5E9',
            borderWidth: 2,
          }
        ]
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
      }
    }
  },
  computed: {
    getSelectedAmount() {
      return this.todos.filter(todo => todo.done === false).length
    }
  },
  async created() {
    this.user = this.sessionService.getCurrentUser();
    console.log(this.user)
  }
}

</script>
