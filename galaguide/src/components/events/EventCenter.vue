<template>
    <div class="container">
      <div class="event-center">
      <div class="column" v-for="status in eventStatuses" :key="status.name">
        <h2>{{ status.name }}</h2>
        <Vue3Marquee :pauseOnHover="true" :vertical="true" :duration="10" class="scroll" style="width: 100%;">
          <div class="card" v-for="event in status.events" :key="event.id">
            <h3>{{ event.title }}</h3>
            <p>{{ event.date }}</p>
            <button @click="toggleFavorite(event)">♥</button>
          </div>
        </Vue3Marquee>
      </div>
    </div>
    <div class="sidebar-space">
      <h1>This is sidebar!</h1>
    </div>
  </div>
  
</template>

<script>
import { Vue3Marquee } from 'vue3-marquee';
export default {
  components: { Vue3Marquee },
  data() {
    return {
      eventStatuses: [
        {
          name: 'Upcoming',
          events: [{ id: 1, title: 'Event 1', date: '2024-01-01', isFavorite: false }],
        },
        {
          name: 'Registration Open',
          events: [{ id: 2, title: 'Event 2', date: '2024-02-01', isFavorite: false }],
        },
        {
          name: 'Happening Now',
          events: [{ id: 3, title: 'Event 3', date: '2024-03-01', isFavorite: false }],
        },
        {
          name: 'Recommendation',
          events: [{ id: 3, title: 'Event 4', date: '2024-04-01', isFavorite: false }],
        },
      ],
    };
  },
  methods: {
    toggleFavorite(event) {
      event.isFavorite = !event.isFavorite;
    },
    hideFooter() {
      const footer = document.querySelector('#footer');
      footer.style.display = 'none';
    },
  },
  mounted() {
    this.hideFooter()
  }
};
</script>
<!-- 
<style scoped>
.event-center {
  display: flex;
  justify-content: space-between;
}

.sidebar-space {
  width: 10%;
  /* Style as needed for sidebar or other content */
}
.column {
  width: 20%;
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.card {
  width: 100%;
  background-color: #f9f9f9;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
}
.scroll {
    position: relative;
    width: 20%;
    height: calc(100% - 50px);
    overflow: hidden;
}
</style> -->
<style scoped>
.container {
  display: flex;
  flex-direction: row-reverse; /* Reverses the order of flex items */
}

.event-center {
  width: 80%;
  display: flex;
  flex-wrap: wrap; /* Allows items to wrap as needed */
  justify-content: space-between;
}

.sidebar-space {
  width: 10%; /* Adjusted to take the full width on its own line if necessary */
  /* Additional styling as needed */
}

.column {
  width: calc(50% - 20px); /* Adjust width to allow two columns per row */
  margin-bottom: 20px; /* Add some space between rows */
  padding: 10px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.card {
  width: 100%;
  background-color: #f9f9f9;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 5px;
}

.scroll {
  position: relative;
  height: calc(100% - 50px); /* Adjust height as necessary */
  overflow: hidden;
}
</style>
