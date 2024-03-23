<script setup>
import { onMounted, ref, computed } from "vue";
import LeftSideBar from "./components/globe/LeftSideBar.vue";
import GlobeFooter from "./components/globe/GlobeFooter.vue";
import AIAssist from "./components/ai/AIAssist.vue";
import { DatePicker } from "v-calendar";
import "v-calendar/style.css";
// import HomePage from "./components/HomePage.vue";

const role = ref(2); // 0 for not logged in, 1 for user, 2 for admin

const hoveredOnCalendar = ref(false);
const hoveredOnAvatar = ref(false);

const delayFalse = (ref, delay) => {
  setTimeout(() => {
    ref = false;
  }, delay);
};

/* can be adjusted at https://vcalendar.io/calendar/attributes.html */

const events = ref([
  {
    description: "Take Noah to basketball practice.",
    isComplete: false,
    dates: { repeat: { weekdays: 5 } }, // Every Friday
    color: "red",
  },
]);

const attributes = computed(() => [
  // Attributes for events
  ...events.value.map((event) => ({
    dates: event.dates,
    dot: {
      color: event.color,
      ...(event.isComplete && { class: "opacity-75" }),
    },
    popover: {
      label: event.description,
      visibility: "hover",
      /*hideIndicator: true,*/
    },
  })),
]);

/* // event style
const date = new Date();
const year = date.getFullYear();
const month = date.getMonth();
const attrs = ref([
    {
        key: 'today',
        highlight: {
            color: 'purple',
            fillMode: 'solid',
            contentClass: 'italic',
        },
        dates: new Date(year, month, 12),
    },
    {
        highlight: {
            color: 'purple',
            fillMode: 'light',
        },
        dates: new Date(year, month, 13),
    },
    {
        highlight: {
            color: 'purple',
            fillMode: 'outline',
        },
        dates: new Date(year, month, 14),
    },
    {
        dot: {
            color: 'green',
            fillMode: 'solid',
        },
        dates: new Date(year, month, 15),
    }
]);*/

function getRoleFromCookie() {
    const cookie = document.cookie.split(';').find(cookie => cookie.trim().startsWith('userRole='))
    console.log('cookie:', cookie)
    if (cookie) {
        const roleValue = cookie.split('=')[1]
        role.value = parseInt(roleValue)
    } else {
        role.value = 1
    }
}

onMounted(() => {
  getRoleFromCookie();
});

function logout() {
    document.cookie = "userRole=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"
    role.value = 0
}
</script>

<template>
  <router-view :role="role" />

  <!--  Top Bar  -->
  <div id="top-bar">
    <div v-if="role === 0">
      <router-link to="/login" class="login" style="color: aliceblue"
        >Login</router-link
      >
      <router-link to="/register" class="register" style="color: aliceblue"
        >Register</router-link
      >
    </div>
    <div v-else-if="role === 1 || role === 2">
      <!--      Help      -->
      <a
        href="/help"
        style="
          position: absolute;
          left: 1%;
          display: flex;
          justify-content: flex-start;
        "
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="30"
          height="30"
          fill="currentColor"
          class="bi bi-question-circle"
          viewBox="-4 -4 20 20"
        >
          <path
            d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"
          />
          <path
            d="M5.255 5.786a.237.237 0 0 0 .241.247h.825c.138 0 .248-.113.266-.25.09-.656.54-1.134 1.342-1.134.686 0 1.314.343 1.314 1.168 0 .635-.374.927-.965 1.371-.673.489-1.206 1.06-1.168 1.987l.003.217a.25.25 0 0 0 .25.246h.811a.25.25 0 0 0 .25-.25v-.105c0-.718.273-.927 1.01-1.486.609-.463 1.244-.977 1.244-2.056 0-1.511-1.276-2.241-2.673-2.241-1.267 0-2.655.59-2.75 2.286zm1.557 5.763c0 .533.425.927 1.01.927.609 0 1.028-.394 1.028-.927 0-.552-.42-.94-1.029-.94-.584 0-1.009.388-1.009.94z"
          />
        </svg>
      </a>

      <!--      Calendar      -->
      <a
        href="#"
        @mouseover="hoveredOnCalendar = true"
        @mouseleave="delayFalse(hoveredOnCalendar, 300)"
        style="
          position: absolute;
          left: 45%;
          transform: translate(-50%, 0);
          display: flex;
          justify-content: flex-start;
        "
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="30"
          height="30"
          fill="currentColor"
          class="bi bi-calendar"
          viewBox="-4 -4 20 20"
        >
          <path
            d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5M1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4z"
          />
        </svg>
      </a>
      <div
        v-if="hoveredOnCalendar"
        @mouseover="hoveredOnCalendar = true"
        @mouseleave="hoveredOnCalendar = false"
        style="
          position: absolute;
          top: 100%;
          left: 45%;
          transform: translate(-50%, 0);
          width: 20%;
          height: 20%;
          box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
          z-index: 100;
        "
      >
        <DatePicker :attributes="attributes" />
      </div>

      <!--      Profile      -->
      <a
        href="#"
        @mouseover="hoveredOnAvatar = true"
        @mouseleave="delayFalse(hoveredOnAvatar, 300)"
        style="
          position: absolute;
          right: 1%;
          display: flex;
          justify-content: flex-start;
        "
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="35"
          height="35"
          fill="currentColor"
          class="bi bi-person-circle"
          viewBox="-2 -2 20 20"
        >
          <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
          <path
            fill-rule="evenodd"
            d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"
          />
        </svg>
      </a>
      <div
        v-if="hoveredOnAvatar"
        @mouseover="hoveredOnAvatar = true"
        @mouseleave="hoveredOnAvatar = false"
        style="
          position: absolute;
          background-color: azure;
          top: 100%;
          left: 92%;
          transform: translate(-50%, 0);
          width: 12%;
          height: 200px;
          box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
          border: 1px solid #ccc !important;
          padding: 14px;
          border-radius: 16px !important;
          z-index: 100;
        "
      >
        <div
          class="avatar"
          style="display: flex; justify-content: center; align-items: center"
        >
          <!-- Avatar code here -->
          <img
            src="https://www.w3schools.com/howto/img_avatar.png"
            alt="Avatar"
            style="
              width: 45px;
              height: 45px;
              border-radius: 50%;
              margin-bottom: 10px;
            "
          />
        </div>
        <div
          style="
            display: flex;
            justify-content: center;
            align-items: center;
            margin-left: 10px;
            font-weight: bold;
            font-size: 1.2em;
          "
        >
          User Name
        </div>
        <hr />

        <div
          class="profile"
          style="
            display: flex;
            margin-left: 5px;
            margin-top: 10px;
            padding: 3px 0;
            text-align: center;
          "
        >
          <div style="margin-right: 16px">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-person-workspace"
              viewBox="0 0 16 16"
            >
              <path
                d="M4 16s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1zm4-5.95a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5"
              />
              <path
                d="M2 1a2 2 0 0 0-2 2v9.5A1.5 1.5 0 0 0 1.5 14h.653a5.4 5.4 0 0 1 1.066-2H1V3a1 1 0 0 1 1-1h12a1 1 0 0 1 1 1v9h-2.219c.554.654.89 1.373 1.066 2h.653a1.5 1.5 0 0 0 1.5-1.5V3a2 2 0 0 0-2-2z"
              />
            </svg>
          </div>
          <router-link
            to="/space"
            style="
              color: black;
              display: inline;
              margin-left: 5px;
              justify-content: center;
            "
            >My Space</router-link
          >
        </div>

        <div
          class="profile"
          style="
            display: flex;
            margin-top: 10px;
            margin-bottom: 5px;
            margin-left: 5px;
            padding: 8x 0;
            text-align: center;
          "
        >
          <div style="margin-right: 21px">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-gear"
              viewBox="0 0 16 16"
            >
              <path
                d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492M5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0"
              />
              <path
                d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115z"
              />
            </svg>
          </div>
          <router-link
            to="/settings"
            style="
              display: inline;
              color: black;
              margin-bottom: 5px;
              margin-left: 5px;
            "
            >Settings</router-link
          >
        </div>

        <hr />

        <div
          class="profile"
          style="display: flex; justify-content: center; align-items: center"
        >
          <router-link
            to="/logout"
            style="
              color: black;
              right: 12%;
              height: 22px;
              width: 50%;
              color: white;
              background-color: #e74c3c;
              border-radius: 5px;
            "
            @click="logout"
            >Logout</router-link
          >
        </div>
      </div>

      <!--      Notification      -->
      <a
        href="#"
        class="notification"
        style="
          position: absolute;
          right: 4.5%;
          display: flex;
          justify-content: flex-start;
        "
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="30"
          height="30"
          fill="currentColor"
          class="bi bi-bell-fill"
          viewBox="-4 -4 20 20"
        >
          <path
            d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z"
          ></path>
        </svg>
      </a>

      <!--      Inbox      -->
      <router-link to="/inbox"
        href="#"
        class="inbox"
        style="
          position: absolute;
          right: 7%;
          display: flex;
          justify-content: flex-start;
        "
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="30"
          height="30"
          fill="currentColor"
          class="bi bi-envelope-fill"
          viewBox="-4 -4 20 20"
        >
          <path
            d="M.05 3.555A2 2 0 0 1 2 2h12a2 2 0 0 1 1.95 1.555L8 8.414.05 3.555ZM0 4.697v7.104l5.803-3.558L0 4.697ZM6.761 8.83l-6.57 4.027A2 2 0 0 0 2 14h12a2 2 0 0 0 1.808-1.144l-6.57-4.027L8 9.586l-1.239-.757Zm3.436-.586L16 11.801V4.697l-5.803 3.546Z"
          />
        </svg>
      </router-link>
    </div>
  </div>

  <LeftSideBar :role="role" />

  <GlobeFooter />
  <AIAssist :role="role"/>
</template>

<style>
@import url("https://fonts.googleapis.com/css2?family=Montserrat&display=swap");

* {
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}

#app {
  /*font-family: Avenir, Helvetica, Arial, sans-serif;*/
  font-family: "Montserrat", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

a {
  color: rgb(43, 54, 58);
  text-decoration: none;
}

a:hover {
  color: rgb(115, 201, 230);
  /*text-decoration: underline;*/
}

body {
  background-color: #e5e1db;
  display: flex;
  flex-direction: column;
}

#top-bar {
  position: absolute;
  top: 0;
  left: 10%;
  width: 90%;
  height: 5%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  z-index: 100;
}

.login,
.register {
  position: absolute;
  top: 50%;
  height: 80%;
  transform: translateY(-50%);
  color: #2c3e50;
  font-size: 0.9em;
  margin-left: 20px;
  margin-right: 20px;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
}

.login {
  right: 12%;
  width: 10%;
  background-color: #23b375;
  border-radius: 5px;
}

.register {
  right: 1%;
  width: 10%;
  background-color: #2a78c7;
  border-radius: 5px;
}

.login:hover {
  background-color: #1f9e64;
}

.register:hover {
  background-color: #2467c4;
}

.notification {
  color: cadetblue;
}

.notification:hover {
  color: LightSeaGreen;
}

.inbox {
  color: cadetblue;
}

.inbox:hover {
  color: LightSeaGreen;
}

::-webkit-scrollbar {
  display: none;
}
</style>
