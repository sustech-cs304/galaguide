<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const inputText = ref("");
const groups = ref([]);

onMounted(() => {
    document.querySelector("#footer").style.display = "none";

    axios.get("/api/groups")
        .then(response => {
            groups.value = response.data;
        })
        .catch(error => {
            console.error('Error fetching groups:', error);
        });
});

const showGroup = (id) => {
    document.querySelector("#main-chat-room").style.display = "block";
    axios.get(`/api/groups/${id}`)
        .then(response => {
            console.log(response.data);
            document.querySelector("#group-details").innerHTML = `
                <div id="group-avatar">
                <h1>${response.data.name}</h1></div>
                <div class="group-members">
                    <h3>Members</h3>
                       ${response.data.members.map(member => `<p class="one-group-member">${member}</p>`).join("")}
                </div>
                <button id="leave-group">Leave Group</button>
            `;
            document.querySelector("#chat-box").innerHTML = response.data.messages.map(message => `
                <div class="msg-username">${message.sender} : <span style="color: grey;">${message.time}</span></div>
                <div class="message">
                    <p>${message.content}</p>
                </div>
            `).join("");
            document.querySelector("#chat-box").scrollTop = document.querySelector("#chat-box").scrollHeight;
        })
        .catch(error => {
            console.error('Error fetching group:', error);
        });
};
</script>

<template>
    <div id="chat-room">
        <div id="left-chat-room-bar">
            <div v-for="group in groups" :key="group.id" class="group">
                <p class="group-name" @click="showGroup(group.id)">
                    {{ group.name }}
                </p>
            </div>
        </div>
        <div id="main-chat-room" style="display: none;">
            <div id="group-details">
                <div id="group-avatar"></div>
                <div id="members-holder"></div>
                <button id="leave-group">Leave Group</button>
            </div>
            <div id="group-chat">
                <div id="pinned"></div>
                <div id="chat-box"></div>
                <div id="bottom-input">
                    <textarea v-model="inputText" rows="4" id="input-box"></textarea>
                    <button id="send-button">Send</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style>
#chat-room {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    height: 100%;
}

#left-chat-room-bar {
    position: fixed;
    top: 5%;
    left: 10%;
    width: 10%;
    height: 95%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#main-chat-room {
    position: absolute;
    top: 5%;
    left: 20%;
    width: 80%;
    height: 95%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#group-details {
    position: absolute;
    top: 0;
    left: 0;
    width: 20%;
    height: 100%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#group-chat {
    position: absolute;
    top: 0;
    left: 20%;
    width: 80%;
    height: 100%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#pinned {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 10%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#chat-box {
    position: absolute;
    top: 10%;
    left: 0;
    width: 100%;
    height: 70%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: auto;
}

#bottom-input {
    position: absolute;
    bottom: 0;
    left: 0%;
    width: 100%;
    height: 20%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#input-box {
    position: absolute;
    top: 50%;
    left: 5%;
    width: 75%;
    height: 50%;
    border-radius: 5px;
    border: 1px solid #ccc;
    padding: 0 10px;
    transform: translateY(-50%);
}

#send-button {
    position: absolute;
    top: 50%;
    left: 85%;
    width: 10%;
    height: 30%;
    border-radius: 5px;
    border: none;
    background-color: #23b375;
    color: white;
    cursor: pointer;
    transform: translateY(-50%);
    font-family: 'Montserrat', sans-serif;
}

#send-button:hover {
    background-color: #1f9e64;
}

.group {
    padding: 10px;
    background-color: #f9f9f9;
    margin-bottom: 10px;
    border-radius: 5px;
    cursor: pointer;
}

.group:hover {
    background-color: #ebebeb;
}

.group-name {
    font-size: 0.9em;
}

.group-name:hover {
    color: #23b375;
}

.message {
    display: flex;
    justify-content: space-between;
    padding: 8px;
    background-color: #f9f9f9;
    width: max-content;
    margin-left: 5%;
    margin-right: 5%;
    border-radius: 8px;
    margin-bottom: 10px;
}

#group-avatar {
    position: absolute;
    top: 0%;
    left: 50%;
    width: 100%;
    height: 20%;
    background-color: #ccc;
    transform: translateX(-50%);
}

#members-holder {
    position: absolute;
    top: 60%;
    left: 50%;
    width: 50%;
    height: 30%;
    transform: translateX(-50%);
}

#leave-group {
    position: absolute;
    bottom: 5%;
    left: 50%;
    width: 50%;
    height: 5%;
    border-radius: 5px;
    border: none;
    background-color: #e74c3c;
    color: white;
    cursor: pointer;
    transform: translateX(-50%);
    font-family: 'Montserrat', sans-serif;
}

#leave-group:hover {
    background-color: #c0392b;
}

.message {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    max-width: 70%;
    margin: 10px;
    padding: 12px;
    border-radius: 12px;
    background-color: #DCF8C6; 
    position: relative;
}

.message p {
    margin: 4px 0;
    font-size: 0.9em;
    line-height: 1.4;
    color: #333;
}

.message p.sender {
    font-weight: bold; 
}

.message p.content {
    margin-left: 0; 
}

.message::before {
    content: '';
    position: absolute;
    top: 50%;
    right: 100%;
    margin-top: -10px;
    border-width: 10px;
    border-style: solid;
    border-color: transparent #DCF8C6 transparent transparent;
}

.msg-username {
    position: relative;
    display: flex;
    left: 10px;
    font-weight: bold;
    color: #555; 
    margin-bottom: 4px; 
    align-self: flex-start; 
}

.group-members {
    position: absolute;
    top: 20%;
    left: 50%;
    transform: translateX(-50%);
    width: 100%;
    height: 60%;
    background-color: #f9f9f9;
    margin-bottom: 10px;
    border-radius: 5px;
    overflow: auto;
}

.one-group-member {
    font-size: 0.9em;
    padding: 8px;
    margin-bottom: 5px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s; 
}

.one-group-member:hover {
    background-color: #ebebeb;
}

</style>