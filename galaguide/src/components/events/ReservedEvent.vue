<!--  -->
<template>
  <div class="container">
    <div class="left-bar">
      <!-- Content for left sidebar -->
    </div>
    <div class="title">
      <h2>My Reservation</h2>
    </div>
    <div class="event" v-for="event in reservedEvents" :key="event.eventId">
      <div class="reservation">
        <p>Event Name: {{ event_name }}</p>
        <p>Period: {{ event_period[parseInt(event.eventId)] }}</p>
        <p>Host: {{ host_name }}</p>
        <p>Phone: {{ event.phoneNumber }}</p>
        <p>Email: {{ event.email }}</p>
      </div>
        
      <div class="event-actions">
          <button type="submit" @click="deleteEvent(event.id)" class="delete-button">Unreserve</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';


function deleteEvent(eventId) {
  console.log("eventId:", eventId);
}

const event_name = ref('Event Name');
const host_name = ref('Host Name');
const event_period = ref(['2024.03.05 - 2024.03.12', '2024.02.03 - 2024.02.04', '2024.01.19 - 2024.01.26'
  , '2024.02.06 - 2024.02.09', '2024.05.13 - 2024.06.10', '2024.04.26 - 2024.05.13', '2024.01.15 - 2024.04.16',
  '2024.03.08 - 2024.03.09', '2024.05.19 - 2024.05.26', '2024.04.06 - 2024.04.12', '2023.12.25 - 2024.01.10',
]);

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
      console.log("reserveEvents:", reservedEvents);

      for (let i = 0; i < reservedEvents.value.length; i++) {
        axios
        .get(`/api/event/${reservedEvents.value[i].eventId}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        })
        .then((response) => {
          console.log("response:", response);
          if (response.status === 200 && response.data.code === 0) {
            event_name.value = response.data.data.title;
          }
        })
        .catch((error) => {
          console.log("error:", error);
        });
        axios
        .get(`/api/user/${reservedEvents.value[i].recipientId}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        })
        .then((response) => {
          console.log("response:", response);
          if (response.status === 200 && response.data.code === 0) {
            console.log("response.data.data:", response.data.data);
            host_name.value = response.data.data.name;
          }
        })
        .catch((error) => {
          console.log("error:", error);
        });
      }
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

.reservation {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px;
  width: 450px;
  height: 150px;
  border: 1px solid #bbaeae;
  border-radius: 15px;
}

.reservation p {
  margin: 5px;
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
