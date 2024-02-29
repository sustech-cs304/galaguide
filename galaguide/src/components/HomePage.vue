<script setup>
import HomeFunctions from "./home/HomeFunctions.vue";
import HomeAnnouncements from "./home/HomeAnnouncements.vue";
import HomeERHolder from "./home/HomeERHolder.vue";
import { defineProps } from 'vue'

const propss = defineProps({
    role: Number
})

</script>

<template>
    <HomeAnnouncements/>
    <HomeFunctions/>
    <HomeERHolder :role="propss.role"/>
</template>

<style scoped>

</style>