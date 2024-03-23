<template>
  <div id="user-space">
    <div class="space">
      <div
        class="space-header"
        style="
          height: 280px;
          margin-left: 12%;
          margin-right: 1.5%;
          border: solid;
          border-color: azure;
        "
      >
        <div class="space-header-top" style="height: 70%">
          <img
            src="https://placehold.co/600x400?text=Background+Image"
            alt="background image"
            style="width: 100%; height: 100%; object-fit: cover"
          />
          <img
            :src="user.photo"
            alt="user photo"
            style="
              position: absolute;
              left: 13.5%;
              top: 23.5%;
              border-radius: 50px;
            "
          />
          <h2
            class="user-name"
            style="position: absolute; left: 20.5%; top: 22.5%"
          >
            {{ userName }}
          </h2>
          <p class="user-bio" style="position: absolute; left: 20.5%; top: 28%">
            {{ userBio }}
          </p>
        </div>

        <div class="space-header-bottom">
          <div class="space-header-bottom-item" id="posts-header">
            <h3 class="space-header-bottom-item-text" @click="disp('posts')">
              Posts
            </h3>
          </div>
          <div class="space-header-bottom-item" id="groups-header">
            <h3 class="space-header-bottom-item-text" @click="disp('groups')">
              Groups
            </h3>
          </div>
          <div class="space-header-bottom-item" id="friends-header">
            <h3 class="space-header-bottom-item-text" @click="disp('friends')">
              Friends
            </h3>
          </div>
        </div>
      </div>

      <div
        class="space-main"
        style="
          display: flex;
          height: auto;
          width: auto;
          margin-left: 12%;
          margin-right: 1.5%;
          margin-top: 1.5%;
        "
      >
        <div class="main-item" id="user-posts" v-if="param === 0">
          <div class="post" v-for="post in userPosts" :key="post.id" style="">
            <h3>{{ post.title }}</h3>
            <p>{{ post.content }}</p>
          </div>
        </div>
        <div class="main-item" id="user-groups" v-if="param === 1">
          <div class="group" v-for="group in userGroups" :key="group.id">
            <img :src="group.photo" alt="group photo" />
            <h3>{{ group.name }}</h3>
            <p>{{ group.description }}</p>
          </div>
        </div>
        <div class="main-item" id="user-friends" v-if="param === 2">
          <div class="friend" v-for="friend in userFriends" :key="friend.id">
            <img :src="friend.photo" alt="friend photo" />
            <h3>{{ friend.name }}</h3>
            <p>{{ friend.bio }}</p>
            <h1></h1>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";

const user = ref({});
const userName = ref("");
const userBio = ref("");
const userEmail = ref("");
const userPosts = ref([]);
const userGroups = ref([]);
const userFriends = ref([]);
const param = ref(0);

const disp = (e) => {
  document.querySelector("#posts-header").style.backgroundColor =
    "antiquewhite";
  document.querySelector("#groups-header").style.backgroundColor =
    "antiquewhite";
  document.querySelector("#friends-header").style.backgroundColor =
    "antiquewhite";

  if (e === "posts") {
    param.value = 0;
    document.querySelector("#posts-header").style.backgroundColor = "aliceblue";
  } else if (e === "groups") {
    param.value = 1;
    document.querySelector("#groups-header").style.backgroundColor =
      "aliceblue";
  } else if (e === "friends") {
    param.value = 2;
    document.querySelector("#friends-header").style.backgroundColor =
      "aliceblue";
  }
};

onMounted(async () => {
  // const response = await axios.get("http://localhost:8080/user");
  // user.value = response.data;
  // userName.value = response.data.name;
  // userBio.value = response.data.bio;
  // userEmail.value = response.data.email;
  // userPosts.value = response.data.posts;
  // userGroups.value = response.data.groups;
  user.value = {
    photo: "https://via.placeholder.com/75",
  };
  userName.value = "User Name";
  userBio.value = "Here is a short bio about the user.";
  userEmail.value = "user@email.com";
  userPosts.value = [
    {
      title: "Post 1",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Post 2",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Post 3",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Post 4",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Post 5",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Post 6",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Post 7",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Post 8",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Post 9",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
    {
      title: "Post 10",
      content:
        "Lorem ipsum, dolor sit amet consectetur adipisicing elit.Distinctio pariatur iusto accusamus, placeat ea sapiente quaerateum veritatis, fugiat, obcaecati saepe itaque sequi ipsam vel. Utvoluptatem ipsa in consectetur, qui similique aliquam labore vitaeporro voluptatum distinctio, optio ipsum tempore animi corrupti,dolor non natus illo iusto repellat vero.",
    },
  ];
  userGroups.value = [
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 1",
      description: "Lorem ipsum, dolor sit amet consectetur.",
    },
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 2",
      description: "Lorem ipsum, dolor sit amet consectetur.",
    },
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 3",
      description: "Lorem ipsum, dolor sit amet consectetur.",
    },
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 4",
      description: "Lorem ipsum, dolor sit amet consectetur adipisicing.",
    },
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 5",
      description: "Lorem ipsum, dolor sit amet consectetur adipisicing.",
    },
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 6",
      description: "Lorem ipsum, dolor sit amet consectetur adipisicing.",
    },
    {
      photo: "https://via.placeholder.com/150",
      name: "Group 7",
      description: "Lorem ipsum, dolor sit amet consectetur adipisicing.",
    },
  ];
  userFriends.value = [
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 1",
      bio: "This is the first friend.",
    },
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 2",
      bio: "This is the second friend.",
    },
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 3",
      bio: "This is the third friend.",
    },
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 4",
      bio: "This is the fourth friend.",
    },
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 5",
      bio: "This is the fifth friend.",
    },
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 6",
      bio: "This is the sixth friend.",
    },
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 7",
      bio: "This is the seventh friend.",
    },
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 8",
      bio: "This is the eighth friend.",
    },
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 9",
      bio: "This is the ninth friend.",
    },
    {
      photo: "https://via.placeholder.com/75",
      name: "Friend 10",
      bio: "This is the tenth friend.",
    },
  ];
});
</script>

<style scoped>
.space-header-bottom {
  display: flex;
  justify-content: space-around;
  height: 30%;
}
.space-header-bottom-item {
  display: block;
  position: relative;
  text-align: center;
  background-color: antiquewhite;
  width: 100%;
  height: 100%;
  border: solid;
  border-color: azure;
  border-width: 1px;
}
.space-header-bottom-item-text {
  position: absolute;
  top: 21%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: x-large;
}
.space-header-bottom-item-text:hover {
  cursor: pointer;
  color: rgb(107, 117, 77);
}
.main-item {
  display: flex;
  flex-wrap: wrap;
  justify-content: left;
  margin: 5px;
  width: 100%;
  height: 450px;
  border: solid;
}

.post {
  width: 14%;
  border: solid;
  border-color: azure;
  border-width: 2px;
  margin-left: 2.66667;
  padding: 10px;
}

.group {
  flex: 1 0 12%; /* explanation below */
  border: solid;
  border-color: azure;
  border-width: 2px;
  margin: 10px;
  padding: 10px;
}

.friend {
  width: 12%;
  height: 40%;
  border: solid;
  border-color: azure;
  border-width: 2px;
  margin: 10px;
  padding: 10px;
}
</style>
