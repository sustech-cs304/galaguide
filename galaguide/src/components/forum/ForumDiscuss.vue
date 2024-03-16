<script setup>
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
const router = useRouter();
const discussId = router.params.id;

const comments = ref([
    {id: 1, content: "Comment 1", sender: "User 1", time: "12:00"},
]);

onMounted(() => {
    console.log(discussId);
    axios.get(`/api/discuss/${discussId}`)
        .then(response => {
            console.log(response.data);
        })
        .catch(error => {
            console.error('Error fetching discussion:', error);
        });
});
</script>

<template>
    <div id="forum-discuss">
        <div id="discuss-title-top-bar">
            <h1>Discuss</h1>
            <div id="discuss-title">
                <h2>Discuss Title</h2>
            </div>
        </div>
        <div id="discuss-body">
            <div id="discuss-content">
                <div id="header-poster-time">
                    <p id="header-poster">Poster</p>
                    <p id="header-time">Time</p>
                </div>
                <p id="discuss-content">Content</p>
            </div>
            <div id="discuss-comments">
                <div v-for="comment in comments" :key="comment.id" class="comment">
                    <div class="comment-sender-time">
                        <p class="comment-sender">{{ comment.sender }}</p>
                        <p class="comment-time">{{ comment.time }}</p>
                    </div>
                    <p class="comment-content">{{ comment.content }}</p>
                </div>
            </div>
        </div>
        <div id="discuss-reply">
            <textarea id="reply-textarea" placeholder="Reply to this discussion"></textarea>
            <button id="reply-button">Reply</button>
        </div>
    </div>
</template>

<style>
#forum-discuss {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 100%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#discuss-title-top-bar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 10%;
    background-color: #23b375;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#discuss-title {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80%;
    height: 100%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#discuss-body {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 100%;
    height: 80%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: auto;
}

#header-poster-time {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 10%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#header-poster {
    font-size: 0.9em;
}

#header-time {
    font-size: 0.9em;
}

#discuss-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 80%;
    height: 40%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#discuss-comments {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 80%;
    height: 50%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

.comment {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 50%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#discuss-reply {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 10%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#reply-textarea {
    width: 80%;
    height: 100%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#reply-button {
    width: 10%;
    height: 100%;
    background-color: #23b375;
    color: white;
    cursor: pointer;
    font-family: "Montserrat", sans-serif;
    border: none;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

#reply-button:hover {
    background-color: #1f9e64;
}

.comment-sender-time {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 10%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}

.comment-sender {
    font-size: 0.9em;
}

.comment-time {
    font-size: 0.9em;
}

.comment-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 80%;
    height: 40%;
    background-color: white;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    overflow: hidden;
}
</style>