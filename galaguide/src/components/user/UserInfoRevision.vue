<template>
    <!-- generate a form that contains name, email, intro, avatar, background image, password that can be revised -->
    <div class="user-info-revision">
        <div class="user-info-revision__title">User Info Revision</div>
        <form class="user-info-revision__form">
            <div class="user-info-revision__form-item">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" :placeholder="user.userName" minlength="4" maxlength="32" />
            </div>
            <div class="user-info-revision__form-item">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" :placeholder="user.userEmail" minlength="6" maxlength="128"/>
            </div>
            <div class="user-info-revision__form-item">
                <label for="intro">Intro</label>
                <textarea id="intro" name="intro" :placeholder="user.userIntro"></textarea>
            </div>
            <div class="user-info-revision__form-item">
                <label for="avatar">Avatar</label>
                <input type="file" id="avatar" name="avatar" />
            </div>
            <div class="user-info-revision__form-item">
                <label for="background">Background Image</label>
                <input type="file" id="background" name="background" />
            </div>
            <div class="user-info-revision__form-item">
                <label for="password">Password</label>
                <input type="password" id="password" name="password"  minlength="6" maxlength="128"/>
            </div>
            <button type="submit" @click="revise" class="user-info-revision__form-submit">Submit</button>
        </form>
    </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const user = ref({
    userAvatarId: "",
    userName: "",
    userEmail: "",
    userIntro: "",
    userFavoriteEvents: [],
    userBrowsedEvents: [],
    userSubscribedEvents: [],
    userGuiro: -1,
    userBackgroundId: "",
});

const router = useRouter();

function checkNameValidity(name) {
    return name.length > 0 && name.length <= 32;
}

function checkEmailValidity(email) {
    return email.includes("@") && email.length <= 128;
}

function checkPasswordValidity(password) {
    return password.length >= 6 && password.length <= 128;
}

function checkAvatarValidity(avatarLink) {
    return avatarLink.length > 0;
}

function checkBackgroundValidity(backgroundLink) {
    return backgroundLink.length > 0;
}

onMounted(async () => {
    axios
        .get('/api/user', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        })
        .then((response) => {
            console.log("response:", response);
            if (response.status === 200 && response.data.code === 0) {
                user.value.userName = response.data.data.name === "" ? "User" : response.data.data.name;
                user.value.userAvatarId = response.data.data.avatarId === null ? "https://via.placeholder.com/100" : response.data.data.avatarId;
                user.value.userBackgroundId = response.data.data.backgroundId === "" ? "https://placehold.co/600x400?text=Background+Image" : response.data.data.backgroundId;
                user.value.userEmail = response.data.data.email === "" ? "123@gmail.com" : response.data.data.email;
                user.value.userIntro = response.data.data.intro === "" ? "This is a user intro." : response.data.data.intro;
            }
        })
        .catch((error) => {
            console.log("error:", error);
        });
});

const revise = () => {
    if (!checkNameValidity(user.value.userName)) {
        alert("Invalid name.");
        return;
    }
    if (!checkEmailValidity(user.value.userEmail)) {
        alert("Invalid email.");
        return;
    }
    if (!checkPasswordValidity(user.value.password)) {
        alert("Invalid password.");
        return;
    }
    if (!checkAvatarValidity(user.value.userAvatarId)) {
        alert("Invalid avatar link.");
        return;
    }
    if (!checkBackgroundValidity(user.value.userBackgroundId)) {
        alert("Invalid background image link.");
        return;
    }

    axios
        .post('/api/user', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            data: {
                name: user.value.userName,
                email: user.value.userEmail,
                intro: user.value.userIntro,
                avatarId: user.value.userAvatarId,
                backgroundId: user.value.userBackgroundId,
                password: user.value.password
            }
        })
        .then((response) => {
            console.log("response:", response);
            if (response.status === 200 && response.data.code === 0) {
                console.log("User info revised successfully.");
            }
            router.push("/space");
        })
        .catch((error) => {
            console.log("error:", error);
        });
    
};
</script>

<style scoped>


</style>