<script setup>
import { onMounted, ref, computed } from "vue";
import axios from "axios";
import LeftSideBar from "./components/globe/LeftSideBar.vue";
import GlobeFooter from "./components/globe/GlobeFooter.vue";
import AIAssist from "./components/ai/AIAssist.vue";
import { DatePicker } from "v-calendar";
import "v-calendar/style.css";

// import HomePage from "./components/HomePage.vue";

const role = ref(0); // 0 for not logged in, 1 for user, 2 for admin
let user = {
  userName: "",
  userAvatar: "",
  userBackground: "",
  userEmail: "",
  userGuiro: 0,
  userBio: "",
  userFavoriteEvents: [],
  userReservedEvents: [],
  userBrowsedEvents: [],
};

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

function checkIn() {
  console.log(localStorage.getItem("token"));
  const options = {
    method: 'POST',
    url: '/api/user/sign-in',
    headers: {
      authorization: `Bearer ${localStorage.getItem("token")}`,
      'content-type': 'application/json'
    },
    data: {}
  };
  axios.request(options).then(function (response) {
    console.log(response.data);
  }).catch(function (error) {
    console.error(error);
  });

  axios
    .get('/api/user', {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
        }
    }).then((response) => {
    console.log("response:", response);
    if (response.status === 200 && response.data.code === 0) {
      if (user.userGuiro !== response.data.data.guiro) {
        user.userGuiro = response.data.data.guiro;
        window.location.reload();
      }
    }
  })
  .catch((error) => {
    console.log("error:", error);
  });
}

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
  console.log("document.cookie:", document.cookie);
  const cookie = document.cookie
    .split(";")
    .find((cookie) => cookie.trim().startsWith("userRole="));
  console.log("cookie:", cookie);
  if (cookie) {
    const roleValue = cookie.split("=")[1];
    console.log("roleValue:", roleValue);
    role.value = parseInt(roleValue);
  } else {
    role.value = 0;
  }
}

function getUserInfo() {
  console.log("document.cookie:", document.cookie);
  const cookie = document.cookie
    .split(";")
    .find((cookie) => cookie.trim().startsWith("userRole="));
  console.log("cookie:", cookie);
  if (cookie && cookie.split("=")[1] !== "0") {
    console.log("cookie:", cookie);
    console.log("Authorization:", `Bearer ${localStorage.getItem('token')}`);
    axios
    .get('/api/user', {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
        }
    })
    .then((response) => {
      console.log("response:", response);
      if (response.status === 200 && response.data.code === 0) {
        user.userName = response.data.data.name;
        user.userGuiro = response.data.data.guiro;
      }
    })
    .catch((error) => {
      console.log("error:", error);
    });
  }
}

function getUserEvents() {
  console.log("document.cookie:", document.cookie);
  const cookie = document.cookie
    .split(";")
    .find((cookie) => cookie.trim().startsWith("userRole="));
  console.log("cookie:", cookie);
  if (cookie && cookie.split("=")[1] !== "0") {
    console.log("cookie:", cookie);
    axios
    .get('/api/reserve/mine', {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
        }
    })
    .then((response) => {
      console.log("response:", response);
      if (response.status === 200 && response.data.code === 0) {
        events.value = response.data.data.map((event) => {
          return {
            description: event.name,
            isComplete: false,
            dates: { start: new Date(event.start), end: new Date(event.end) },
            color: "red",
          };
        });
      }
    })
    .catch((error) => {
      console.log("error:", error);
    });
  }
}

onMounted(() => {
  getRoleFromCookie();
  getUserInfo();
  getUserEvents();
});

function logout() {
  document.cookie = "userRole=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
  role.value = 0;
}
</script>

<template>
  

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
      <router-link
        to="/help"
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
      </router-link>

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
        <router-link to="/space">
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
        </router-link>
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
          height: 160px;
          box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
          border: 1px solid #ccc !important;
          padding: 14px;
          border-radius: 16px !important;
          z-index: 100;
        "
        id="pop-up"
      >
        <div
          style="
            display: flex;
            justify-content: center;
            align-items: center;
            margin-left: 10px;
            font-weight: bold;
            font-size: 1.2em;
            margin-top:10px;
          "
        >
          {{ user.userName }}
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
              width="20"
              height="20"
              fill="currentColor"
              class="bi bi-cash-coin"
              viewBox="-4 -4 20 20"
            >
              <path
                fill-rule="evenodd"
                d="M11 15a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm5-4a5 5 0 1 1-10 0 5 5 0 0 1 10 0z"
              />
              <path
                d="M9.438 11.944c.047.596.518 1.06 1.363 1.116v.44h.375v-.443c.875-.061 1.386-.529 1.386-1.207 0-.618-.39-.936-1.09-1.1l-.296-.07v-1.2c.376.043.614.248.671.532h.658c-.047-.575-.54-1.024-1.329-1.073V8.5h-.375v.45c-.747.073-1.255.522-1.255 1.158 0 .562.378.92 1.007 1.066l.248.061v1.272c-.384-.058-.639-.27-.696-.563h-.668zm1.36-1.354c-.369-.085-.569-.26-.569-.522 0-.294.216-.514.572-.578v1.1h-.003zm.432.746c.449.104.655.272.655.569 0 .339-.257.571-.709.614v-1.195l.054.012z"
              />
              <path
                d="M1 0a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h4.083c.058-.344.145-.678.258-1H3a2 2 0 0 0-2-2V3a2 2 0 0 0 2-2h10a2 2 0 0 0 2 2v3.528c.38.34.717.728 1 1.154V1a1 1 0 0 0-1-1H1z"
              />
              <path
                d="M9.998 5.083 10 5a2 2 0 1 0-3.132 1.65 5.982 5.982 0 0 1 3.13-1.567z"
              />
            </svg>
          </div>
          <span style="color: black; display: inline; justify-content: center; margin-left: 10px;">
            Guiro: {{ user.userGuiro }}
          </span>
        </div>

        <div
          class="profile"
          style="
            display: flex;
            margin-top: 10px;
            margin-bottom: 5px;
            margin-left: 33px;
            padding: 8x 0;
            text-align: center;
          "
        >
          <div style="margin-right: 21px">
            <!-- add check in button -->
            <button
              @click="checkIn"
              class="check-in-button"
              >
              Check In
            </button>
          </div>
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
          right: 5.5%;
          display: flex;
          justify-content: flex-start;
        "
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="30"
          height="30"
          fill="currentColor"
          class="bi bi-bell"
          viewBox="-4 -4 20 20"
        >
          <path
            d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2M8 1.918l-.797.161A4 4 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4 4 0 0 0-3.203-3.92zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5 5 0 0 1 13 6c0 .88.32 4.2 1.22 6"
          />
        </svg>
      </a>

      <!--      Inbox      -->
      <router-link
        to="/inbox"
        href="#"
        class="inbox"
        style="
          position: absolute;
          right: 9%;
          display: flex;
          justify-content: flex-start;
        "
      >
        <svg
          width="30"
          height="30"
          viewBox="-4 -4 20 20"
          fill="currentColor"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M0 4C0 2.89543 0.895431 2 2 2H14C15.1046 2 16 2.89543 16 4V12C16 13.1046 15.1046 14 14 14H2C0.895431 14 0 13.1046 0 12V4ZM2 3C1.44772 3 1 3.44772 1 4V4.2169L8 8.4169L15 4.2169V4C15 3.44772 14.5523 3 14 3H2ZM15 5.3831L10.2919 8.20794L15 11.1052V5.3831ZM14.9662 12.2586L9.32583 8.7876L8 9.5831L6.67417 8.7876L1.03376 12.2586C1.14774 12.6855 1.53715 13 2 13H14C14.4628 13 14.8523 12.6855 14.9662 12.2586ZM1 11.1052L5.70808 8.20794L1 5.3831V11.1052Z"
          />
        </svg>
      </router-link>

      <!--      Favorite Events      -->
      <router-link
        to="/favoriteEvent"
        href="#"
        class="favorite-events"
        style="
          position: absolute;
          right: 12.5%;
          display: flex;
          justify-content: flex-start;
        "
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="30"
          height="30"
          fill="currentColor"
          class="bi bi-heart"
          viewBox="-4 -4 20 20"
        >
          <path
            d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15"
          />
        </svg>
      </router-link>

      <!-- Browsed Event -->
      <router-link
        to="/browsedEvent" 
        href="#" 
        class="browsed-events" 
        style="
          position: absolute;
          top: 12%; 
          right: 16%;
          display: flex;
          justify-content: flex-start;
        "
      > 
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512" width="26" height="26">
          <path fill="var(--ci-primary-color, currentColor)" d="M256.25,16A240,240,0,0,0,88,84.977V16H56V144H184V112H106.287A208,208,0,0,1,256.25,48C370.8,48,464,141.2,464,255.75S370.8,463.5,256.25,463.5,48.5,370.3,48.5,255.75h-32A239.75,239.75,0,0,0,425.779,425.279,239.75,239.75,0,0,0,256.25,16Z" class="ci-primary"/>
          <polygon fill="var(--ci-primary-color, currentColor)" points="240 111.951 239.465 288 368 288 368 256 271.563 256 272 112.049 240 111.951" class="ci-primary"/>
        </svg> 
      </router-link>

      <!-- Reserved Event -->
      <router-link
        to="/reservedEvent" 
        href="#" 
        class="reserved-events" 
        style="
          position: absolute;
          right: 19.5%;
          display: flex;
          justify-content: flex-start;
        "
      > 
      <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-cart2" viewBox="-2 -2 18 18">
        <path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5M3.14 5l1.25 5h8.22l1.25-5zM5 13a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0m9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0"/>
      </svg>
      </router-link>

    </div>
  </div>
  <router-view :role="role" />

  <LeftSideBar :role="role" />

  <GlobeFooter />
  <AIAssist :role="role" />
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

.notification:hover {
  color: LightSeaGreen;
}

.inbox:hover {
  color: LightSeaGreen;
}

.favorite-events:hover {
  color: LightSeaGreen;
}

.browsed-events:hover {
  color: LightSeaGreen;
}

.reserved-events:hover {
  color: LightSeaGreen;
}

.check-in-button {
  background-color: #2a78c7;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  font-size: 1em;
  font-weight: bold;
  cursor: pointer;
  padding-left: 10px;
}

.check-in-button:hover {
  background-color: #2467c4;
}

::-webkit-scrollbar {
  display: none;
}

#pop-up {
  animation: fadeIn 0.5s;
  animation-fill-mode: forwards;
  animation-timing-function: ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    overflow: hidden;
    scale: 0;
  }
  to {
    opacity: 1;
    overflow: hidden;
    scale: 1;
  }
}
</style>
