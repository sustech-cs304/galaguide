<!-- Reservation center and event -->
<template>
    <!-- <div class="form">
        <ReservationForm></ReservationForm>
    </div> -->
    <div style="height: 100%;display: flex;flex-direction: row;max-width: 100%; margin: auto;">
        <div style="width: 15%;">
            
        </div>
        <div style="width: 80%; justify-content: flex-end;">
            <!-- <EventDetail></EventDetail> -->
            <!-- <EventSearch></EventSearch> -->
            <EventCard></EventCard>
            <!-- <FavoriteEvent></FavoriteEvent> -->
        </div>
    </div>


</template>

<script>
//import ReservationForm from './ReservationForm.vue'
//import EventDetail from './EventDetail.vue';
//import EventSearch from '../events/EventSearch.vue'
//import FavoriteEvent from '../events/FavoriteEvent.vue'
// import { defineStore } from 'pinia';
import EventCard from '../events/EventCard.vue';
export default {
    name: 'ReserveCenter',
    components: {
        // ReservationForm,
        //EventDetail,
        // EventSearch,
        //FavoriteEvent,
        EventCard,
    }
}
</script>
<style scoped>
.form {
    width: 100%;
}
</style>