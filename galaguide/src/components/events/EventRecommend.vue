<!-- A column component for recommendation and top-rank events -->
<template>
  <div class="recommend-column">
    <div class="tabs">
      <button :class="{ active: activeTab === 'rank' }" @click="activeTab = 'rank'">Rank</button>
      <button :class="{ active: activeTab === 'new' }" @click="activeTab = 'new'">New</button>
    </div>
    <div class="content">
      <div v-show="activeTab === 'rank'" class="tab-content">
        <!-- Iterate over ranked events and display them -->
        <div v-for="event in rankEvents" :key="event.id" class="event-card">
          <h2>{{ event.title }}</h2>
        </div>
      </div>
      <div v-show="activeTab === 'new'" class="tab-content">
        <!-- Iterate over new events and display them -->
        <div v-for="event in newEvents" :key="event.id" class="event-card">
          <h2>{{ event.title }}</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
const rankEvents = ref([]);

const newEvents = ref([]);
const activeTab = ref('rank');
onMounted(() => {
  // Fetch ranked events
  fetch('/api/events/top-rated')
    .then((response) => response.json())
    .then((data) => {
      rankEvents.value = data;
    })
    .catch((error) => {
      console.error('Error fetching ranked events:', error);
    });

  // Fetch new events
  fetch('/api/events/newest')
    .then((response) => response.json())
    .then((data) => {
      newEvents.value = data;
    })
    .catch((error) => {
      console.error('Error fetching new events:', error);
    });
});

</script>

<style scoped>
.recommend-column {
  width: 100%;
  display: flex;
  background-color: rgb(186, 194, 201);
}

.tabs {
  width: 100%;
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
  display: block;
}

.content .tab-content .event-card {
  padding: 20px;
  margin-bottom: 2px;
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
