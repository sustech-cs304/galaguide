<!-- Detail display of an event -->
<template>
  <div class="main">
    <div class="side-bar">
      <h2 style="color: #e5e1db; width: 10%">This is sidebar</h2>
    </div>
    <div class="event-detail">
      <div class="title-container">
        <h1>{{ event_data.event_title }}</h1>
        <div class="title-buttons">
          <button @click="addToFavorites">❤️ Add to Favorites</button>
        </div>
      </div>
      <div class="poster-container">
        <img :src="event_data.posterUrl" @load="loading = false" @error="loadError" v-if="!loading" />
        <div v-else>Loading...</div>
        <div v-if="error">Error loading image, please try again later.</div>
      </div>
      <div class="introduction-container">
        <p>{{ event_data.event_introduction }}</p>
      </div>
      <div class="details-container">
        <div class="detail" v-for="(detail, key) in event_data.event_details" :key="key">
          <h2>{{ key }}</h2>
          <p>{{ detail }}</p>
        </div>
      </div>
      <h2>gallery</h2>
      <div class="gallery-container">
        <div v-for="image in event_data.gallery" :key="image.id" class="image-container">
          <img :src="image.url" :alt="image.alt" class="event-image" />
        </div>
      </div>

      <div>
        <h2>Reservation Form</h2>
        <form class="reservation-form">
          <!-- Name Field -->
          <div>
            <label for="name">Name:</label>
            <input id="name" v-model.trim="formData.name" type="text" required>
          </div>

          <!-- Email Field -->
          <div>
            <label for="email">Email:</label>
            <input id="email" v-model.trim="formData.email" type="email" required>
          </div>

          <!-- Phone Number-->
          <div>
            <label for="phone">Phone:</label>
            <input id="phone" v-model.trim="formData.phone" type="text" required>
          </div>

        </form>
      </div>
      <div class="action-buttons">
        <button class="reserve-button" @click="reserveNow">Reserve</button>
        <button class="help-button" @click="getHelp">Help</button>
      </div>
    </div>
  </div>

</template>

<script setup>

// we need to store the value of formData
import { ref, onMounted, } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
const formData = ref({
  name: '',
  email: '',
  phone: '',
});

const event_data = ref(
  {
    event_title: 'Sample Event Title', // This will be dynamic based on event data
    event_introduction: 'This is a sample introduction of the event that gives users insight into what to expect.',
    event_details: {
      Time: '10:00 AM - 3:00 PM',
      Host: 'Host Name',
      Fee: '$20 per person',
      Category: 'Arts & Music'
      // More details can be added here as needed
    },
    gallery: [{ id: 1, src: "w1.com", alt: "w0.com" }, { id: 2, src: "w2.com", alt: "w0.com" }],
    posterUrl: 'https://via.placeholder.com/400x300?text=Event+Poster', // Mock-up URL

  }
)
const loading = ref(true);
const error = ref(false);
const eventID = useRoute().params.eventID;

onMounted(async () => {
  // Fetch event details based on eventID
  loading.value = true;
  try {
    const response = await axios.get(`/api/events/${eventID}`);
    event_data.value = response.data;
  } catch (e) {
    error.value = true;
    console.error(e);
  } finally {
    loading.value = false;
  }
});

const addToFavorites = () => {
  // Logic to add the event to the user's favorites
};
const validateForm = () => {
  // Validation logic
  if (!formData.value.name.trim()) {
    alert('Please enter your name.');
    return false;
  }
  if (!formData.value.email.trim() || !/\S+@\S+\.\S+/.test(formData.value.email)) {
    alert('Please enter a valid email address.');
    return false;
  }
  if (!formData.value.phone.trim()) {
    alert('Please enter your phone number.');
    return false;
  }
  
  // Confirmation dialog
  if (confirm('Do you really want to submit the form?')) {
    return true; // Proceed to submit the form if confirmed
  }
};


const reserveNow = () => {
  // before really submit the form, validate the form by jump-out box
  if (!validateForm()) {
    return;
  }
  // use axios to post the form data into the server
  axios.post('/api/reserve/create-order', formData.value)
    .then((response) => {
      console.log(response.data);
      // Show success message to the user
    })
    .catch((error) => {
      console.error('Error reserving event:', error);
      // Show error message to the user
    });
};

const loadError = () => {
  error.value = true;
  event_data.value.posterUrl = 'https://via.placeholder.com/400x300?text=Default+Image';
};

const getHelp = () => {
  // Logic to provide help to the user
};

</script>

<style scoped>
.main {
  height: 100%;
  display: flex;
  flex-direction: row;
  max-width: 90%;
  margin: auto;
}

.event-detail {
  width: 80%;
  height: 100%;
  flex: 4;
  color: #5c5c5c;
  background-color: #fff;
  padding: 20px;
  border: 1px solid #dcdcdc;
  border-radius: 10px; /* Updated */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Updated */
  transition: border-color 0.3s ease-in-out;
}

.event-detail:hover {
  border-color: #a2a2a2;
}

.title-container {
  padding-bottom: 10px;
  margin-bottom: 20px;
  border-bottom: 2px solid #cbcbcb;
  position: relative;
}

.title-container h1 {
  font-size: 28px;
  color: #3e3e3e;
  transition: color 0.3s ease;
}

.title-container:hover h1 {
  color: #5e5e5e;
}

.title-buttons {
  padding-right: 15px;
  position: absolute;
  right: 20px;
  top: 20px;
}

.poster-container {
  text-align: center;
}

.poster-container img {
  width: auto;
  max-width: 100%;
  height: auto;
  display: inline-block;
  border-radius: 10px; /* Updated */
  transition: box-shadow 0.3s ease;
}

.poster-container img:hover {
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.2);
}

.introduction-container {
  padding: 16px;
  background-color: #f8f8f8;
  margin: 16px 0;
  border-left: 4px solid #dcdcdc;
  border-radius: 10px; /* Updated */
  transition: border-color 0.3s ease-in-out;
}

.introduction-container:hover {
  border-left-color: #a2a2a2;
}

.details-container {
  display: flex;
  flex-wrap: wrap;
  width: 80%;
  margin: 0 auto;
  justify-content: space-between;
  padding: 16px;
  background-color: #fff;
  border-top: 1px solid #dcdcdc;
  border-bottom: 1px solid #dcdcdc;
  border-radius: 10px; /* Updated */
}

.detail h2 {
  font-size: 18px;
  color: #3e3e3e;
  margin-bottom: 4px;
}

.detail p {
  margin: 0;
  color: #5c5c5c;
  font-size: 16px;
}

.gallery-container {
  display: flex;
  overflow-x: auto;
  gap: 16px;
  padding: 16px;
  background-color: #f8f8f8;
}

.image-container {
  width: 200px;
  height: 200px;
  flex: 0 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  border: 1px solid #dcdcdc;
  margin: 0 8px;
  border-radius: 10px; /* Updated */
}

.event-image {
  display: block;
  max-width: 100%;
  max-height: 100%;
  border-radius: 10px; /* Updated */
  transition: transform 0.3s ease;
}

.event-image:hover {
  transform: scale(1.03);
}

.action-buttons {
  display: flex;
  justify-content: center;
  padding: 16px;
}

.reserve-button,.help-button {
  font-size: 16px;
  padding: 8px 16px;
  margin: 0 8px;
  border: none;
  border-radius: 10px; /* Updated */
  cursor: pointer;
}
</style>
