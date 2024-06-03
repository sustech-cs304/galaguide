<!--  -->
<template>
  <div class="container">
    <div class="left-bar">
      <!-- Content for left sidebar -->
    </div>
    <div class="title">
      <h2>My Favorite Events</h2>
    </div>
    <div class="event" v-for="event in favoriteEvents" :key="event.id">
      <EventCard :gala="favoriteEvents" />
      <!-- More details can be displayed here -->
      <div class="event-actions">
        <button type="submit" @click="shareEvent" class="share-button">Share</button>
        <button type="submit" @click="deleteEvent" class="delete-button">Delete</button>
      </div>
      
    </div>
  </div>
</template>

<script setup>
import EventCard from './EventCard.vue';
import axios from 'axios';
import { onMounted, ref } from 'vue';

const shareEvent = () => {
  // Logic to share the event
  console.log('Sharing the event');
};

function deleteEvent() {
  // Logic to delete the event
  console.log("Deleting the event");
}

const favoriteEvents = ref([]);

function fetchFavoriteEvents() {
  console.log("document.cookie:", document.cookie);
  const cookie = document.cookie
    .split(";")
    .find((cookie) => cookie.trim().startsWith("userRole="));
  console.log("cookie:", cookie);
  if (cookie && cookie.split("=")[1] !== "0") {
    console.log("cookie:", cookie);
    axios
    .get('/api/user/favorite', {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
        }
    })
    .then((response) => {
      console.log("response:", response);
      if (response.status === 200 && response.data.code === 0) {
        favoriteEvents.value = response.data.data;
      }
    })
    .catch((error) => {
      console.log("error:", error);
    });
  }
}

onMounted(() => {
  fetchFavoriteEvents();
});
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
}

.left-bar {
  display: flex;
  flex-direction: column;
  width: 20%;
}

.title {
  display: flex;
  justify-content: center;
}

.event {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px;
}

.event-actions {
  display: flex;
  justify-content: center;
}

.share-button {
  margin-right: 10px;
}

.delete-button {
  margin-left: 10px;
}

.event-actions button {
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.event-actions button:hover {
  background-color: #f0f0f0;
}

.event-actions button:active {
  background-color: #e0e0e0;
}

.event-actions button:focus {
  outline: none;
}
</style>
