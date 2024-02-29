import { createApp } from 'vue'
import App from './App.vue'
import router from './router';
import Vue3Marquee from 'vue3-marquee'
import router from './router'

import { setupCalendar, Calendar, DatePicker } from 'v-calendar';
import 'v-calendar/style.css';

const app = createApp(App)
app.use(router)
app.use(Vue3Marquee)
app.use(router)
app.mount('#app')

// Use plugin defaults (optional)
app.use(setupCalendar, {})

// Use the components
app.component('VCalendar', Calendar)
app.component('VDatePicker', DatePicker)
