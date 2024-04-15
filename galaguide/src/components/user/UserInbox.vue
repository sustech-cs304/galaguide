<script setup>
import axios from 'axios';
import {onMounted, ref} from 'vue'

const msgs = ref([])

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
      <div v-for="msg in msgs" :key="msg.title" class="inbox-message">
        <h2 class="msg-title">{{ msg.title }}</h2>
        <p class="msg-sender">{{ msg.sender }}</p>
        <p class="msg-time">{{ msg.time }}</p>
        <p class="msg-content" v-html="msg.content"></p>
      </div>
    </div>
  </div>
</template>

<style scoped>
/*
 * AI-generated-content
 * tool: Copilot
 * version: latest
 * usage: CSS Modify
 * weight: part of
 */
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

.inbox-message {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 70%;
}

.inbox-message:hover {
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
