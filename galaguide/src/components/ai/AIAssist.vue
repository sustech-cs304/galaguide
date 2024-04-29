<template>
    <div id="ai-assist" class="draggable" :style="{ top: yPos + 'px', left: xPos + 'px' }" @mousedown="startDrag" @mousemove="dragging"
        @mouseup="endDrag">
        <div class='wave -one'></div>
        <div class='wave -two'></div>
        <div class='wave -three'></div>
        <div class="gal">
            <div class="eye"></div>
            <div class="eye"></div>
        </div>

    </div>
    <div v-if="showModal" class="modal">
        <div class="modal-content" id="modal-content">
            <span class="close" @click="closeModal">&times;</span>
            <h1>Chat</h1>
            <p>Hello! I'm <strong>Gal</strong>, your personal AI assistant. What can I help you with today?</p>
            <input type="text" placeholder="Type your message here" id="help-holder" />
            <button id="ai-send-button" @click="sendmsg">
                Send
            </button>
        </div>
    </div>
</template>
  
<script setup>
import axios from 'axios';
import { onMounted, ref } from 'vue';

const isDragging = ref(false);
const xPos = ref(1300);
const yPos = ref(700);
const offsetX = ref(0);
const offsetY = ref(0);
const lastMouseDownTime = ref(0);
const showModal = ref(false);

const startDrag = (event) => {
    isDragging.value = true;
    offsetX.value = event.clientX - xPos.value;
    offsetY.value = event.clientY - yPos.value;
    lastMouseDownTime.value = Date.now();
};

const dragging = (event) => {
    if (isDragging.value) {
        xPos.value = event.clientX - offsetX.value;
        yPos.value = event.clientY - offsetY.value;
    }
};

const endDrag = () => {
    isDragging.value = false;
    if (Date.now() - lastMouseDownTime.value < 200) {
        lastMouseDownTime.value = 0;
        showModal.value = true;
    }
};

const closeModal = () => {
    showModal.value = false;
};

const sendmsg = () => {
    axios.post('/api/ai', {
        message: document.getElementById('help-holder').value,
    }).then((response) => {
        console.log(response.data);

        const mymessage = document.createElement('p');
        mymessage.innerHTML = '<strong>Me:</strong><br/>' + document.getElementById('help-holder').value;
        mymessage.classList.add('query-my-message');
        document.querySelector('#modal-content').appendChild(mymessage);

        document.getElementById('help-holder').value = '';

        const message = document.createElement('p');
        message.innerHTML = '<strong>Gal:</strong><br/>' + JSON.parse(response.data).message;
        message.classList.add('query-ai-message');
        document.querySelector('#modal-content').appendChild(message);
    }).catch((error) => {
        console.error(error);
    });
};

onMounted(() => {
    document.addEventListener('keypress', (event) => {
        if (event.key === 'Enter') {
            try {
                sendmsg();
            } catch (error) {
                console.error(error);
            }
        }
    });
});
</script>
  
<style>
.draggable {
    display: none;
    position: fixed;
    cursor: pointer;
    width: 100px;
    height: 100px;
    border-radius: 50%;
    background-color: rgba(0, 0, 255, 0.5);
    border: 3px solid rgba(0, 0, 255, 0.8);
    box-shadow: 0 0 20px rgba(0, 0, 255, 0.5);
    right: 10px;
    bottom: 10px;
}

.gal {
    width: 60px;
    height: 60px;
    background-color: rgba(255, 255, 255, 0.9);
    border-radius: 50%;
    margin: 20px auto;
    position: relative;
    animation: bounce 1s infinite alternate;
}

.eye {
    position: absolute;
    width: 10px;
    height: 20px;
    background-color: rgb(140, 137, 137);
    top: 15px;
    border-radius: 5px;
}

.eye:nth-child(1) {
    left: 15px;
}

.eye:nth-child(2) {
    right: 15px;
}

@keyframes bounce {
    0% {
        transform: translateY(0);
    }

    100% {
        transform: translateY(-10px);
    }
}

.wave {
    opacity: .4;
    position: absolute;
    top: -50%;
    left: -50%;
    background: #0af;
    width: 200%;
    height: 200%;
    /* margin-left: -15px;
    margin-top: -15px; */
    /* margin-left: -250px;
  margin-top: -250px; 
  transform-origin: 50% 48%;*/
    border-radius: 43%;
    animation: drift 3000ms infinite linear;
}

.wave.-three {
    animation: drift 5000ms infinite linear;
    opacity: .2;
    background: rgb(0, 204, 255);
}

.wave.-two {
    animation: drift 7000ms infinite linear;
    opacity: .1;
    background: yellow;
}

@keyframes drift {
    from {
        transform: rotate(0deg);
    }

    from {
        transform: rotate(360deg);
    }
}

.modal {
    display: block;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    overflow: scroll;
}

.modal-content {
    background-color: #fefefe;
    margin: 20% auto;
    padding: 20px;
    border: 1px solid #888;
    width: 50%;
}

.close {
    color: #aaaaaa;
    float: right;
    font-size: 28px;
    font-weight: bold;
}

.close:hover,
.close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}

#help-holder {
    width: 80%;
    height: 30px;
    border-radius: 5px;
    border: 1px solid #ccc;
    padding: 0 10px;
    margin-bottom: 20px;
}


#ai-send-button {
    width: 80%;
    height: 30px;
    border-radius: 5px;
    border: none;
    background-color: #23b375;
    color: white;
    cursor: pointer;
    font-family: 'Montserrat', sans-serif;
}

#ai-send-button:hover {
    background-color: #1f9e64;
}

#modal-content p {
    width: 50%;
    left: 50%;
    transform: translate(50%);
    margin-top: 10px;
    margin-bottom: 10px;
}

.query-my-message {
    background-color: #14e6a0;
    padding: 10px;
    border-radius: 5px;
    margin-bottom: 10px;
    text-align: left;
    overflow-wrap: break-word;
}

.query-ai-message {
    background-color: #58c6fc;
    padding: 10px;
    border-radius: 5px;
    margin-bottom: 10px;
    text-align: right;
    overflow-wrap: break-word;
}
</style>
  