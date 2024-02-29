<template>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h3>Login</h3>
                <form @submit="check">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" v-model="user.name">
                    </div>
                    <div class="form-group">
                        <label>Mail</label>
                        <input type="text" class="form-control" v-model="user.email">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="text" class="form-control" v-model="user.password">
                    </div>
                    <button type="submit" class="btn btn-primary" v-on:click="login()">Submit</button>
                    <router-link to="/register" class="btn btn-link">Register</router-link>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>
import UserService from "../../service/UserService";
import { ref } from "vue";

const user = ref({
    name: "",
    email: "",
    password: ""
});

const errors = ref([]);

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
            if (user.value.password === password) {
                alert("Login successful");
                console.log("Login successful");
            } else {
                alert("Invalid credentials");
                console.log("Invalid credentials");
                errors.value.push("Invalid credentials");
            }
        }
    });
};
</script>

<style scoped>
</style>