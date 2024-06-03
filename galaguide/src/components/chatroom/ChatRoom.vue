<script setup>
import axios from "axios";
import { ref, onMounted} from "vue";

const inputText = ref("");
const groups = ref([]);
const cur_g_id = ref(null);

var socket = null;
var lockReconnect = false;
var wsUrl = 'ws://43.139.21.229:9262/ws';
// var wsUrl = 'ws://10.16.165.97:9260/ws';
var timeout = 4000;
var timeoutnum = null;

const getCookie = (name) => {
    let cookieValue = null;
    if (document.cookie && document.cookie !== '') {
        const cookies = document.cookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            const cookie = cookies[i].trim();
            if (cookie.substring(0, name.length + 1) === (name + '=')) {
                cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                break;
            }
        }
    }
    return cookieValue;
};

const initWebsocket = async () => {
    if ('WebSocket' in window) {
        // socket = new WebSocket(wsUrl, getCookie("token"));
        socket = new WebSocket(wsUrl);
        console.log('WebSocket created');
        socket.onerror = function () {
            reconnect();
        }
        socket.onopen = function () {
            console.log('WebSocket open');
            clearTimeout(timeoutnum);
            // The first message sent to the server must be:
            // { "event": "auth", "token": "your_token" }
            socket.send(JSON.stringify({ "event": "auth", "token": localStorage.getItem("token") }));
            console.log('WebSocket auth sent');
        }
        socket.onmessage = function (event) {
            console.log('WebSocket message:', event.data);
            const jsonData = JSON.parse(event.data);
            // document.querySelector("#chat-box").innerHTML += `<div class="message">
            //     <div class="msg-username">${jsonData.sender} : <span style="color: grey;">${jsonData.time}&emsp;</span></div>
            //         <p>${jsonData.content}</p>
            //     </div>
            // `;
            // document.querySelector("#chat-box").innerHTML += `<div class="message">
            //     <div class="user-info">
            //         <img src="${jsonData.avatar}" alt="User Avatar" class="avatar">
            //         <div class="msg-username">${jsonData.sender} : <span class="message-time">${jsonData.time}&emsp;</span></div>
            //     </div>
            //     <p>${jsonData.content}</p>
            // </div>
            // `;
            document.querySelector("#chat-box").innerHTML += `<div class="message">
                <div class="user-info">
                    <img src="${jsonData.avatar}" alt="User Avatar" class="avatar">
                    <div class="msg-username">${jsonData.sender} <span class="message-time" style="color: grey;">&emsp;${jsonData.time}&emsp;</span></div>
                </div>
                <p>${easyMarkdownConversion(jsonData.content)}</p>
            </div>
            `;
            document.querySelectorAll(".message").forEach(msg => {
                if (msg.querySelector(".msg-username").textContent.includes(getCookie("username"))) {
                    msg.classList.add("my-message");
                }
            });
            document.querySelector("#chat-box").scrollTop = document.querySelector("#chat-box").scrollHeight;
        }
        socket.onclose = function () {
            console.log('WebSocket close');
            reconnect();
        }
    } else {
        console.log('Your browser does not support WebSocket!');
    }
};

const reconnect = () => {
    if (lockReconnect) return;
    lockReconnect = true;
    timeoutnum = setTimeout(() => {
        initWebsocket();
        lockReconnect = false;
    }, timeout);
};

const sendMessage = (g_id) => {
    if (inputText.value.trim() === "") {
        return;
    }
    let json_msg = {
        "sender": getCookie("username"),
        "content": inputText.value,
        "time": new Date().toLocaleTimeString(),
        "group_id": g_id
    };
    json_msg = JSON.stringify(json_msg);
    socket.send(json_msg);
    inputText.value = "";
};

const easyMarkdownConversion = (text) => {
    let newText = text;
    if (!newText) return newText;
    // text inside ``` ``` is protected from markdown conversion
    let codeBlocks = newText.match(/```(.*?)```/g);
    if (codeBlocks) {
        codeBlocks.forEach(block => {
            newText = newText.replace(block, `<code>${block}</code>`);
        });
    }
    // // bold text
    // newText = newText.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
    // // italic text
    // newText = newText.replace(/\*(.*?)\*/g, '<em>$1</em>');
    // // strikethrough text
    // newText = newText.replace(/~~(.*?)~~/g, '<del>$1</del>');
    // // inline code
    // newText = newText.replace(/`(.*?)`/g, '<code>$1</code>');
    // images
    newText = newText.replace(/!\[(.*?)\]\((.*?)\)/g, '<img src="$2" alt="$1">');
    // links
    newText = newText.replace(/\[(.*?)\]\((.*?)\)/g, '<a href="$2" target="_blank" style="color: blue;">$1</a>');
    // blockquotes
    newText = newText.replace(/>(.*)/g, '<blockquote>$1</blockquote>');
    // unordered lists
    newText = newText.replace(/^\s*\*\s(.*)/gm, '<ul><li>$1</li></ul>');
    // ordered lists
    newText = newText.replace(/^\s*\d\.\s(.*)/gm, '<ol><li>$1</li></ol>');
    return newText;
};

onMounted(() => {
    document.querySelector("#footer").style.display = "none";

    axios.get("/v2-api/groups",{
        headers: {
            'Authorization': `Bearer ${getCookie("token")}`
        }
    })
        .then(response => {
            groups.value = response.data;
        })
        .catch(error => {
            console.error('Error fetching groups:', error);
        });

    initWebsocket();

    document.querySelector('#input-box').addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            sendMessage(cur_g_id.value);
        }
    });
});

const showGroup = (id) => {
    document.querySelector("#main-chat-room").style.display = "block";
    axios.get(`/v2-api/groups/${id}`)
        .then(response => {
            console.log(response.data);
            document.querySelector("#group-details").innerHTML = `
                <div id="group-avatar" style="background-image: url(${response.data.avatar})">
                    <h3 style="position: absolute; 
                    top: 10%; 
                    left: 50%; 
                    transform: translateX(-50%); 
                    width: 90%;
                    text-align: center;
                    color: white;
                    text-shadow: 2px 2px 2px black;
                    ">${response.data.name}</h3>
                </div>
                <div class="group-members">
                    <h3>Members</h3>
                       ${response.data.members.map(member => `
                       <router-link to="/space/${member.id}">
                            <div class="one-group-member">
                                <img src="${member.avatar}" alt="User Avatar" class="avatar">
                                <p>${member.name}</p>
                            </div>
                        </router-link>
                       `).join("")}
                </div>
                <button id="leave-group">Leave Group</button>
            `;
            // document.querySelector("#chat-box").innerHTML = response.data.messages.map(message => `
            // <div class="message">
            //     <div class="msg-username">
            //         ${message.sender} : <span style="color: grey;">${message.time}&emsp;</span></div>
            //         <p>${message.content}</p>
            // </div>
            // `).join("");

            document.querySelector("#chat-box").innerHTML = response.data.messages.map(message => `
                <div class="message">
                    <div class="user-info">
                        <img src="${response.data.members.find(member => member.name === message.sender).avatar}" alt="User Avatar" class="avatar">
                        <div class="msg-username">${message.sender} <span class="message-time" style="color: grey;">&emsp;${message.time}&emsp;</span></div>
                    </div>
                    <p>${easyMarkdownConversion(message.content)}</p>
                </div>
            `).join("");

            document.querySelector("#pinned").innerHTML = `
                <p><strong>Pinned Message:</strong> ${response.data.pinned.content}<br/>${response.data.pinned.sender} <span style="color: grey;">${response.data.pinned.time}</span></p>
            `;

            document.querySelector("#chat-box").scrollTop = document.querySelector("#chat-box").scrollHeight;
            cur_g_id.value = id;
            // document.querySelector("#group-avatar").style.backgroundImage = `url(${response.data.avatar})`;
            document.querySelectorAll(".message").forEach(msg => {
                if (msg.querySelector(".msg-username").textContent.includes(getCookie("username"))) {
                    msg.classList.add("my-message");
                }
            });

            // enable replying to messages
            document.querySelectorAll(".message").forEach(msg => {
                msg.addEventListener("click", () => {
                    inputText.value = `> ${msg.querySelector(".msg-username").textContent.split(" ")[0]}\n> \n> ${msg.querySelector("p").textContent}\n\n`;
                    document.querySelector("#input-box").focus();
                });
            });
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
        <div id="char-room-tip" style="display: block;
            position: absolute;
            top: 50%;
            left: 60%;
            transform: translate(-50%, -50%);
            text-align: center;">
            <h1>Click on a group to start chatting</h1>
        </div>
        <div id="main-chat-room" style="display: none;">
            <div id="group-details">
                <div id="group-avatar"></div>
                <div id="members-holder"></div>
                <button id="leave-group">Leave Group</button>
            </div>
            <div id="group-chat">
                <div id="pinned"></div>
                <div id="chat-box">
                </div>
                <div id="bottom-input">
                    <textarea v-model="inputText" rows="4" id="input-box"></textarea>
                    <button id="send-button" @click="sendMessage(cur_g_id)">
                        Send
                    </button>
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
    overflow: auto;
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
    border-radius: 20px;
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

/* .message {
    display: flex;
    justify-content: space-between;
    padding: 8px;
    background-color: #f9f9f9;
    width: max-content;
    margin-left: 5%;
    margin-right: 5%;
    border-radius: 8px;
    margin-bottom: 10px;
} */

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
    width: max-content;
    margin-right: 5%;
    background-color: #DCF8C6; 
    position: relative;
}

.message.my-message {
    background-color: #15d3f0; 
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

.message.my-message::before {
    border-color: transparent transparent transparent #15d3f0;
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

.avatar {
    width: 40px;
    height: 40px; 
    border-radius: 50%; 
    margin-right: 10px; 
}

.user-info {
    display: flex;
    align-items: center;
}

#group-avatar {
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    height: 20%;
    width: 100%;
    align-items: center;
}
</style>