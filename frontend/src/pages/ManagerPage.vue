<template>
  <div class="mt-6">
    <h1 class="text-4xl text-neutral-800 font-bold">
      Manager panel
    </h1>
    <div class="overflow-x-auto relative shadow-md sm:rounded-lg mt-8">
      <table class="w-full text-sm text-left text-neutral-700">
        <caption class="p-5 text-neutral-700 bg-neutral-50">
          <h2 class="float-left text-3xl font-bold">Operators</h2>
          <button
              name="newUserButton"
              @click="showNewUserModal = true"
              class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm sm:w-auto px-5 py-2.5 text-center transition-colors flex float-right items-center">
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
            SHIP
          </th>
          <th scope="col" class="py-3 px-6">
            <span class="sr-only">Edit</span>
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="operator in operators" :key="operator.id" class="bg-neutral-50 text-base hover:bg-neutral-100">
          <th scope="row" class="flex items-center py-4 px-6 text-neutral-700 whitespace-nowrap">
            <img class="w-10 h-10 rounded-full" src="@/assets/img/profile_picture.jpeg" alt="Profile image">
            <div class="pl-3">
              <div class="text-base font-semibold">{{ operator.firstName }} {{ operator.lastName }}</div>
            </div>
          </th>
          <td class="py-4 px-6">
            {{ operator.email }}
          </td>
          <td class="py-4 px-6">
            {{ operator.ship?.name }}
          </td>
          <td class="py-4 px-6">

            <button name="deleteUserButton" type="button" @click="deleteUser(operator)"
                    class="font-medium text-rose-500 hover:underline float-right">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"/>
              </svg>
            </button>
            <button type="button" @click="editUser(operator)"
                    name="editUserButton"
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
    <p class="hidden mt-5 text-sm font-medium text-rose-500" id="operatorError">Something went wrong while adding a new operator
    </p>
  </div>

  <div class="mt-6">
    <div class="overflow-x-auto relative shadow-md sm:rounded-lg mt-8">
      <table class="w-full text-sm text-left text-neutral-700">
        <caption class="p-5 text-neutral-700 bg-neutral-50">
          <h2 class="float-left text-3xl font-bold">Ships</h2>
          <button
              name="newShipButton"
              @click="showNewShipModal = true"
              class="text-white bg-primary-500 hover:bg-primary-600 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm sm:w-auto px-5 py-2.5 text-center transition-colors flex float-right items-center">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor" class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round" d="M12 6v12m6-6H6"/>
            </svg>
            Add Ship
          </button>
        </caption>
        <thead class="text-sm text-neutral-900 uppercase bg-neutral-50 border-t-2 border-neutral-200">
        <tr>
          <th scope="col" class="py-3 px-6">
            NAME
          </th>
          <th scope="col" class="py-3 px-6">
            SHIP-ID
          </th>
          <th scope="col" class="py-3 px-6">
            OPERATORS
          </th>
          <th scope="col" class="py-3 px-6">
            <span class="sr-only">DELETE</span>
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="ship in ships" :key="ship.id" class="bg-neutral-50 text-base hover:bg-neutral-100">
          <th scope="row" class="flex items-center py-4 px-6 text-neutral-700 whitespace-nowrap">
            <img class="w-10 h-10 rounded-full" src="@/assets/img/profile_picture.jpeg" alt="Profile image">
            <div class="pl-3">
              <div class="text-base font-semibold">{{ ship.name }}</div>
            </div>
          </th>
          <td class="py-4 px-6">
            {{ ship.smartShipId }}
          </td>
          <td class="py-4 px-6">
            <button type="button" @click="showOperatorsForShip(ship)"
                    name="showShipOperatorsButton"
                    class="font-medium text-neutral-700 hover:underline mr-4">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M2.036 12.322a1.012 1.012 0 010-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178z" />
                <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
            </button>
          </td>
          <td class="py-4 px-6">
            <button type="button" @click="deleteShip(ship)"
                    name="deleteShipButton"
                    class="font-medium text-rose-500 hover:underline float-right">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                   stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0"/>
              </svg>
            </button>

            <button type="button" @click="editShipSensors(ship)"
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
    <p class="hidden mt-5 text-sm font-medium text-rose-500" id="shipError">Something went wrong while adding a new Ship
    </p>
  </div>

  <add-operator-modal v-model="showNewUserModal" v-on:close="showNewUserModal=false"
                      v-on:add="(email, firstName, lastName, password, assignedShip) => addUser(email, firstName, lastName, password, assignedShip)"
  ></add-operator-modal>

  <edit-account-modal v-model="showEditUserModal" v-on:close="showEditUserModal=false" v-bind:toEditUser="this.toEditUser"
                      v-on:save="(operator, assignedShip) => saveUser(operator, assignedShip)"
  ></edit-account-modal>

  <add-ship-modal v-model="showNewShipModal" v-on:close="showNewShipModal=false"
                      v-on:add="(name, shipId) => addShip(name, shipId)"
  ></add-ship-modal>

  <show-operators-for-ship-modal v-model="showOperatorsShipModal"
                                 v-on:close="showOperatorsShipModal=false"
                                 v-bind:operatorsForShip="this.operatorsForShip"
  ></show-operators-for-ship-modal>

  <EditShipSensorsModal v-model="showEditShipSensorsModal"  v-on:close="showEditShipSensorsModal=false" :ship="this.toEditShipSensors" />
</template>

<script>

import AddOperatorModal from "@/components/modals/AddAccountModal";
import EditAccountModal from "@/components/modals/EditAccountModal";
import AddShipModal from "@/components/modals/AddShipModal";
import showOperatorsForShipModal from "@/components/modals/showOperatorsForShipModal";
import EditShipSensorsModal from "@/components/modals/EditShipSensorsModal";

import emailjs from 'emailjs-com';
import User from "@/models/User";

export default {
  name: "ManagerIndex",
  inject: ['sessionService','userManagementService', 'shipService'],
  components: {AddOperatorModal, EditAccountModal, AddShipModal, showOperatorsForShipModal, EditShipSensorsModal},
  async created() {
    //If the user isn't a manager, send the user to the dashboard
    if (this.sessionService.getCurrentUser().role !== 'Manager') {
      this.$router.push(this.$route.matched[0].path)
    }
    //Get all the operators from the database via the backend
    this.operators = await this.userManagementService.findAccountForRole(User.ROLE.Operator);
    //Get all the ships from the database via the backend
    this.ships = await this.shipService.getAllShips();
  },
  data() {
    return {
      showNewUserModal: false,
      showEditUserModal: false,
      showNewShipModal: false,
      showOperatorsShipModal: false,
      toEditUser: null,
      operatorsForShip: null,
      operators: [],
      ships: [],


      toEditShipSensors: null,
      showEditShipSensorsModal: false
    }
  },
  methods: {
    async refresh() {
      //Get all the operators from the database via the backend.
      this.operators = await this.userManagementService.findAccountForRole(User.ROLE.Operator);
      //Get all the ships from the database via the backend
      this.ships = await this.shipService.getAllShips();
    },
    async deleteUser(user) {
      //Show a popup window asking if they really want to delete the user.
      const userSave =  confirm('Are you sure you want to delete ' + user.firstName + ' ' + user.lastName
          + ' (id:' + user.id + ')?');
      //Delete the user if they pressed confirm and call the refresh method, which gets the operators again.
      if (userSave) {
        await this.userManagementService.deleteUserById(user.id);
        await this.refresh();
      }
    },
    async deleteShip(ship) {
      //Show a popup window asking if they really want to delete the ship.
      const shipSave = confirm('Are you sure you want to delete the ' + ship.name
          + ' (id:' + ship.id + ')?');
      //Delete the ship if they pressed confirm and call the refresh method, which gets the ships again.
      if (shipSave) {
        const operatorsForDeletableShip = await this.userManagementService.getOperatorsForShip(ship);
        if (operatorsForDeletableShip != null) {
          for (let i = 0; i < operatorsForDeletableShip.length; i++) {
            await this.userManagementService.updateUser(operatorsForDeletableShip[i], null)
          }
        }
        await this.shipService.deleteShipById(ship.id);
        await this.refresh();
      }
    },
    async addUser(email, firstName, lastName, password, assignedShip) {

      //Make a regex object that is used to check if the given email is valid.
      const emailRegex =
          new RegExp(/^[A-Za-z0-9_!#$%&'*+/=?`{|}~^.-]+@[A-Za-z0-9.-]+$/, "gm");

      //Check if everything is filled in and the email is valid.
      if (emailRegex.test(email) && firstName !== '' && lastName !== '' && password !== '' && assignedShip !== '') {
        let error = document.getElementById('operatorError');

        //Add a new operator with the given data via the backend
        const addedUser = await this.userManagementService.addAccount(email, firstName, lastName, password, User.ROLE.Operator, assignedShip);

        //Show an error message if a new user didn't get created.
        if (addedUser == null) {
          error.classList.remove('hidden');
        } else {
          error.classList.add('hidden');

          let emailParams = {
            name: addedUser.firstName,
            email: addedUser.email,
            password: password
          };

          //Send an email to the new user.
          await emailjs.send("service_i66vivu", "template_ufzdlte", emailParams, "LB6axeycasCvaughh")
              .then(function(response) {
                console.log('SUCCESS!', response.status, response.text);
              }, function(error) {
                console.log('FAILED...', error);
              });

        }

        //Call the refresh to get the operators again.
        await this.refresh();
      }
    },
    async addShip(name, shipId) {
      //Check if everything is filled in.
      if (name !== '' && shipId !== '') {
        let error = document.getElementById('shipError');

        //Add a new ship with the given data via the backend
        const addedShip = await this.shipService.addShip(name, shipId);

        //Show an error message if a new ship didn't get created.
        if (addedShip == null) {
          error.classList.remove('hidden');
        } else {
          error.classList.add('hidden');
        }

        //Call the refresh to get the ships again.
        await this.refresh();
      }
    },
    editUser(operator) {
      this.toEditUser = operator;
      this.showEditUserModal = true;
    },
    showOperatorsForShip(ship) {
      this.operatorsForShip = ship;
      this.showOperatorsShipModal = true;
    },
    async saveUser(operator, assignedShip) {
      await this.userManagementService.updateUser(operator, assignedShip)
      await this.refresh();
    },
    editShipSensors(ship){
      this.toEditShipSensors = ship;
      this.showEditShipSensorsModal = true;
    }
  }
}

</script>

<style scoped>

</style>