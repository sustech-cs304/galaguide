<!-- This component represent a small event card that offers brief info to user -->
<template>
    <div class="event-card" @mouseover="showButtons = true" @mouseleave="showButtons = false">
        <div class="event-card-poster">
            <img :src="'/api/asset/' + posterId" alt="Event Poster" style="width: 100px; height: 100px; margin-right: 30px;"/>
        </div>
        <div class="event-card-content">
            <h3>{{ title }}</h3>
            <p>Host: {{ host }}</p>
        </div>
        
    </div>
</template>

<script setup>
import axios from 'axios';
import { ref, defineProps, onMounted } from 'vue';

const props = defineProps({
    title: String,
    posterId: String,
    hostId: Number
});

const showButtons = ref(false);

const host = ref('Host Name');

function findHost() {
    console.log('Host ID:', props.hostId);
    axios
        .get(`/api/user/${props.hostId}`, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
            },
        })
        .then((response) => {
            console.log('response:', response.data);
            host.value = response.data.data.name;
        })
        .catch((error) => {
            console.error('Error fetching host details:', error);
        });
}

onMounted(() => {
    findHost();
});
</script>


<style scoped>
.event-card {
    display: flex;
    flex-direction: row;
    margin: 2px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.event-card:hover .event-card-buttons {
    display: flex;
    flex-direction: colomn;
    align-items: center;
}

.event-card-poster img {
    width: 70%;
    height: 80%;
    align-self: center;
}

.event-card-content {
    flex-grow: 1;
}

.event-card-buttons {
    display: none;
    flex-direction: colomn;
}

.event-card-buttons button {
    padding: 5px 10px;
    cursor: pointer;
    margin: 5px 0;
    margin-right: 10px;
    width: auto;
    height: 40%;
    border-radius: 15;
    border: none;
    background-color: #91a792;
    color: #000;
    box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.1);
    transition: background-color 0.3s;
}

.event-card-buttons button:hover {
    background-color: #3d9b74;
}
</style>