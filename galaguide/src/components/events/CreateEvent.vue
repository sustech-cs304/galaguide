<template>
  <div style="display: flex;">
    <div style="display: flex; flex-direction: row; width: 10%;">

    </div>
    <div class="create-event">
      <form>
        <div class="form-group">
          <label for="event-title">Event Title</label>
          <input type="text" id="event-title" v-model="formData.title" />
        </div>
        <div class="form-group">
          <label for="event-introduction">Event Introduction</label>
          <textarea id="event-introduction" v-model="formData.introduction"></textarea>
        </div>
        <div class="form-group-horizontal">
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
            <!-- a drop down selection -->
            <select id="event-category" v-model="formData.category">
              <option v-for="option in category_options" :key="option.value" :value="option.value">
                {{ option.label }}
              </option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label for="event-poster">Event Poster</label>
          <div style="border: 2px; border-radius: 16px; border-color: black;">
            <ImageUploader />
          </div>
          
        </div>
        <button @click.prevent="createEvent">Create Event</button>
      </form>
    </div>
  </div>

</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import ImageUploader from '../image/ImageUploader.vue';
const category_options = [
  { label: 'Sport', value: 'Sport' },
  { label: 'Music', value: 'Music' },
  { label: 'Education', value: 'Education' },
  { label: 'Art', value: 'Art' },
  { label: 'Other', value: 'Other'}
];
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
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 15px;
}
select {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group-horizontal {
  display: flex;
  align-self: center;
  width: 80%;
  justify-content: space-between;
  gap: 15px;
}

.form-group label {
  margin-bottom: 5px;
  font-size: 22px;
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