<script setup>
import { defineProps } from 'vue'

const props = defineProps({
  role: Number
})
</script>

<template>
    <div class="home-er-holder">
        <h1 style="text-align: center; margin-bottom: 20px;">Events & Reservations</h1>
        <div v-if="props.role === 0">
            <p style="text-align: center; margin-top: 20px;">Please log in to view events and make reservations.</p>
        </div>
    </div>
</template>

<style scoped>
.home-er-holder {
    position: absolute;
    top: calc(290px + 7%);
    left: 12%;
    width: 86%;
    height: 1470px;
    background-color: white;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    /* justify-content: center; */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}
</style>