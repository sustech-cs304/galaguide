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
        <div class="modal-content">
            <span class="close" @click="closeModal">&times;</span>
            <h1>Chat</h1>
            <p>Hello! I'm <strong>Gal</strong>, your personal AI assistant. What can I help you with today?</p>
            <input type="text" placeholder="Type your message here" id="help-holder"/>
            <button type="submit" class="btn btn-primary" id="send-button">
                Send
            </button>
        </div>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            isDragging: false,
            xPos: 1300,
            yPos: 700,
            offsetX: 0,
            offsetY: 0,
            lastMouseDownTime: 0,
            showModal: false,
        };
    },
    methods: {
        startDrag(event) {
            this.isDragging = true;
            this.offsetX = event.clientX - this.xPos;
            this.offsetY = event.clientY - this.yPos;
            this.lastMouseDownTime = Date.now();
        },
        dragging(event) {
            if (this.isDragging) {
                this.xPos = event.clientX - this.offsetX;
                this.yPos = event.clientY - this.offsetY;
            }
        },
        endDrag() {
            this.isDragging = false;
            if (Date.now() - this.lastMouseDownTime < 200) {
                this.lastMouseDownTime = 0;
                this.showModal = true;
            }
        },
        closeModal() {
            // Close the modal
            this.showModal = false;
        }
    }
};
</script>
  
<style scoped>
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
}

.modal-content {
    background-color: #fefefe;
    margin: 20% auto;
    padding: 20px;
    border: 1px solid #888;
    width: 80%;
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

.btn-primary {
    width: 20%;
    height: 30px;
    border-radius: 5px;
    border: none;
    background-color: #23b375;
    color: white;
    cursor: pointer;
    font-family: 'Montserrat', sans-serif;
}

.btn-primary:hover {
    background-color: #1f9e64;
}
</style>
  