<script setup>
import { defineProps } from "vue";

defineProps({
  role: Number,
});
</script>

<template>
  <div class="left-side-bar">
    <div class="bar-logo">
      <router-link to="/"><img src="../../assets/logo.png" alt="logo.png" style="width: 70%; height: auto" />
        <h1 style="color: white">GalaGuide</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 200px">
      <router-link to="/events" v-if="role > 0"><img src="../../assets/events.png" alt="events.png"
          style="width: 30%; height: auto" />
        <h1>Events</h1>
      </router-link>
      <router-link to="/login" v-else><img src="../../assets/events.png" alt="events.png"
          style="width: 30%; height: auto" />
        <h1>Events</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 300px">
      <router-link to="/search" v-if="role > 0">
        <img src="../../assets/reservations.png" alt="reservations.png" style="width: 30%; height: auto" />
        <h1>Gala Search</h1>
      </router-link>
      
      <router-link to="/login" v-else>
        <img src="../../assets/reservations.png" alt="reservations.png" style="width: 30%; height: auto" />
        <h1>Gala Search</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 400px">
      <router-link to="/forum" v-if="role > 0"><img src="../../assets/forum.png" alt="forum.png"
          style="width: 30%; height: auto" />
        <h1>Forum</h1>
      </router-link>
      <router-link to="/login" v-else><img src="../../assets/forum.png" alt="forum.png"
          style="width: 30%; height: auto" />
        <h1>Forum</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 500px">
      <router-link to="/chatroom" v-if="role > 0"><img src="../../assets/chat.png" alt="chat.png"
          style="width: 30%; height: auto" />
        <h1>Chat Room</h1>
      </router-link>
      <router-link to="/login" v-else><img src="../../assets/chat.png" alt="chat.png"
          style="width: 30%; height: auto" />
        <h1>Chat Room</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 600px">
      <router-link to="/map" v-if="role > 0"><img src="../../assets/map.png" alt="map.png"
          style="width: 30%; height: auto" />
        <h1>Map</h1>
      </router-link>
      <router-link to="/login" v-else><img src="../../assets/map.png" alt="map.png"
          style="width: 30%; height: auto" />
        <h1>Map</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 700px">
      <router-link to="/image-host" v-if="role > 0"><img src="../../assets/image-host.png" alt="image.png"
          style="width: 30%; height: auto" />
        <h1>Image Host</h1>
      </router-link>
      <router-link to="/login" v-else><img src="../../assets/image-host.png" alt="image.png"
          style="width: 30%; height: auto" />
        <h1>Image Host</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 800px">
      <router-link to="/console" v-if="role > 0"><img src="../../assets/console.png" alt="console.png"
          style="width: 30%; height: auto" />
        <h1>Console</h1>
      </router-link>
      <router-link to="/login" v-else><img src="../../assets/console.png" alt="console.png"
          style="width: 30%; height: auto" />
        <h1>Console</h1>
      </router-link>
    </div>
  </div>
</template>

<style scoped>
.left-side-bar {
  position: fixed;
  top: 0;
  left: 0;
  width: 10%;
  height: 100%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: scroll;
}

.bar-logo {
  position: absolute;
  width: 100%;
  height: 200;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #afc5db;
  color: white;
  font-size: 50%;
  font-weight: bold;
}

.bar-options {
  position: absolute;
  width: 90%;
  left: 5%;
  border-radius: 10px;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #9ca3ab;
  color: white;
  font-size: 50%;
  font-weight: bold;
}

.bar-logo:hover {
  background-color: #34495e;
}

.bar-options:hover {
  background-color: #a5adba;
}

.bar-logo img,
.bar-options {
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>
