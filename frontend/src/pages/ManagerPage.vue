<template>
  <div class="mt-6">
    <h1 class="text-4xl text-neutral-800 font-bold">Manager panel</h1>
    <div class="overflow-x-auto relative shadow-md sm:rounded-lg mt-8">
      <table class="w-full text-sm text-left text-neutral-700">
        <caption class="p-5 text-neutral-700 bg-neutral-50">
          <h2 class="float-left text-3xl font-bold">Operators</h2>
          <button
              class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center transition-colors flex float-right items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v12m6-6H6"/>
            </svg>
            Add Operator
          </button>
        </caption>
        <thead class="text-sm text-neutral-900 uppercase bg-neutral-50 border-t-2 border-neutral-200">
        <tr>
          <th scope="col" class="py-3 px-6">
            NAME
          </th>
          <th scope="col" class="py-3 px-6">
            EMAIL ADDRESS
          </th>
          <th scope="col" class="py-3 px-6">
            BOAT
          </th>
          <th scope="col" class="py-3 px-6">
            <span class="sr-only">Edit</span>
          </th>
        </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id" class="bg-white bg-neutral-50 text-base hover:bg-neutral-100">
          <th scope="row" class="flex items-center py-4 px-6 text-neutral-700 whitespace-nowrap">
            <img class="w-10 h-10 rounded-full" src="@/assets/img/profile_picture.jpeg" alt="Profile image">
            <div class="pl-3">
              <div class="text-base font-semibold">{{ user.firstName }} {{ user.lastName }}</div>
            </div>
          </th>
          <td class="py-4 px-6">
            {{ user.email }}
          </td>
          <td class="py-4 px-6">
            Titanic
          </td>
          <td class="py-4 px-6">
            <button type="button" @click="deleteUser(user)" class="font-medium text-rose-500 hover:underline float-right">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"/>
              </svg>
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: "ManagerIndex",
  data() {
    return {
      users: [
        {id: 1, firstName: "John", lastName: "Smith", email: "test@mail.com", role: "Operator"},
        {id: 2, firstName: "John", lastName: "Smith", email: "test@mail.com", role: "Operator"},
        {id: 3, firstName: "John", lastName: "Smith", email: "test@mail.com", role: "Operator"},
        {id: 4, firstName: "John", lastName: "Smith", email: "test@mail.com", role: "Operator"},
        {id: 5, firstName: "John", lastName: "Smith", email: "test@mail.com", role: "Operator"},
        {id: 6, firstName: "John", lastName: "Smith", email: "test@mail.com", role: "Operator"},
      ],
    }
  },
  methods: {
    deleteUser(user){
      console.log(user)
    }
  }
}

</script>

<style scoped>

</style>