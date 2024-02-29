<template>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h3>Register</h3>
                <form @submit="validateAndSubmit">
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
                    <button type="submit" class="btn btn-success">Submit</button>
                    <a href="/login" class="btn btn-link">Login</a>
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


</script>