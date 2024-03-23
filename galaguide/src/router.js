import {createRouter, createWebHistory} from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'HomePage',
        component: () => import(/* webpackChunkName: "home" */ './components/HomePage.vue')
    },
    /*{
        path: '/about',
        name: 'About',
        component: () => import(/!* webpackChunkName: "about" *!/ './components/About.vue')
    },*/
    {
        path: '/login',
        name: 'UserLogin',
        component: () => import(/* webpackChunkName: "login" */ './components/user/UserLogin.vue')
    },
    {
        path: '/register',
        name: 'UserRegister',
        component: () => import(/* webpackChunkName: "register" */ './components/user/UserRegister.vue')
    },
    {
        path: '/events',
        name: 'EventCenter',
        component: () => import( /* webpackChunkName: "events" */ './components/events/EventCenter.vue')
    },
    {
        path: '/chatroom',
        name: 'ChatRoom',
        component: () => import( /* webpackChunkName: "chatroom" */ './components/chatroom/ChatRoom.vue')
    },
    {
        path: '/inbox',
        name: 'Inbox',
        component: () => import( /* webpackChunkName: "inbox" */ './components/user/UserInbox.vue')
    },
    {
        path: '/logout',
        name: 'UserLogout',
        component: () => import( /* webpackChunkName: "logout" */ './components/user/UserLogout.vue')
    },
    {
        path: '/reservations',
        name: 'ReservationCenter',
        component: () => import( /* webpackChunkName: "reservations" */ './components/reservations/ReserveCenter.vue')
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
