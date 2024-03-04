<script setup>
import { ref, onMounted } from "vue";

const inputText = ref("");

onMounted(() => {
    document.querySelector("#footer").style.display = "none";
});
</script>

<template>
    <div id="chat-room">
        <div id="left-chat-room-bar"></div>
        <div id="main-chat-room">
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
</style>