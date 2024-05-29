<script setup>
/* 
 * Console component for admin users
 *
 * This component is responsible for displaying the admin console to manage the application using commandline-like interface.
 * 
 * Main features:
 * 
 * - User management:
 *     - View all users: `user list`
 *     - View user details (including reservations, images, etc.): `user {id} {reservations|images|events|forum|chatrooms}`
 *     - Delete users: `user delete {id}`
 * 
 * - Image management:
 *     - View all images: `image list`
 *     - Delete images: `image delete {id}`
 * 
 * - Event management:
 *     - View all events: `event list`
 *     - View event details: `event {id}`
 *     - Reject/Accept pending events: `event reject|accept {id}`
 * 
 * - Forum management:
 *     - View all forum posts: `forum list`
 *     - Delete forum posts: `forum delete {id}`
 * 
 * - Chatroom management:
 *     - View all chatrooms: `chatroom list`
 *     - View chatroom details: `chatroom {id}`
 *     - Delete chatrooms: `chatroom delete {id}`
 * 
 * - Help:
 *     - Display help message: `help`
 * 
 * 
 */

import { onMounted, ref } from 'vue';
import axios from 'axios';

const consoleInput = ref('');
const consoleOutput = ref('');
const pre = 'admin@console:~$';

onMounted(() => {
    consoleOutput.value = 'Welcome to the admin console. Type <span style="color: yellow">help</span> to display help message.';
});

const handleConsoleInput = () => {
    consoleOutput.value += '\n' + '<span style="color: wheat">' + pre + '&emsp;' + consoleInput.value + '</span>';
    if (consoleInput.value === 'help') {
        consoleOutput.value += getHelpMessage();
    }
    else if (consoleInput.value === 'clear') {
        consoleOutput.value = '';
    }
    else if (consoleInput.value.startsWith('user')) {
        if (consoleInput.value === 'user list') {
            axios.get('/api/user').then((res) => {
                if (res.data.code !== 0) {
                    consoleOutput.value += '<br><span style="color: red">' + res.data.message + '</span>';
                }
                else if (res.data.data.length === 0) {
                    consoleOutput.value += '<br>No users found.';
                }
                else {
                    res.data.data.forEach((user) => {
                        consoleOutput.value += '<br>' + user.id + ' - ' + user.email;
                    });
                }
            }).catch((err) => {
                consoleOutput.value += '<br><span style="color: red">' + err.response.data.message + '</span>';
            });
        }
        else if (consoleInput.value.startsWith('user delete')) {
            const id = consoleInput.value.split(' ')[2];
            axios.delete(`/api/user/${id}`).then((res) => {
                consoleOutput.value += '<br>' + JSON.stringify(res.data, null, 2);
            }).catch((err) => {
                consoleOutput.value += '<br><span style="color: red">' + err.response.data.message + '</span>';
            });
        }
        else {
            consoleOutput.value += '<br><span style="color: red">Invalid command. Type <span style="color: yellow">help</span> to display help message.</span>';
        }
    }
    else if (consoleInput.value.startsWith('image')) {
        if (consoleInput.value === 'image list') {
            axios.get('/api/asset').then((res) => {
                if (res.data.code !== 0) {
                    consoleOutput.value += '<br><span style="color: red">' + res.data.message + '</span>';
                }
                else if (res.data.data.length === 0) {
                    consoleOutput.value += '<br>No images found.';
                }
                else {
                    res.data.data.forEach((img) => {
                        consoleOutput.value += '<br>' + img.uuid + ' - ' + img.name;
                    });
                }
            }).catch((err) => {
                consoleOutput.value += '<br><span style="color: red">' + err.response.data.message + '</span>';
            });
        }
        else if (consoleInput.value.startsWith('image delete')) {
            const id = consoleInput.value.split(' ')[2];
            axios.delete(`/api/asset/${id}`).then((res) => {
                consoleOutput.value += '<br>' + JSON.stringify(res.data, null, 2);
            }).catch((err) => {
                consoleOutput.value += '<br><span style="color: red">' + err.response.data.message + '</span>';
            });
        }
        else {
            consoleOutput.value += '<br><span style="color: red">Invalid command. Type <span style="color: yellow">help</span> to display help message.</span>';
        }
    }
    else if (consoleInput.value.startsWith('event')) {
        if (consoleInput.value === 'event list') {
            axios.get('/api/event').then((res) => {
                if (res.data.code !== 0) {
                    consoleOutput.value += '<br><span style="color: red">' + res.data.message + '</span>';
                }
                else if (res.data.data.length === 0) {
                    consoleOutput.value += '<br>No events found.';
                }
                else {
                    res.data.data.forEach((event) => {
                        consoleOutput.value += '<br>' + event.id + ' - ' + event.name;
                    });
                }
            }).catch((err) => {
                consoleOutput.value += '<br><span style="color: red">' + err.response.data.message + '</span>';
            });
        }
        else if (consoleInput.value.startsWith('event reject')) {
            const id = consoleInput.value.split(' ')[2];
            axios.put(`/api/event/${id}/reject`).then((res) => {
                consoleOutput.value += '<br>' + JSON.stringify(res.data, null, 2);
            }).catch((err) => {
                consoleOutput.value += '<br><span style="color: red">' + err.response.data.message + '</span>';
            });
        }
        else if (consoleInput.value.startsWith('event accept')) {
            const id = consoleInput.value.split(' ')[2];
            axios.put(`/api/event/${id}/accept`).then((res) => {
                consoleOutput.value += '<br>' + JSON.stringify(res.data, null, 2);
            }).catch((err) => {
                consoleOutput.value += '<br><span style="color: red">' + err.response.data.message + '</span>';
            });
        }
        else {
            consoleOutput.value += '<br><span style="color: red">Invalid command. Type <span style="color: yellow">help</span> to display help message.</span>';
        }
    }
    else {
        consoleOutput.value += '<br><span style="color: red">Command not found.</span> Type <span style="color: yellow">help</span> to display help message.';
    }
    consoleInput.value = '';
}

const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
        handleConsoleInput();
        // window.scrollTo(0, document.body.scrollHeight);
        document.getElementById('console-output').scrollTop = document.getElementById('console-output').scrollHeight;
    }
}

const getHelpMessage = () => {
    return `
    <p style="color: green">User management:</p>
    <p>    - View all users: <span style="color: yellow">user list</span></p>
    <p>    - View user details (including reservations, images, etc.): <span style="color: yellow">user {id} {reservations|images|events|forum|chatrooms}</span></p>
    <p>    - Delete users: <span style="color: yellow">user delete {id}</span></p>
    <p style="color: green">Image management:</p>
    <p>    - View all images: <span style="color: yellow">image list</span></p>
    <p>    - Delete images: <span style="color: yellow">image delete {id}</span></p>
    <p style="color: green">Event management:</p>
    <p>    - View all events: <span style="color: yellow">event list</span></p>
    <p>    - View event details: <span style="color: yellow">event {id}</span></p>
    <p>    - Reject/Accept pending events: <span style="color: yellow">event reject|accept {id}</span></p>
    <p style="color: green">Forum management:</p>
    <p>    - View all forum posts: <span style="color: yellow">forum list</span></p>
    <p>    - Delete forum posts: <span style="color: yellow">forum delete {id}</span></p>
    <p style="color: green">Chatroom management:</p>
    <p>    - View all chatrooms: <span style="color: yellow">chatroom list</span></p>
    <p>    - View chatroom details: <span style="color: yellow">chatroom {id}</span></p>
    <p>    - Delete chatrooms: <span style="color: yellow">chatroom delete {id}</span></p>
    <p style="color: green">Help:</p>
    <p>    - Display help message: <span style="color: yellow">help</span></p>
    `;
}
</script>

<template>
    <div id="admin-console">
        <div id="console">
            <div id="console-output">
                <p v-for="line in consoleOutput.split('\n')" :key="line" v-html="line"></p>
            </div>
            <div id="console-input">
                <input type="text" placeholder="Enter command here" v-model="consoleInput" @keydown="handleKeyDown" />
            </div>
        </div>
    </div>
</template>

<style scoped>
#admin-console {
    position: absolute;
    top: 0%;
    left: 10%;
    width: 90%;
    height: 100%;
    overflow: scroll;
}

#console {
    position: absolute;
    top: 10%;
    left: 10%;
    width: 80%;
    height: 80%;
    background-color: black;
    color: white;
    border-radius: 10px;
    padding: 20px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    font-family:system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

#console-output {
    height: 90%;
    overflow: scroll;
    z-index: 999;
}

#console-input {
    height: 10%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}

#console-input input {
    width: 90%;
    height: 100%;
    border: none;
    background-color: black;
    color: white;
    font-size: 100%;
    padding: 0px;
}

#console-input input:focus {
    outline: none;
}

#console-output p {
    font-size: 100%;
    margin: 0;
}

#console-output p:nth-child(odd) {
    /*color: rgb(189, 194, 189);*/
    color: white;
}

#console-output p:nth-child(even) {
    color: white;
}

#console-output p:hover {
    background-color: #333;
}

#console-output p:hover {
    background-color: #333;
}


</style>