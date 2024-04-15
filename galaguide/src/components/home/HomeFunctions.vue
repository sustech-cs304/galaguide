<script setup>

import {defineProps} from 'vue'

const props = defineProps({
  role: Number
})

const createEvent = () => {
  console.log('Create an event');
};

const makeReservation = () => {
  console.log('Make a reservation');
};
</script>

<template>
  <div v-if="props.role > 0" class="home-functions">
    <h1 style="text-align: center; margin-bottom: 20px;">Quick Functions</h1>
    <input class="search" placeholder="Search in GalaGuide..." type="text"/>
    <button class="create-event" @click="createEvent">
      <a href="/create-event" style="color: aliceblue;">Create an event</a>
    </button>
    <button class="make-res" @click="makeReservation">
      <a href="/make-res" style="color: aliceblue;">Make a reservation</a>
    </button>
  </div>
  <div v-if="props.role === 0" class="home-functions">
    <p style="text-align: center; margin-top: 20px;position: absolute;top:50%;">Please log in to use quick functions</p>
  </div>
</template>

<style scoped>
.home-functions {
  position: absolute;
  top: 7%;
  right: 2%;
  width: 35%;
  height: 270px;
  background-color: white;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.search {
  width: 80%;
  height: 30px;
  margin-bottom: 20px;
  border-radius: 5px;
  border: 1px solid #ccc;
  padding: 0 10px;
}

.create-event, .make-res {
  width: 80%;
  height: 30px;
  margin-bottom: 10px;
  border-radius: 5px;
  border: none;
  color: white;
  cursor: pointer;
}

.create-event {
  background-color: #23b375;
}

.create-event:hover {
  background-color: #1f9e64;
}

.make-res {
  background-color: #2a78c7;
}

.make-res:hover {
  background-color: #2467c4;
}
</style>
  