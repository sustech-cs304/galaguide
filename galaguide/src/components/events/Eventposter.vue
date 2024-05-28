<template>
    <div class="event-poster">
        <div class="navigation-buttons">
            <!-- <a @click="prevEvent">
                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor"
                    class="bi bi-chevron-left" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                        d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0" />
                </svg>
            </a> -->
            <!-- Posters will be rendered here -->
            <div class="posters">
                <!-- <Vue3Marquee :pauseOnHover="true" :vertical="false" :pause=isPaused :duration=0.3>
                    
                </Vue3Marquee> -->
                <div v-for="event in events" :key="event.id" class="poster"
                    :style="{ transform: `translateX(-${currentEventIndex * 100}%)` }">
                    <router-link to="/events/{{event.id}}">
                        <!-- <img src="../../assets/posters/poster.jpg" alt="poster.jpg" style="width: 100%; height: auto"/>
                         -->
                        <h1>{{ event.name }}</h1>
                    </router-link>
                </div>
            </div>
            <!-- <a @click="nextEvent">
                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor"
                    class="bi bi-chevron-right" viewBox="0 0 16 16">
                    <path fill-rule="evenodd"
                        d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708" />
                </svg>
            </a> -->
        </div>
        <div class="indicator-buttons">
            <button v-for="event in events" :key="event.id" @click="goToEvent(event.id)">
                <!-- Indicator content goes here -->
                <h3> </h3>
            </button>
        </div>
    </div>
</template>

<script>
//import { Vue3Marquee } from 'vue3-marquee';
export default {
    name: 'EventPoster',
    data() {
        return {
            events: [
                { id: 0, name: 'Event Name 0' },
                { id: 1, name: 'Event Name 1' },
                { id: 2, name: 'Event Name 2' },
                { id: 3, name: 'Event Name 3' },
                { id: 4, name: 'Event Name 4' },
                { id: 5, name: 'Event Name 5' },
            ],
            currentEventIndex: 0,
            isPaused: true,
        };
    },
    methods: {
        prevEvent() {
            // this.isPaused = false;
            // setTimeout(() => this.isPaused = true, 300);  // Scrolls for a short period then stops
            this.currentEventIndex = (this.currentEventIndex + 5) % 6
        },
        nextEvent() {
            // this.isPlaying = true;
            // setTimeout(() => this.isPlaying = false, 1000);  // Scrolls for a short period then stops
            this.currentEventIndex = (this.currentEventIndex + 1) % 6
        },
        goToEvent(index) {
            // this.isPlaying = true;
            // setTimeout(() => this.isPlaying = false, 1000);  // Scrolls for a short period then stops
            this.currentEventIndex = index
        },
    },
    // components: { Vue3Marquee, },
};
</script>
<style scoped>
.event-poster {
    position: relative;
    height: 280px;
    overflow: hidden;
    /* Set a fixed height or make it responsive based on your design */
}

.navigation-buttons {
    display: flex;
    align-items: center;
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    justify-content: space-between;
    padding: 0 0px;
    opacity: 0;
    transition: opacity 0.5s ease;
}

.navigation-buttons button {
    background-color: rgba(0, 0, 0, 0.5);
    /* Semi-transparent black */
    border: none;
    color: rgba(32, 139, 201, 0.479);
    padding: 10px;
    cursor: pointer;
}

.event-poster:hover .navigation-buttons {
    opacity: 1;
}

.posters {
    width: 100%;
    display: contents;
    height: 400px;
    overflow: hidden;
    /* background-color: bisque; */

}

.poster {
    z-index: 1;
    min-width: 100%;
    background-size: cover;
    background-position: center;
    transition: transform 0.5s ease;

}

.poster h3 {
    color: blue;
}

.indicator-buttons {
    position: absolute;
    right: 60px;
    bottom: 15px;
    display: flex;
    margin-top: 20px;
    padding: 10px 0;
    flex-direction: row;
}

.indicator-buttons button {
    background-color: #a7a1a1;
    border: none;
    height: 10px;
    width: 100px;
    margin: 0 5px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.indicator-buttons button:hover {
    background-color: #303c44;
}

.indicator-buttons button.active {
    background-color: #4d4444;
}
</style>