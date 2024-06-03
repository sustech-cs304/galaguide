<script setup>
import { onMounted, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import CustomAlert from "../../components/CustomAlert.vue";
const route = useRoute();
const discussId = route.params.id;
const textareaInput = ref("");
const router = useRouter();

const comments = ref([]);

const filteredComments = ref([]);

const relatedDiscussions = ref([
  {
    id: 1,
    title: "Animal Science Major advice?",
    content:
    "I'm a freshman and I'm thinking about majoring in Animal Science. I'm not sure if it's the right choice for me. Can anyone give me some advice?",
    posterName: "User 1",
    createTime: "12:00",
    likes: 1,
    tags: ["Animal Science", "Major Advice"],
  }
]);

// const getCookie = (name) => {
//   const value = `; ${document.cookie}`;
//   const parts = value.split(`; ${name}=`);
//   if (parts.length === 2) return parts.pop().split(";").shift();
// };

onMounted(() => {
  console.log(discussId);
  const token = localStorage.getItem("token");
  // if token exists, set the token in the axios headers
  if (token) {
    axios.defaults.headers.common["Bearer"] = token;
  }
  updateViewingHistory(discussId);
  getRelatedDiscussions();
  document.querySelector('#comment-form').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
      postComment();
    }
  });
  axios
    .get(`/api/discuss/${discussId}`)
    .then((response) => {
      console.log(response.data);
      comments.value = response.data.data;
      filteredComments.value = comments.value.slice(1);
    })
    .catch((error) => {
      console.error("Error fetching discussion:", error);
    });
});

const updateViewingHistory = (id) => {
  console.log("Updating viewing history");
  axios
    .post(`/api/discuss/history`, {
      discuss_id: id,
    })
    .then((response) => {
      console.log(response.data);
    })
    .catch((error) => {
      console.error("Error updating viewing history:", error);
    });
};

const getCookie = (name) => {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(";").shift();
};

const postComment = () => {
  console.log("Posting comment");
  axios
    .post(`/api/discuss/${discussId}/upload-reply`, {
      title: " ",
      content: textareaInput.value,
      posterId: getCookie("user_id"),
      time: new Date().toLocaleString()
    })
    .then((response) => {
      console.log(response.data);
      window.location.reload();
    })
    .catch((error) => {
      console.error("Error posting comment:", error);
    });
};

const likeComment = (id) => {
  console.log("Liking post");
  axios
    .post(`/api/discuss/${discussId}/like/${id}`)
    .then((response) => {
      console.log(response.data);
      // window.location.reload();
      if (response.status === 200 && response.data.code === 0) {
        comments.value[id].likes = response.data.data.likes;
        filteredComments.value = comments.value.slice(1);
      }
    })
    .catch((error) => {
      console.error("Error liking post:", error);
    });
};

const replyComment = (name) => {
  document.querySelector("#comment-form textarea").focus();
  textareaInput.value = `@${name} `;
};

const sortBy = (criteria) => {
  if (criteria === 'time') {
    sortedByTime.value *= -1;
    document.querySelector('#sort-buttons button:nth-child(1)').style.backgroundColor = 'rgb(254, 121, 73)';
    document.querySelector('#sort-buttons button:nth-child(2)').style.backgroundColor = 'grey';
  } else if (criteria === 'likes') {
    sortedByLikes.value *= -1;
    document.querySelector('#sort-buttons button:nth-child(2)').style.backgroundColor = 'rgb(254, 121, 73)';
    document.querySelector('#sort-buttons button:nth-child(1)').style.backgroundColor = 'grey';
  }
  filteredComments.value.sort((a, b) => {
    if (criteria === 'time') {
      if (sortedByTime.value === 1) {
        return new Date(a.time) - new Date(b.time);
      } else {
        return new Date(b.time) - new Date(a.time);
      }
    } else if (criteria === 'likes') {
      if (sortedByLikes.value === 1) {
        return (a.likes || 0) - (b.likes || 0);
      } else {
        return (b.likes || 0) - (a.likes || 0);
      }
    }
  });
};

const sortedByLikes = ref(1); // 1 for ascending, -1 for descending
const sortedByTime = ref(1); // 1 for ascending, -1 for descending

const getRelatedDiscussions = () => {
  console.log("Getting related discussions");
  axios
    .get(`/api/discuss/${discussId}/related`)
    .then((response) => {
      console.log(response.data);
      relatedDiscussions.value = response.data.data;
    })
    .catch((error) => {
      console.error("Error fetching related discussions:", error);
    });
};

const sharePost = () => {
  console.log("Sharing post");
  navigator.clipboard.writeText(window.location.href);
  showAlert.value = true;
  //alert("Link copied to clipboard!");
};

const showAlert = ref(false);

const editPost = () => {
  console.log("Editing post");
  router.push(`/forum/discuss/${discussId}/edit`);
};

const deletePost = () => {
  console.log("Deleting post");
  axios
    .delete(`/api/discuss/${discussId}`)
    .then((response) => {
      console.log(response.data);
      router.push("/forum");
    })
    .catch((error) => {
      console.error("Error deleting post:", error);
    });
};

const deleteComment = (id) => {
  console.log("Deleting comment");
  axios
    .delete(`/api/discuss/${discussId}/delete-reply/${id}`)
    .then((response) => {
      console.log(response.data);
      window.location.reload();
    })
    .catch((error) => {
      console.error("Error deleting comment:", error);
    });
};
</script>

<template>
  <div id="forum-discuss">
    <div id="discuss-header">
      <router-link to="/forum" id="back-button"></router-link>
      <div id="op-info">
        <img :src="`/api/user/${comments[0].sender_id}/avatar`" />
        <p id="op-name">{{ comments[0].sender_name }}</p>
        <p id="op-time">&nbsp;at&nbsp;{{ comments[0].time }}</p>
      </div>
      <div class="actions"> 
        <button style="background-color: rgb(253, 135, 155);" @click="likeComment(0)">
          Liked by {{ comments[0].likes }}
        </button>
        <button style="background-color: rgb(254, 121, 73);" @click="replyComment(comments[0].sender_name)">
          Comment
        </button>
        <button style="background-color: darkmagenta;" @click="sharePost">
          Share
        </button>
        <button v-if="comments[0].sender_id == localStorage.getItem('id')" style="background-color: rgb(0, 179, 255);" @click="editPost">
          Edit
        </button>
        <button v-if="comments[0].sender_id == localStorage.getItem('id')" style="background-color: rgb(0, 179, 255);" @click="deletePost">
          Delete
        </button>
      </div>
    </div>
    <div id="discuss-content">
      <h1>{{ comments[0].title }}</h1>
      <p>{{ comments[0].content }}</p>
    </div>
    <div id="comments-holder">
      <div id="sort-buttons">
        <button @click="sortBy('time')">
          Sort by Time
          <span v-if="sortedByTime === 1">▲</span>
          <span v-else>▼</span>
        </button>
        <button @click="sortBy('likes')">
          Sort by Likes
          <span v-if="sortedByLikes == 1">▲</span>
          <span v-else>▼</span>
        </button>
      </div>
      <div v-for="comment in filteredComments" :key="comment.id" class="comment">
        <div class="comment-info">
          <img :src="`/api/user/${comment.sender_id}/avatar`" />
          <p class="comment-sender">{{ comment.sender_name }}</p>
          <p class="comment-time">{{ comment.time }}</p>
        </div>
        <p class="comment-content">{{ comment.content }}</p>
        <div class="actions">
          <button @click="likeComment(comment.id)">
            Liked by {{ comment.likes }}
          </button>
          <button style="background-color: rgb(254, 121, 73);" @click="replyComment(comment.sender_name)">
            Reply
          </button>
          <button v-if="comment.sender_id == localStorage.getItem('id')" style="background-color: darkmagenta;" @click="deleteComment(comment.id)">
            Delete
          </button>
        </div>
      </div>
    </div>
    <div id="comment-form">
      <textarea placeholder="Write a comment" v-model="textareaInput" rows="4" cols="50"></textarea>
      <button @click="postComment">
        Post Comment
      </button>
    </div>
    <div id="discuss-right-bar">
      <h2>Community Guidelines</h2>
      <p>
        Please be respectful and courteous to others. Do not post any inappropriate content. If you see any inappropriate content, please report it.
      </p>
      <p>
        For full guidelines, please visit our <router-link to="/guidelines" style="color: rgb(0, 179, 255);">Community Guidelines</router-link>.
      </p>
      <h2>Tags</h2>
      <div class="discuss-tags">
        <span v-for="tag in comments[0].tags" :key="tag" class="discuss-tag">{{ tag }}</span>
      </div>
      <h2>Related Discussions</h2>
      <div class="related-discussions">
        <div v-for="discussion in relatedDiscussions" :key="discussion.id" class="related-discussion">
          <router-link :to="`/forum/discuss/${discussion.id}`">
            <h3>{{ discussion.title }}</h3>
          </router-link>
          <p>{{ discussion.content }}</p>
          <h4>{{ discussion.posterName }} at {{ discussion.createTime }}</h4>
        </div>
      </div>
      <button @click="getRelatedDiscussions" id="check-others">
        Check Others
      </button>
    </div>
    <CustomAlert 
      :visible="showAlert" 
      title="Congrats!" 
      message="Link copied to clipboard!" 
      @close="showAlert = false"
    />
  </div>
</template>

<style>
/*
* AI-generated-content
* tool: Copilot
* version: latest
* usage: CSS Modify
* weight: part of
*/
#forum-discuss {
  position: absolute;
  top: 3%;
  left: 10%;
  width: 90%;
  overflow: visible;
}

#discuss-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  /* background-color: rgb(251, 251, 251); */
}

#back-button {
  width: 50px;
  height: 50px;
  background-color: rgb(229, 229, 229);
  border-radius: 10%;
}

#back-button::before {
  content: "<";
  font-size: 30px;
}

#back-button:hover {
  cursor: pointer;
  background-color: rgb(234, 234, 234);
}

#op-info {
  display: flex;
  align-items: center;
}

#op-info img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

#op-name {
  margin-left: 10px;
  font-size: 200%;
}

#op-time {
  margin-left: 10px;
}

.actions button {
  margin-left: 10px;
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 10px 24px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  transition-duration: 0.4s;
  cursor: pointer;
  border-radius: 10px;
}

.actions button:hover {
  background-color: white;
  color: rgb(151, 185, 252);
  border: 2px solid #fafcfa;
}

#discuss-content {
  padding: 10px;
  background-color: rgb(251, 251, 251);
  position: relative;
  width: 60%;
  /*margin: auto;*/
  border-radius: 10px;
  left: 5%;
}

#comments-holder {
  background-color: white;
  position: relative;
  left: 5%;
  width: 60%;
  /*margin: auto;*/
  border-radius: 10px;
  padding: 10px;
  margin-top: 10px;
}

.comment {
  padding: 10px;
  background-color: rgb(251, 251, 251);
  margin-top: 10px;
  border-radius: 10px;
}

.comment-info {
  display: flex;
  align-items: center;
}

.comment-info img {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.comment-sender {
  margin-left: 10px;
  font-size: 150%;
}

.comment-time {
  margin-left: 10px;
}

.comment-content {
  padding: 10px;
  background-color: rgb(251, 251, 251);
}

#comment-form {
  background-color: rgb(251, 251, 251);
  position: relative;
  left: 5%;
  width: 60%;
  /* margin: auto; */
  border-radius: 10px;
  padding: 10px;
  margin-top: 10px;
}

#comment-form textarea {
  width: 90%;
  height: 100px;
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 10px;
  font-family: Monteserrat;
}

#comment-form button {
  background-color: #4ca2af;
  border: none;
  color: white;
  padding: 10px 24px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  transition-duration: 0.4s;
  cursor: pointer;
  border-radius: 10px;
}

#comment-form button:hover {
  background-color: white;
  color: rgb(151, 185, 252);
  border: 2px solid #fafcfa;
}

#sort-buttons {
  display: flex;
  justify-content: space-between;
}

#sort-buttons button {
  background-color: #4CAF50;
  border: none;
  color: white;
  padding: 10px 24px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  transition-duration: 0.4s;
  cursor: pointer;
  border-radius: 10px;
}

#sort-buttons button:hover {
  background-color: white;
  color: rgb(151, 185, 252);
  /*border: 2px solid #fafcfa;*/
}

#discuss-right-bar {
  position: absolute;
  top: 15%;
  right: 10px;
  width: 30%;
  background-color: rgb(251, 251, 251);
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 10px;
}

.related-discussion {
  padding: 10px;
  background-color: rgb(235, 235, 235);
  margin-top: 10px;
  border-radius: 10px;
}

.related-discussion:hover {
  background-color: rgb(245, 245, 245);
  border: 1px solid rgb(235, 235, 235);
}

.discuss-tags {
  display: flex;
  flex-wrap: wrap;
}

.discuss-tag {
  padding: 5px;
  background-color: rgb(159, 199, 90);
  margin: 5px;
  border-radius: 10px;
}

.discuss-tag:hover {
  background-color: rgb(92, 218, 132);
  border: 1px solid rgb(66, 176, 86);
}

#check-others {
  background-color: #479fb3;
  border: none;
  color: white;
  padding: 10px 24px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 8px 4px;
  transition-duration: 0.4s;
  cursor: pointer;
  border-radius: 10px;
}

#check-others:hover {
  background-color: white;
  color: rgb(151, 185, 252);
  border: 2px solid #fafcfa;
}
</style>
