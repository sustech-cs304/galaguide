<template>
  <div id="user-space">
    <div class="space">
      <div class="space-header">
        <img
          :src="user.userAvatar"
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
          <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-pencil-square" viewBox="-6 -6 22 22">
            <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
            <path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/>
          </svg>
        </h2>
        <h4 class="user-email" style="position: absolute; left: 22.5%; top: 33%">
          {{ user.userEmail }}
        </h4>
        <p class="user-bio" style="position: absolute; left: 22.5%; top: 38%">
          {{ user.userBio }}
        </p>
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

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

const user = ref({});

onMounted(async () => {
  // const response = await axios.get("/api/user/details");
  // user.value = response.data;
  
  user.value = {
    userAvatar: "https://via.placeholder.com/100",
    userName: document.cookie.split(";")
      .find((cookie) => cookie.trim().startsWith("userName=")) === undefined ? "User Name" :
      document.cookie.split(";")
      .find((cookie) => cookie.trim().startsWith("userName=")).split("=")[1],
    userEmail: "123456789@google.com",
    userBio: "Here is a short bio about the user.",
    userFavoriteEvents: [
      {
        title: "Event 1",
        content: "Lorem ipsum, dolor sit amet consectetur.",
        link: "https://www.google.com",
        date: "2022-10-10 08:00",
      },
      {
        title: "Event 2",
        content: "Lorem ipsum, dolor sit amet consectetur.",
        link: "https://www.google.com",
        date: "2022-10-10 08:00",
      },
      {
        title: "Event 3",
        content: "Lorem ipsum, dolor sit amet consectetur.",
        link: "https://www.google.com",
        date: "2022-10-10 08:00",
      },
    ],
    userBrowsedEvents: [
      {
        title: "Event 1",
        content: "Lorem ipsum, dolor sit amet consectetur.",
        link: "https://www.google.com",
        date: "2022-10-10 08:00",
      },
      {
        title: "Event 2",
        content: "Lorem ipsum, dolor sit amet consectetur.",
        link: "https://www.google.com",
        date: "2022-10-10 08:00",
      },
      {
        title: "Event 3",
        content: "Lorem ipsum, dolor sit amet consectetur.",
        link: "https://www.google.com",
        date: "2022-10-10 08:00",
      },
    ],
    userPosts: [
      {
        photo: "https://via.placeholder.com/150",
        name: "Post 1",
        description: "Lorem ipsum, dolor sit amet consectetur.",
      },
      {
        photo: "https://via.placeholder.com/150",
        name: "Post 2",
        description: "Lorem ipsum, dolor sit amet consectetur.",
      },
      {
        photo: "https://via.placeholder.com/150",
        name: "Post 3",
        description: "Lorem ipsum, dolor sit amet consectetur.",
      },
    ],
    userGroups: [
      {
        id: 1,
        photo: "https://via.placeholder.com/150",
        name: "Group 1",
        description: "Lorem ipsum, dolor sit amet consectetur.",
      },
      {
        id: 2,
        photo: "https://via.placeholder.com/150",
        name: "Group 2",
        description: "Lorem ipsum, dolor sit amet consectetur.",
      },
      {
        id: 3,
        photo: "https://via.placeholder.com/150",
        name: "Group 3",
        description: "Lorem ipsum, dolor sit amet consectetur.",
      },
    ]
  };
});
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
</style>
