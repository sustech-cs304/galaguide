<!-- Reservation center and event -->
<template>
    <div class="form">
        <ReservationForm></ReservationForm>
    </div>
</template>

<script>
import ReservationForm from './ReservationForm.vue'
export default{
    name: 'ReserveCenter',
    components: {
        ReservationForm
    }
}
</script>
<style scoped>
.form{
    width: 100%;
}
</style>