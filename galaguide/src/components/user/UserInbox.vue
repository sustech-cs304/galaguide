<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue'

const msgs = ref([
    {
        title: 'Welcome to the new semester!',
        sender: 'Admin',
        time: 'Yesterday',
        content: 'Welcome back to the new semester! We hope you have a great time learning and growing with us!<br>\
                Btw, the new course schedule is out, don\'t forget to check it out!<br>\
                Have a great day!'
    },
    {
        title: 'Invitation to the English Corner on Feburary 24th (Saturday)',
        sender: 'English Club',
        time: '2024-02-21',
        content: 'Dear students,<br>\
                We are glad to invite you to the English Corner on Feburary 24th (Saturday).<br>\
                The English Corner is a great place to practice your English and meet new friends.<br>\
                We hope to see you there!'
    }
])

onMounted(() => {
    axios.get('/api/user/inbox')
        .then(response => {
            msgs.value = response.data
        })
        .catch(error => {
            console.error('Error fetching messages:', error)
        })
})

</script>

<template>
    <div id="user-inbox">
        <h1>Inbox</h1>
        <div class="inbox">
            <div class="message" v-for="msg in msgs" :key="msg.title">
                <h2 class="msg-title">{{ msg.title }}</h2>
                <p class="msg-sender">{{ msg.sender }}</p>
                <p class="msg-time">{{ msg.time }}</p>
                <p class="msg-content" v-html="msg.content"></p>
            </div>
        </div>
    </div>
</template>

<style scoped>
    #user-inbox {
        margin-top: 20px;
        position: absolute;
        left: 10%;
        width: 90%;
        font-family: Montserrat, sans-serif;
    }

    h1 {
        color: #333;
        text-align: center;
    }

    .inbox {
        display: flex;
        flex-direction: column;
        gap: 20px;
        left: 50%;
        align-items: center;
        margin-bottom: 50px;
    }

    .message {
        padding: 20px;
        border: 1px solid #ddd;
        border-radius: 5px;
        background-color: #fff;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        width: 70%;
    }

    .message:hover {
        background-color: #f5f5f5;
    }

    .msg-title {
        color: #333;
        margin-top: 0;
        font-size: 18px;
        font-weight: bold;
    }

    .msg-sender {
        color: #666;
        font-size: 14px;
        margin-bottom: 5px;
    }

    .msg-time {
        color: #999;
        font-size: 12px;
        margin-bottom: 10px;
    }

    .msg-content {
        color: #333;
        font-size: 16px;
    }
</style>
