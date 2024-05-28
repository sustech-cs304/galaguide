<!-- this page is for users to create their own events -->
<template>
  <div style="display: flex;">
    <div style="width: 10%; display: flex;">
      <h2>This is side bar</h2>
    </div>
    <div class="create-event">
      <form>
        <div class="form-group">
          <label for="event-title">Event Title</label>
          <input type="text" id="event-title" v-model="formData.title" />
        </div>
        <div class="form-group
      ">
          <label for="event-introduction">Event Introduction</label>
          <textarea id="event-introduction" v-model="formData.introduction"></textarea>
        </div>
        <div class="form-group">
          <label for="event-time">Event Time</label>
          <input type="datetime-local" id="event-time" v-model="formData.time" />
        </div>
        <div class="form-group">
          <label for="event-host">Host</label>
          <input type="text" id="event-host" v-model="formData.host" />
        </div>
        <div class="form-group">
          <label for="event-fee">Fee</label>
          <input type="text" id="event-fee" v-model="formData.fee" />
        </div>
        <div class="form-group">
          <label for="event-category">Category</label>
          <input type="text" id="event-category" v-model="formData.category" />
        </div>
        <div class="form-group">
          <label for="event-poster">Event Poster</label>
          <input type="text" id="event-poster" v-model="formData.posterUrl" />
        </div>
        <button @click="createEvent">Create Event</button>
      </form>
    </div>
  </div>

</template>

<script setup>

import { ref } from 'vue';
import axios from 'axios';

const formData = ref({
  title: '',
  introduction: '',
  time: '',
  host: '',
  fee: '',
  category: '',
  posterUrl: '',
});

const createEvent = async () => {
  try {
    const response = await axios.post('/api/event/create', formData.value);
    console.log(response.data);
  } catch (error) {
    console.error(error);
  }
};
</script>

<style scoped>
.create-event {
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 80%;
  margin: auto;
  padding: 20px;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

form {
  align-content: center;
  width: 70%;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  margin-bottom: 5px;
  font-weight: bold;
  color: #333;
}

.form-group input,
.form-group textarea {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
  font-size: 16px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group textarea {
  resize: vertical;
  height: 100px;
}

button {
  align-self: center;
  padding: 10px 20px;
  background-color: #cbe091;
  color: #000;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  font-size: 16px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #a5adba;
}
</style>