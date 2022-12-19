<template>
  <div class="mt-6">
    <h1 class="text-4xl text-neutral-800 font-bold">
      Admin panel
    </h1>
    <div class="overflow-x-auto relative shadow-md sm:rounded-lg mt-8">
      <table class="w-full text-sm text-left text-neutral-700">
        <caption class="p-5 text-neutral-700 bg-neutral-50">
          <h2 class="float-left text-3xl font-bold">Managers</h2>
          <button
              @click="showNewUserModal = true"
              class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300
              font-medium rounded-lg text-sm sm:w-auto px-5 py-2.5 text-center transition-colors
              flex float-right items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v12m6-6H6"/>
            </svg>
            Add Manager
          </button>
        </caption>
        <thead class="text-sm text-neutral-900 uppercase bg-neutral-50 border-t-2 border-neutral-200">
        <tr>
          <th scope="col" class="py-3 px-6">
            NAME
          </th>
          <th scope="col" class="py-3 px-6S">
            EMAIL ADDRESS
          </th>
          <th scope="col" class="py-3 px-6">
            <span class="sr-only">Edit</span>
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="manager in managers" :key="manager.id" class="bg-neutral-50 text-base hover:bg-neutral-100">
          <th scope="row" class="flex items-center py-4 px-6 text-neutral-700 whitespace-nowrap">
            <img class="w-10 h-10 rounded-full" src="@/assets/img/profile_picture.jpeg" alt="Profile image">
            <div class="pl-3">
              <div class="text-base font-semibold">{{ manager.firstName }} {{ manager.lastName }}</div>
            </div>
          </th>
          <td class="py-4 px-6">
            {{ manager.email }}
          </td>
          <td class="py-4 px-6">
            <button type="button" @click="deleteUser(manager)"
                    class="font-medium text-rose-500 hover:underline float-right">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"/>
              </svg>
            </button>
            <button type="button" @click="editUser(manager)"
                    class="font-medium text-neutral-700 hover:underline float-right mr-4">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
              </svg>
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <p class="hidden mt-5 text-sm font-medium text-rose-500" id="error">Something went wrong while adding a new manager
    </p>
  </div>

  <add-account-modal v-model="showNewUserModal" v-on:close="showNewUserModal=false"
                     v-on:add="(email, firstName, lastName, password) => addUser(email, firstName, lastName, password)"
  ></add-account-modal>

  <edit-account-modal v-model="showEditUserModal" v-on:close="showEditUserModal=false" v-bind:toEditUser="this.toEditUser"
                      v-on:save="(operator) => saveUser(operator)"
  ></edit-account-modal>

</template>

<script>

import AddAccountModal from "@/components/modals/AddAccountModal";
import EditAccountModal from "@/components/modals/EditAccountModal";
import emailjs from 'emailjs-com';
import User from "@/models/User";

export default {
  name: "AdminIndex",
  inject: ['sessionService', 'userManagementService'],
  components: {AddAccountModal, EditAccountModal},
  async created() {
    //If the user isn't an admin, send the user to the dashboard
    // if (this.sessionService.getCurrentUser().role !== 'Admin') {
    //   this.$router.push(this.$route.matched[0].path)
    // }
    //Get all the managers from the database via the backend
    this.managers = await this.userManagementService.findAccountForRole(User.ROLE.Manager);
  },
  data() {
    return {
      showNewUserModal: false,
      showEditUserModal: false,
      toEditUser: null,
      managers: [],
    }
  },
  methods: {
    async refresh() {
      //Get all the managers from the database via the backend.
      this.managers = await this.userManagementService.findAccountForRole(User.ROLE.Manager);
    },
    async deleteUser(user) {
      //Show a popup window asking if they really want to delete the user.
      const userSave = confirm('Are you sure you want to delete ' + user.firstName + ' ' + user.lastName
          + ' (id: ' + user.id + ')?');
      //Delete the user if they pressed confirm and call the refresh method, which gets the managers again.
      if (userSave) {
        await this.userManagementService.deleteUserById(user.id);
        await this.refresh();
      }
    },
    async addUser(email, firstName, lastName, password) {

      //Make a regex object that is used to check if the given email is valid.
      const emailRegex =
          new RegExp(/^[A-Za-z0-9_!#$%&'*+/=?`{|}~^.-]+@[A-Za-z0-9.-]+$/, "gm");

      //Check if everything is filled in and the email is valid.
      if (emailRegex.test(email) && firstName !== '' && lastName !== '' && password !== '') {
        let error = document.getElementById('error');

        //Add a new manager with the given data via the backend
        const addedUser = await this.userManagementService.addAccount(email, firstName, lastName, password, User.ROLE.Manager, '');

        //Show an error message if a new user didn't get created.
        if (addedUser == null) {
          error.classList.remove('hidden');
        } else {
          error.classList.add('hidden');

          let emailParams = {
            name: addedUser.firstName,
            email: addedUser.email
          };

          await emailjs.send("service_i66vivu", "template_ufzdlte", emailParams, "LB6axeycasCvaughh")
              .then(function (response) {
                console.log('SUCCESS!', response.status, response.text);
              }, function (error) {
                console.log('FAILED...', error);
              });

        }

        //Call the refresh to get the managers again.
        await this.refresh();
      }

    },
    editUser(manager) {
      this.toEditUser = manager;
      this.showEditUserModal = true;
    },
    async saveUser(manager) {
      await this.userManagementService.updateUser(manager)
      await this.refresh();
    }
  }
}

</script>

<style scoped>

</style>