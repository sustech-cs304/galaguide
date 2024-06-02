<!--  -->
<template>
  <div class="container">
    <div class="left-bar">
      <!-- Content for left sidebar -->
    </div>
    <!-- <aside class="left-bar"> -->
    <!-- Content for left sidebar -->
    <!-- </aside> -->
      <header class="title">
        <h2>My Favorite Events</h2>
      </header>
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
    .get('/api/event', {
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
}

.left-bar {
  width: 10%;
  background-color: #f8f8f8;
  opacity: 0;
  /* Subtle background for the sidebar */
  padding: 20px;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

.main-content {
  width: 100%;
  padding: 20px;
}

.title {
  font-size: 24px;
  color: #333;
  padding-bottom: 10px;
  margin-bottom: 20px;
  border-bottom: 2px solid #cbcbcb;
}

.event {
  background-color: #fff;
  border: 1px solid #dcdcdc;
  border-radius: 4px;
  padding: 16px;
  margin: 16px 0;
  transition: all 0.3s ease;
}

.event:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.event h2 {
  color: #5c5c5c;
}

.event p {
  color: #6c6c6c;
  margin-bottom: 16px;
}

.event-actions {
  display: flex;
  justify-content: flex-end;
}

.event-actions button {
  padding: 8px 16px;
  margin: 0 8px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.event-actions button:hover {
  background-color: #e7e7e7;
}

/* Extra styles for share and delete buttons for differentiation */
.share-button {
  color: #110909;
  /* A neutral dark color for text */
}

.delete-button {
  background-color: rgba(189, 18, 18, 0.849);
  /* Default background to white */
  color: #f7f7f7;
  /* Default text color to dark */
  border: 1px solid #dcdcdc;
  /* Light border for the button */
  border-radius: 4px;
  /* Rounded corners for the button */
  padding: 8px 16px;
  /* Padding inside the button */
  margin-left: 8px;
  /* Margin to the left of the button */
  transition: background-color 0.3s, color 0.3s, border-color 0.3s;
  /* Smooth transition for color and border */
  cursor: pointer;
  /* Cursor to pointer to indicate it's clickable */
}

.delete-button:hover {
  background-color: #be4f4f;
  /* Light red background on hover */
  color: #b33a3a;
  /* Red text color on hover for emphasis */
  border: 2px solid #b33a3a;
  /* Solid border to highlight the button on hover */
}
</style>
