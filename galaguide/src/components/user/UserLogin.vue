<template>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h1>Login with your account</h1>
                <form @submit="check">
                    <div class="form-group">
                        <label>Name: </label>
                        <input type="text" class="form-control" v-model="user.name">
                    </div>
                    <div class="form-group">
                        <label>Password: </label>
                        <input type="text" class="form-control" v-model="user.password">
                    </div>
                    <div class="form-group">
                        <label id="errors" style="color: red;position: relative;left: 0;"></label>
                    </div>
                    <button type="submit" class="btn btn-primary" v-on:click="login()">Submit</button>
                </form>
                <router-link to="/register" class="btn btn-link">Don't have an account? Register.</router-link>
            </div>
        </div>
    </div>
    <div class="words"></div>
</template>

<script setup>
import UserService from "../../service/UserService";
import { ref, onMounted, onUnmounted } from "vue";

const user = ref({
    name: "",
    email: "",
    password: ""
});

const errors = ref([]);

const encSHA256 = async (str) => {
    const encoder = new TextEncoder();
    const data = encoder.encode(str);
    const hash = await crypto.subtle.digest("SHA-256", data);
    let result = "";
    const view = new DataView(hash);
    for (let i = 0; i < hash.byteLength; i += 4) {
        result += ("00000000" + view.getUint32(i).toString(16)).slice(-8);
    }
    return result;
};

const check = (e) => {
    e.preventDefault();
    errors.value = [];

    UserService.findUserByName(user.value.name).then((res) => {
        if (res.data.length === 0) {
            alert("Invalid credentials");
            console.log("Invalid credentials");
            errors.value.push("Invalid credentials");
        } else {
            let password = "";
            for (let i = 0; i < res.data.length; i++) {
                if (user.value.name === res.data[i].name) {
                    user.value.id = res.data[i].id;
                    password = res.data[i].password;
                    break;
                }
            }
            if (encSHA256(user.value.password) === password) {
                // alert("Login successful");
                console.log("Login successful");
            } else {
                // alert("Invalid credentials");
                document.getElementById("errors").textContent = "Invalid credentials";
                console.log("Invalid credentials");
                errors.value.push("Invalid credentials");
            }
        }
    });
};

onMounted(() => {
    console.log("UserLogin mounted");
    const ele = document.querySelector(".words");
    setInterval(() => {
        for (let i = 0; i < 1; i++) {
            const span = document.createElement("span");
            span.textContent = "🍀 Welcome to GalaGuide";
            span.style.fontSize = Math.random() * 20 + 10 + "px";
            span.style.color = `hsl(${Math.random() * 360}, 50%, 50%)`;
            span.style.position = "absolute";
            span.style.left = "100%";
            span.style.top = Math.random() * 100 + "%";
            span.style.zIndex = -1;
            ele.appendChild(span);
            let shit = 100;
            setInterval(() => {
                shit -= 0.1;
                span.style.left = shit + "%";
            }, 10);
            setTimeout(() => {
                span.remove();
            }, 20000);
        }
    }, 3000);
});

onUnmounted(() => {
    console.log("UserLogin unmounted");
});
</script>

<style scoped>
.container {
    margin-top: 50px;
    position: absolute;
    left: 10%;
    width: 90%;
}

label {
    position: relative;
    margin-bottom: 5px;
    font-size: 1.2rem;
    color: #333;
    left: -15%;
    width: 10%;
}

h1 {
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 20px;
}

input {
    position: absolute;
    width: 20%;
    height: 30px;
    left: 50%;
    margin-bottom: 20px;
    border-radius: 5px;
    border: 1px solid #ccc;
    padding: 0 10px;
    transform: translate(-50%);
}

.btn-primary {
    position: absolute;
    width: 5%;
    height: 30px;
    left: 50%;
    margin-bottom: 20px;
    border-radius: 5px;
    border: none;
    background-color: #23b375;
    color: white;
    cursor: pointer;
    transform: translate(-50%);
    font-family: 'Montserrat', sans-serif;
}

.btn-primary:hover {
    background-color: #1f9e64;
}

.btn-link {
    position: absolute;
    width: 55%;
    top: 120%;
    height: 30px;
    left: 50%;
    margin-bottom: 20px;
    border-radius: 5px;
    border: none;
    color: black;
    cursor: pointer;
    transform: translate(-50%);
    font-family: 'Montserrat', sans-serif;
    justify-content: center;
    align-items: center;
    text-align: center;
}

.btn-link:hover {
    color: rgb(133, 207, 232);
    text-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

@keyframes fadeInOut {
    0%, 100% { opacity: 0; }
    50% { opacity: 1; }
}

.words {
    border: 1px solid black;
    white-space: nowrap;
    overflow: hidden;
    display: inline-block;
}
</style>