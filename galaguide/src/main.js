import { createApp } from 'vue'
import App from './App.vue'
import router from './router';
import { Vue3SeamlessScroll } from 'vue3-seamless-scroll'

const app = createApp(App)
app.use(Vue3SeamlessScroll)
app.use(router)
app.mount('#app')
