<!-- This component represent a small event card that offers brief info to user -->
<template>
    <div class="event-card">
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
            <div class="event-card-buttons">
                <button @click="galaDetail">Details</button>
                <button @click="favoriteGala">Favorite</button>
            </div>
        </div>
    </div>
</template>

<script lang="js">
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
    name: 'EventCard',
    props: {
        eventId: {
            type: Number,
            required: true,
        },
    },
    setup(props) {
        const gala = ref({
            id: -1,
            title: 'Sample Event Title',
            host: 'Host Name',
            fee: '$20 per person',
            category: 'Arts & Music',
            event_time: '10:00 AM - 3:00 PM',
            registration_time: '10:00 AM - 2:00 PM',
            description: 'This is a sample introduction of the event that gives users insight into what to expect.',
            posterUrl: 'https://via.placeholder.com/400x300?text=Event+Poster',
        });

        onMounted(() => {
            axios.post('/api/event', { id: props.eventId })
                .then(response => {
                    gala.value = response.data;
                })
                .catch(error => {
                    console.error('Error fetching gala details:', error);
                });
        });

        return {
            gala,
            galaDetail() {
                this.$router.push({ path: '/events/' + props.eventId });
            },
            favoriteGala() {
                console.log('Favorite event:', gala.value.title);
            },
        };
    },
};
</script>


<style scoped>
.event-card {
    display: flex;
    flex-direction: row;
    height: 40%;
    margin: 10px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    /* Light grey border */
}

.event-card-poster {
    height: auto;
    width: 30%;
    margin-right: 10px;
}

.event-card-poster img {
    width: 100%;
    height: 100%;
}

.event-card-content {
    text-align: left;
    flex-grow: 1;
}

.event-card-content h3 {
    align-self: flex-start;
    margin: 0;
}

.event-card-details {
    margin-top: 5px;
    margin-bottom: 5px;
}

.event-card-buttons button {
    padding: 5px 10px;
    margin-right: 10px;
    cursor: pointer;
    border: 1px solid #cfe458;
    border-radius: 4px;
}

.event-card-buttons button:last-child {
    margin-right: 0;
}
</style>