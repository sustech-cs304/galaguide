<template>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h1>Register to enroll in campus galas!</h1>
                <form @submit="validateAndSubmit">
                    <div class="form-group">
                        <label>Your Name: </label>
                        <input type="text" class="form-control" v-model="user.name">
                    </div>
                    <div class="form-group">
                        <label>Your Email: </label>
                        <input type="text" class="form-control" v-model="user.email">
                    </div>
                    <div class="form-group">
                        <label>Your Password: </label>
                        <input type="text" class="form-control" v-model="user.password">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                    <router-link to="/login" class="btn btn-link">Already have an account? Login.</router-link>
                </form>
            </div>
        </div>
    </div>
    <div class="words"></div>
</template>

<script setup>
import UserService from "../../service/UserService";
import { ref, onMounted } from "vue";

const user = ref({
    name: "",
    email: "",
    password: ""
});
const errors = ref([]);

const validateAndSubmit = (e) => {
    e.preventDefault()
    errors.value = [];
    if (!user.value.name) {
        errors.value.push("Enter valid values");
    }
    UserService.findUserByName(user.value.name).then((res) => {
        if (res.data.name === user.value.name) {
            errors.value.push("User already exists");
        }
    });
};

const sentences = [
    "Welcome to GalaGuide! 🎉",
    "Enroll in campus galas! 🎈",
    "Create your own events! 🎊",
    "Make reservations! 🎁",
    "Join the fun! 🎇",
    "Meet new people! 🎆",
    "Enjoy the campus life! 🎀",
    "Explore the campus! 🎐",
    "Discover new places! 🎑",
    "Have fun! 🎄"
];

onMounted(() => {
    const ele = document.querySelector(".words");
    setInterval(() => {
        for (let i = 0; i < 1; i++) {
            const span = document.createElement("span");
            let tmp = Math.floor(Math.random() * 10);
            span.textContent = "🍀 " + sentences[tmp];
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
    }, 1000);
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
    left: -18%;
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

.words {
    border: 1px solid black;
    white-space: nowrap;
    overflow: hidden;
    display: inline-block;
}
</style>