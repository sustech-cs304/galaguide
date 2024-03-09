<script setup>
import HomeFunctions from "./home/HomeFunctions.vue";
import HomeAnnouncements from "./home/HomeAnnouncements.vue";
import HomeERHolder from "./home/HomeERHolder.vue";
import { defineProps, onMounted, onUnmounted } from 'vue'

const propss = defineProps({
    role: Number
})

onMounted(() => {
    document.querySelector('#ai-assist').style.display = 'block';
})

onUnmounted(() => {
    document.querySelector('#ai-assist').style.display = 'none';
    document.querySelector('#footer').style.display = 'none';
})

</script>

<template>
    <HomeAnnouncements/>
    <HomeFunctions/>
    <HomeERHolder :role="propss.role"/>
</template>

<style scoped>

</style>