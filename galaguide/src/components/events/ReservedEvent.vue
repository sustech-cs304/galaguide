<!--  -->
<template>
  <div class="container">
    <div class="left-bar">
      <!-- Content for left sidebar -->
    </div>
    <div class="title">
      <h2>My Reservation</h2>
    </div>
    <div class="event" v-for="event in reservedEvents" :key="event.id">
      <EventCard :title="event.title"
      :posterId="event.posterId"
      :hostId="event.hostId"
      :eventId="event.id"/>    
        
      <div class="event-actions">
          <button type="submit" @click="deleteEvent(event.id)" class="delete-button">Unreserve</button>
        </div>
    </div>
  </div>
</template>

<script setup>
import EventCard from './EventCard.vue';
import axios from 'axios';
import { onMounted, ref } from 'vue';


function deleteEvent(eventId) {
  console.log("eventId:", eventId);
}


const reservedEvents = ref([]);

function fetchReservedEvents() {
  console.log("document.cookie:", document.cookie);
  const cookie = document.cookie
    .split(";")
    .find((cookie) => cookie.trim().startsWith("userRole="));
  console.log("cookie:", cookie);
  if (cookie && cookie.split("=")[1] !== "0") {
    console.log("cookie:", cookie);
    console.log("Authorization:", `Bearer ${localStorage.getItem('token')}`);
    // let token = localStorage.getItem('token');
    axios
    .get('/api/reserve/mine', {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
        }
    })
    .then((response) => {
      console.log("response:", response);
      if (response.status === 200 && response.data.code === 0) {
        reservedEvents.value = response.data.data;
      }
      console.log("reserveEvents:", reservedEvents.value[0]);
    })
    .catch((error) => {
      console.log("error:", error);
    });
  }
}

onMounted(() => {
  fetchReservedEvents();
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
