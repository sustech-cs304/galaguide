<script setup>
/* 
 * Home page for the admin console.
 * 
 * Main features:
 * 
 * - User management
 * - Image management
 * - Event management
 * - Forum management
 * - Chat room management
 * 
 */
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();

onMounted(() => {
    const userRole = localStorage.getItem('userRole');
    console.log(userRole);
    console.log(router);
    // if (userRole < 1) router.push('/');
});
</script>

<template>
    <div id="admin-console">
        <h1>Admin Console</h1>
        <div id="admin-console-menu">
            <div class="admin-console-option" id="option-user-management">
                <h2>User Management</h2>
                <svg
                    xmlns="http://www.w3.org/2000/svg"
                    width="50"
                    height="50"
                    fill="currentColor"
                    class="bi bi-person-circle"
                    viewBox="-2 -2 20 20"
                >
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
                    <path
                    fill-rule="evenodd"
                    d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"
                    />
                </svg>
                <div class="link-container">
                    <router-link class="router-link" to="/admin/users">
                        <img src="../../assets/arrow_right.png" alt="" style="width: 15px; height: 15px;" />
                        View all users
                    </router-link>
                    <router-link class="router-link" to="/admin/users/add">
                        <img src="../../assets/arrow_right.png" alt="" style="width: 15px; height: 15px;" />
                        Add a user
                    </router-link>
                </div>
            </div>
            <div class="admin-console-option" id="option-image-management">
                <h2>Image Management</h2>
                <img src="../../assets/image-host.png" alt="Image host" style="width: 50px; height: 50px;" />
                <div class="link-container">
                    <router-link class="router-link" to="/admin/images">
                        <img src="../../assets/arrow_right.png" alt="" style="width: 15px; height: 15px;" />
                        View all images
                    </router-link>
                </div>
            </div>
            <div class="admin-console-option" id="option-event-management">
                <h2>Event Management</h2>
                <img src="../../assets/events.png" alt="Event" style="width: 50px; height: 50px;" />
                <div class="link-container">
                    <router-link class="router-link" to="/admin/events">
                        <img src="../../assets/arrow_right.png" alt="" style="width: 15px; height: 15px;" />
                        View all events
                    </router-link>
                    <router-link class="router-link" to="/admin/events/requests">
                        <img src="../../assets/arrow_right.png" alt="" style="width: 15px; height: 15px;" />
                        View active requests
                    </router-link>
                </div>
            </div>
            <div class="admin-console-option" id="option-forum-management">
                <h2>Forum Management</h2>
                <img src="../../assets/forum.png" alt="Forum" style="width: 50px; height: 50px;" />
                <div class="link-container">
                    <router-link class="router-link" to="/forum">
                        <img src="../../assets/arrow_right.png" alt="" style="width: 15px; height: 15px;" />
                        View all posts
                    </router-link>
                </div>
            </div>
            <div class="admin-console-option" id="option-chat-room-management">
                <h2>Chat Room Management</h2>
                <img src="../../assets/chat.png" alt="Chat" style="width: 50px; height: 50px;" />
                <div class="link-container">
                    <router-link class="router-link" to="/admin/chat">
                        <img src="../../assets/arrow_right.png" alt="" style="width: 15px; height: 15px;" />
                        View all rooms
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>


<style scoped>
#admin-console {
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

#admin-console-menu {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 20px;
}

.admin-console-option {
    background-color: #ffffff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    animation: popIn 1s ease-in-out;
}

.admin-console-option:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.admin-console-option h2 {
    margin-top: 0;
    color: #007BFF;
}

.link-container {
    display: flex;
    flex-direction: column;
}

.router-link {
    font-size: 120%;
    margin: 10px 0;
    color: #0faf77;
    text-decoration: none;
    font-weight: bold;
    transition: color 0.3s ease, transform 0.3s ease;
}

.router-link:hover {
    color: #00ff6a;
    transform: translateX(10px);
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
</style>
