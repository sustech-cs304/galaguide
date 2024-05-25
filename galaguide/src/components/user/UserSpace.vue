<template>
  <div id="user-space">
    <div class="space">
      <div class="space-header">
        <img
          :src="user.userAvatarId"
          alt="user photo"
          style="
            position: absolute;
            left: 14.5%;
            top: 30.5%;
            border-radius: 50px;
          "
        />
        <h2 class="user-name" style="position: absolute; left: 22.5%; top: 28%">
          {{ user.userName }}
          
        </h2>
        <h4 class="user-email" style="position: absolute; left: 22.5%; top: 33%">
          {{ user.userEmail }}
        </h4>
        <p class="user-intro" style="position: absolute; left: 22.5%; top: 38%">
          {{ user.userIntro }}
        </p>
      </div>

      <div class="revise-button" @click="revise" style="position: absolute; top: 36%; left: 91%;">
        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16">
          <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
          <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
        </svg>
      </div>

      <div class="space-main" style="display: flex; flex-direction: column">
        <div class="space-main-event">
          <div class="space-main-bar">
            <h2>Favorite Events</h2>
            <router-link to="/favoriteEvent"> <h4>View All</h4></router-link>
          </div>
        </div>

        <div class="cards">
          <div class="card" v-for="event in user.userBrowsedEvents" :key="event.title">
            <router-link :to="event.link"> 
              <h3>{{ event.title }}</h3>
            </router-link>
            <p>{{ event.content }}</p>
          </div>
        </div>

        <div class="space-main-event">
          <div class="space-main-bar">
            <h2>Browsed Events</h2>
            <router-link to="/browsedEvent"> 
              <h4>View All</h4>
            </router-link>
          </div>
        </div>

        <div class="cards">
          <div class="card" v-for="event in user.userFavoriteEvents" :key="event.title">
            <h3>{{ event.title }}</h3>
            <p>{{ event.content }}</p>
          </div>
        </div>

        <div class="space-main-event">
          <div class="space-main-bar">
            <h2>Reserved Events</h2>
            <router-link to="/reservedEvent"> 
              <h4>View All</h4>
            </router-link>
          </div>
        </div>

        <div class="cards">
          <div class="card" v-for="event in user.userSubscribedEvents" :key="event.title">
            <h3>{{ event.title }}</h3>
            <p>{{ event.content }}</p>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const router = useRouter();

const user = ref({
  userAvatarId: "",
  userName: "",
  userEmail: "",
  userIntro: "",
  userFavoriteEvents: [],
  userBrowsedEvents: [],
  userSubscribedEvents: [],
  userGuiro: -1,
  userBackgroundId: "",
});

onMounted(async () => {
  axios
  .get('/api/user', {
      headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
      }
  })
  .then((response) => {
    console.log("response:", response);
    if (response.status === 200 && response.data.code === 0) {
      user.value.userName = response.data.data.name === "" ? "User" : response.data.data.name;
      user.value.userAvatarId = response.data.data.avatarId === null ? "https://via.placeholder.com/100" : response.data.data.avatarId;
      user.value.userBackgroundId = response.data.data.backgroundId === "" ? "https://placehold.co/600x400?text=Background+Image" : response.data.data.backgroundId;
      user.value.userEmail = response.data.data.email === "" ? "123@gmail.com" : response.data.data.email;
      user.value.userGuiro = response.data.data.guiro;
      user.value.userIntro = response.data.data.intro === "" ? "This is a user intro." : response.data.data.intro;
      user.value.userFavoriteEvents = response.data.data.favoriteEvents;
      user.value.userSubscribedEvents = response.data.data.subscribedEvents;
      user.value.userBrowsedEvents = response.data.data.browsedEvents;
    }
  })
  .catch((error) => {
    console.log("error:", error);
  });

  axios
  .get('/api/user/favorite', {
      headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
      }
  })
  .then((response) => {
    console.log("response", response);
    if (response.status === 200 && response.data.code === 0) {
      console.log(response.data.data);
    }
  })
  .catch((error) => {
    console.log("error:", error);
  });
});

const revise = () => {
  router.push("/userInfoRevision");
};
</script>

<style scoped>
.scrollbar-demo-item {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 500px;
  height: 50px;
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background-color: black;
  color: var(--el-color-primary);
}

.space-header {
  height: 280px;
  margin-left: 12%;
  margin-right: 1.5%;
  border: solid;
  border-color: rgba(191, 203, 203, 0.625);
  border-radius: 25px;
  background-image: url("https://placehold.co/600x400?text=Background+Image");
  background-size: cover;
  background-position: center;
}

.space-main {
  margin-top: 2%;
  margin-left: 12%;
  margin-right: 1.5%;
  padding-left: 1%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  height: max-content;
  border: solid;
  border-radius: 10px;
  border-color: rgba(170, 159, 159, 0.301);
  background-color: rgba(202, 199, 186, 0.218);
}

.space-main-event,
.space-main-forum,
.space-main-chat {
  margin-top: 1%;
  margin-bottom: 1%;
  padding-left: 1%;
  width: 90%;
  height: auto;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  border: solid;
  border-color: rgba(180, 197, 197, 0.449);
  border-radius: 10px;
  background-color: #b9b9a048;
}

.space-main-bar {
  display: flex;
  width: 100%;
  padding: 0;
}

.space-main-bar > h2 {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  margin: 0;
  padding: 0;
}

.space-main-bar > router-link > h4 {
  height: 50%;
  width: 10%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  margin: 0;
  padding: 0;
  font-size: 1em;
  font-weight: 500;
  color: rgb(87, 115, 115);
}

.space-main-bar > router-link > h4 :hover {
  color: rgb(112, 118, 74);
}

.cards {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: auto;
}

.card {
  margin: 1%;
  padding: 1%;
  width: 27%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  border: solid;
  border-color: rgba(128, 110, 118, 0.449);
  border-radius: 10px;
  background-color: #d5bfa148;
}

.revise-button {
  cursor: pointer;
}

.revise-button:hover {
  background-color: #d5bfa148;
}
</style>
