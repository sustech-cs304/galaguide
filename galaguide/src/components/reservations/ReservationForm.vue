<template>
  <form class="reservation-form" @submit.prevent="validateForm">
    <!-- Name Field -->
    <div>
      <label for="name">Name:</label>
      <input id="name" v-model.trim="formData.name" required type="text">
      <p v-if="errors.name" class="error">{{ errors.name }}</p>
    </div>

    <!-- Email Field -->
    <div>
      <label for="email">Email:</label>
      <input id="email" v-model.trim="formData.email" required type="email">
      <p v-if="errors.email" class="error">{{ errors.email }}</p>
    </div>

    <!-- Event Selection -->
    <div>
      <label for="event">Event:</label>
      <select id="event" v-model="formData.event" required>
        <option disabled value="">Please select one</option>
        <option v-for="event in events" :key="event.id" :value="event.id">
          {{ event.title }}
        </option>
      </select>
      <p v-if="errors.event" class="error">{{ errors.event }}</p>
    </div>

    <button type="submit">Reserve</button>
  </form>
</template>

<script>
export default {
  name: 'ReservationForm',
  props: {
    events: Array,
  },
  data() {
    return {
      formData: {name: '', email: '', event: ''},
      errors: {},
    };
  },
  methods: {
    validateForm() {
      this.errors = {}; // Reset errors before validation
      let valid = true;

      // Name validation
      if (!this.formData.name) {
        this.errors.name = 'Name is required.';
        valid = false;
      }

      // Email validation
      if (!this.formData.email.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) {
        this.errors.email = 'A valid email is required.';
        valid = false;
      }

      // Event selection validation
      if (!this.formData.event) {
        this.errors.event = 'Please select an event.';
        valid = false;
      }

      if (valid) {
        this.submitForm(); // Proceed with form submission if valid
      }
    },
    submitForm() {
      // Implement submission logic here, potentially involving an API call
      console.log('Form submitted', this.formData);
      alert('Reservation successful!'); // Replace with more sophisticated feedback
    },
  },
}
</script>

<style>
.error {
  color: red;
  font-size: 0.875em;
}

body {
  font-family: 'Helvetica Neue', Arial, sans-serif;
  background-color: #f4f4f4;
  color: #333;
  line-height: 1.6;
}

.reservation-form {
  background-color: #ffffff;
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.reservation-form label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

.reservation-form input[type="text"],
.reservation-form input[type="email"],
.reservation-form select {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.reservation-form button {
  background-color: #0056b3;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.reservation-form button:hover {
  background-color: #004494;
}

.error {
  color: #d32f2f;
  margin-top: -15px;
  margin-bottom: 15px;
  font-size: 0.875em;
}

@media (max-width: 768px) {
  .reservation-form {
    padding: 15px;
  }

  .reservation-form button {
    width: 100%;
  }
}
</style>