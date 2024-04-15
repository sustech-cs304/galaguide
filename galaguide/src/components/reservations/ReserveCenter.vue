<!-- Reservation center and event -->
<template>
    <!-- <div class="form">
        <ReservationForm></ReservationForm>
    </div> -->
    <div style="height: 100%;display: flex;flex-direction: row;max-width: 100%; margin: auto;">
        <div style="width: 10%;">
            <h1>This is side-bar</h1>
        </div>
        <div style="width: 80%;">
            <!-- <EventDetail></EventDetail> -->
            <!-- <EventSearch></EventSearch> -->
            <FavoriteEvent></FavoriteEvent>
        </div>
    </div>


</template>

<script>
// import ReservationForm from './ReservationForm.vue'
//import EventDetail from './EventDetail.vue';
//import EventSearch from '../events/EventSearch.vue'
import FavoriteEvent from '../events/FavoriteEvent.vue'
// import { defineStore } from 'pinia';
export default {
    name: 'ReserveCenter',
    components: {
        // ReservationForm,
        //EventDetail,
        //EventSearch,
        FavoriteEvent,
    }
}
</script>
<style scoped>
.form {
    width: 100%;
}
</style>