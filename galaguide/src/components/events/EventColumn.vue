<template>
  <img class="arrow left-arrow" src="../../assets/arrow_left.png" @click="prevColumn"/>


  <div v-if="activeColumn" class="column">
    <h2>{{ activeColumn.name }}</h2>
    <Vue3Marquee :duration="10" :pauseOnHover="true" :vertical="true" class="scroll">
      <div v-for="event in activeColumn.events" :key="event.id" class="card">
        <h3>{{ event.title }}</h3>
        <p>{{ event.date }}</p>
        <button @click="toggleFavorite(event)">♥</button>
      </div>
    </Vue3Marquee>
  </div>
  <img class="arrow right-arrow" src="../../assets/arrow_right.png" @click="nextColumn"/>
</template>

<script>
import {Vue3Marquee} from 'vue3-marquee';

export default {
  data() {
    return {
      activeStatus: 'Upcoming',
      eventStatuses: [
        {
          name: 'Upcoming',
          events: [{id: 1, title: 'Event 1', date: '2024-01-01', isFavorite: false}],
        },
        {
          name: 'Registration Open',
          events: [{id: 2, title: 'Event 2', date: '2024-02-01', isFavorite: false}],
        },
        {
          name: 'Happening Now',
          events: [{id: 3, title: 'Event 3', date: '2024-03-01', isFavorite: false}],
        },
        {
          name: 'Recommendation',
          events: [{id: 3, title: 'Event 4', date: '2024-04-01', isFavorite: false}],
        },
      ],

    };
  },
  computed: {
    activeColumn() {
      return this.eventStatuses.find(status => status.name === this.activeStatus);
    }
  },
  components: {Vue3Marquee,},
  methods: {
    toggleFavorite(event) {
      event.isFavorite = !event.isFavorite;
    },
    prevColumn() {
      const currentIndex = this.eventStatuses.findIndex(status => status.name === this.activeStatus);
      const prevIndex = (currentIndex - 1 + this.eventStatuses.length) % this.eventStatuses.length;
      this.activeStatus = this.eventStatuses[prevIndex].name;
    },
    nextColumn() {
      const currentIndex = this.eventStatuses.findIndex(status => status.name === this.activeStatus);
      const nextIndex = (currentIndex + 1) % this.eventStatuses.length;
      this.activeStatus = this.eventStatuses[nextIndex].name;
    },
  },

};
</script>
<style scoped>
.arrow {
  height: 10%;
  position: relative;
  /* Absolute position for the arrows */
  top: 50%;
  /* Center vertically */
  transform: translateY(-50%);
  /* Align arrow correctly with the center */
  z-index: 10;
  /* Ensure arrows are above the column content */
}

.left-arrow {
  left: 10px;
  transform: translateY(50%);
}

.right-arrow {
  right: 10px;
  transform: translateY(50%);
}

.column {
  width: 80%;
  height: 100%;
  /* Solid border as seen in the image */
  border-radius: 25px;
  /* Rounded corners as per design */
  padding: 20px;
  /* Padding inside the column */
  margin: 0;
  /* Remove any default margin */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  /* Subtle shadow for depth */
  background-color: #fff;
  /* Assuming a white background as standard */
  display: flex;
  flex-direction: column;
  align-items: center;
  /* Center items for a polished look */
}

h2 {
  font-size: 1.5em;
  margin-bottom: 20px;
  color: #333;
  text-align: center;
}

.scroll {
  width: 100%;
  height: 100%;
  overflow: hidden;
  margin-top: 20px;
}

.card {
  width: 90%;
  background-color: #f9f9f9;
  border-radius: 10px;
  margin: 10px 0;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
  align-items: center;
  /* Center content within each card */
}

button {
  border: none;
  /* Remove default button border */
  background-color: transparent;
  /* No background for a cleaner look */
  cursor: pointer;
  font-size: 1.2em;
  margin-top: 10px;
}

/* Additional responsive or interactive styles as needed */
</style>
