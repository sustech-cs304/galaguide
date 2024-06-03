<template>
    <div id="review-event-requests">
        <h1>Review Event Requests</h1>
        <div class="event-list">
            <div v-for="event in events" :key="event.id" class="event-card">
                <div class="event-summary" @click="toggleDetails(event.id)">
                    <div class="summary-header">
                        <h2>{{ event.title }}</h2>
                        <button class="toggle-button">{{ event.showDetails ? 'Collapse' : 'Expand' }}</button>
                    </div>
                    <p>
                        <img src="../../assets/organization.svg" alt="" class="icon" style="width: 20px;height: 20px;">
                        <span class="label">Host:</span> <span class="value">{{ event.host }}</span>
                    </p>
                    <p>
                        <img src="../../assets/coin.png" alt="" class="icon" style="width: 20px;height: 20px;">
                        <span class="label">Fee:</span> <span class="value">{{ event.fee }}</span>
                    </p>
                    <p>
                        <img src="../../assets/tag.png" alt="" class="icon" style="width: 20px;height: 20px;">
                        <span class="label">Category:</span> <span class="value">{{ event.category }}</span>
                    </p>
                </div>
                <transition name="fade">
                    <div v-if="event.showDetails" class="event-details">
                        <p>
                            <img src="../../assets/desc.png" alt="" class="icon" style="width: 20px;height: 20px;">
                            <span class="label">Description:</span> <span class="value">{{ event.description }}</span>
                        </p>
                        <p>
                            <img src="../../assets/poster.png" alt="" class="icon" style="width: 20px;height: 20px;">
                            <span class="label">Poster:</span> <img :src="event.poster" alt="Event Poster" class="poster">
                        </p>
                        <p>
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-people" viewBox="0 0 16 16" style="margin-right: 10px;">
  <path d="M15 14s1 0 1-1-1-4-5-4-5 3-5 4 1 1 1 1zm-7.978-1L7 12.996c.001-.264.167-1.03.76-1.72C8.312 10.629 9.282 10 11 10c1.717 0 2.687.63 3.24 1.276.593.69.758 1.457.76 1.72l-.008.002-.014.002zM11 7a2 2 0 1 0 0-4 2 2 0 0 0 0 4m3-2a3 3 0 1 1-6 0 3 3 0 0 1 6 0M6.936 9.28a6 6 0 0 0-1.23-.247A7 7 0 0 0 5 9c-4 0-5 3-5 4q0 1 1 1h4.216A2.24 2.24 0 0 1 5 13c0-1.01.377-2.042 1.09-2.904.243-.294.526-.569.846-.816M4.92 10A5.5 5.5 0 0 0 4 13H1c0-.26.164-1.03.76-1.724.545-.636 1.492-1.256 3.16-1.275ZM1.5 5.5a3 3 0 1 1 6 0 3 3 0 0 1-6 0m3-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4"/>
</svg>
                            <span class="label">Capacity:</span> <span class="value">{{ event.capacity }}</span>
                        </p>
                        <div>
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-list-task" viewBox="0 0 16 16" style="margin-right: 10px;">
  <path fill-rule="evenodd" d="M2 2.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5V3a.5.5 0 0 0-.5-.5zM3 3H2v1h1z"/>
  <path d="M5 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5M5.5 7a.5.5 0 0 0 0 1h9a.5.5 0 0 0 0-1zm0 4a.5.5 0 0 0 0 1h9a.5.5 0 0 0 0-1z"/>
  <path fill-rule="evenodd" d="M1.5 7a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5H2a.5.5 0 0 1-.5-.5zM2 7h1v1H2zm0 3.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm1 .5H2v1h1z"/>
</svg>
                            <span class="label">Sessions:</span>
                            <ul>
                                <li v-for="session in event.sessions" :key="session.id">
                                    <span class="value">{{ session.startTime }} - {{ session.endTime }} at {{ session.venue
                                    }}</span>
                                </li>
                            </ul>
                        </div>
                        <button @click="acceptEvent(event.id)" class="accept-button">Accept</button>
                        <button @click="showRejectForm(event.id)" class="reject-button">Reject</button>
                        <transition name="slide-fade">
                            <div v-if="event.showRejectForm" class="reject-form">
                                <textarea v-model="rejectMessage" placeholder="Reason for rejection"
                                    class="input-field"></textarea>
                                <button @click="rejectEvent(event.id)" class="send-button">Send</button>
                            </div>
                        </transition>
                    </div>
                </transition>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';

const events = ref([
    { id: 1, title: 'Music Concert', description: 'A wonderful evening of music.', poster: 'concert.jpg', host: 'John Doe', fee: '$50', category: 'Music', capacity: 200, sessions: [{ id: 1, startTime: '2024-06-01 19:00', endTime: '2024-06-01 22:00', venue: 'Concert Hall' }], showDetails: false, showRejectForm: false },
    { id: 2, title: 'Art Exhibition', description: 'An exhibition of modern art.', poster: 'art.jpg', host: 'Jane Smith', fee: 'Free', category: 'Art', capacity: 100, sessions: [{ id: 1, startTime: '2024-06-10 10:00', endTime: '2024-06-10 17:00', venue: 'Art Gallery' }], showDetails: false, showRejectForm: false },
    // Add more events as needed
]);

const rejectMessage = ref('');

const toggleDetails = (eventId) => {
    const event = events.value.find((event) => event.id === eventId);
    if (event) {
        event.showDetails = !event.showDetails;
        if (!event.showDetails) {
            event.showRejectForm = false; // Hide reject form if collapsing details
        }
    }
};

const showRejectForm = (eventId) => {
    const event = events.value.find((event) => event.id === eventId);
    if (event) {
        event.showRejectForm = !event.showRejectForm;
    }
};

const acceptEvent = (eventId) => {
    // alert(`Event with ID: ${eventId} has been accepted.`);
    // POST /events/:eventId/review
    const token = localStorage.getItem('token');
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    axios.post(`/events/${eventId}/review`)
        .then((response) => {
            if (response.status === 200) {
                const event = events.value.find((event) => event.id === eventId);
                if (event) {
                    event.showDetails = false; // Collapse details after accepting
                }
            }
        })
        .catch((error) => {
            // alert(`An error occurred: ${error.message}`);
            console.error(error);
        });
};

const rejectEvent = (eventId) => {
    alert(`Event with ID: ${eventId} has been rejected with message: "${rejectMessage.value}"`);
    // Implement further logic for rejecting the event
    rejectMessage.value = ''; // Clear the rejection message
    const event = events.value.find((event) => event.id === eventId);
    if (event) {
        event.showRejectForm = false;
    }
};
</script>
<style scoped>
/*
* AI-generated-content
* tool: Copilot
* version: latest
* usage: CSS Modify
* weight: part of
*/
.toggle-button {
    background: none;
    border: none;
    color: #007BFF;
    cursor: pointer;
    font-weight: bold;
}

#review-event-requests {
    /* font-family: 'Arial', sans-serif; */
    background-color: #f9f9f9;
    padding: 20px;
    max-width: 1200px;
    margin: 0 auto;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    animation: fadeIn 1s ease-in-out;
    position: relative;
    left: 5%;
}

h1 {
    text-align: center;
    color: #333;
    margin-bottom: 40px;
    animation: slideDown 1s ease-in-out;
}

.event-list {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;
}

.event-card {
    background-color: #ffffff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    animation: popIn 1s ease-in-out;
}

.event-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.event-card p {
    display: flex;
    align-items: center;
    margin: 10px 0;
}

.icon {
    width: 20px;
    height: 20px;
    margin-right: 10px;
}

.label {
    font-weight: bold;
    color: #333;
    margin-right: 5px;
}

.value {
    color: #666;
}

.poster {
    max-width: 100px;
    margin-left: 10px;
}

.event-details {
    margin-top: 20px;
    overflow: hidden;
    animation: fadeInDetails 0.5s ease;
}

.accept-button,
.reject-button,
.send-button {
    padding: 10px 20px;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.3s ease;
}

.accept-button {
    background-color: #28a745;
}

.accept-button:hover {
    background-color: #218838;
    transform: scale(1.05);
}

.reject-button {
    background-color: #dc3545;
    margin-left: 10px;
}

.reject-button:hover {
    background-color: #c82333;
    transform: scale(1.05);
}

.reject-form {
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.input-field {
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 16px;
}

.send-button {
    background-color: #007BFF;
}

.send-button:hover {
    background-color: #0056b3;
    transform: scale(1.05);
}

/* Animations */
@keyframes fadeIn {
    from {
        opacity: 0;
    }

    to {
        opacity: 1;
    }
}

@keyframes slideDown {
    from {
        transform: translateY(-20px);
        opacity: 0;
    }

    to {
        transform: translateY(0);
        opacity: 1;
    }
}

@keyframes popIn {
    from {
        transform: scale(0.9);
        opacity: 0;
    }

    to {
        transform: scale(1);
        opacity: 1;
    }
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

.slide-fade-enter-active,
.slide-fade-leave-active {
    transition: all 0.5s ease;
}

.slide-fade-enter-from,
.slide-fade-leave-to {
    transform: translateY(-10px);
    opacity: 0;
}

@keyframes fadeInDetails {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>
