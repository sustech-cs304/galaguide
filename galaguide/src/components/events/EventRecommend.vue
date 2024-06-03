<!-- A column component for recommendation and top-rank events -->
<template>
  <div class="recommend-column">
    <div class="tabs">
      <button :class="{ active: activeTab === 'rank' }" @click="showRank">Rank</button>
      <button :class="{ active: activeTab === 'new' }" @click="showNews">New</button>
    </div>
    <div class="content">
      <div v-show="activeTab === 'rank'" class="tab-content">
        <!-- Iterate over ranked events and display them -->
        <div v-for="event in rankEvents" :key="event.id" class="event-card">
          <EventCard :title="event.title" :posterId="event.posterId" :hostId="event.hostId" :eventId="event.id"/>
        </div>
      </div>
      <div v-show="activeTab === 'new'" class="tab-content">
        <!-- Iterate over new events and display them -->
        <div v-for="event in newEvents" :key="event.id" class="event-card">
          <EventCard :="event" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import EventCard from './EventCard.vue';
import axios from 'axios';
const rankEvents = ref([]);

const newEvents = ref([]);
const activeTab = ref('rank');
onMounted(() => {
  // Fetch ranked events
  axios.get('/api/event/top-rated')
    .then((response) => {
      console.log(response.data)
      rankEvents.value = response.data.data;
    })
    .catch((error) => {
      console.error('Error fetching top-rated events:', error);
    });

  // Fetch new events
  axios.get('/api/event/newest')
    .then((response) => {
      console.log(response.data)
      newEvents.value = response.data.data;
    })
    .catch((error) => {
      console.error('Error fetching new events:', error);
    });
});

const showRank = () => {
  activeTab.value = 'rank';
  axios.get('/api/event/top-rated')
    .then((response) => {
      rankEvents.value = response.data;
    })
    .catch((error) => {
      console.error('Error fetching top-rated events:', error);
    });
};

const showNews = () => {
  activeTab.value = 'new';
  axios.get('/api/event/newest')
    .then((response) => {
      newEvents.value = response.data;
    })
    .catch((error) => {
      console.error('Error fetching new events:', error);
    });
};
</script>

<style scoped>
.recommend-column {
  width: 100%;
  display: flex;
  flex-direction: column;
  background-color: rgb(186, 194, 201);
}

.tabs {
  width: 100%;
  display: inline-flex;
}

.tabs button {
  width: 50%;
  font-size: large;
  padding: 10px;
  border: none;
  background-color: #aaa4a4;
  margin-right: 0px;
  cursor: pointer;
}

.tabs button.active {
  width: 50%;
  background-color: white;
}

.content {
  justify-content: center;

}

.content .tab-content {
  align-items: center;
  justify-content: center;
  background-color: white;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.content .tab-content .event-card {
  width: 98%;
  padding: 0px;
  margin-bottom: 0px;
  border: 1px solid #ccc;
  border-radius: 4px;
  border-color: antiquewhite;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  background-color: white;
  transition: box-shadow 0.3s ease;
}

.content .tab-content .event-card:hover {

  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.content .tab-content h3 {
  margin: 0;
  font-size: 1.2em;
}
</style>
