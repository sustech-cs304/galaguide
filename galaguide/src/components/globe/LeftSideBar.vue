<script setup>
import {defineProps} from "vue";

defineProps({
  role: Number,
});
</script>

<template>
  <div class="left-side-bar">
    <div class="bar-logo">
      <router-link to="/"><img alt="logo.png" src="../../assets/logo.png" style="width: 70%; height: auto"/>
        <h1 style="color: white">GalaGuide</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 210px">
      <router-link v-if="role > 0" to="/events"><img alt="events.png" src="../../assets/events.png"
                                                     style="width: 30%; height: auto"/>
        <h1>Events</h1>
      </router-link>
      <router-link v-else to="/login"><img alt="events.png" src="../../assets/events.png"
                                           style="width: 30%; height: auto"/>
        <h1>Events</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 330px">
      <router-link v-if="role > 0" to="/reservations">
        <img alt="reservations.png" src="../../assets/reservations.png" style="width: 30%; height: auto"/>
        <h1>Reservations</h1>
      </router-link>

      <router-link v-else to="/login">
        <img alt="reservations.png" src="../../assets/reservations.png" style="width: 30%; height: auto"/>
      </router-link>
    </div>
    <div class="bar-options" style="top: 450px">
      <router-link v-if="role > 0" to="/forum"><img alt="forum.png" src="../../assets/forum.png"
                                                    style="width: 30%; height: auto"/>
        <h1>Forum</h1>
      </router-link>
      <router-link v-else to="/login"><img alt="forum.png" src="../../assets/forum.png"
                                           style="width: 30%; height: auto"/>
        <h1>Forum</h1>
      </router-link>
    </div>
    <div class="bar-options" style="top: 570px">
      <router-link v-if="role > 0" to="/chatroom"><img alt="chat.png" src="../../assets/chat.png"
                                                       style="width: 30%; height: auto"/>
        <h1>Chat Room</h1>
      </router-link>
      <router-link v-else to="/login"><img alt="chat.png" src="../../assets/chat.png"
                                           style="width: 30%; height: auto"/>
        <h1>Chat Room</h1>
      </router-link>
    </div>
  </div>
</template>

<style scoped>
/*
 * AI-generated-content
 * tool: Copilot
 * version: latest
 * usage: CSS Modify
 * weight: part of
 */
.left-side-bar {
  position: fixed;
  top: 0;
  left: 0;
  width: 10%;
  height: 100%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
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
