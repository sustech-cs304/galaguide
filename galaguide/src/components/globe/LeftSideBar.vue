<script setup>
import { defineProps } from 'vue'

defineProps({
    role: Number
})
</script>

<template>
    <div class="left-side-bar">
        <div class="bar-logo">
            <router-link to="/"><img src="../../assets/logo.png" alt="logo.png" style="width: 50%; height: auto;" />
                <h1 style="color: white;">GalaGuide</h1>
            </router-link>
        </div>
        <div class="bar-options" style="top: 160px;">
            <router-link to="/events" v-if="role > 0"><img src="../../assets/events.png" alt="events.png"
                    style="width: 30%; height: auto;" />
                <h1>Events</h1>
            </router-link>
            <router-link to="/login" v-else><img src="../../assets/events.png" alt="events.png"
                    style="width: 30%; height: auto;" />
                <h1>Events</h1>
            </router-link>
        </div>
        <div class="bar-options" style="top: 280px;">
            <router-link to="/reservations" v-if="role > 0"><img src="../../assets/reservations.png" alt="reservations.png"
                    style="width: 30%; height: auto;" />
                <h1>Reservations</h1>
            </router-link>
            <router-link to="/login" v-else><img src="../../assets/reservations.png" alt="reservations.png"
                    style="width: 30%; height: auto;" />
                <h1>Reservations</h1>
            </router-link>
        </div>
        <div class="bar-options" style="top: 400px;">
            <img src="../../assets/forum.png" alt="forum.png" style="width: 30%; height: auto;" />
            <h1>Forum</h1>
        </div>
        <div class="bar-options" style="top: 520px;">
            <router-link to="/chatroom" v-if="role > 0"><img src="../../assets/chat.png" alt="chat.png"
                    style="width: 30%; height: auto;" />
                <h1>Chat Room</h1>
            </router-link>
            <router-link to="/login" v-else><img src="../../assets/chat.png" alt="chat.png"
                    style="width: 30%; height: auto;" />
                <h1>Chat Room</h1>
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
    overflow: hidden;
}

.bar-logo {
    position: absolute;
    width: 100%;
    height: 150px;
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
    height: 100px;
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
