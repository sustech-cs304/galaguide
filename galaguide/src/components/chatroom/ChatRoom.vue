<script setup>
import axios from "axios";
import { ref, onMounted } from "vue";

const inputText = ref("");
const groups = ref([
    {
        name: "Group 1",
        id: 1,
        members: ["User 1", "User 2", "User 3"],
        messages: [
            { sender: "User 1", content: "Hello" },
            { sender: "User 2", content: "Hi" },
            { sender: "User 3", content: "Hey" }
        ]
    },
    {
        name: "Group 2",
        id: 2,
        members: ["User 1", "User 2", "User 3"],
        messages: [
            { sender: "User 1", content: "Hello" },
            { sender: "User 2", content: "Hi" },
            { sender: "User 3", content: "Hey" }
        ]
    }
]);

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
            document.querySelector("#group-details").innerHTML = `
                <h1>${response.data.name}</h1>
                <p>Members: ${response.data.members.join(", ")}</p>
            `;
            document.querySelector("#chat-box").innerHTML = response.data.messages.map(message => `
                <div class="message">
                    <p>${message.sender}</p>
                    <p>${message.content}</p>
                </div>
            `).join("");
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
            <div id="group-details"></div>
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

<style scoped>
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
    overflow: hidden;
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
    width: 80%;
    margin-left: 5%;
    border-radius: 8px;
    margin-bottom: 10px;
}
</style>