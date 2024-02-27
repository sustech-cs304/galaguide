<script setup>
</script>

<template>
    <div class="left-side-bar">
        <div class="bar-logo">
            <img src="../assets/logo.png" alt="logo.png" style="width: 30%; height: auto;" />
            <h1>GalaGuide</h1>
        </div>
        <div class="bar-options" style="top: 120px;">
            <img src="../assets/logo.png" alt="events.png" style="width: 30%; height: auto;" />
            <h1>Events</h1>
        </div>
        <div class="bar-options" style="top: 240px;">
            <img src="../assets/logo.png" alt="reservations.png" style="width: 30%; height: auto;" />
            <h1>Reservations</h1>
        </div>
        <div class="bar-options" style="top: 360px;">
            <img src="../assets/logo.png" alt="forum.png" style="width: 30%; height: auto;" />
            <h1>Forum</h1>
        </div>
        <div class="bar-options" style="top: 480px;">
            <img src="../assets/logo.png" alt="chat.png" style="width: 30%; height: auto;" />
            <h1>Chat</h1>
        </div>
    </div>
</template>

<style scoped>
.left-side-bar {
    position: fixed;
    top: 0;
    left: 0;
    width: 10%;
    height: 100%;
    background-color: white;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

.bar-logo {
    position: absolute;
    width: 100%;
    height: 100px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: #2c3e50;
    color: white;
    font-size: 50%;
    font-weight: bold;
}

.bar-options {
    position: absolute;
    width: 90%;
    left: 5%;
    border-radius: 10px;
    height: 100px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    background-color: #9ca3ab;
    color: white;
    font-size: 50%;
    font-weight: bold;
}

.bar-logo:hover{
    background-color: #34495e;
}

.bar-options:hover{
    background-color: #a5adba;
}

.bar-logo img, .bar-options {
    margin-top: 10px;
    margin-bottom: 10px;
}
</style>
