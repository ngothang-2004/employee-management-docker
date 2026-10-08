import { createApp } from 'vue'
import AdminLteVue from '@adminlte/vue'

import '@adminlte/vue/css'
import 'bootstrap-icons/font/bootstrap-icons.css'
import 'bootstrap'

import App from './App.vue'

createApp(App)
  .use(AdminLteVue)
  .mount('#app')