<script setup>
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
const route = useRoute();
const discussId = route.params.id;

const comments = ref([
  {
    id: 1,
    sender_id: 1,
    sender_name: " ",
    time: " ",
    title: " ",
    content: " ",
  }
]);

const filteredComments = ref([]);

onMounted(() => {
  console.log(discussId);
  axios
    .get(`/api/discuss/${discussId}`)
    .then((response) => {
      console.log(response.data);
      comments.value = response.data;
      filteredComments.value = comments.value.slice(1);
    })
    .catch((error) => {
      console.error("Error fetching discussion:", error);
    });
});
</script>

<template>
  <div id="forum-discuss">
    <div id="discuss-header">
      <router-link to="/forum" id="back-button"></router-link>
      <div id="op-info">
        <img :src="`/api/user/${comments[0].sender_id}/avatar`" />
        <p id="op-name">{{ comments[0].sender_name }}</p>
        <p id="op-time">&nbsp;at&nbsp;{{ comments[0].time }}</p>
      </div>
      <div class="actions"> 
        <button style="background-color: rgb(253, 135, 155);">Liked by {{ comments[0].likes }}</button>
        <button style="background-color: rgb(254, 121, 73);">Comment</button>
        <button style="background-color: darkmagenta;">Share</button>
      </div>
    </div>
    <div id="discuss-content">
      <h1>{{ comments[0].title }}</h1>
      <p>{{ comments[0].content }}</p>
    </div>
    <div id="comments-holder">
      <div v-for="comment in filteredComments" :key="comment.id" class="comment">
        <div class="comment-info">
          <img :src="`/api/user/${comment.sender_id}/avatar`" />
          <p class="comment-sender">{{ comment.sender_name }}</p>
          <p class="comment-time">{{ comment.time }}</p>
        </div>
        <p class="comment-content">{{ comment.content }}</p>
        <div class="actions">
          <button>Liked by {{ comment.likes }}</button>
          <button>Reply</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
#forum-discuss {
  position: absolute;
  top: 3%;
  left: 10%;
  width: 90%;
  overflow: hidden;
}

#discuss-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  /* background-color: rgb(251, 251, 251); */
}

#back-button {
  width: 50px;
  height: 50px;
  background-color: rgb(229, 229, 229);
  border-radius: 10%;
}

#back-button::before {
  content: "<";
  font-size: 30px;
}

#back-button:hover {
  cursor: pointer;
  background-color: rgb(234, 234, 234);
}

#op-info {
  display: flex;
  align-items: center;
}

#op-info img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

#op-name {
  margin-left: 10px;
  font-size: 200%;
}

#op-time {
  margin-left: 10px;
}

.actions button {
  margin-left: 10px;
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 10px 24px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  transition-duration: 0.4s;
  cursor: pointer;
  border-radius: 10px;
}

.actions button:hover {
  background-color: white;
  color: rgb(151, 185, 252);
  border: 2px solid #fafcfa;
}

#discuss-content {
  padding: 10px;
  background-color: rgb(251, 251, 251);
  width: 60%;
  margin: auto;
  border-radius: 10px;
  left: 50%;
}

#comments-holder {
  background-color: white;
  width: 60%;
  margin: auto;
  border-radius: 10px;
  padding: 10px;
}

.comment {
  padding: 10px;
  background-color: rgb(251, 251, 251);
  margin-top: 10px;
  border-radius: 10px;
}

.comment-info {
  display: flex;
  align-items: center;
}

.comment-info img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.comment-sender {
  margin-left: 10px;
  font-size: 150%;
}

.comment-time {
  margin-left: 10px;
}

.comment-content {
  padding: 10px;
  background-color: rgb(251, 251, 251);
}
</style>
