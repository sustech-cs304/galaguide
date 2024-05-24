import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "HomePage",
    component: () =>
      import(/* webpackChunkName: "home" */ "./components/HomePage.vue"),
  },
  /*{
        path: '/about',
        name: 'About',
        component: () => import(/!* webpackChunkName: "about" *!/ './components/About.vue')
    },*/
  {
    path: "/login",
    name: "UserLogin",
    component: () =>
      import(/* webpackChunkName: "login" */ "./components/user/UserLogin.vue"),
  },
  {
    path: "/register",
    name: "UserRegister",
    component: () =>
      import(
        /* webpackChunkName: "register" */ "./components/user/UserRegister.vue"
      ),
  },
  {
    path: "/events",
    name: "EventCenter",
    component: () =>
      import(
        /* webpackChunkName: "events" */ "./components/events/EventCenter.vue"
      ),
  },
  {
    path: "/search",
    name: "SearchCenter",
    component: () =>
      import(
        /* webpackChunkName: "search" */ "./components/events/SearchHomePage/SearchCenter.vue"
      ),
  },
  {
    path: "/events/search/:keyword",
    name: "SearchResult",
    component: () =>
      import(
        /* webpackChunkName: "search" */ "./components/events/EventSearch.vue"
      ),
  },
  {
    path: "/events/:eventId",
    name: "EventDetail",
    component: () =>
      import(
        /* webpackChunkName: "event-details" */ "@/components/reservations/EventDetail.vue"
      ),
  },
  {
    path: "/favorite",
    name: "FavoriteEvent",
    component: () =>
      import(
        /* webpackChunkName: "events" */ "./components/events/FavoriteEvent.vue"
      ),
  },
  {
    path: "/chatroom",
    name: "ChatRoom",
    component: () =>
      import(
        /* webpackChunkName: "chatroom" */ "./components/chatroom/ChatRoom.vue"
      ),
  },
  {
    path: "/inbox",
    name: "Inbox",
    component: () =>
      import(/* webpackChunkName: "inbox" */ "./components/user/UserInbox.vue"),
  },
  {
    path: "/logout",
    name: "UserLogout",
    component: () =>
      import(
        /* webpackChunkName: "logout" */ "./components/user/UserLogout.vue"
      ),
  },
  {
    path: "/reservations",
    name: "ReservationCenter",
    component: () =>
      import(
        /* webpackChunkName: "reservations" */ "./components/reservations/ReserveCenter.vue"
      ),
  },
  {
    path: "/space",
    name: "UserSpace",
    component: () =>
      import(/* webpackChunkName: "space" */ "./components/user/UserSpace.vue"),
  },
  {
    path: "/forum",
    name: "ForumHome",
    component: () =>
      import(
        /* webpackChunkName: "forum" */ "./components/forum/ForumHome.vue"
      ),
  },
  {
    path: "/forum/discuss/:id",
    name: "ForumDiscuss",
    component: () =>
      import(
        /* webpackChunkName: "forum-details" */ "./components/forum/ForumDiscuss.vue"
      ),
  },
  {
    path: "/help",
    name: "HelpCenter",
    component: () =>
      import(/* webpackChunkName: "help" */ "./components/help/HelpCenter.vue"),
  },
  {
    path: "/space/:userId",
    name: "UserSpaceWithId",
    component: () =>
      import(/* webpackChunkName: "space" */ "./components/user/UserSpace.vue"),
  },
  {
    path: "/map",
    name: "MapCenter",
    component: () =>
      import(/* webpackChunkName: "map" */ "./components/map/MapCenter.vue"),
  },
  {
    path: "/image-host",
    name: "ImageHost",
    component: () =>
      import(/* webpackChunkName: "image-host" */ "./components/image/ImageHost.vue"),
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
