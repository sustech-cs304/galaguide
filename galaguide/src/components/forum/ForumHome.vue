<script setup>
import { onMounted, ref } from "vue";

onMounted(() => {
  console.log("Forum Home Page");
});

const posts = ref([
  {
    id: 1,
    title: "Animal Science Major advice?",
    content:
      "I'm a freshman and I'm thinking about majoring in Animal Science. I'm not sure if it's the right choice for me. Can anyone give me some advice?",
    sender: "User 1",
    time: "12:00",
    likes: 1,
    tags: ["Animal Science", "Major Advice"],
  },
  {
    id: 2,
    title: "How to get into the College of Engineering?",
    content:
      "I'm a high school student and I'm interested in majoring in Computer Science. I'm not sure how to get into the College of Engineering. Can anyone give me some advice?",
    sender: "User 2",
    time: "12:00",
    likes: 2,
    tags: ["College of Engineering", "Computer Science"],
  },
  {
    id: 3,
    title: "SUSTech or PKU?",
    content:
      "So SUSTech and PKU both accepted me. I'm not sure which one to choose. Can anyone give me some advice?",
    sender: "User 3",
    time: "12:10",
    likes: 3,
    tags: ["SUSTech", "PKU"],
  },
  {
    id: 4,
    title: "What GRE scores do I need to get into the MSME program?",
    content:
      "Hello, to get into the Masters of Science in Mechanical Engineering program, what GRE scores do I need?",
    sender: "User 4",
    time: "12:20",
    likes: 4,
    tags: ["GRE", "MSME"],
  },
  {
    id: 5,
    title: "How to get into the College of Engineering?",
    content:
      "I'm a high school student and I'm interested in majoring in Computer Science. I'm not sure how to get into the College of Engineering. Can anyone give me some advice?",
    sender: "User 2",
    time: "12:00",
    likes: 5,
    tags: ["College of Engineering", "Computer Science"],
  },
  {
    id: 6,
    title: "SUSTech or PKU?",
    content:
      "So SUSTech and PKU both accepted me. I'm not sure which one to choose. Can anyone give me some advice?",
    sender: "User 3",
    time: "12:10",
    likes: 6,
    tags: ["SUSTech", "PKU"],
  },
  {
    id: 7,
    title: "What GRE scores do I need to get into the MSME program?",
    content:
      "Hello, to get into the Masters of Science in Mechanical Engineering program, what GRE scores do I need?",
    sender: "User 4",
    time: "12:20",
    likes: 7,
    tags: ["GRE", "MSME"],
  },
]);
</script>

<template>
  <div id="forum-home">
    <h1>Recent Posts</h1>
    <div id="posts-filter">
      <input type="text" placeholder="Search Posts" />
      <button>Search</button>
      <button>Advanced Search</button>
      <div id="advanced-search"></div>
    </div>
    <div id="posts-holder">
      <div v-for="post in posts" :key="post.id" class="post">
        <div class="post-title-time">
          <router-link to="/forum/discuss/" style="width: 100%">
            <p class="post-title">{{ post.title }}</p>
          </router-link>
          <p class="post-time">{{ post.time }}</p>
        </div>
        <p class="post-content">{{ post.content }}</p>
        <p class="post-sender">Posted by {{ post.sender }}</p>
        <!-- Likes -->
        <div class="post-likes">
          <button>Like</button>
          <p>{{ post.likes }} Likes</p>
        </div>
        <!-- Tags -->
        <div class="post-tags">
          <p>Tags:</p>
          <div v-for="tag in post.tags" :key="tag" class="tag">
            <p>{{ tag }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
#forum-home {
  display: flex;
  flex-direction: column;
  margin-left: 11%;
  align-items: center;
  width: 88%;
  height: 100%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

#posts-holder {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: white;
  overflow: auto;
  margin-bottom: 1%;
}

.post {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  height: auto;
  margin-top: 1%;
  padding: 1%;
  background-color: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  border-radius: 10px;
}

.post-title-time {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  height: auto;
  background-color: rgb(241, 245, 206);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  border-radius: 10px;
}

.post-title {
  font-size: 150%;
  font-weight: bold;
  margin-left: 1%;
}

.post-time {
  font-size: 100%;
  margin-right: 1%;
}

.post-content {
  font-size: 100%;
  background-color: rgb(207, 245, 206);
}

.post-sender {
  font-size: 100%;
}

/* .post:hover {
    background-color: #f0f0f0;
} */

.post-title-time:hover {
  background-color: #cbe091;
}

#posts-filter input {
  width: 50%;
  height: 30px;
  margin-top: 1%;
  margin-bottom: 1%;
  border-radius: 10px;
}

#posts-filter button {
  width: max-content;
  height: 30px;
  margin-top: 1%;
  margin-bottom: 1%;
  border-radius: 10px;
  background-color: #cbe091;
}

#posts-filter button:hover {
  background-color: #a5adba;
}

#advanced-search {
  display: none;
}

.post-likes {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: auto;
  overflow: hidden;
  border-radius: 10px;
}

.post-likes button {
  height: 30px;
  margin-right: 1%;
  background-color: #cbe091;
  border-radius: 10px;
}

.post-likes button:hover {
  background-color: #a5adba;
}

.post-tags {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: auto;
  overflow: hidden;
  border-radius: 10px;
}

.tag {
  display: flex;
  justify-content: center;
  align-items: center;
  width: auto;
  height: auto;
  background-color: #f7f1a9;
  border: 1px solid black;
  border-radius: 10px;
  margin-left: 1%;
}

.tag p {
  font-size: 100%;
  margin-left: 1%;
  margin-right: 1%;
}

.tag:hover {
  background-color: #6fd7ac;
}
</style>
