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
    path: "/event/create",
    name: "EventCreate",
    component: () =>
      import(
        /* webpackChunkName: "event-create" */ "./components/events/CreateEvent.vue"
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
    path: "/events/search",
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
    path: "/favoriteEvent",
    name: "FavoriteEvent",
    component: () =>
      import(
        /* webpackChunkName: "events" */ "./components/events/FavoriteEvent.vue"
      ),
  },
  {
    path: "/browsedEvent",
    name: "BrowsedEvent",
    component: () =>
      import(
        /* webpackChunkName: "events" */ "./components/events/BrowsedEvent.vue"
      ),
  },
  {
    path: "/reservedEvent",
    name: "ReservedEvent",
    component: () =>
      import(
        /* webpackChunkName: "events" */ "./components/events/ReservedEvent.vue"
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
    path: "/forum/discuss/:id/edit",
    name: "ForumDiscussEdit",
    component: () =>
      import(
        /* webpackChunkName: "forum-edit" */ "./components/forum/ForumCreate.vue"
      ),
  },
  {
    path: "/forum/create",
    name: "ForumCreate",
    component: () =>
      import(
        /* webpackChunkName: "forum-create" */ "./components/forum/ForumCreate.vue"
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
  },
  {
    path: "/userInfoRevision",
    name: "UserInfoRevision",
    component: () =>
      import(/* webpackChunkName: "userInfoRevision" */ "./components/user/UserInfoRevision.vue"),
  },
  {
    path: "/console",
    name: "Console",
    component: () =>
      import(/* webpackChunkName: "console" */ "./components/console/AdminConsole.vue"),
  },
  {
    path: "/admin/users",
    name: "ViewAllUsers",
    component: () =>
      import("./components/console/ViewAllUsers.vue"),
  },
  {
    path: "/admin/users/add",
    name: "AddNewUser",
    component: () =>
      import("./components/console/AddNewUser.vue"),
  },
  {
    path: "/admin/events/requests",
    name: "ReviewEventRequests",
    component: () =>
      import("./components/console/ReviewEventRequests.vue"),
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
