<template>
  <div id="user-space">
    <div class="space">
      <div class="space-header">
        <img
            :src="user.photo"
            alt="user photo"
            style="
            position: absolute;
            left: 14.5%;
            top: 30.5%;
            border-radius: 50px;
          "
        />
        <h2 class="user-name" style="position: absolute; left: 22.5%; top: 31%">
          {{ userName }}
        </h2>
        <p class="user-bio" style="position: absolute; left: 22.5%; top: 37.5%">
          {{ userBio }}
        </p>
      </div>

      <div class="space-main" style="display: flex; flex-direction: column">
        <div class="space-main-event">
          <div class="space-main-bar">
            <h2>Favorite Events</h2>
            <router-link to="/favorite"><h4>View All</h4></router-link>
          </div>
        </div>
        <div class="cards">
          <div v-for="event in userEvents" :key="event.title" class="card">
            <h3>{{ event.title }}</h3>
            <p>{{ event.content }}</p>
          </div>
        </div>

        <div class="space-main-forum">
          <div class="space-main-bar">
            <h2>Liked Posts</h2>
            <h4>View All</h4>
          </div>
        </div>
        <div class="cards">
          <div v-for="post in userPosts" :key="post.title" class="card">
            <img :src="post.photo" alt="post photo"/>
            <h3>{{ post.name }}</h3>
            <p>{{ post.description }}</p>
          </div>
        </div>

        <div class="space-main-chat">
          <div class="space-main-bar">
            <h2>Chat</h2>
            <h4>View All</h4>
          </div>
        </div>
        <div class="cards">
          <div v-for="group in userGroups" :key="group.name" class="card">
            <h3>{{ group.name }}</h3>
            <img :src="group.photo" alt="group photo"/>
            <p>{{ group.description }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";

const user = ref({});
const userName = ref("");
const userBio = ref("");
const userEvents = ref("");
const userPosts = ref([]);
const userGroups = ref([]);

onMounted(async () => {
  // const response = await axios.get("http://localhost:8080/user");
  // user.value = response.data;
  // userName.value = response.data.name;
  // userBio.value = response.data.bio;
  // userEmail.value = response.data.email;
  // userPosts.value = response.data.posts;
  // userGroups.value = response.data.groups;
  /*
 * AI-generated-content
 * tool: Copilot
 * version: latest
 * usage: Placeholder data
 * weight: whole
 */
  user.value = {
    photo: "https://via.placeholder.com/100",
  };
  userName.value = "User Name";
  userBio.value = "Here is a short bio about the user.";
  userEvents.value = [
    {
      title: "Event 1",
      content:
          "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Event 2",
      content:
          "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Event 3",
      content:
          "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
  ];
  userPosts.value = [
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
  ];
  userGroups.value = [
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 1",
      description: "Lorem ipsum, dolor sit amet consectetur.",
    },
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 2",
      description: "Lorem ipsum, dolor sit amet consectetur.",
    },
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 3",
      description: "Lorem ipsum, dolor sit amet consectetur.",
    },
  ];
});
</script>

<style scoped>
/*
 * AI-generated-content
 * tool: Copilot
 * version: latest
 * usage: CSS Modify
 * weight: part of
 */
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
  justify-content: start;
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
  justify-content: start;
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
  color: rgb(82, 177, 177);
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
  justify-content: start;
  align-items: center;
  border: solid;
  border-color: rgba(128, 110, 118, 0.449);
  border-radius: 10px;
  background-color: #d5bfa148;
}
</style>
