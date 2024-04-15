<script setup>
import {onMounted, ref} from "vue";
import {useRoute} from "vue-router";
import axios from "axios";

const route = useRoute();
const discussId = route.params.id;

const comments = ref([
  {id: 1, content: "Comment 1", sender: "User 1", time: "12:00"},
]);

onMounted(() => {
  console.log(discussId);
  axios
      .get(`/api/discuss/${discussId}`)
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error("Error fetching discussion:", error);
      });
});
</script>

<template>
  <div id="forum-discuss">
    <div id="discuss-title-top-bar">
      <div style="margin-right: 8%">
        <svg
            class="bi bi-chat-left-text"
            fill="currentColor"
            height="54"
            viewBox="0 0 16 16"
            width="54"
            xmlns="http://www.w3.org/2000/svg"
        >
          <path
              d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"
          />
          <path
              d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6zm0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5z"
          />
        </svg>
      </div>
      <div id="discuss-title">
        <h2>Discuss Title</h2>
      </div>
    </div>
    <div id="discuss-body">
      <div id="discuss-content">
        <div id="header-poster-time">
          <p id="header-poster">Poster</p>
          <p id="header-time">Time</p>
        </div>
        <p id="discuss-content">Content</p>
      </div>
      <div id="discuss-comments">
        <div v-for="comment in comments" :key="comment.id" class="comment">
          <div class="comment-sender-time">
            <p class="comment-sender">{{ comment.sender }}</p>
            <p class="comment-time">{{ comment.time }}</p>
          </div>
          <p class="comment-content">{{ comment.content }}</p>
        </div>
      </div>
    </div>
    <div id="discuss-reply">
      <textarea
          id="reply-textarea"
          placeholder="Reply to this discussion"
      ></textarea>
      <button id="reply-button">Reply</button>
    </div>
  </div>
</template>

<style>
/*
 * AI-generated-content
 * tool: Copilot
 * version: latest
 * usage: CSS Modify
 * weight: part of
 */
#forum-discuss {
  display: flex;
  flex-direction: column;
  margin-left: 11%;

  align-items: center;
  width: 88%;
  height: 100%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#discuss-title-top-bar {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-left: 1%;
  width: 100%;
  height: 10%;
  background-color: #23b375;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#discuss-title {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80%;
  height: 100%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#discuss-body {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 80%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: auto;
}

#header-poster-time {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 10%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#header-poster {
  font-size: 0.9em;
}

#header-time {
  font-size: 0.9em;
}

#discuss-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  height: 40%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#discuss-comments {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  height: 50%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.comment {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 50%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#discuss-reply {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 10%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#reply-textarea {
  width: 80%;
  height: 100%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#reply-button {
  width: 10%;
  height: 100%;
  background-color: #23b375;
  color: white;
  cursor: pointer;
  font-family: "Montserrat", sans-serif;
  border: none;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#reply-button:hover {
  background-color: #1f9e64;
}

.comment-sender-time {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: 10%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.comment-sender {
  font-size: 0.9em;
}

.comment-time {
  font-size: 0.9em;
}

.comment-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  height: 40%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}
</style>
