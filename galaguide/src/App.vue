<script setup>
import { ref, onMounted } from 'vue'
import HomeAnnouncements from './components/HomeAnnouncements.vue'
import HomeFunctions from './components/HomeFunctions.vue'
import HomeERHolder from './components/HomeERHolder.vue'
import LeftSideBar from './components/LeftSideBar.vue'

const role = ref(0) // 0 for not logged in, 1 for user, 2 for admin

function getRoleFromCookie() {
    const cookie = document.cookie.split(';').find(cookie => cookie.trim().startsWith('userRole='))
    console.log('cookie:', cookie)
    if (cookie) {
        const roleValue = cookie.split('=')[1]
        role.value = parseInt(roleValue)
    }
    else {
        role.value = 0
    }
}

onMounted(() => {
    getRoleFromCookie()
})

function logout() {
    document.cookie = "userRole=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"
    role.value = 0
}
</script>

<template>
        <div id="top-bar">
            <div v-if="role === 0">
                <a href="/login" class="login" style="color: aliceblue;">Login</a>
                <a href="/register" class="register" style="color: aliceblue;">Register</a>
            </div>
            <div v-else-if="role === 1 || role === 2">
                <a href="/profile">Profile</a>
                <a href="/inbox">Inbox</a>
                <a href="/logout" @click="logout">Logout</a>
            </div>
        </div>
        <LeftSideBar />
        <HomeAnnouncements />
        <HomeFunctions />
        <HomeERHolder :role="role" />
</template>

<style>
@import url('https://fonts.googleapis.com/css2?family=Montserrat&display=swap');

#app {
    /*font-family: Avenir, Helvetica, Arial, sans-serif;*/
    font-family: 'Montserrat', sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
}

a {
    color: rgb(43, 54, 58);
    text-decoration: none;
}

a:hover {
    color: rgb(115, 201, 230);
    /*text-decoration: underline;*/
}

body {
    background-color: #e5e1db;
}

#top-bar {
    position: absolute;
    top: 0;
    left: 10%;
    width: 90%;
    height: 5%;
    background-color: white;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    z-index: 100;
}

.login,
.register {
    position: absolute;
    top: 50%;
    height: 80%;
    transform: translateY(-50%);
    color: #2c3e50;
    font-size: 0.9em;
    margin-left: 20px;
    margin-right: 20px;
    text-align: center;
    display: flex;
    justify-content: center;
    align-items: center;
    font-weight: bold;
}

.login {
    right: 12%;
    width: 10%;
    background-color: #23b375;
    border-radius: 5px;
}

.register {
    right: 1%;
    width: 10%;
    background-color: #2a78c7;
    border-radius: 5px;
}

.login:hover {
    background-color: #1f9e64;
}

.register:hover {
    background-color: #2467c4;
}
</style>
