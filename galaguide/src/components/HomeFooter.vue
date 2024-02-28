<script setup>
import { ref, onMounted } from 'vue'

const maxHeight = ref(0)

onMounted(() => {
    setTimeout(() => {
        maxHeight.value = document.getElementById('home-er-holder').clientHeight +
            document.getElementById('home-announcements').clientHeight +
            document.getElementById('top-bar').clientHeight
        // console.log(ele.clientHeight,ele.style.border,ele.clientTop)
        console.log('maxHeight:', maxHeight.value)
        document.getElementById('footer').style.top = `calc( 10% + ${maxHeight.value}px )`
    }, 500)
})

</script>

<template>
    <div class="footer" id="footer"></div>
</template>

<style scoped>
.footer {
    position: absolute;
    top: 0;
    left: 10%;
    width: 90%;
    height: 50px;
    background-color: #f0f0f0;
}
</style>
