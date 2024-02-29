import { createRouter, createWebHistory } from "vue-router";
import LeftSideBar from '../components/LeftSideBar.vue'
import EventCenter from '../components/EventCenter.vue'


const routes = [
    {
        path: '/',
        name: 'LeftSideBar',
        component: LeftSideBar,
      },
      {
        path: '/events',
        name: 'Events',
        component: EventCenter,
      },
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
});

export default router