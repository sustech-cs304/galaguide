import { createApp } from 'vue'
import App from './App.vue'
import Vue3Marquee from 'vue3-marquee'

const app = createApp(App)
app.use(Vue3Marquee)
app.mount('#app')
