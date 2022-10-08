<template>
  <div class="mt-6">
    <h1 class="text-xl font-bold">Profile</h1>
    <!--Profile picture and bio-->
    <div class="flex space-x-4">
      <div class="bg-white flex-1 h-full rounded-2xl p-4">
        <div class="profile-picture flex inline-block items-center">
          <img class="w-20 h-20 object-none object-top rounded-2xl mr-6" src="@/assets/img/profile_picture.jpeg" alt=""
               style="object-fit: cover">
          <div>
            <h2 class="text-4xl font-bold text-slate-900"> John Smith</h2>
            <p class="text-slate-500">I wanna see the ship that ships all the ship shipping ships</p>
          </div>
        </div>
        <!--User info, email-->
        <div class="grid grid-cols-4 gap-4 pt-6 pb-6">
          <div class="flex space-x-4">
            <div class="w-12 h-12 bg-sky-100 rounded-lg text-sky-700 flex items-center justify-center">
              <svg class="w-8 h-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5}
                   stroke="#2D689D" className="w-6 h-6">
                <path strokeLinecap="round" strokeLinejoin="round"
                      d="M21.75 6.75v10.5a2.25 2.25 0 01-2.25 2.25h-15a2.25 2.25 0 01-2.25-2.25V6.75m19.5 0A2.25 2.25 0 0019.5 4.5h-15a2.25 2.25 0 00-2.25 2.25m19.5 0v.243a2.25 2.25 0 01-1.07 1.916l-7.5 4.615a2.25 2.25 0 01-2.36 0L3.32 8.91a2.25 2.25 0 01-1.07-1.916V6.75"/>
              </svg>
            </div>
            <div>
              <p class="text-slate-700">{{ email }} test@mail.com</p>
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
                <p class="text-slate-700">{{ birthday }} 13 July 2000</p>
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
                <p class="text-slate-700">{{ "" }} Captain</p>
                <p class="text-slate-400">Function</p>
              </div>

            </div>
          </div>
        </div>
      </div>

      <div class="bg-white w-96 rounded-2xl p-4">
        <div class="flex space-x-2">
          <h2 class="font-bold text-2xl text-slate-900">Notifications</h2>
          <span class="bg-sky-100 text-sky-700 pt-1 pb-1 pl-2 pr-2 rounded-md font-bold">{{ notifAmount }} 2</span>
        </div>
        <svg class="text-sky-500 w-6 h-6 stroke-1.5" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="w-6 h-6">
          <path strokeLinecap="round" strokeLinejoin="round" d="M11.25 11.25l.041-.02a.75.75 0 011.063.852l-.708 2.836a.75.75 0 001.063.853l.041-.021M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-9-3.75h.008v.008H12V8.25z" />
        </svg>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProfileIndex"
}
</script>

<style scoped>

</style>