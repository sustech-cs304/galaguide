<template>
    <!-- generate a form that contains name, email, intro, avatar, background image, password that can be revised -->
    <div class="user-info-revision">
        <div class="user-info-revision__title">User Info Revision</div>
        <form class="user-info-revision__form">
            <div class="user-info-revision__form-item">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" :placeholder="user.userName" minlength="4" maxlength="32" v-model="revisedUser.userName" />
            </div>
            <div class="user-info-revision__form-item">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" :placeholder="user.userEmail" minlength="6" maxlength="128" v-model="revisedUser.userEmail" />
            </div>
            <div class="user-info-revision__form-item">
                <label for="intro">Intro</label>
                <textarea id="intro" name="intro" :placeholder="user.userIntro" v-model="revisedUser.userIntro"></textarea>
            </div>
            <div class="user-info-revision__form-item">
                <label for="avatar">Avatar</label>
                <input type="file" id="avatar" name="avatar" />
            </div>
            <div class="user-info-revision__form-item">
                <label for="background">Background Image</label>
                <input type="file" id="background" name="background" />
            </div>
            <button type="submit" @click="revise" class="user-info-revision__form-submit">Submit</button>
            <!-- add link to revise password -->
            <p class="user-password-revision__link" @click="showPasswordForm">Revise Password</p>
            <!-- add revise password form -->
        </form>
        <div class="user-info-revision__form-item--password" @keydown="revisePassword">
            <label for="oldPassword">Old Password</label>
            <input type="password" id="oldPassword" name="oldPassword" v-model="oldPassword" placeholder="Old Password" required />
            <label for="newPassword">New Password</label>
            <input type="password" id="newPassword" name="newPassword" v-model="newPassword" placeholder="New Password" required />
            <label for="confirmPassword">Confirm Password</label>
            <input type="password" id="confirmPassword" name="confirmPassword" v-model="confirmPassword" placeholder="Confirm Password" required />
            <button type="submit" @click="revisePassword">Revise Password</button>
        </div>
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

const revisedUser = ref({
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

const oldPassword = ref("");
const newPassword = ref("");
const confirmPassword = ref("");

const router = useRouter();

function showPasswordForm() {
    document.querySelector(".user-info-revision__form").style.display = "none";
    document.querySelector(".user-info-revision__form-item--password").style.display = "flex";
    document.querySelector(".user-info-revision__form-item--password").style.flexDirection = "column";
    document.querySelector(".user-info-revision__form-item--password").style.alignItems = "center";
    document.querySelector(".user-info-revision__form-item--password").style.justifyContent = "center";
    document.querySelector(".user-info-revision__form-item--password").style.marginBottom = "10px";
}

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

function revisePassword() {
    if (!checkPasswordValidity(oldPassword.value)) {
        alert("Old password is invalid");
        router.push("/userInfoRevision");
        return;
    }
    if (!checkPasswordValidity(newPassword.value)) {
        alert("New password is invalid");
        router.push("/userInfoRevision");
        return;
    }
    if (newPassword.value !== confirmPassword.value) {
        alert("Passwords do not match");
        router.push("/userInfoRevision");
        return;
    }

    console.log("old:", oldPassword.value);
    console.log("new:", newPassword.value);

    axios
        .post('/api/user/change-password', {
            old: oldPassword.value,
            new: newPassword.value
        }, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        })
        .then((response) => {
            console.log("response:", response);
            if (response.status === 200 && response.data.code === 0) {
                console.log("Password revised successfully.");
            }
            router.push("/space");
        })
        .catch((error) => {
            console.log("error:", error);
        });
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
    if (!checkNameValidity(revisedUser.value.userName)) {
        alert("Invalid name.");
        router.push("/userInfoRevision");
        return;
    }
    if (!checkEmailValidity(revisedUser.value.userEmail)) {
        alert("Invalid email.");
        router.push("/userInfoRevision");
        return;
    }
    if (!checkAvatarValidity(revisedUser.value.userAvatarId)) {
        alert("Invalid avatar link.");
        router.push("/userInfoRevision");
        return;
    }
    if (!checkBackgroundValidity(revisedUser.value.userBackgroundId)) {
        alert("Invalid background image link.");
        router.push("/userInfoRevision");
        return;
    }

    axios
        .post('/api/user/edit', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            },
            data: {
                name: revisedUser.value.userName,
                email: revisedUser.value.userEmail,
                intro: revisedUser.value.userIntro,
                avatarId: revisedUser.value.userAvatarId,
                backgroundId: revisedUser.value.userBackgroundId
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
.user-info-revision {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
    margin-bottom: 20px;
    background-color: #f8f9fa;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 500px;
    margin-left: auto;
    margin-right: auto;
}

.user-info-revision__title {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 20px;
}

.user-info-revision__form {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
}

.user-info-revision__form-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-bottom: 10px;
}

.user-info-revision__form-item label {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 5px;
}

.user-info-revision__form-item input {
    width: 300px;
    height: 30px;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.user-info-revision__form-item textarea {
    width: 300px;
    height: 100px;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
    resize: none;
}

.user-info-revision__form-submit {
    width: 100px;
    height: 40px;
    margin-top: 20px;
    background-color: #007bff;
    color: white;
    font-size: 16px;
    font-weight: bold;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.user-info-revision__form-submit:hover {
    background-color: #0056b3;
}

.user-info-revision__form-item--password {
    display: none;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    margin-bottom: 10px;
}

.user-info-revision__form-item--password label {
    margin-top: 20px;
}

.user-info-revision__form-item--password input {
    width: 300px;
    height: 30px;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.user-info-revision__form-item--password button {
    width: 150px;
    height: 40px;
    margin-top: 20px;
    background-color: #007bff;
    color: white;
    font-size: 16px;
    font-weight: bold;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.user-info-revision__form-item--password button:hover {
    background-color: #0056b3;
}

.user-password-revision__link {
    margin-top: 20px;
    cursor: pointer;
}

.user-password-revision__link:hover {
    color: #0056b3;
}

</style>