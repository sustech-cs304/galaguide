<template>
    <div id="view-all-users">
        <h1>View All Users</h1>
        <div class="user-list">
            <div v-for="user in users" :key="user.id" class="user-card">
                <div class="user-summary" @click="toggleDetails(user.id)">
                    <div class="summary-header">
                        <h2>{{ user.name }}</h2>
                        <button class="toggle-button">{{ user.showDetails ? 'Collapse' : 'Expand' }}</button>
                    </div>
                    <p>
                        <img src="../../assets/email.png" alt="Email Icon" class="icon">
                        <span class="label">Email:</span> <span class="value">{{ user.email }}</span>
                    </p>
                    <p>
                        <img src="../../assets/status.png" alt="Status Icon" class="icon">
                        <span class="label">Status:</span> <span class="value">{{ user.status }}</span>
                    </p>
                </div>
                <transition name="fade">
                    <div v-if="user.showDetails" class="user-details">
                        <p>
                            <img src="../../assets/joined.png" alt="Joined Icon" class="icon">
                            <span class="label">Joined:</span> <span class="value">{{ user.joined }}</span>
                        </p>
                        <p>
                            <img src="../../assets/info.png" alt="Info Icon" class="icon">
                            <span class="label">Additional Info:</span> <span class="value">{{ user.info }}</span>
                        </p>
                        <button @click="showNotificationForm(user.id)" class="notify-button">Send Notification</button>
                        <transition name="slide-fade">
                            <div v-if="user.showNotificationForm" class="notification-form">
                                <input v-model="notificationTitle" type="text" placeholder="Title" class="input-field">
                                <textarea v-model="notificationContent" placeholder="Content" class="input-field"></textarea>
                                <button @click="sendNotification(user.id)" class="send-button">Send</button>
                            </div>
                        </transition>
                    </div>
                </transition>
            </div>
        </div>
    </div>
</template>
  


<script setup>
import { ref } from 'vue';

const users = ref([
  { id: 1, name: 'Alice', status: 'Active', email: 'alice@example.com', joined: '2023-01-01', info: 'Additional info about Alice', showDetails: false, showNotificationForm: false },
  { id: 2, name: 'Bob', status: 'Inactive', email: 'bob@example.com', joined: '2022-05-12', info: 'Additional info about Bob', showDetails: false, showNotificationForm: false },
  // Add more users as needed
]);

const notificationTitle = ref('');
const notificationContent = ref('');

const toggleDetails = (userId) => {
  const user = users.value.find((user) => user.id === userId);
  if (user) {
    user.showDetails = !user.showDetails;
    if (!user.showDetails) {
      user.showNotificationForm = false; // Hide notification form if collapsing details
    }
  }
};

const showNotificationForm = (userId) => {
  const user = users.value.find((user) => user.id === userId);
  if (user) {
    user.showNotificationForm = !user.showNotificationForm;
  }
};

const sendNotification = (userId) => {
  const user = users.value.find((user) => user.id === userId);
  if (user) {
    alert(`Notification sent to ${user.name} with title: "${notificationTitle.value}" and content: "${notificationContent.value}"`);
    // Clear the form fields after sending
    notificationTitle.value = '';
    notificationContent.value = '';
    user.showNotificationForm = false;
  }
};
</script>


<style scoped>
#view-all-users {
    /* font-family: 'Arial', sans-serif; */
    background-color: #f9f9f9;
    padding: 20px;
    max-width: 1200px;
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

.user-list {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: 20px;
}

.user-card {
    background-color: #ffffff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    animation: popIn 1s ease-in-out;
}

.user-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.user-summary {
    cursor: pointer;
}

.summary-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.toggle-button {
    background: none;
    border: none;
    color: #007BFF;
    cursor: pointer;
    font-weight: bold;
}

.user-details {
    margin-top: 20px;
    overflow: hidden;
    animation: fadeInDetails 0.5s ease;
}

.notify-button {
    padding: 10px 20px;
    background-color: #007BFF;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease, transform 0.3s ease;
}

.notify-button:hover {
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
}

@keyframes popIn {
    from {
        transform: scale(0.9);
        opacity: 0;
    }

    to {
        transform: scale(1);
        opacity: 1;
    }
}

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
}

@keyframes fadeInDetails {
    from {
        opacity: 0;
        transform: translateY(-10px);
    }

    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.user-card p {
    display: flex;
    align-items: center;
    margin: 10px 0;
}

.icon {
    width: 20px;
    height: 20px;
    margin-right: 10px;
}

.label {
    font-weight: bold;
    color: #333;
    margin-right: 5px;
}

.value {
    color: #666;
}

.notification-form {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.input-field {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
}

.send-button {
  padding: 10px 20px;
  background-color: #007BFF;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.send-button:hover {
  background-color: #0056b3;
  transform: scale(1.05);
}

.slide-fade-enter-active, .slide-fade-leave-active {
  transition: all 0.5s ease;
}

.slide-fade-enter-from, .slide-fade-leave-to {
  transform: translateY(-10px);
  opacity: 0;
}
</style>
