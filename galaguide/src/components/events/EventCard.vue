<!-- This component represent a small event card that offers brief info to user -->
<template>
    <div class="event-card" @mouseover="showButtons = true" @mouseleave="showButtons = false">
        <div class="event-card-poster">
            <img :src="gala.posterUrl" alt="Event Poster" />
        </div>
        <div class="event-card-content">
            <h3>{{ gala.title }}</h3>
            <p>{{ gala.host }}</p>
            <div class="event-card-details">
                <p>Registration Time: {{ gala.registration_time }}</p>
                <p>Event Time: {{ gala.event_time }}</p>
            </div>
        </div>
        
    </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
// import axios from 'axios';
// gala and its detail is pushed from another component
const gala = defineProps(['gala']);



const showButtons = ref(false);

// const reserveGala = () => {
//     console.log('Reserve event:', gala.value.title);
// };

// onMounted(async () => {
//     try {
//         const response = await axios.get(`/api/event/${props.value.eventId}`);
//         gala.value = response.data;
//     } catch (error) {
//         console.error('Error fetching gala details:', error);
//     }
// });
// export default {
//     name: 'EventCard',2
//     props: {
//         eventId: Number,
//     },
//     setup(props) {
//         const gala = ref({
//             id: -1,
//             title: 'Sample Event Title',
//             host: 'Host Name',
//             fee: '$20 per person',
//             category: 'Arts & Music',
//             event_time: '10:00 AM - 3:00 PM',
//             registration_time: '10:00 AM - 2:00 PM',
//             description: 'This is a sample introduction of the event that gives users insight into what to expect.',
//             posterUrl: 'https://via.placeholder.com/400x300?text=Event+Poster',
//         });
//         const showButtons = ref(false);

//         onMounted(async () => {
//             try {
//                 const response = await axios.get(`/api/event/${props.eventId}`);
//                 gala.value = response.data;
//             } catch (error) {
//                 console.error('Error fetching gala details:', error);
//             }
//         });

//         const favoriteGala = () => {
//             console.log('Favorite event:', gala.value.title);
//         };

//         const reserveGala = () => {
//             console.log('Reserve event:', gala.value.title);
//         };

//         return { gala, showButtons, favoriteGala, reserveGala };
//     },
// };
</script>


<style scoped>
.event-card {
    display: flex;
    flex-direction: row;
    margin: 10px;
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