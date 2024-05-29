<template>
    <div id="add-new-user">
        <h1>Add New User</h1>
        <form @submit.prevent="submitForm" class="user-form">
            <div class="form-group">
                <label for="name">Name</label>
                <input v-model="name" type="text" id="name" placeholder="Enter full name" class="input-field" required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input v-model="email" type="email" id="email" placeholder="Enter email address" class="input-field"
                    required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input v-model="password" type="password" id="password" placeholder="Enter password" class="input-field"
                    required>
            </div>
            <div class="form-group">
                <label for="role">Role</label>
                <select v-model="role" id="role" class="input-field">
                    <option value="admin">Admin</option>
                    <option value="editor">Editor</option>
                    <option value="viewer">Viewer</option>
                </select>
            </div>
            <div class="form-group">
                <label for="status">Status</label>
                <select v-model="status" id="status" class="input-field">
                    <option value="active">Active</option>
                    <option value="inactive">Inactive</option>
                </select>
            </div>
            <div class="form-group">
                <label for="dob">Date of Birth</label>
                <input v-model="dob" type="date" id="dob" class="input-field">
            </div>
            <div class="form-group">
                <label for="phone">Phone Number</label>
                <input v-model="phone" type="tel" id="phone" placeholder="Enter phone number" class="input-field">
            </div>
            <div class="form-group">
                <label for="address">Address</label>
                <input v-model="address" type="text" id="address" placeholder="Enter address" class="input-field">
            </div>
            <button type="submit" class="submit-button">Create User</button>
        </form>
    </div>
</template>

<script setup>
import { ref } from 'vue';

const name = ref('');
const email = ref('');
const password = ref('');
const role = ref('viewer');
const status = ref('active');
const dob = ref('');
const phone = ref('');
const address = ref('');

const submitForm = () => {
    const newUser = {
        name: name.value,
        email: email.value,
        password: password.value,
        role: role.value,
        status: status.value,
        dob: dob.value,
        phone: phone.value,
        address: address.value,
    };

    alert(`User Created: ${JSON.stringify(newUser)}`);

    // Here you would typically send the newUser object to your backend server.

    // Clear the form after submission
    name.value = '';
    email.value = '';
    password.value = '';
    role.value = 'viewer';
    status.value = 'active';
    dob.value = '';
    phone.value = '';
    address.value = '';
};
</script>

<style scoped>
#add-new-user {
    /* font-family: 'Arial', sans-serif; */
    background-color: #f9f9f9;
    padding: 20px;
    max-width: 600px;
    margin: 0 auto;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    animation: fadeIn 1s ease-in-out;
    position: relative;
    left: 5%;
}

h1 {
    text-align: center;
    color: #333;
    margin-bottom: 40px;
    animation: slideDown 1s ease-in-out;
}

.user-form {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.form-group {
    display: flex;
    flex-direction: column;
}

.form-group label {
    font-weight: bold;
    color: #333;
    margin-bottom: 5px;
}

.input-field {
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 16px;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.input-field:focus {
    border-color: #007BFF;
    box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

.submit-button {
    padding: 15px 20px;
    background-color: #007BFF;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    transition: background-color 0.3s ease, transform 0.3s ease;
}

.submit-button:hover {
    background-color: #0056b3;
    transform: scale(1.05);
}

/* Animations */
@keyframes fadeIn {
    from {
        opacity: 0;
    }

    to {
        opacity: 1;
    }
}

@keyframes slideDown {
    from {
        transform: translateY(-20px);
        opacity: 0;
    }

    to {
        transform: translateY(0);
        opacity: 1;
    }
}</style>